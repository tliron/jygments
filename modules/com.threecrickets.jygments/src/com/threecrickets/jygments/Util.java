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

package com.threecrickets.jygments;

/**
 * @author Tal Liron
 */
public class Util
{
	public static String literalRegEx( String expression )
	{
		return "\\Q" + expression + "\\E";
	}

	public static String replace( String string, String occurence, String replacement )
	{
		return string.replace( literalRegEx( occurence ), replacement );
	}

	public static String escapeHtml( String text )
	{
		text = text.replace( "&", "&amp;" );
		text = text.replace( "<", "&lt;" );
		text = text.replace( ">", "&gt;" );
		text = text.replace( "\"", "&quot;" );
		text = text.replace( "'", "&#39;" );
		return text;
	}

	public static String asHtml( String text )
	{
		text = escapeHtml( text );
		text = text.replace( " ", "&nbsp;" );
		return text;
	}
}
