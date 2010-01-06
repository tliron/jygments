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
import java.util.List;
import java.util.Map;

import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.Util;
import com.threecrickets.jygments.grammar.Token;
import com.threecrickets.jygments.grammar.TokenType;
import com.threecrickets.jygments.style.ColorStyleElement;
import com.threecrickets.jygments.style.EffectStyleElement;
import com.threecrickets.jygments.style.FontStyleElement;
import com.threecrickets.jygments.style.Style;
import com.threecrickets.jygments.style.StyleElement;

/**
 * @author Tal Liron
 */
public class HtmlFormatter extends Formatter
{
	//
	// Construction
	//

	public HtmlFormatter() throws ResolutionException
	{
		this( Style.getByName( "default" ), false, null, null );
	}

	public HtmlFormatter( Style style, boolean full, String title, String encoding )
	{
		super( style, full, title, encoding );
	}

	//
	// Formatter
	//

	@Override
	public void format( Iterable<Token> tokenSource, Writer writer ) throws IOException
	{
		writer.write( DOC_HEADER1 );
		writer.write( "  <title>" );
		writer.write( Util.escapeHtml( getTitle() ) );
		writer.write( "</title>\n" );
		writer.write( DOC_HEADER2 );
		writer.write( getEncoding() );
		writer.write( DOC_HEADER3 );
		writer.write( CSSFILE_TEMPLATE );
		formatStyleSheet( writer );
		writer.write( DOC_HEADER4 );
		if( getTitle().length() > 0 )
		{
			writer.write( "<h2>" );
			writer.write( Util.escapeHtml( getTitle() ) );
			writer.write( "</h2>\n" );
		}
		StringBuilder line = new StringBuilder();
		for( Token token : tokenSource )
		{
			if( token.getValue().equals( "\n" ) )
			{
				writer.write( line.toString() );
				writer.write( "<br />\n" );
				line = new StringBuilder();
			}
			else
			{
				if( token.getType().getShortName().length() > 0 )
				{
					line.append( "<span class=\"" );
					line.append( token.getType().getShortName() );
					line.append( "\">" );
					line.append( Util.asHtml( token.getValue() ) );
					line.append( "</span>" );
				}
				else
					line.append( Util.asHtml( token.getValue() ) );
			}
		}
		if( line.length() > 0 )
		{
			writer.write( line.toString() );
			writer.write( "<br />\n" );
		}
		writer.write( DOC_FOOTER );
		writer.flush();
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private String classPrefix = "";

	private static final String CSSFILE_TEMPLATE = "    td.linenos { background-color: #f0f0f0; padding-right: 10px; }\n" + "    span.lineno { background-color: #f0f0f0; padding: 0 5px 0 5px; }\n"
		+ "    pre { line-height: 125%; }\n";

	private static final String DOC_HEADER1 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" + "<html>\n" + "<head>\n";

	private static final String DOC_HEADER2 = "  <meta http-equiv=\"content-type\" content=\"text/html; charset=";

	private static final String DOC_HEADER3 = "\">\n" + "  <style type=\"text/css\">\n";

	private static final String DOC_HEADER4 = "  </style>\n" + "</head>\n" + "<body>\n";

	/*
	 * private static final String DOC_HEADER_EXTERNAL_CSS =
	 * "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n" +
	 * "   \"http://www.w3.org/TR/html4/strict.dtd\">\n" + "\n" + "<html>\n" +
	 * "<head>\n" + "  <title>%(title)s</title>\n" +
	 * "  <meta http-equiv=\"content-type\" content=\"text/html; charset=%(encoding)s\">\n"
	 * + "  <link rel=\"stylesheet\" href=\"%(cssfile)s\" type=\"text/css\">\n"
	 * + "</head>\n" + "<body>\n" + "<h2>%(title)s</h2>\n";
	 */

	private static final String DOC_FOOTER = "</body>\n" + "</html>\n";

	private void formatStyleSheet( Writer writer ) throws IOException
	{
		for( Map.Entry<TokenType, List<StyleElement>> entry : getStyle().getStyleElements().entrySet() )
		{
			TokenType tokenType = entry.getKey();
			List<StyleElement> styleElementsForTokenType = entry.getValue();
			writer.write( "    ." );
			writer.write( classPrefix );
			writer.write( tokenType.getShortName() );
			writer.write( " { " );
			for( StyleElement styleElement : styleElementsForTokenType )
			{
				if( styleElement instanceof ColorStyleElement )
				{
					ColorStyleElement colorStyleElement = (ColorStyleElement) styleElement;
					if( colorStyleElement.getType() == ColorStyleElement.Type.Foreground )
						writer.write( "color: " );
					else if( colorStyleElement.getType() == ColorStyleElement.Type.Background )
						writer.write( "background-color: " );
					else if( colorStyleElement.getType() == ColorStyleElement.Type.Border )
						writer.write( "border: 1px solid " );
					writer.write( colorStyleElement.getColor() );
					writer.write( "; " );
				}
				else if( styleElement instanceof EffectStyleElement )
				{
					if( styleElement == EffectStyleElement.Bold )
						writer.write( "font-weight: bold; " );
					else if( styleElement == EffectStyleElement.Italic )
						writer.write( "font-style: italic; " );
					else if( styleElement == EffectStyleElement.Underline )
						writer.write( "text-decoration: underline; " );
				}
				else if( styleElement instanceof FontStyleElement )
				{
					// We don't want to set fonts in this formatter
				}
			}
			writer.write( "} /* " );
			writer.write( tokenType.getName() );
			writer.write( " */\n" );
		}
	}
}
