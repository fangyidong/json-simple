package org.json.simple.parser;

%%

%{
private StringBuffer sb=new StringBuffer();

%}

%switch
%unicode
%state STRING_BEGIN

HEX_D = [a-fA-F0-9]
INT = [-]?[0-9]+
DOUBLE = {INT}((\.[0-9]+)?([eE][-+]?[0-9]+)?)
WS = [ \t\r\n]
UNESCAPED_CH = [^\"\\]
%%

<STRING_BEGIN> \"	 			{ yybegin(YYINITIAL);return new Yytoken(Yytoken.TYPE_VALUE,sb.toString());}
<STRING_BEGIN> {UNESCAPED_CH}+	{ sb.append(yytext());}
<STRING_BEGIN> \\\" 			{sb.append('"');}
<STRING_BEGIN> \\\\				{sb.append('\\');}
<STRING_BEGIN> \\\/				{sb.append('/');}
<STRING_BEGIN> \\b				{sb.append('\b');}
<STRING_BEGIN> \\f				{sb.append('\f');}
<STRING_BEGIN> \\n				{sb.append('\n');}
<STRING_BEGIN> \\r				{sb.append('\r');}
<STRING_BEGIN> \\t				{sb.append('\t');}
<STRING_BEGIN> \\u{HEX_D}{HEX_D}{HEX_D}{HEX_D}	{	int ch=Integer.parseInt(yytext().substring(2),16);
													sb.append((char)ch);
												}
												
<YYINITIAL> \" 					{ sb.delete(0, sb.length());yybegin(STRING_BEGIN);}
<YYINITIAL> {INT}				{ Long val=Long.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE,val);}
<YYINITIAL> {DOUBLE}			{ Double val=Double.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE,val);}
<YYINITIAL> "true"|"false"		{ Boolean val=Boolean.valueOf(yytext()); return new Yytoken(Yytoken.TYPE_VALUE,val);}
<YYINITIAL> "null"				{ return new Yytoken(Yytoken.TYPE_VALUE,null);}
<YYINITIAL> "{"					{ return new Yytoken(Yytoken.TYPE_LEFT_BRACE,null);}
<YYINITIAL> "}"					{ return new Yytoken(Yytoken.TYPE_RIGHT_BRACE,null);}
<YYINITIAL> "["					{ return new Yytoken(Yytoken.TYPE_LEFT_SQUARE,null);}
<YYINITIAL> "]"					{ return new Yytoken(Yytoken.TYPE_RIGHT_SQUARE,null);}
<YYINITIAL> ","					{ return new Yytoken(Yytoken.TYPE_COMMA,null);}
<YYINITIAL> ":"					{ return new Yytoken(Yytoken.TYPE_COLON,null);}
<YYINITIAL> {WS}+		    	{}
