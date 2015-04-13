

#### Example 1 - Server side JSP encoding ####
service.jsp:
```
  <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.simple.JSONObject"%>
  <%
    JSONObject obj=new JSONObject();
    obj.put("name","foo");
    obj.put("num",new Integer(100));
    obj.put("balance",new Double(1000.21));
    obj.put("is_vip",new Boolean(true));
    obj.put("nickname",null);
    out.print(obj);
    out.flush();
  %>
```

Please note that you need to place [json\_simple-1.1.jar](http://json-simple.googlecode.com/files/json_simple-1.1.jar) in WEB-INF/lib before running the JSP. Then the client side will get the resulting JSON text.

#### Example 2 - Client side XMLHttpRequest ####
client.html:
```
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<script type="text/javascript">
function createXMLHttpRequest(){
  // See http://en.wikipedia.org/wiki/XMLHttpRequest
  // Provide the XMLHttpRequest class for IE 5.x-6.x:
  if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
    try { return new ActiveXObject("Msxml2.XMLHTTP.6.0") } catch(e) {}
    try { return new ActiveXObject("Msxml2.XMLHTTP.3.0") } catch(e) {}
    try { return new ActiveXObject("Msxml2.XMLHTTP") } catch(e) {}
    try { return new ActiveXObject("Microsoft.XMLHTTP") } catch(e) {}
    throw new Error( "This browser does not support XMLHttpRequest." )
  };
  return new XMLHttpRequest();
}

var AJAX = createXMLHttpRequest();

function handler() {
  if(AJAX.readyState == 4 && AJAX.status == 200) {
      var json = eval('(' + AJAX.responseText +')');
      alert('Success. Result: name => ' + json.name + ',' + 'balance => ' + json.balance);
  }else if (AJAX.readyState == 4 && AJAX.status != 200) {
    alert('Something went wrong...');
  }
}

function show(){
  AJAX.onreadystatechange = handler;
  AJAX.open("GET", "service.jsp");
  AJAX.send("");
};
</script>

<body>
  <a href="#" onclick="javascript:show();"> Click here to get JSON data from the server side</a>
</html>
```

Please place client.html and service.jsp (see [Example 1](http://code.google.com/p/json-simple/wiki/JSPAndAJAXExamples#Example_1_-_Server_side_JSP_encoding)) in the same directory and then open client.html in IE or Firefox, click the link and you'll get result.