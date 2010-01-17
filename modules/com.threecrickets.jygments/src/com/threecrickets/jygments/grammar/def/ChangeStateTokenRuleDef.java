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

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;

/**
 * @author Tal Liron
 */
public class ChangeStateTokenRuleDef extends TokenRuleDef
{
	public ChangeStateTokenRuleDef( String stateName, String pattern, String tokenTypeName, String nextStateName )
	{
		super( stateName, pattern, tokenTypeName );
		ArrayList<String> list = new ArrayList<String>( 1 );
		list.add( nextStateName );
		this.nextStateNames = list;
	}

	public ChangeStateTokenRuleDef( String stateName, String pattern, String tokenTypeName, Iterable<String> nextStateNames )
	{
		super( stateName, pattern, tokenTypeName );
		this.nextStateNames = nextStateNames;
	}

	//
	// TokenRuleDef
	//

	@Override
	protected TokenRule createTokenRule( Pattern pattern, TokenType tokenType, Grammar grammar )
	{
		return new TokenRule( pattern, tokenType, grammar.getStates( nextStateNames ), 0 );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Iterable<String> nextStateNames;
}
