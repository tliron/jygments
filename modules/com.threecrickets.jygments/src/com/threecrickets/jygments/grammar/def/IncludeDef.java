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

package com.threecrickets.jygments.grammar.def;

import com.threecrickets.jygments.Def;
import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.State;

/**
 * @author Tal Liron
 */
public class IncludeDef extends Def<Grammar>
{
	public IncludeDef( String stateName, String includedStateName )
	{
		this.stateName = stateName;
		this.includedStateName = includedStateName;
	}

	//
	// Def
	//

	@Override
	public boolean resolve( Grammar grammar ) throws ResolutionException
	{
		State includedState = grammar.getState( includedStateName );

		// Only include a resolved state
		if( includedState.isResolved() )
		{
			State state = grammar.getState( stateName );
			state.include( includedState );
			resolved = true;
			return true;
		}

		return false;
	}

	@Override
	public Def<Grammar> getCause( Grammar grammar )
	{
		return grammar.getState( includedStateName ).getCause( grammar );
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return super.toString() + " " + stateName + ", " + includedStateName;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final String stateName;

	private final String includedStateName;
}
