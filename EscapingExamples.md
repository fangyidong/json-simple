#### Escaping text that contains special characters ####
JSONObject.escape() escapes quotes,\, /, \r, \n, \b, \f, \t and other control characters. It can be used to escape JavaScript codes.

```
  String s = "\"foo\" is not \"bar\". specials: \b\r\n\f\t\\/";

  s = JSONObject.escape(s);
		
  System.out.println(s);
```

Result:
```
  \"foo\" is not \"bar\". specials: \b\r\n\f\t\\\/
```