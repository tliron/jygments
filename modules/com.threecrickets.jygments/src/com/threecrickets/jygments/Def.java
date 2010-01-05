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
public abstract class Def<C>
{
	//
	// Attributes
	//

	public Def<C> getCause( C container )
	{
		return null;
	}

	//
	// Operations
	//

	public boolean resolve( C container ) throws ResolutionException
	{
		return false;
	}

	public boolean isResolved()
	{
		return resolved;
	}

	//
	// Object
	//

	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}

	// //////////////////////////////////////////////////////////////////////////
	// Protected

	protected boolean resolved = false;
}
