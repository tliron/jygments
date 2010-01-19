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

import com.threecrickets.jygments.grammar.Token;


/**
 * @author Tal Liron
 */
public abstract class DelegatedLexer extends Lexer
{
	public DelegatedLexer()
	{
		super();
	}

	@Override
	public Iterable<Token> getTokensUnprocessed( String text )
	{
		// Iterable<Token> tokens = languageLexer.getTokensUnprocessed( text );
		// return tokens;
		return null;
	}

	// private final Lexer rootLexer;

	// private final Lexer languageLexer;
}
