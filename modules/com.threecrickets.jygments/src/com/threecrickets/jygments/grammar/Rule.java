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

import java.util.regex.Pattern;

/**
 * @author Tal Liron
 */
public abstract class Rule
{
	public Rule( Pattern pattern, State nextState )
	{
		this.pattern = pattern;
		this.nextState = nextState;
	}

	//
	// Attributes
	//

	public Pattern getPattern()
	{
		return pattern;
	}

	public State getNextState()
	{
		return nextState;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Pattern pattern;

	private final State nextState;
}