package org.json.simple;

%%

%{
private StringBuilder sb=new StringBuilder();

int getPosition(){
	return yychar;
}

%}

%table
%unicode
%state STRING_BEGIN

%yylexthrow DeserializationException
%char

HEX_D = [a-fA-F0-9]
DOUBLE = [-]?[0-9]+((\.[0-9]+)?([eE][-+]?[0-9]+)?)
WS = [ \t\r\n]
UNESCAPED_CH = [^\"\\]
FALLBACK_CH = .
%%

<STRING_BEGIN> \"	 			{ yybegin(YYINITIAL);return new Yytoken(Yytoken.Types.DATUM, sb.toString());}
<STRING_BEGIN> {UNESCAPED_CH}+	{ sb.append(yytext());}
<STRING_BEGIN> \\\" 			{sb.append('"');}
<STRING_BEGIN> \\\\				{sb.append('\\');}
<STRING_BEGIN> \\\/				{sb.append('/');}
<STRING_BEGIN> \\b				{sb.append('\b');}
<STRING_BEGIN> \\f				{sb.append('\f');}
<STRING_BEGIN> \\n				{sb.append('\n');}
<STRING_BEGIN> \\r				{sb.append('\r');}
<STRING_BEGIN> \\t				{sb.append('\t');}
<STRING_BEGIN> \\u{HEX_D}{HEX_D}{HEX_D}{HEX_D}	{	try{
			int ch=Integer.parseInt(yytext().substring(2),16);
			sb.append((char)ch);
		}catch(Exception e){
			/* The lexer is broken if it can build a 4 byte character code and fail to append the character. */
			throw new DeserializationException(yychar, DeserializationException.Problems.UNEXPECTED_EXCEPTION, e);
		}
	}
<STRING_BEGIN> \\				{sb.append('\\');}
												
<YYINITIAL> \" 					{ sb = null; sb = new StringBuilder(); yybegin(STRING_BEGIN);}
<YYINITIAL> {DOUBLE}			{ java.math.BigDecimal val= new java.math.BigDecimal(yytext()); return new Yytoken(Yytoken.Types.DATUM, val);}
<YYINITIAL> "true"|"false"		{ Boolean val=Boolean.valueOf(yytext()); return new Yytoken(Yytoken.Types.DATUM, val);}
<YYINITIAL> "null"				{ return new Yytoken(Yytoken.Types.DATUM, null);}
<YYINITIAL> "{"					{ return new Yytoken(Yytoken.Types.LEFT_BRACE, null);}
<YYINITIAL> "}"					{ return new Yytoken(Yytoken.Types.RIGHT_BRACE, null);}
<YYINITIAL> "["					{ return new Yytoken(Yytoken.Types.LEFT_SQUARE, null);}
<YYINITIAL> "]"					{ return new Yytoken(Yytoken.Types.RIGHT_SQUARE, null);}
<YYINITIAL> ","					{ return new Yytoken(Yytoken.Types.COMMA, null);}
<YYINITIAL> ":"					{ return new Yytoken(Yytoken.Types.COLON, null);}
<YYINITIAL> {WS}+		    	{}
<YYINITIAL> {FALLBACK_CH}		{ throw new DeserializationException(yychar, DeserializationException.Problems.UNEXPECTED_CHARACTER, new Character(yycharat(0)));}
