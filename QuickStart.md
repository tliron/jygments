# Command line #

To start using Jygments:

  1. Download the [latest release](http://code.google.com/p/jygments/downloads/detail?name=jygments-R25.zip&can=2&q=) and unzip
  1. Add all the .jar files in the lib directory to your CLASSPATH
  1. Issue:
```
java com.threecrickets.jygments.Jygments <sourcefile> > output.html
```


# From a program #

This is a simple example program that takes a file name on the command line, and produces formatted HTML:

```
import com.threecrickets.jygments.format.*;
import com.threecrickets.jygments.grammar.*;
import java.io.FileInputStream;
import java.io.PrintWriter;
import org.apache.commons.io.IOUtils;

public class Highlighter {

    public static void main( String[] args ) throws Throwable
    {
        Lexer lexer = Lexer.getForFileName( args[0] );
        Formatter formatter = Formatter.getByName( "html" );
        String code = IOUtils.toString( new FileInputStream( args[0] ) );
        formatter.format( lexer.getTokens( code ), new PrintWriter( System.out ) );
    }
}
```