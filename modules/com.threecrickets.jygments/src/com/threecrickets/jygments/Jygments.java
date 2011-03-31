/**
 * Copyright 2010-2011 Three Cricketsckets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.jygments;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.threecrickets.jygments.format.Formatter;
import com.threecrickets.jygments.grammar.Lexer;
import com.threecrickets.jygments.grammar.Token;

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
		
		/*code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/common/applications/prudence-test/web/static/style/soft-cricket.css" ) );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "css.html" ) ) );

		code = "from sys import out\ndef pip(a=nil):\n    pass";
		code = "def pip(x):\n    print 'hi'\n";
		code = "class Mine(object):\n    def pip(x=None):\n        pass\n        print x\n";
		code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/libraries/pygments/lib/python/Lib/pygments/lexer.py" ) );
		// System.out.println(code);
		lexer = Lexer.getByName( "python" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "python.html" ) ) );

		code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/clojure/defaults/instance/default.clj" ) );
		lexer = Lexer.getByName( "clojure" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "clojure.html" ) ) );

		code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/javascript/defaults/instance/default.js" ) );
		lexer = Lexer.getByName( "javascript" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "javascript.html" ) ) );*/

		code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/python/applications/stickstick/web/static/index.html" ) );
		lexer = Lexer.getByName( "html" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "html.html" ) ) );
		
		//code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/javascript/applications/prudence-test/web/dynamic/test/rhino.html"));
		code = Util.streamToString( new FileInputStream( "/Depot/Projects/Collaborative/Prudence/clojure/applications/prudence-test/web/dynamic/test/clojure.html"));
		lexer = Lexer.getByName( "prudenceHtml" );
		highlight( code, lexer, formatter, new PrintWriter( new FileWriter( "prudenceHtml.html" ) ) );

		//highlight( code, lexer, formatter, new PrintWriter( System.out ) );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private Jygments()
	{
	}
}
