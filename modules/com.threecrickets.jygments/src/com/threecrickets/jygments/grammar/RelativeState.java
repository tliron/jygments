package com.threecrickets.jygments.grammar;

public class RelativeState extends State
{
	public RelativeState( boolean push, int depth )
	{
		this.push = push;
		this.depth = depth;
	}
	
	public boolean isPush()
	{
		return push;
	}
	
	public int getDepth()
	{
		return depth;
	}

	private final boolean push;

	private final int depth;
}
