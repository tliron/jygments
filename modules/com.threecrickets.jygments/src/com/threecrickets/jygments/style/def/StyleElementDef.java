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

package com.threecrickets.jygments.style.def;

import com.threecrickets.jygments.Def;
import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.TokenType;
import com.threecrickets.jygments.style.Style;
import com.threecrickets.jygments.style.StyleElement;

/**
 * @author Tal Liron
 */
public class StyleElementDef extends Def<Style>
{
	public StyleElementDef( String tokenTypeName, String styleElementName )
	{
		this.tokenTypeName = tokenTypeName;
		this.styleElementName = styleElementName;
	}

	@Override
	public boolean resolve( Style style ) throws ResolutionException
	{
		TokenType tokenType = TokenType.getTokenTypeByName( tokenTypeName );
		if( tokenType == null )
			throw new ResolutionException( "Unknown token type: " + tokenTypeName );

		StyleElement styleElement = StyleElement.getStyleElementByName( styleElementName );
		if( styleElement == null )
			throw new ResolutionException( "Unknown style element: " + styleElementName );

		style.addStyleElement( tokenType, styleElement );

		resolved = true;
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final String tokenTypeName;

	private final String styleElementName;
}
