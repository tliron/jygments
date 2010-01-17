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
import java.util.regex.Pattern;

/**
 * @author Tal Liron
 */
public abstract class Rule
{
	public Rule( Pattern pattern, State... nextStates )
	{
		this.pattern = pattern;
		ArrayList<State> list = new ArrayList<State>( nextStates.length );
		for( State nextState : nextStates )
			list.add( nextState );
		this.nextStates = list;
	}

	public Rule( Pattern pattern, Iterable<State> nextStates )
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

	public Iterable<State> getNextStates()
	{
		return nextStates;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Pattern pattern;

	private final Iterable<State> nextStates;
}