/**
 * Copyright 2010 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
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
import com.threecrickets.jygments.grammar.RelativeState;
import com.threecrickets.jygments.grammar.Rule;
import com.threecrickets.jygments.grammar.State;
import com.threecrickets.jygments.grammar.Token;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;
import com.threecrickets.jygments.grammar.def.ChangeStateTokenRuleDef;
import com.threecrickets.jygments.grammar.def.TokenRuleDef;

/**
 * @author Tal Liron
 */
public class RegexLexer extends Lexer
{
	// //////////////////////////////////////////////////////////////////////////
	// Protected

	@Override
	public List<Token> getTokensUnprocessed( String text )
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
					if( rule instanceof TokenRule )
					{
						TokenRule tokenRule = (TokenRule) rule;
						if( tokenRule.getNextStates() != null )
						{
							for( State aNextState : tokenRule.getNextStates() )
							{
								if( nextState == null )
									nextState = aNextState;
								else
									// Combine states
									nextState = new State( nextState, aNextState );
							}
						}

						List<TokenType> tokenTypes = tokenRule.getTokenTypes();
						if( tokenTypes.size() == 1 )
							// Single token
							tokens.add( new Token( pos, tokenTypes.get( 0 ), matcher.group() ) );
						else
						{
							if( tokenTypes.size() != matcher.groupCount() )
								throw new RuntimeException( "The number of token types in the rule does not match the number of groups in the regular expression" );

							// Multiple tokens by group
							int group = 1;
							for( TokenType tokenType : tokenTypes )
								tokens.add( new Token( pos, tokenType, matcher.group( group++ ) ) );
						}
					}

					// Change state
					if( nextState instanceof RelativeState )
					{
						// Push or pop
						RelativeState relativeState = (RelativeState) nextState;
						if( relativeState.isPush() )
							stateStack.add( state );
						else
							for( int depth = relativeState.getDepth(); depth > 0; depth-- )
								stateStack.remove();
					}
					else if( nextState != null )
						state = nextState;

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
				if( patternObject instanceof List<?> )
				{
					StringBuilder pattern = new StringBuilder();
					for( String patternElement : (List<String>) patternObject )
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
					// Command is a pattern
					String pattern = command;

					if( pattern.startsWith( "#" ) && patterns.containsKey( pattern.substring( 1 ) ) )
						// Use a predefined pattern
						pattern = patterns.get( pattern.substring( 1 ) );

					if( arguments instanceof List<?> )
					{
						List<Object> argumentsList = new ArrayList<Object>();
						for( Object argument : (List<Object>) arguments )
							argumentsList.add( argument );

						Object tokenTypeNames = argumentsList.get( 0 );

						if( argumentsList.size() == 1 )
						{
							if( tokenTypeNames instanceof String )
							{
								ArrayList<String> list = new ArrayList<String>( 1 );
								list.add( (String) tokenTypeNames );
								tokenTypeNames = list;
							}

							if( !( tokenTypeNames instanceof List<?> ) )
								throw new ResolutionException( "Expected token type name or array of token type names in rule in state \"" + stateName + "\"" );

							getState( stateName ).addDef( new TokenRuleDef( stateName, pattern, (List<String>) tokenTypeNames ) );
						}
						else if( argumentsList.size() == 2 )
						{
							Object nextStateNames = argumentsList.get( 1 );
							if( nextStateNames instanceof String )
							{
								ArrayList<String> list = new ArrayList<String>( 1 );
								list.add( (String) nextStateNames );
								nextStateNames = list;
							}

							if( !( nextStateNames instanceof List<?> ) )
								throw new ResolutionException( "Expected state name or array of state names in rule in state \"" + stateName + "\"" );

							if( tokenTypeNames instanceof String )
							{
								ArrayList<String> list = new ArrayList<String>( 1 );
								list.add( (String) tokenTypeNames );
								tokenTypeNames = list;
							}

							if( !( tokenTypeNames instanceof List<?> ) )
								throw new ResolutionException( "Expected token type name or array of token type names in rule in state \"" + stateName + "\"" );

							getState( stateName ).addDef( new ChangeStateTokenRuleDef( stateName, pattern, (List<String>) tokenTypeNames, (List<String>) nextStateNames ) );
						}
						else
							throw new ResolutionException( "Too many arguments for rule in state \"" + stateName + "\"" );
					}
					else if( arguments instanceof String )
					{
						// Simple token type name rule
						rule( stateName, pattern, (String) arguments );
					}
					else
						throw new ResolutionException( "Unexpected argument in state \"" + stateName + "\": " + arguments );
				}
			}
		}
	}
}
