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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.threecrickets.jygments.Def;
import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Grammar;
import com.threecrickets.jygments.grammar.State;
import com.threecrickets.jygments.grammar.TokenRule;
import com.threecrickets.jygments.grammar.TokenType;

/**
 * @author Tal Liron
 */
public class TokenRuleDef extends Def<Grammar>
{
	public TokenRuleDef( String stateName, String pattern, String tokenTypeName )
	{
		this.stateName = stateName;
		this.pattern = pattern;
		this.tokenTypeName = tokenTypeName;
	}

	//
	// Attributes
	//

	public String getStateName()
	{
		return stateName;
	}

	public String getPattern()
	{
		return pattern;
	}

	public String getTokenTypeName()
	{
		return tokenTypeName;
	}

	//
	// Def
	//

	@Override
	public boolean resolve( Grammar grammar ) throws ResolutionException
	{
		Pattern pattern;
		try
		{
			pattern = Pattern.compile( this.pattern, Pattern.MULTILINE );
		}
		catch( PatternSyntaxException x )
		{
			throw new ResolutionException( "RegEx syntax error: " + this.pattern, x );
		}

		TokenType tokenType = TokenType.getTokenTypeByName( tokenTypeName );
		if( tokenType == null )
			throw new ResolutionException( "Unknown token type: " + tokenTypeName );

		TokenRule rule = createTokenRule( pattern, tokenType, grammar );
		State state = grammar.getState( stateName );
		state.addRule( rule );
		resolved = true;
		return true;
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return super.toString() + " " + stateName + ", " + pattern + ", " + tokenTypeName;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Protected

	protected TokenRule createTokenRule( Pattern pattern, TokenType tokenType, Grammar grammar )
	{
		return new TokenRule( pattern, tokenType );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final String stateName;

	private final String pattern;

	private final String tokenTypeName;
}
