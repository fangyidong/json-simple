

#### Example 1 - Convenient way: Use JSONValue ####
```
  System.out.println("=======decode=======");
		
  String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
  Object obj=JSONValue.parse(s);
  JSONArray array=(JSONArray)obj;
  System.out.println("======the 2nd element of array======");
  System.out.println(array.get(1));
  System.out.println();
		
  JSONObject obj2=(JSONObject)array.get(1);
  System.out.println("======field \"1\"==========");
  System.out.println(obj2.get("1"));	

		
  s="{}";
  obj=JSONValue.parse(s);
  System.out.println(obj);
		
  s="[5,]";
  obj=JSONValue.parse(s);
  System.out.println(obj);
		
  s="[5,,2]";
  obj=JSONValue.parse(s);
  System.out.println(obj);
```

JSONObject is a java.util.Map and JSONArray is a java.util.List, so you can access them with standard operations of Map or List. Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information on entity mapping while parsing.

#### Example 2 - Faster way: Reuse instance of JSONParser ####
```
  JSONParser parser=new JSONParser();

  System.out.println("=======decode=======");
		
  String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
  Object obj=parser.parse(s);
  JSONArray array=(JSONArray)obj;
  System.out.println("======the 2nd element of array======");
  System.out.println(array.get(1));
  System.out.println();
		
  JSONObject obj2=(JSONObject)array.get(1);
  System.out.println("======field \"1\"==========");
  System.out.println(obj2.get("1"));	

		
  s="{}";
  obj=parser.parse(s);
  System.out.println(obj);
		
  s="[5,]";
  obj=parser.parse(s);
  System.out.println(obj);
		
  s="[5,,2]";
  obj=parser.parse(s);
  System.out.println(obj);
```

JSONObject is a java.util.Map and JSONArray is a java.util.List, so you can access them
with standard operations of Map or List. Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information on entity mapping while parsing.

