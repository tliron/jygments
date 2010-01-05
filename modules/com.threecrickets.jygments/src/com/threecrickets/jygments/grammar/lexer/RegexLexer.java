/**
 * Copyright 2010 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://www.threecrickets.com/
 */

package com.threecrickets.jygments.grammar.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;

import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Rule;
import com.threecrickets.jygments.grammar.State;
import com.threecrickets.jygments.grammar.Token;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;

/**
 * @author Tal Liron
 */
public class RegexLexer extends Lexer
{
	// //////////////////////////////////////////////////////////////////////////
	// Protected

	@Override
	public Iterable<Token> getTokensUnprocessed( String text )
	{
		List<Token> tokens = new ArrayList<Token>();

		// Start at root state
		Queue<State> stateStack = new LinkedList<State>();
		State state = getState( "root" );
		stateStack.add( state );

		int pos = 0;
		while( pos < text.length() - 1 )
		{
			boolean matches = false;

			// Does any rule in the current state match?
			// System.out.println("Text: " + text.substring( pos ));
			for( Rule rule : state.getRules() )
			{
				// System.out.println("Trying pattern: " +
				// rule.pattern.pattern());
				Matcher matcher = rule.getPattern().matcher( text );
				int eol = text.indexOf( '\n', pos );
				matcher.region( pos, eol );
				if( matcher.lookingAt() )
				{
					// System.out.println( "Match! " + matcher.group() + " " +
					// rule );

					// Yes, so apply it!
					State nextState = null;
					int depth = 0;
					if( rule instanceof TokenRule )
					{
						TokenRule tokenRule = (TokenRule) rule;
						nextState = tokenRule.getNextState();
						depth = tokenRule.getDepth();
						tokens.add( new Token( pos, tokenRule.getTokenType(), matcher.group() ) );
					}

					// Change state
					if( nextState != null )
					{
						if( nextState == State.Pop )
						{
							while( depth-- > 0 )
								stateStack.remove();
						}
						else if( nextState == State.Push )
						{
							stateStack.add( state );
						}
						else if( nextState != null )
						{
							state = nextState;
						}
					}

					pos = matcher.end();
					// System.out.println( pos );
					matches = true;
					break;
				}
			}

			if( !matches )
			{
				// System.err.println(text.substring( pos ));
				// pos = text.indexOf( '\n', pos );
				// if( pos == -1 )
				// pos = text.length() - 1;
				// if( text.substring( pos ).endsWith( "\n" ) )
				{
					// At EOL, reset state to "root"
					stateStack.clear();
					state = getState( "root" );
					stateStack.add( state );
					tokens.add( new Token( pos, TokenType.Text, "\n" ) );
				}
				pos += 1;
			}
		}

		return tokens;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addJson( Map<String, Object> json ) throws ResolutionException
	{
		super.addJson( json );

		// Initialize patterns
		Object patternsObject = json.get( "patterns" );
		Map<String, String> patterns = new HashMap<String, String>();
		if( patternsObject != null )
		{
			if( !( patternsObject instanceof Map<?, ?> ) )
				throw new ResolutionException( "\"patterns\" must be a map" );
			for( Map.Entry<String, Object> entry : ( (Map<String, Object>) patternsObject ).entrySet() )
			{
				String patternName = entry.getKey();
				Object patternObject = entry.getValue();
				if( patternObject instanceof Iterable<?> )
				{
					StringBuilder pattern = new StringBuilder();
					for( String patternElement : (Iterable<String>) patternObject )
						pattern.append( patternElement );
					patterns.put( patternName, pattern.toString() );
				}
				else if( patternObject instanceof String )
					patterns.put( patternName, (String) patternObject );
				else
					throw new ResolutionException( "Unexpected value in \"patterns\" map: " + patternObject );
			}
		}

		Object statesObject = json.get( "states" );
		if( statesObject == null )
			throw new ResolutionException( "Grammar does not contain \"states\" map" );
		if( !( statesObject instanceof Map<?, ?> ) )
			throw new ResolutionException( "\"states\" must be a map" );
		for( Map.Entry<String, Object> entry : ( (Map<String, Object>) statesObject ).entrySet() )
		{
			String stateName = entry.getKey();
			Object stateObject = entry.getValue();
			if( !( stateObject instanceof Map<?, ?> ) )
				throw new ResolutionException( "State \"" + stateName + "\" must be a map" );
			for( Map.Entry<String, Object> stateEntry : ( (Map<String, Object>) stateObject ).entrySet() )
			{
				String command = stateEntry.getKey();
				Object arguments = stateEntry.getValue();
				if( command.equals( "#include" ) )
				{
					if( !( arguments instanceof String ) )
						throw new ResolutionException( "\"#include\" command in state \"" + stateName + "\" must have a string as an argument" );
					include( stateName, (String) arguments );
				}
				else
				{
					// Use a pattern
					if( command.startsWith( "#" ) )
						command = patterns.get( command.substring( 1 ) );

					// Command is a pattern
					if( arguments instanceof Iterable<?> )
					{
						List<String> argumentsList = new ArrayList<String>();
						for( String argument : (Iterable<String>) arguments )
							argumentsList.add( argument );

						if( argumentsList.size() == 1 )
							rule( stateName, command, argumentsList.get( 0 ) );
						else if( argumentsList.size() == 2 )
							rule( stateName, command, argumentsList.get( 0 ), argumentsList.get( 1 ) );
						else
							throw new ResolutionException( "Too many arguments in state \"" + stateName + "\"" );
					}
					else if( arguments instanceof String )
					{
						rule( stateName, command, (String) arguments );
					}
					else
						throw new ResolutionException( "Unexpected argument in state \"" + stateName + "\": " + arguments );
				}
			}
		}
	}
}
