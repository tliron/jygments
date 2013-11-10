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
