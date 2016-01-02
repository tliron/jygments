/**
 * Copyright 2010-2016 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.jygments.grammar.def;

import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.Rule;
import com.threecrickets.jygments.grammar.SaveRule;
import com.threecrickets.jygments.grammar.State;

/**
 * @author Tal Liron
 */
public class SaveDef extends StateDef
{
	public SaveDef( String stateName, String savedStateName )
	{
		super( stateName );
		this.savedStateName = savedStateName;
	}

	//
	// Def
	//

	@Override
	public boolean resolve( Grammar grammar ) throws ResolutionException
	{
		State state = grammar.getState( stateName );
		State savedState = grammar.getState( savedStateName );

		// Only include a resolved state
		if( savedState.isResolved() )
		{
			if( placeHolder != null )
			{
				int location = state.getRules().indexOf( placeHolder );
				state.getRules().remove( placeHolder );
				state.addRuleAt( location, new SaveRule( savedState ) );
			}
			else
				state.addRule( new SaveRule( savedState ) );

			resolved = true;
			return true;
		}
		else if( placeHolder == null )
		{
			// Remember location
			placeHolder = new Rule();
			state.addRule( placeHolder );
		}

		return false;
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return super.toString() + " " + stateName + ", " + savedStateName;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final String savedStateName;
}
