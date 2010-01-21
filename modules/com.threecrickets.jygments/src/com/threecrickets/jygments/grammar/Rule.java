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
public class Rule
{
	//
	// Construction
	//

	public Rule()
	{
		this( null, null );
	}

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

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Pattern pattern;

	private final List<State> nextStates;
}