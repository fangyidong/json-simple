

#### Example 1-1 - Encode a JSON object ####
```
  //import org.json.simple.JSONObject;
  
  JSONObject obj=new JSONObject();
  obj.put("name","foo");
  obj.put("num",new Integer(100));
  obj.put("balance",new Double(1000.21));
  obj.put("is_vip",new Boolean(true));
  obj.put("nickname",null);
  System.out.print(obj);
```
> Result: {"balance":1000.21,"num":100,"nickname":null,"is\_vip":true,"name":"foo"}

> JSONObject is subclass of java.util.HashMap. No ordering is provided. If you need strict ordering of elements use JSONValue.toJSONString( map ) method with ordered map implementation such as java.util.LinkedHashMap (see example 1-3).
> > Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 1-2 - Encode a JSON object - Streaming ####
```
  //import org.json.simple.JSONObject;
  
  JSONObject obj=new JSONObject();
  obj.put("name","foo");
  obj.put("num",new Integer(100));
  obj.put("balance",new Double(1000.21));
  obj.put("is_vip",new Boolean(true));
  obj.put("nickname",null);
  StringWriter out = new StringWriter();
  obj.writeJSONString(out);
  String jsonText = out.toString();
  System.out.print(jsonText);
```

> Result: {"balance":1000.21,"num":100,"nickname":null,"is\_vip":true,"name":"foo"}

> JSONObject is subclass of java.util.HashMap. No ordering is provided. If you need strict ordering of elements use JSONValue.toJSONString( map ) method with ordered map implementation such as java.util.LinkedHashMap (see example 1-3).
> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 1-3 - Encode a JSON object - Using Map ####
```
  //import java.util.LinkedHashMap;
  //import java.util.Map;
  //import org.json.simple.JSONValue;
  
  Map obj=new LinkedHashMap();
  obj.put("name","foo");
  obj.put("num",new Integer(100));
  obj.put("balance",new Double(1000.21));
  obj.put("is_vip",new Boolean(true));
  obj.put("nickname",null);
  String jsonText = JSONValue.toJSONString(obj);
  System.out.print(jsonText);
```
> Result: {"name":"foo","num":100,"balance":1000.21,"is\_vip":true,"nickname":null}

> Now the order of the object entries is preserved, which is different from example 1-1.
> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 1-4 - Encode a JSON object - Using Map and streaming ####
```
  //import java.util.LinkedHashMap;
  //import java.util.Map;
  //import org.json.simple.JSONValue;
  
   Map obj=new LinkedHashMap();
   obj.put("name","foo");
   obj.put("num",new Integer(100));
   obj.put("balance",new Double(1000.21));
   obj.put("is_vip",new Boolean(true));
   obj.put("nickname",null);
   StringWriter out = new StringWriter();
   JSONValue.writeJSONString(obj, out);
   String jsonText = out.toString();
   System.out.print(jsonText);
```
> Result: {"name":"foo","num":100,"balance":1000.21,"is\_vip":true,"nickname":null}

> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 2-1 - Encode a JSON array ####
```
  //import org.json.simple.JSONArray;
  
  JSONArray list = new JSONArray();
  list.add("foo");
  list.add(new Integer(100));
  list.add(new Double(1000.21));
  list.add(new Boolean(true));
  list.add(null);
  System.out.print(list);
```
> Result: ["foo",100,1000.21,true,null]

#### Example 2-2 - Encode a JSON array - Streaming ####
```
  //import org.json.simple.JSONArray;
  
  JSONArray list = new JSONArray();
  list.add("foo");
  list.add(new Integer(100));
  list.add(new Double(1000.21));
  list.add(new Boolean(true));
  list.add(null);
  StringWriter out = new StringWriter();
  list.writeJSONString(out);
  String jsonText = out.toString();
  System.out.print(jsonText);
```
> Result: ["foo",100,1000.21,true,null]

> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 2-3 - Encode a JSON array - Using List ####
```
  //import org.json.simple.JSONValue;
  
  LinkedList list = new LinkedList();
  list.add("foo");
  list.add(new Integer(100));
  list.add(new Double(1000.21));
  list.add(new Boolean(true));
  list.add(null);
  String jsonText = JSONValue.toJSONString(list);
  System.out.print(jsonText);
```
> Result: ["foo",100,1000.21,true,null]

> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 2-4 - Encode a JSON array - Using List and streaming ####
```
  //import org.json.simple.JSONValue;

  LinkedList list = new LinkedList();
  list.add("foo");
  list.add(new Integer(100));
  list.add(new Double(1000.21));
  list.add(new Boolean(true));
  list.add(null);
  StringWriter out = new StringWriter();
  JSONValue.writeJSONString(list, out);
  String jsonText = out.toString();
  System.out.print(jsonText);
```
> Result: ["foo",100,1000.21,true,null]

> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.

#### Example 3 - Merge two JSON objects ####
```
  //import org.json.simple.JSONObject;
  
  JSONObject obj1 = new JSONObject();
  obj1.put("name","foo");
  obj1.put("num",new Integer(100));
  obj1.put("balance",new Double(1000.21));
  		
  JSONObject obj2 = new JSONObject();
  obj2.put("is_vip",new Boolean(true));
  obj2.put("nickname",null);
  obj2.putAll(obj1);
  System.out.print(obj2);
```
> Result: {"balance":1000.21,"num":100,"nickname":null,"is\_vip":true,"name":"foo"}, the same as the one of Example 1.

