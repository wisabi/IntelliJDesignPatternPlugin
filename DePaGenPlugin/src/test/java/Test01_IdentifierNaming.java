import com.DesignPatternFactory.NameCheck;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Assert.*;
import sun.awt.X11.XSystemTrayPeer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Test01_IdentifierNaming {

    final String[] invalidIdentifiers01 = {
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while", "var", "null",
             "true", "false"
    };

    final String[] invalidIdentifiers02 = {
            "0", "00", "var-1", "var 1", "1var", "var.1", "1.var", "%var", "var%", "var%var",
            "(var)", "var^", "var!", "!var", " ", ""
    };

    final String[] validIdentifiers = {
            "vars", "var1", "var_1", "xxx", "var$", "$var", "v"
    };

    @Test
    public void testIdentifier01(){
        for(String identifier : invalidIdentifiers01) {
            Assert.assertFalse(NameCheck.checkIdentifier(identifier));
        }
    }

    @Test
    public void testIdentifier02(){
        for(String identifier : invalidIdentifiers01) {
            Assert.assertTrue(NameCheck.checkIdentifier(identifier.toUpperCase()));
        }
    }

    @Test
    public void testIdentifier03(){
        for(String identifier : validIdentifiers) {
            Assert.assertTrue(NameCheck.checkIdentifier(identifier));
        }
    }

    @Test
    public void testIdentifier04(){
        for(String identifier : invalidIdentifiers02) {
            Assert.assertFalse(NameCheck.checkIdentifier(identifier));
        }
    }
}
