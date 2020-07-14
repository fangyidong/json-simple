/* See: README for this file's copyright, terms, and conditions. */
package com.github.cliftonlabs.json_simple;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures the lexer hasn't regressed in functionality or breaks its API contract. */
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

	/** Ensure concatenated JSON values are lexable.
	 * @throws IOException if the test failed.
	 * @throws JsonException if the test failed. */
	@Test
	public void testLexingConcatenatedJsonValues() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("nullnullnullnull12.33.21truetruenullfalse\"\"{}[]");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(null, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(null, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(null, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(null, lexed.getValue());
		try{
			lexed = lexer.yylex();
		}catch(final JsonException caught){
			/* Concatenated numbers don't always work well. */
			Assert.assertEquals(JsonException.Problems.UNEXPECTED_EXCEPTION, caught.getProblemType());
		}
		try{
			lexed = lexer.yylex();
		}catch(final JsonException caught){
			/* Concatenated numbers don't always work well. */
			Assert.assertEquals(JsonException.Problems.UNEXPECTED_CHARACTER, caught.getProblemType());
			Assert.assertEquals(21, caught.getPosition());
		}
		/* Instead of the 12.3 and 3.21 concatenated together we ended up with 21! */
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(new BigDecimal("21"), lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(true, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(true, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(null, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(false, lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals("", lexed.getValue());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.LEFT_BRACE, lexed.getType());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.RIGHT_BRACE, lexed.getType());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.LEFT_SQUARE, lexed.getType());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.RIGHT_SQUARE, lexed.getType());
	}

	/** Ensures a negative number is lexable.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingNegativeNumber() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("-123456789098765432101234567890987654321");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(new BigDecimal("-123456789098765432101234567890987654321"), lexed.getValue());
	}

	/** Ensures a number with a decimal place in it is lexable.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingNumberWithDecimal() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("-1234567890987654321.01234567890987654321");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321"), lexed.getValue());
	}

	/** Ensures a number with an exponent is lexable.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingNumberWithExponent() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("-1234567890987654321.01234567890987654321E-50");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(new BigDecimal("-1234567890987654321.01234567890987654321E-50"), lexed.getValue());
	}

	/** Ensures a positive number is lexable.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingPositiveNumber() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("123456789098765432101234567890987654321");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals(new BigDecimal("123456789098765432101234567890987654321"), lexed.getValue());
	}

	/** Ensures a String containing escaped characters and various unicode characters is lexable.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingStringContainingEscapedCharacters() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("\"ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]\\/`123456789-=~!@#$%^&*_+()\\r\\b\\n\\t\\f\\\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ\"");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.DATUM, lexed.getType());
		Assert.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ<>:{}abcdefghijklmnopqrstuvwxyz,.;'[]/`123456789-=~!@#$%^&*_+()\r\b\n\t\f\\К௪ၐᎺអὲ⍚❂⼒ぐ㋺ꁐꁚꑂ", lexed.getValue());
	}

	/** Ensures that unexpected characters are a problem between expected characters.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingUnexpectedCharacter() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("{a : b}");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.LEFT_BRACE, lexed.getType());
		try{
			lexed = lexer.yylex();
		}catch(final JsonException caught){
			Assert.assertEquals(JsonException.Problems.UNEXPECTED_CHARACTER, caught.getProblemType());
			Assert.assertEquals(new Character('a'), caught.getUnexpectedObject());
			Assert.assertEquals(1, caught.getPosition());
		}
		/* The exception should have left the lexed token unchanged. */
		Assert.assertEquals(Yytoken.Types.LEFT_BRACE, lexed.getType());
	}

	/** Ensure white space is ignored while lexing outside of Strings.
	 * @throws IOException if the test fails.
	 * @throws JsonException if the test fails. */
	@Test
	public void testLexingWhiteSpace() throws IOException, JsonException{
		StringReader lexable;
		Yylex lexer;
		Yytoken lexed;
		lexable = new StringReader("[\t \n\r\n{ \t \t\n\r}");
		lexer = new Yylex(lexable);
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.LEFT_SQUARE, lexed.getType());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.LEFT_BRACE, lexed.getType());
		lexed = lexer.yylex();
		Assert.assertEquals(Yytoken.Types.RIGHT_BRACE, lexed.getType());
	}
}