#### Example 4 - Merge two JSON arrays ####
```
  JSONArray list1 = new JSONArray();
  list1.add("foo");
  list1.add(new Integer(100));
  list1.add(new Double(1000.21));
  
  JSONArray list2 = new JSONArray();
  list2.add(new Boolean(true));
  list2.add(null);
  list2.addAll(list1);
  System.out.print(list2);
```
> Result: [true,null,"foo",100,1000.21], the order of which is different from the one of Example 2.

#### Example 5-1 - Combination of JSON primitives, JSON object and JSON arrays ####
```
  JSONArray list1 = new JSONArray();
  list1.add("foo");
  list1.add(new Integer(100));
  list1.add(new Double(1000.21));
  
  JSONArray list2 = new JSONArray();
  list2.add(new Boolean(true));
  list2.add(null);
		
  JSONObject obj = new JSONObject();
  obj.put("name","foo");
  obj.put("num",new Integer(100));
  obj.put("balance",new Double(1000.21));
  obj.put("is_vip",new Boolean(true));
  obj.put("nickname",null);
    
  obj.put("list1", list1);
  obj.put("list2", list2);
		
  System.out.println(obj);
```
> Result: {"balance":1000.21,"list2":[true,null],"num":100,"list1":["foo",100,1000.21],"nickname":null,"is\_vip":true,"name":"foo"}

#### Example 5-2 - Combination of JSON primitives, Map and List ####
```
  Map m1 = new LinkedHashMap();
  Map m2 = new HashMap();
  List  l1 = new LinkedList();

  m1.put("k11","v11");
  m1.put("k12","v12");
  m1.put("k13", "v13");
  m2.put("k21","v21");
  m2.put("k22","v22");
  m2.put("k23","v23");
  l1.add(m1);
  l1.add(m2);

  String jsonString = JSONValue.toJSONString(l1);
		
  System.out.println(jsonString);
```
> Result: [{"k11":"v11","k12":"v12","k13":"v13"},{"k22":"v22","k21":"v21","k23":"v23"}]

#### Example 5-3 - Combination of JSON primitives, JSONObject, Map and List, and streaming ####
```
  StringWriter out = new StringWriter();
        
  JSONObject obj = new JSONObject();
  LinkedHashMap m1 = new LinkedHashMap();
  LinkedList l1 = new LinkedList();
  obj.put("k1", "v1");
  obj.put("k2", m1);
  obj.put("k3", l1);
  m1.put("mk1", "mv1");
  l1.add("lv1");
  l1.add("lv2");
  m1.put("mk2", l1);
        
  obj.writeJSONString(out);
  System.out.println("jsonString:");
  System.out.println(out.toString());
  String jsonString = obj.toJSONString();
  System.out.println(jsonString);
```
> Result:
```
  jsonString:
  {"k3":["lv1","lv2"],"k1":"v1","k2":{"mk1":"mv1","mk2":["lv1","lv2"]}}
  {"k3":["lv1","lv2"],"k1":"v1","k2":{"mk1":"mv1","mk2":["lv1","lv2"]}}
```

#### Example 6-1 - Customize JSON outputs ####
```
/*class User implements JSONAware{
	private int id;
	private String name;
	private String password;
	
	public User(int id, String name, String password){
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		
		sb.append(JSONObject.escape("userName"));
		sb.append(":");
		sb.append("\"" + JSONObject.escape(name) + "\"");
		
		sb.append(",");
		
		sb.append(JSONObject.escape("ID"));
		sb.append(":");
		sb.append(id);
		
		sb.append("}");
		
		return sb.toString();
	}
}*/

  JSONArray users = new JSONArray();
  users.add(new User(123,"foo1", "secret1"));
  users.add(new User(124,"foo2", "secret2"));
  users.add(new User(125,"\"foo2\"", "secret2"));
  System.out.println(users);
```
> Result: [{userName:"foo1",ID:123},{userName:"foo2",ID:124},{userName:"\"foo2\"",ID:125}]

> User.toJSONString() seems to be a bit complicated. The purpose is to demonstrate the usage of JSONObject.escape(). It can be simpler:
```
  public String toJSONString(){
    JSONObject obj = new JSONObject();
    obj.put("userName", name);
    obj.put("ID", new Integer(id));
    return obj.toString();
  }
```
> Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information. (Note: If you are using version 1.0.2 or earlier, you need to override Object.toString() of your bean to get customized output.)

#### Example 6-2 - Customize JSON outputs - Streaming ####
```
/*class User implements JSONStreamAware{
	private int id;
	private String name;
	private String password;
	
	public User(int id, String name, String password){
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
       public void writeJSONString (Writer out) throws IOException{
                LinkedHashMap obj = new LinkedHashMap();
                obj.put("userName", name);
                obj.put("ID", new Integer(id));
                JSONValue.writeJSONString(obj, out);
       }
}*/

  JSONArray users = new JSONArray();
  users.add(new User(123,"foo1", "secret1"));
  users.add(new User(124,"foo2", "secret2"));
  users.add(new User(125,"\"foo2\"", "secret2"));
  StringWriter out = new StringWriter();
  users.writeJSONString(out);
  System.out.println(out.toString());
```
> Result: [{"userName":"foo1","ID":123},{"userName":"foo2","ID":124},{"userName":"\"foo2\"","ID":125}]

> Please note that you don't have to implement JSONStreamAware to support streaming output of your bean, you can only implement JSONAware instead of JSONStreamAware. In the latter case, JSONAware.toString() is called and the result is written to the output stream. You can implement JSONStreamAware for better performance. If a class implements both JSONStreamAware and JSONAware, JSONStreamAware is given precedence while streaming. Please refer [Mapping Between JSON and Java Entities](MappingBetweenJSONAndJavaEntities.md) for more information.