package org.json.simple;

import junit.framework.TestCase;

/**
 * @author Mikhail Kantur<kami@slink.ws>
 */
public class PrettyStringTest extends TestCase {

	public void testObjectPrettyPrint() throws Exception {

		JSONObject json3 = new JSONObject();
		json3.put("ssubkey1", "ssubvalue1");
		json3.put("ssubkey2", "ssubvalue2");

		JSONObject json2 = new JSONObject();
		json2.put("subkey1", "subvalue1");
		json2.put("subkey2", json3);

		JSONObject json1 = new JSONObject();

		json1.put("key1", "value1");
		json1.put("key2", json2);
		json1.put("key3", json3);

		System.out.println(json1.toPrettyJSONString(2));
	}

	public void testArrayPrettyPrintA() throws Exception {
		JSONObject json3 = new JSONObject();
		json3.put("ssubkey1", "ssubvalue1");
		json3.put("ssubkey2", "ssubvalue2");
		JSONObject json2 = new JSONObject();
		json2.put("subkey1", "subvalue1");
		json2.put("subkey2", json3);
		JSONObject json1 = new JSONObject();
		json1.put("key1", "value1");
		json1.put("key2", json2);
		json1.put("key3", json3);
		JSONArray array = new JSONArray();
		array.add(json1);
		array.add(json1);
		array.add(json1);
		JSONObject test = new JSONObject();
		test.put("array", array);
		System.out.println(test.toPrettyJSONString(2));
	}

	public void testArrayPrettyPrintB() throws Exception {
		JSONObject json3 = new JSONObject();
		json3.put("ssubkey1", "ssubvalue1");
		json3.put("ssubkey2", "ssubvalue2");
		JSONObject json2 = new JSONObject();
		json2.put("subkey1", "subvalue1");
		json2.put("subkey2", json3);
		JSONObject json1 = new JSONObject();
		json1.put("key1", "value1");
		json1.put("key2", json2);
		json1.put("key3", json3);
		JSONArray array = new JSONArray();
		array.add(json1);
		array.add(json1);
		array.add(json1);
		System.out.println(array.toPrettyJSONString(2));
	}
}