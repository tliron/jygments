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

package com.threecrickets.jygments.grammar;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tal Liron
 */
public class TokenType
{
	//
	// Constants
	//

	public static final TokenType Token = create( "Token", "" );

	// Main

	public static final TokenType Text = create( "Text", "" );

	public static final TokenType Whitespace = create( "Whitespace", "w" );

	public static final TokenType Error = create( "Error", "err" );

	public static final TokenType Other = create( "Other", "x" );

	// Keywords

	public static final TokenType Keyword = create( "Keyword", "k" );

	public static final TokenType Keyword_Constant = create( "Keyword.Constant", "kc" );

	public static final TokenType Keyword_Declaration = create( "Keyword.Declaration", "kd" );

	public static final TokenType Keyword_Namespace = create( "Keyword.Namespace", "kn" );

	public static final TokenType Keyword_Pseudo = create( "Keyword.Pseudo", "kp" );

	public static final TokenType Keyword_Reserved = create( "Keyword.Reserved", "kr" );

	public static final TokenType Keyword_Type = create( "Keyword.Type", "kt" );

	// Names

	public static final TokenType Name = create( "Name", "n" );

	public static final TokenType Name_Attribute = create( "Name.Attribute", "na" );

	public static final TokenType Name_Builtin = create( "Name.Builtin", "nb" );

	public static final TokenType Name_Builtin_Pseudo = create( "Builtin.Pseudo", "bp" );

	public static final TokenType Name_Class = create( "Name.Class", "nc" );

	public static final TokenType Name_Constant = create( "Name.Constant", "no" );

	public static final TokenType Name_Decorator = create( "Name.Decorator", "nd" );

	public static final TokenType Name_Entity = create( "Name.Entity", "ni" );

	public static final TokenType Name_Exception = create( "Name.Exception", "ne" );

	public static final TokenType Name_Function = create( "Name.Function", "nf" );

	public static final TokenType Name_Property = create( "Name.Property", "py" );

	public static final TokenType Name_Label = create( "Name.Label", "nl" );

	public static final TokenType Name_Namespace = create( "Name.Namespace", "nn" );

	public static final TokenType Name_Other = create( "Name.Other", "nx" );

	public static final TokenType Name_Tag = create( "Name.Tag", "nt" );

	public static final TokenType Name_Variable = create( "Name.Variable", "nv" );

	public static final TokenType Name_Variable_Class = create( "Name.Variable.Class", "vc" );

	public static final TokenType Name_Variable_Global = create( "Name.Variable.Global", "vg" );

	public static final TokenType Name_Variable_Instance = create( "Name.Variable.Instance", "vi" );

	// Literals

	public static final TokenType Literal = create( "Literal", "l" );

	public static final TokenType Literal_Date = create( "Literal.Date", "ld" );

	// Strings

	public static final TokenType String = create( "String", "s" );

	public static final TokenType String_Backtick = create( "String.Backtick", "sb" );

	public static final TokenType String_Char = create( "String.Char", "sc" );

	public static final TokenType String_Doc = create( "String.Doc", "sd" );

	public static final TokenType String_Double = create( "String.Double", "s2" );

	public static final TokenType String_Escape = create( "String.Escape", "se" );

	public static final TokenType String_Heredoc = create( "String.Heredoc", "sh" );

	public static final TokenType String_Interpol = create( "String.Interpol", "si" );

	public static final TokenType String_Other = create( "String.Other", "sx" );

	public static final TokenType String_Regex = create( "String.Regex", "sr" );

	public static final TokenType String_Single = create( "String.Single", "s1" );

	public static final TokenType String_Symbol = create( "String.Symbol", "ss" );

	// Numbers

	public static final TokenType Number = create( "Number", "m" );

	public static final TokenType Number_Float = create( "Number.Float", "mf" );

	public static final TokenType Number_Hex = create( "Number.Hex", "mh" );

	public static final TokenType Number_Integer = create( "Number.Integer", "mi" );

	public static final TokenType Number_Integer_Long = create( "Number.Integer.Long", "il" );

	public static final TokenType Number_Oct = create( "Number.Oct", "mo" );

	// Operators

	public static final TokenType Operator = create( "Operator", "o" );

	public static final TokenType Operator_Word = create( "Operator.Word", "ow" );

	// Punctuation

	public static final TokenType Punctuation = create( "Punctuation", "p" );

	// Comments

	public static final TokenType Comment = create( "Comment", "c" );

	public static final TokenType Comment_Multiline = create( "Comment.Multiline", "cm" );

	public static final TokenType Comment_Preproc = create( "Comment.Preproc", "cp" );

	public static final TokenType Comment_Single = create( "Comment.Single", "c1" );

	public static final TokenType Comment_Special = create( "Comment.Special", "cs" );

	// Generics

	public static final TokenType Generic = create( "Generic", "g" );

	public static final TokenType Generic_Deleted = create( "Generic.Deleted", "gd" );

	public static final TokenType Generic_Emph = create( "Generic.Emph", "ge" );

	public static final TokenType Generic_Error = create( "Generic.Error", "gr" );

	public static final TokenType Generic_Heading = create( "Generic.Heading", "gh" );

	public static final TokenType Generic_Inserted = create( "Generic.Inserted", "gi" );

	public static final TokenType Generic_Output = create( "Generic.Output", "go" );

	public static final TokenType Generic_Prompt = create( "Generic.Prompt", "gp" );

	public static final TokenType Generic_Strong = create( "Generic.Strong", "gs" );

	public static final TokenType Generic_Subheading = create( "Generic.Subheading", "gu" );

	public static final TokenType Generic_Traceback = create( "Generic.Traceback", "gt" );

	//
	// Static attributes
	//

	public static TokenType getTokenTypeByName( String name )
	{
		return tokenTypesByName.get( name );
	}

	public static TokenType getTokenTypeByShortName( String shortName )
	{
		return tokenTypesByShortName.get( shortName );
	}

	//
	// Attributes
	//

	public String getName()
	{
		return name;
	}

	public String getShortName()
	{
		return shortName;
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return name;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private static Map<String, TokenType> tokenTypesByName;

	private static Map<String, TokenType> tokenTypesByShortName;

	private static final TokenType create( String name, String shortName )
	{
		TokenType tokenType = new TokenType( name, shortName );

		if( tokenTypesByName == null )
			tokenTypesByName = new HashMap<String, TokenType>();
		if( tokenTypesByShortName == null )
			tokenTypesByShortName = new HashMap<String, TokenType>();

		tokenTypesByName.put( name, tokenType );
		tokenTypesByShortName.put( shortName, tokenType );

		return tokenType;
	}

	private final String name;

	private final String shortName;

	private TokenType( String name, String shortName )
	{
		this.name = name;
		this.shortName = shortName;
	}
}
