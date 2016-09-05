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

package com.threecrickets.jygments.contrib;

import java.util.regex.Pattern;

/**
 * @author Stephen Just
 */
public class DiffLexer extends RegexLexer {

    public DiffLexer() {
        super();

        addFilename("*.patch");
        addFilename("*.diff");

        addAlias("diff");
        addAlias("udiff");

        addMimeType("text/x-diff");
        addMimeType("text/x-patch");

        int flags = Pattern.MULTILINE;
        include("root", "header");

        rule("header", "([Ii]ndex|diff).*\\n", flags, "Generic.Heading", "basics");
        rule("header", "[A-zA-z\\-]+:", flags, "Generic.Strong", "noparse");
        rule("header", "---\\n", flags, "Generic.Heading");
        rule("header", ".*\\n", flags, "Text");

        rule("basics", "\\n", flags, "Text");
        rule("basics", "\\+.*\\n", flags, "Generic.Inserted");
        rule("basics", "-.*\\n", flags, "Generic.Deleted");
        rule("basics", "!.*\\n", flags, "Generic.Strong");
        rule("basics", "@.*\\n", flags, "Generic.Subheading");
        rule("basics", "([Ii]ndex|diff|[Nn]ew|[Dd]eleted|[Mm]ode).*\\n", flags, "Generic.Heading");
        rule("basics", "=.*\\n", flags, "Generic.Heading");
        rule("basics", ".*\\n", flags, "Text");

        rule("noparse", ".*\\n", flags, "Text", "#pop");

        try {
            resolve();
        } catch (ResolutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float analyzeText(String text) {
        if (text.startsWith("Index: "))
            return 1.0f;
        if (text.startsWith("diff "))
            return 1.0f;
        if (text.startsWith("--- "))
            return 0.9f;
        return 0.0f;
    }
}
