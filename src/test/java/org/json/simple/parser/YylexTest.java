package org.json.simple.parser;

import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that parser.Yylex hasn't regressed in functionality or breaks its API contract. Deprecated warnings are
 * suppressed since it is intentionally testing deprecated code for backwards compatibility. */
@SuppressWarnings("deprecation")
public class YylexTest{
    /** Called before each Test Method. */
    @Before
    public void setUp(){
        /* All of the implemented tests use local variables in their own respective method. */
    }

    /** Called after each Test method. */
    @After
    public void tearDown(){
        /* All of the implemented tests use local variables in their own respective method. */
    }

    /** Ensures tokens are lexed.
     * @throws Exception if the test failed. */
    @Test
    public void testYylex() throws Exception{
        String s = "\"\\/\"";
        StringReader in = new StringReader(s);
        Yylex lexer = new Yylex(in);
        Yytoken token = lexer.yylex();
        Assert.assertEquals(Yytoken.TYPE_VALUE, token.type);
        Assert.assertEquals("/", token.value);
        s = "\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
        in = new StringReader(s);
        lexer = new Yylex(in);
        token = lexer.yylex();
        Assert.assertEquals(Yytoken.TYPE_VALUE, token.type);
        Assert.assertEquals("abc/\r\b\n\t\f\\", token.value);
        s = "[\t \n\r\n{ \t \t\n\r}";
        in = new StringReader(s);
        lexer = new Yylex(in);
        token = lexer.yylex();
        Assert.assertEquals(Yytoken.TYPE_LEFT_SQUARE, token.type);
        token = lexer.yylex();
        Assert.assertEquals(Yytoken.TYPE_LEFT_BRACE, token.type);
        token = lexer.yylex();
        Assert.assertEquals(Yytoken.TYPE_RIGHT_BRACE, token.type);
        s = "\b\f{";
        in = new StringReader(s);
        lexer = new Yylex(in);
        ParseException err = null;
        try{
            token = lexer.yylex();
        }catch(final ParseException e){
            err = e;
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
            Assert.assertEquals(0, e.getPosition());
            Assert.assertEquals(new Character('\b'), e.getUnexpectedObject());
        }catch(final IOException ie){
            throw ie;
        }
        Assert.assertTrue(err != null);
        s = "{a : b}";
        in = new StringReader(s);
        lexer = new Yylex(in);
        err = null;
        try{
            lexer.yylex();
            token = lexer.yylex();
        }catch(final ParseException e){
            err = e;
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
            Assert.assertEquals(new Character('a'), e.getUnexpectedObject());
            Assert.assertEquals(1, e.getPosition());
        }catch(final IOException ie){
            throw ie;
        }
        Assert.assertTrue(err != null);
    }
}
