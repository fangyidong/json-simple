/*
 * $Id: Test.java,v 1.1 2006/04/15 14:40:06 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple;

import java.util.List;

import junit.framework.TestCase;


/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class Test extends TestCase{

	public void testDecode() throws Exception{
		System.out.println("=======decode=======");
		
		String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		Object obj=JSONValue.parse(s);
		JSONArray array=(JSONArray)obj;
		System.out.println("======the 2nd element of array======");
		System.out.println(array.get(1));
		System.out.println();
		assertEquals("{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}",array.get(1).toString());
		
		JSONObject obj2=(JSONObject)array.get(1);
		System.out.println("======field \"1\"==========");
		System.out.println(obj2.get("1"));	
		assertEquals("{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}",obj2.get("1").toString());
		
		s="{}";
		obj=JSONValue.parse(s);
		assertEquals("{}",obj.toString());
		
		s="[5,]";
		obj=JSONValue.parse(s);
		assertEquals("[5]",obj.toString());
		
		s="[5,,2]";
		obj=JSONValue.parse(s);
		assertEquals("[5,2]",obj.toString());
		
		s="[\"hello\\bworld\\\"abc\\tdef\\\\ghi\\rjkl\\n123\\u4e2d\"]";
		obj=JSONValue.parse(s);
		assertEquals("hello\bworld\"abc\tdef\\ghi\rjkl\n123中",((List)obj).get(0).toString());
		
	}
	
	public void testEncode() throws Exception{
		System.out.println("=======encode=======");
		
		JSONArray array1=new JSONArray();
		array1.add("abc\u0010a/");
		array1.add(new Integer(123));
		array1.add(new Double(222.123));
		array1.add(new Boolean(true));
		System.out.println("======array1==========");
		System.out.println(array1);
		System.out.println();
		assertEquals("[\"abc\\u0010a\\/\",123,222.123,true]",array1.toString());
		
		JSONObject obj1=new JSONObject();
		obj1.put("name","fang");
		obj1.put("age",new Integer(27));
		obj1.put("is_developer",new Boolean(true));
		obj1.put("weight",new Double(60.21));
		obj1.put("array1",array1);
		System.out.println("======obj1 with array1===========");
		System.out.println(obj1);
		System.out.println();
		assertEquals("{\"array1\":[\"abc\\u0010a\\/\",123,222.123,true],\"weight\":60.21,\"age\":27,\"name\":\"fang\",\"is_developer\":true}",obj1.toString());
		
		obj1.remove("array1");
		array1.add(obj1);
		System.out.println("======array1 with obj1========");
		System.out.println(array1);
		System.out.println();
		assertEquals("[\"abc\\u0010a\\/\",123,222.123,true,{\"weight\":60.21,\"age\":27,\"name\":\"fang\",\"is_developer\":true}]",array1.toString());
	}
}