#### Example 3 - Exception handling ####
```
  String jsonText = "[[null, 123.45, \"a\\tb c\"]}, true";
  JSONParser parser = new JSONParser();
		
  try{
    parser.parse(jsonText);
  }
  catch(ParseException pe){
    System.out.println("position: " + pe.getPosition());
    System.out.println(pe);
  }
```
Result:
```
  position: 25
  Unexpected token RIGHT BRACE(}) at position 25.
```
Please refer [ParseException.java](http://code.google.com/p/json-simple/source/browse/trunk/src/org/json/simple/parser/ParseException.java) for more information.

#### Example 4 - Container factory ####
You can use [ContainerFactory](http://code.google.com/p/json-simple/source/browse/trunk/src/org/json/simple/parser/ContainerFactory.java) to create containers for parsed JSON objects and JSON arrays:
```
  String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
  JSONParser parser = new JSONParser();
  ContainerFactory containerFactory = new ContainerFactory(){
    public List creatArrayContainer() {
      return new LinkedList();
    }

    public Map createObjectContainer() {
      return new LinkedHashMap();
    }
			
  };
		
  try{
    Map json = (Map)parser.parse(jsonText, containerFactory);
    Iterator iter = json.entrySet().iterator();
    System.out.println("==iterate result==");
    while(iter.hasNext()){
      Map.Entry entry = (Map.Entry)iter.next();
      System.out.println(entry.getKey() + "=>" + entry.getValue());
    }
			
    System.out.println("==toJSONString()==");
    System.out.println(JSONValue.toJSONString(json));
  }
  catch(ParseException pe){
    System.out.println(pe);
  }
```
Result:
```
  ==iterate result==
  first=>123
  second=>[4, 5, 6]
  third=>789
  ==toJSONString()==
  {"first":123,"second":[4,5,6],"third":789}
```

If you don't specify a container factory, org.json.simple.JSONObject is used for a Map and org.json.simple.JSONArray is used for a List. Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information on entity mapping while parsing.

#### Example 5 - Stoppable SAX-like content handler ####
JSON.simple introduces a simplified and stoppable SAX-like content handler to process JSON text stream. The user can pause at any point of the logical input stream, processing other logic, then resume parsing if needed, or abort parsing if he gets the desired result, without having to wait for the whole input stream to finish. Then we have a very fast parser without sacrificing the flexibility. Here's an example of finding values of any object entry that matches a desired key:

KeyFinder.java:
```
class KeyFinder implements ContentHandler{
  private Object value;
  private boolean found = false;
  private boolean end = false;
  private String key;
  private String matchKey;
	
  public void setMatchKey(String matchKey){
    this.matchKey = matchKey;
  }
	
  public Object getValue(){
    return value;
  }
	
  public boolean isEnd(){
    return end;
  }
	
  public void setFound(boolean found){
    this.found = found;
  }
	
  public boolean isFound(){
    return found;
  }
	
  public void startJSON() throws ParseException, IOException {
    found = false;
    end = false;
  }

  public void endJSON() throws ParseException, IOException {
    end = true;
  }

  public boolean primitive(Object value) throws ParseException, IOException {
    if(key != null){
      if(key.equals(matchKey)){
        found = true;
	this.value = value;
	key = null;
	return false;
      }
    }
    return true;
  }

  public boolean startArray() throws ParseException, IOException {
    return true;
  }

	
  public boolean startObject() throws ParseException, IOException {
    return true;
  }

  public boolean startObjectEntry(String key) throws ParseException, IOException {
    this.key = key;
    return true;
  }
	
  public boolean endArray() throws ParseException, IOException {
    return false;
  }

  public boolean endObject() throws ParseException, IOException {
    return true;
  }

  public boolean endObjectEntry() throws ParseException, IOException {
    return true;
  }
}
```

Main logic:
```
  String jsonText = "{\"first\": 123, \"second\": [{\"k1\":{\"id\":\"id1\"}}, 4, 5, 6, {\"id\": 123}], \"third\": 789, \"id\": null}";
  JSONParser parser = new JSONParser();
  KeyFinder finder = new KeyFinder();
  finder.setMatchKey("id");
  try{
    while(!finder.isEnd()){
      parser.parse(jsonText, finder, true);
      if(finder.isFound()){
        finder.setFound(false);
        System.out.println("found id:");
        System.out.println(finder.getValue());
      }
    }		
  }
  catch(ParseException pe){
    pe.printStackTrace();
  }

```

Result:
```
  found id:
  id1
  found id:
  123
  found id:
  null
```

Please refer [ContentHandler.java](http://code.google.com/p/json-simple/source/browse/trunk/src/org/json/simple/parser/ContentHandler.java) for more information.

#### Example 6 - Build whole object graph on top of SAX-like content handler ####

Please note that JSON.simple has provided the build in functionality to do the same work. Please refer above examples for more information. Here is just an example to show how to use the SAX-like interface in a slightly more complex scenario.


Transformer.java:
```
class Transformer implements ContentHandler{
        private Stack valueStack;
        
        public Object getResult(){
            if(valueStack == null || valueStack.size() == 0)
                return null;
            return valueStack.peek();
        }
        
        public boolean endArray () throws ParseException, IOException {
            trackBack();
            return true;
        }

        public void endJSON () throws ParseException, IOException {}

        public boolean endObject () throws ParseException, IOException {
            trackBack();
            return true;
        }

        public boolean endObjectEntry () throws ParseException, IOException {
            Object value = valueStack.pop();
            Object key = valueStack.pop();
            Map parent = (Map)valueStack.peek();
            parent.put(key, value);
            return true;
        }

        private void trackBack(){
            if(valueStack.size() > 1){
                Object value = valueStack.pop();
                Object prev = valueStack.peek();
                if(prev instanceof String){
                    valueStack.push(value);
                }
            }
        }
        
        private void consumeValue(Object value){
            if(valueStack.size() == 0)
                valueStack.push(value);
            else{
                Object prev = valueStack.peek();
                if(prev instanceof List){
                    List array = (List)prev;
                    array.add(value);
                }
                else{
                    valueStack.push(value);
                }
            }
        }
        
        public boolean primitive (Object value) throws ParseException, IOException {
            consumeValue(value);
            return true;
        }

        public boolean startArray () throws ParseException, IOException {
            List array = new JSONArray();
            consumeValue(array);
            valueStack.push(array);
            return true;
        }

        public void startJSON () throws ParseException, IOException {
            valueStack = new Stack();
        }

        public boolean startObject () throws ParseException, IOException {
            Map object = new JSONObject();
            consumeValue(object);
            valueStack.push(object);
            return true;
        }

        public boolean startObjectEntry (String key) throws ParseException, IOException {
            valueStack.push(key);
            return true;
        }
        
    }
```

Main logic:
```
    String jsonString = <Input JSON text>;
    Object value = null;
    JSONParser parser = new JSONParser();
    Transformer transformer = new Transformer();
        
    parser.parse(jsonString, transformer);
    value = transformer.getResult();
```

The result is similar to one return from the following code:
```
    String jsonString = <Input JSON text>;
    Object value = null;
    JSONParser parser = new JSONParser();
    value = parser.parse(jsonString);
```

#### Notes - Multithreading and extensions ####
JSONParser is NOT thread-safe. And please note that JSON string such as `[5,,,2]` is accepted by the parser and is treated as `[5,2]`. This doesn't violate the [JSON specification](http://www.ietf.org/rfc/rfc4627.txt), and it increases the error toleration of the input data. Some JSON grammar checker may need a 'strict' mode. Considering adding this feature.

Since it's a bit controversial on the extension of the parser (see comments of this wiki page), I'd like to make some clarifications on this topic here.

Some users may concern about exchanging important data, say medical information or financial data, between applications. I think you need to make sure the following things in such scenarios:

  1. You need to make sure you are using a reliable and full compliant encoder on the side of the source;
  1. Or if you also accept data from a non-trusted source, even a 'strict' decoder is inadequate. You may need extra validation rules to verify the source. For example, a user may send totally compliant `[5,2]` even though you expect `[5,0,2]`.

In both cases, a liberal parser will do nothing harmful to your application.

The reason of accepting something like `[5,,2]` is that:

  1. A careless user or an encoder may repeat a comma (such as by pressing the key too long :-), which is harmless;
  1. The comma is actually redundant syntactically, if two adjacent entities are recognized.

I know some users may be FUD in front of a liberal parser, but as I mentioned above, it's harmless and is allowed in RFC4627 (actually the author of RFC4627 adopts this feature in the reference implementation).

Please feel free to leave a comment or post in the discussion group if you have further concerns. Thanks.