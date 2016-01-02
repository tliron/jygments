/**
 * Copyright 2010-2016 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of a BSD license. See
 * attached license.txt.
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.jygments.style.def;

import java.util.List;

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
	//
	// Construction
	//

	public StyleElementDef( String tokenTypeName, List<String> styleElementNames )
	{
		this.tokenTypeName = tokenTypeName;
		this.styleElementNames = styleElementNames;
	}

	//
	// Def
	//

	@Override
	public boolean resolve( Style style ) throws ResolutionException
	{
		TokenType tokenType = TokenType.getTokenTypeByName( tokenTypeName );
		if( tokenType == null )
			throw new ResolutionException( "Unknown token type: " + tokenTypeName );

		//TokenType parent = tokenType.getParent();
		//boolean addToParent = false;
		//if( ( parent != null ) && ( !style.getStyleElements().containsKey( parent ) ) )
			//addToParent = true;
		for( String styleElementName : styleElementNames )
		{
			StyleElement styleElement = StyleElement.getStyleElementByName( styleElementName );
			if( styleElement == null )
				throw new ResolutionException( "Unknown style element: " + styleElementName );

			style.addStyleElement( tokenType, styleElement );
			//if( addToParent )
				//style.addStyleElement( parent, styleElement );
		}

		resolved = true;
		return true;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final String tokenTypeName;

	private final List<String> styleElementNames;
}
