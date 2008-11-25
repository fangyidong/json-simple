package org.json.simple.parser;

import java.io.StringReader;

import junit.framework.TestCase;

public class YylexTest extends TestCase {

	public void testYylex() throws Exception{
		String s="\"\\/\"";
		System.out.println(s);
		StringReader in = new StringReader(s);
		Yylex lexer=new Yylex(in);
		Yytoken token=lexer.yylex();
		assertEquals(Yytoken.TYPE_VALUE,token.type);
		assertEquals("/",token.value);
		
		s="\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Yylex(in);
		token=lexer.yylex();
		assertEquals(Yytoken.TYPE_VALUE,token.type);
		assertEquals("abc/\r\b\n\t\f\\",token.value);
		
		s="[\t \n\r\n{ \t \t\n\r}";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Yylex(in);
		token=lexer.yylex();
		assertEquals(Yytoken.TYPE_LEFT_SQUARE,token.type);
		token=lexer.yylex();
		assertEquals(Yytoken.TYPE_LEFT_BRACE,token.type);
		token=lexer.yylex();
		assertEquals(Yytoken.TYPE_RIGHT_BRACE,token.type);
		
		s="\b\f{";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Yylex(in);
		Error err=null;
		try{
			token=lexer.yylex();
		}
		catch(Error e){
			err=e;
			System.out.println("error:"+err);
		}
		assertTrue(err!=null);
	}

}
