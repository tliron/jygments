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
public class TokenRule extends Rule
{
	public TokenRule( Pattern pattern, TokenType tokenType )
	{
		this( pattern, tokenType, (Iterable<State>) null, 0 );
	}

	public TokenRule( Pattern pattern, TokenType tokenType, State nextState, int depth )
	{
		super( pattern, nextState );
		this.tokenType = tokenType;
		this.depth = depth;
	}

	public TokenRule( Pattern pattern, TokenType tokenType, Iterable<State> nextStates, int depth )
	{
		super( pattern, nextStates );
		this.tokenType = tokenType;
		this.depth = depth;
	}

	//
	// Attributes
	//

	public TokenType getTokenType()
	{
		return tokenType;
	}

	public int getDepth()
	{
		return depth;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final TokenType tokenType;

	private final int depth;
}
