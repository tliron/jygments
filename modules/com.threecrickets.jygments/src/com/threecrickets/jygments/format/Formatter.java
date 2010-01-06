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

package com.threecrickets.jygments.format;

import java.io.IOException;
import java.io.Writer;

import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.Token;
import com.threecrickets.jygments.style.Style;

/**
 * @author Tal Liron
 */
public abstract class Formatter
{
	//
	// Static operations
	//

	public static Formatter getByName( String name ) throws ResolutionException
	{
		if( Character.isLowerCase( name.charAt( 0 ) ) )
			name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 ) + "Formatter";

		Formatter lexer = getByFullName( name );
		if( lexer != null )
			return lexer;
		else
		{
			String pack = Formatter.class.getPackage().getName();
			name = pack + "." + name;
			return getByFullName( name );
		}
	}

	public static Formatter getByFullName( String fullName ) throws ResolutionException
	{
		try
		{
			return (Formatter) Formatter.class.getClassLoader().loadClass( fullName ).newInstance();
		}
		catch( InstantiationException x )
		{
		}
		catch( IllegalAccessException x )
		{
		}
		catch( ClassNotFoundException x )
		{
		}

		return null;
	}

	public Formatter( Style style, boolean full, String title, String encoding )
	{
		this.style = style;
		this.title = title != null ? title : "";
		this.encoding = encoding != null ? encoding : "utf8";
	}

	public Style getStyle()
	{
		return style;
	}

	public String getTitle()
	{
		return title;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public abstract void format( Iterable<Token> tokenSource, Writer writer ) throws IOException;

	private final Style style;

	private final String title;

	private final String encoding;
}
