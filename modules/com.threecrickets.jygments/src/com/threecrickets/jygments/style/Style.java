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

package com.threecrickets.jygments.style;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.threecrickets.jygments.Jygments;
import com.threecrickets.jygments.NestedDef;
import com.threecrickets.jygments.ResolutionException;
import com.threecrickets.jygments.grammar.TokenType;
import com.threecrickets.jygments.style.def.StyleElementDef;

/**
 * @author Tal Liron
 */
public class Style extends NestedDef<Style>
{
	//
	// Static operations
	//

	public static Style getByName( String name ) throws ResolutionException
	{
		if( Character.isLowerCase( name.charAt( 0 ) ) )
			name = Character.toUpperCase( name.charAt( 0 ) ) + name.substring( 1 ) + "Style";

		Style style = getByFullName( name );
		if( style != null )
			return style;
		else
		{
			String pack = Jygments.class.getPackage().getName();
			name = pack + "." + name;
			return getByFullName( name );
		}
	}

	@SuppressWarnings("unchecked")
	public static Style getByFullName( String fullName ) throws ResolutionException
	{
		try
		{
			return (Style) Jygments.class.getClassLoader().loadClass( fullName ).newInstance();
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

		InputStream stream = Jygments.class.getClassLoader().getResourceAsStream( fullName.replace( '.', '/' ) + ".json" );
		if( stream != null )
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.getJsonFactory().configure( JsonParser.Feature.ALLOW_COMMENTS, true );
			try
			{
				Map<String, Object> json = objectMapper.readValue( stream, HashMap.class );
				Style style = new Style();
				style.addJson( json );
				style.resolve();
				return style;
			}
			catch( JsonParseException x )
			{
				throw new ResolutionException( x );
			}
			catch( JsonMappingException x )
			{
				throw new ResolutionException( x );
			}
			catch( IOException x )
			{
				throw new ResolutionException( x );
			}
		}

		return null;
	}

	//
	// Attributes
	//

	public Map<TokenType, List<StyleElement>> getStyleElements()
	{
		return styleElements;
	}

	//
	// Operations
	//

	public void addStyleElement( TokenType tokenType, StyleElement styleElement )
	{
		List<StyleElement> styleElementsForTokenType = styleElements.get( tokenType );
		if( styleElementsForTokenType == null )
		{
			styleElementsForTokenType = new ArrayList<StyleElement>();
			styleElements.put( tokenType, styleElementsForTokenType );
		}
		styleElementsForTokenType.add( styleElement );
	}

	public void resolve() throws ResolutionException
	{
		resolve( this );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Protected

	protected void add( String tokenTypeName, String... styleElementNames )
	{
		for( String styleElementName : styleElementNames )
			addDef( new StyleElementDef( tokenTypeName, styleElementName ) );
	}

	@SuppressWarnings("unchecked")
	protected void addJson( Map<String, Object> json ) throws ResolutionException
	{
		for( Map.Entry<String, Object> entry : json.entrySet() )
		{
			String tokenTypeName = entry.getKey();
			if( entry.getValue() instanceof Iterable<?> )
			{
				for( String styleElementName : (Iterable<String>) entry.getValue() )
					add( tokenTypeName, styleElementName );
			}
			else if( entry.getValue() instanceof String )
				add( tokenTypeName, (String) entry.getValue() );
			else
				throw new ResolutionException( "Unexpected value in style definition: " + entry.getValue() );
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final Map<TokenType, List<StyleElement>> styleElements = new HashMap<TokenType, List<StyleElement>>();
}
