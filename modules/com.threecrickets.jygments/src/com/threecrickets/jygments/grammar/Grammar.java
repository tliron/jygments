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

package com.threecrickets.jygments.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threecrickets.jygments.Def;
import com.threecrickets.jygments.NestedDef;
import com.threecrickets.jygments.ResolutionException;

/**
 * @author Tal Liron
 */
public class Grammar extends NestedDef<Grammar>
{
	//
	// Attributes
	//

	public State getState( String stateName )
	{
		State state = statesByName.get( stateName );
		if( state == null )
		{
			state = new State( stateName );
			statesByName.put( stateName, state );
			addDef( state );
		}
		return state;
	}

	public State resolveState( String stateName ) throws ResolutionException
	{
		if( stateName.startsWith( "#pop" ) )
		{
			int depth = 1;
			if( stateName.length() > 4 )
			{
				String depthString = stateName.substring( 5 );
				try
				{
					depth = Integer.parseInt( depthString );
				}
				catch( NumberFormatException x )
				{
					throw new ResolutionException( x );
				}
			}
			return new RelativeState( false, depth );
		}
		else if( stateName.startsWith( "#push" ) )
		{
			int depth = 1;
			if( stateName.length() > 5 )
			{
				String depthString = stateName.substring( 6 );
				try
				{
					depth = Integer.parseInt( depthString );
				}
				catch( NumberFormatException x )
				{
					throw new ResolutionException( x );
				}
			}
			return new RelativeState( true, depth );
		}
		else if( stateName.startsWith( "#using" ) )
		{
			if( stateName.length() <= 6 )
				throw new ResolutionException( "#using does not include lexer name" );

			String lexerName = stateName.substring( 7 );
			Lexer lexer = Lexer.getByName( lexerName );
			return lexer.getState( "root" );
		}

		State state = getState( stateName );
		if( state.isResolved() )
			return state;
		else
			return null;
	}

	public List<State> resolveStates( List<String> stateNames ) throws ResolutionException
	{
		ArrayList<State> states = new ArrayList<State>();
		State state, stateToAdd = null;
		for( String stateName : stateNames )
		{
			state = resolveState( stateName );
			if( state == null )
				return null;

			if( state instanceof RelativeState )
			{
				if( stateToAdd != null )
				{
					states.add( stateToAdd );
					stateToAdd = null;
				}
				states.add( state );
			}
			else
			{
				if( stateToAdd == null )
					stateToAdd = state;
				else
					stateToAdd = new State( stateToAdd, state );
			}
		}
		if( stateToAdd != null )
			states.add( stateToAdd );
		return states;
	}

	//
	// Operations
	//

	public void resolve() throws ResolutionException
	{
		resolve( this );

		// Are we resolved?
		for( Map.Entry<String, State> entry : statesByName.entrySet() )
		{
			if( !entry.getValue().isResolved() )
			{
				String message = "Unresolved state: " + entry.getKey();
				Def<Grammar> cause = entry.getValue().getCause( this );
				while( cause != null )
				{
					message += ", cause: " + cause;
					cause = cause.getCause( this );
				}
				throw new ResolutionException( message );
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Map<String, State> statesByName = new HashMap<String, State>();
}
