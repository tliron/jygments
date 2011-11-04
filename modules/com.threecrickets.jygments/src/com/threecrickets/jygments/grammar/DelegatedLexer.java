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

package com.threecrickets.jygments.grammar;

import com.threecrickets.jygments.ResolutionException;
import java.util.Map;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;


/**
 * @author Tal Liron
 */
public class DelegatedLexer extends Lexer
{
    
    public class Insertion {
        public int index;
        public List<Token> lng_buffer;
    
        public Insertion(int index, List<Token> lng_buffer) {
            super();
            this.index = index;
            this.lng_buffer = lng_buffer;
        }
    }    
    
	@Override
	public Iterable<Token> getTokensUnprocessed( String text )
	{
	    StringBuilder buffered = new StringBuilder();
		List<Token> lng_buffer = new LinkedList<Token>();
		List<Insertion> insertions = new LinkedList<Insertion>();
		
		Iterable<Token> tokens = languageLexer.getTokensUnprocessed( text );
		
		for(Token t : tokens) {
		    if(t.getType().getName().equals("Other")) {
                if(!lng_buffer.isEmpty()) {
                    insertions.add(new Insertion(buffered.length(), lng_buffer));
                    lng_buffer = new LinkedList<Token>();
                }
		        buffered.append(t.getValue());
		    }
		    else
		        lng_buffer.add(t);
		}
		if(!lng_buffer.isEmpty())
            insertions.add(new Insertion(buffered.length(), lng_buffer));
		    
		return do_insertions(insertions, rootLexer.getTokensUnprocessed(buffered.toString()));
	}

    private Iterable<Token> do_insertions(List<Insertion> insertions, Iterable<Token> tokens) {

        ListIterator li = insertions.listIterator();
        Insertion next_ins = li.hasNext() ? (Insertion) li.next() : null;
        int len = 0;
        LinkedList<Token> rc = new LinkedList<Token>();
                
        for(Token t : tokens) {
            len += t.getValue().length();
            String s = t.getValue();
            int pos = 0;
            while(next_ins != null && next_ins.index <= len) {
                rc.add(new Token(t.getPos(), t.getType(), s.substring(pos, s.length() + (next_ins.index - len))));
                pos = s.length() + (next_ins.index - len);
                for(Token tt : next_ins.lng_buffer)
                    rc.add(tt);                    
                next_ins = li.hasNext() ? (Insertion) li.next() : null;
            }
            rc.add(new Token(t.getPos(), t.getType(), s.substring(pos)));
        }

        // Do remaining tokens
        while(li.hasNext())
            for(Token tt : ((Insertion) li.next()).lng_buffer)
                rc.add(tt);        

        return rc;
    }

	private Lexer rootLexer;
	private Lexer languageLexer;

	protected void addJson( Map<String, Object> json ) throws ResolutionException
	{
		super.addJson( json );		
		rootLexer = Lexer.getByName((String) json.get("root_lexer"));
		languageLexer = Lexer.getByName((String) json.get("language_lexer"));
    }

}
