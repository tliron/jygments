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
import java.util.List;

import com.threecrickets.jygments.NestedDef;

/**
 * @author Tal Liron
 */
public class State extends NestedDef<Grammar>
{
	//
	// Constants
	//

	public static final State Pop = new State();

	public static final State Push = new State();

	//
	// Attributes
	//

	public void addRule( Rule rule )
	{
		rules.add( rule );
	}

	public List<Rule> getRules()
	{
		return rules;
	}

	//
	// Operations
	//

	public void include( State includedState )
	{
		rules.addAll( includedState.rules );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final List<Rule> rules = new ArrayList<Rule>();
}
