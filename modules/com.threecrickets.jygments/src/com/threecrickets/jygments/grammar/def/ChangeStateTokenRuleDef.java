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
import java.util.List;
import java.util.regex.Pattern;

import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;

/**
 * @author Tal Liron
 */
public class ChangeStateTokenRuleDef extends TokenRuleDef
{
	public ChangeStateTokenRuleDef( String stateName, String pattern, List<String> tokenTypeNames, List<String> nextStateNames )
	{
		super( stateName, pattern, tokenTypeNames );
		this.nextStateNames = nextStateNames;
	}

	public ChangeStateTokenRuleDef( String stateName, String pattern, String[] tokenTypeNames, String... nextStateNames )
	{
		super( stateName, pattern, tokenTypeNames );
		ArrayList<String> list = new ArrayList<String>( nextStateNames.length );
		for( String nextStateName : nextStateNames )
			list.add( nextStateName );
		this.nextStateNames = list;
	}

	//
	// TokenRuleDef
	//

	@Override
	protected TokenRule createTokenRule( Pattern pattern, List<TokenType> tokenTypes, Grammar grammar ) throws ResolutionException
	{
		return new TokenRule( pattern, tokenTypes, grammar.resolveStates( nextStateNames ) );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final List<String> nextStateNames;
}
