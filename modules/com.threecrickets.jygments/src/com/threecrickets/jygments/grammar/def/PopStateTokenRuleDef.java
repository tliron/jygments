/**
 * Copyright 2010 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://www.threecrickets.com/
 */

package com.threecrickets.jygments.grammar.def;

import java.util.regex.Pattern;

import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.State;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;

/**
 * @author Tal Liron
 */
public class PopStateTokenRuleDef extends TokenRuleDef
{
	public PopStateTokenRuleDef( String stateName, String pattern, String tokenTypeName, int depth )
	{
		super( stateName, pattern, tokenTypeName );
		this.depth = depth;
	}

	//
	// TokenRuleDef
	//

	@Override
	protected TokenRule createTokenRule( Pattern pattern, TokenType tokenType, Grammar grammar )
	{
		return new TokenRule( pattern, tokenType, State.Pop, depth );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final int depth;
}
