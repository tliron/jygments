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

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Tal Liron
 */
public abstract class Rule
{
	public Rule( Pattern pattern, List<State> nextStates )
	{
		this.pattern = pattern;
		this.nextStates = nextStates;
	}

	//
	// Attributes
	//

	public Pattern getPattern()
	{
		return pattern;
	}

	public List<State> getNextStates()
	{
		return nextStates;
	}

	public State getNextState()
	{
		State nextState = null;
		if( nextStates != null )
		{
			for( State aNextState : nextStates )
			{
				if( nextState == null )
					nextState = aNextState;
				else
					// Combine states
					nextState = new State( nextState, aNextState );
			}
		}
		return nextState;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Pattern pattern;

	private final List<State> nextStates;
}