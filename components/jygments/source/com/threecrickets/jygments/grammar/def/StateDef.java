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

import com.threecrickets.jygments.Def;
import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.Rule;

public abstract class StateDef extends Def<Grammar>
{
	//
	// Construction
	//

	public StateDef( String stateName )
	{
		this.stateName = stateName;
	}

	//
	// Attributes
	//

	public String getStateName()
	{
		return stateName;
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return super.toString() + " " + stateName;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Protected

	protected final String stateName;

	protected Rule placeHolder = null;
}
