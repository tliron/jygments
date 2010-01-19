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

package com.threecrickets.jygments;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.threecrickets.jygments.format.Formatter;
import com.threecrickets.jygments.grammar.Token;
import com.threecrickets.jygments.grammar.lexer.Lexer;

/**
 * @author Tal Liron
 */
public abstract class Jygments
{
	//
	// Static operations
	//

	public static Iterable<Token> lex( String code, Lexer lexer )
	{
		return lexer.getTokens( code );
	}

	public static void format( Iterable<Token> tokens, Formatter formatter, Writer writer ) throws IOException
	{
		formatter.format( tokens, writer );
	}

	public static void highlight( String code, Lexer lexer, Formatter formatter, Writer writer ) throws IOException
	{
		format( lex( code, lexer ), formatter, writer );
	}

	//
	// Main
	//

	public static void main( String[] arguments ) throws IOException, ResolutionException
	{
		Formatter formatter = Formatter.getByName( "html" );

		String code = " p { width: 10px; }\np { width: 10px; }\n h1 { line-height: 100%; }\n";
		Lexer lexer = Lexer.getByName( "css" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "css.html" ) ) );

		code = "from sys import out\ndef pip(a=nil):\n    pass";
		code = "def pip(x):\n    print 'hi'\n";
		code = "class Mine(object):\n    def pip(x=None):\n        pass\n        print x\n";
		lexer = Lexer.getByName( "python" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "python.html" ) ) );

		highlight( code, lexer, formatter, new PrintWriter( System.out ) );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private Jygments()
	{
	}
}
