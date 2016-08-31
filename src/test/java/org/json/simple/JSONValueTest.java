package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that JSONValue hasn't regressed in functionality or breaks its API contract. Deprecated warnings are
 * suppressed since it is intentionally testing deprecated code for backwards compatibility. */
@SuppressWarnings("deprecation")
public class JSONValueTest{
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

    /** Ensures an array of array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testArraysOfArraysToJsonString() throws IOException{
        StringWriter writer;
        final int[][][] nestedIntArray = new int[][][]{{{1}, {5}}, {{2}, {6}}};
        final String expectedNestedIntString = "[[[1],[5]],[[2],[6]]]";
        Assert.assertEquals(expectedNestedIntString, JSONValue.toJSONString(nestedIntArray));
        writer = new StringWriter();
        JSONValue.writeJSONString(nestedIntArray, writer);
        Assert.assertEquals(expectedNestedIntString, writer.toString());
        final String[][] nestedStringArray = new String[][]{{"a", "b"}, {"c", "d"}};
        final String expectedNestedStringString = "[[\"a\",\"b\"],[\"c\",\"d\"]]";
        Assert.assertEquals(expectedNestedStringString, JSONValue.toJSONString(nestedStringArray));
        writer = new StringWriter();
        JSONValue.writeJSONString(nestedStringArray, writer);
        Assert.assertEquals(expectedNestedStringString, writer.toString());
    }

    /** Ensures a boolean array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testBooleanArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((boolean[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new boolean[0]));
        Assert.assertEquals("[true]", JSONValue.toJSONString(new boolean[]{true}));
        Assert.assertEquals("[true,false,true]", JSONValue.toJSONString(new boolean[]{true, false, true}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((boolean[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new boolean[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new boolean[]{true}, writer);
        Assert.assertEquals("[true]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new boolean[]{true, false, true}, writer);
        Assert.assertEquals("[true,false,true]", writer.toString());
    }

    /** Ensures a byte array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testByteArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((byte[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new byte[0]));
        Assert.assertEquals("[12]", JSONValue.toJSONString(new byte[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new byte[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((byte[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new byte[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new byte[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new byte[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures a char array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testCharArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((char[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new char[0]));
        Assert.assertEquals("[\"a\"]", JSONValue.toJSONString(new char[]{'a'}));
        Assert.assertEquals("[\"a\",\"b\",\"c\"]", JSONValue.toJSONString(new char[]{'a', 'b', 'c'}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((char[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new char[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new char[]{'a'}, writer);
        Assert.assertEquals("[\"a\"]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new char[]{'a', 'b', 'c'}, writer);
        Assert.assertEquals("[\"a\",\"b\",\"c\"]", writer.toString());
    }

    /** Ensures a double array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testDoubleArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((double[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new double[0]));
        Assert.assertEquals("[12.8]", JSONValue.toJSONString(new double[]{12.8}));
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", JSONValue.toJSONString(new double[]{-7.1, 22.234, 86.7, -99.02}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((double[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new double[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new double[]{12.8}, writer);
        Assert.assertEquals("[12.8]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new double[]{-7.1, 22.234, 86.7, -99.02}, writer);
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
    }

    /** Ensures JSON values can be encoded.
     * @throws IOException if the test failed. */
    @SuppressWarnings("unchecked")
    @Test
    public void testEncodeJsonValues() throws IOException{
        final JSONArray array1 = new JSONArray();
        final JSONObject obj1 = new JSONObject();
        array1.add("abc\u0010a/");
        array1.add(new Integer(123));
        array1.add(new Double(222.123));
        array1.add(new Boolean(true));
        Assert.assertEquals("[\"abc\\u0010a\\/\",123,222.123,true]", array1.toString());
        obj1.put("array1", array1);
        Assert.assertEquals("{\"array1\":[\"abc\\u0010a\\/\",123,222.123,true]}", obj1.toString());
        obj1.remove("array1");
        array1.add(obj1);
        array1.add(null);
        Assert.assertEquals("[\"abc\\u0010a\\/\",123,222.123,true,{},null]", array1.toString());
        final List<Object> list = new ArrayList<Object>();
        list.add("abc\u0010a/");
        list.add(new Integer(123));
        list.add(new Double(222.123));
        list.add(new Boolean(true));
        list.add(null);
        Assert.assertEquals("[\"abc\\u0010a\\/\",123,222.123,true,null]", JSONArray.toJSONString(list));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("array1", list);
        Assert.assertEquals("{\"array1\":[\"abc\\u0010a\\/\",123,222.123,true,null]}", JSONObject.toJSONString(map));
        final Map<String, Object> m1 = new LinkedHashMap<String, Object>();
        final Map<String, Object> m2 = new LinkedHashMap<String, Object>();
        final List<Object> l1 = new LinkedList<Object>();
        m1.put("k11", "v11");
        m1.put("k12", "v12");
        m1.put("k13", "v13");
        m2.put("k21", "v21");
        m2.put("k22", "v22");
        m2.put("k23", "v23");
        l1.add(m1);
        l1.add(m2);
        String jsonString = JSONValue.toJSONString(l1);
        Assert.assertEquals("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\"},{\"k21\":\"v21\",\"k22\":\"v22\",\"k23\":\"v23\"}]", jsonString);
        StringWriter out = new StringWriter();
        JSONValue.writeJSONString(l1, out);
        jsonString = out.toString();
        Assert.assertEquals("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\"},{\"k21\":\"v21\",\"k22\":\"v22\",\"k23\":\"v23\"}]", jsonString);
        final List<Object> l2 = new LinkedList<Object>();
        final Map<String, Object> m3 = new LinkedHashMap<String, Object>();
        m3.put("k31", "v3");
        m3.put("k32", new Double(123.45));
        m3.put("k33", new Boolean(false));
        m3.put("k34", null);
        l2.add("vvv");
        l2.add("1.23456789123456789");
        l2.add(new Boolean(true));
        l2.add(null);
        m3.put("k35", l2);
        m1.put("k14", m3);
        out = new StringWriter();
        JSONValue.writeJSONString(l1, out);
        jsonString = out.toString();
        Assert.assertEquals("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\",\"k14\":{\"k31\":\"v3\",\"k32\":123.45,\"k33\":false,\"k34\":null,\"k35\":[\"vvv\",\"1.23456789123456789\",true,null]}},{\"k21\":\"v21\",\"k22\":\"v22\",\"k23\":\"v23\"}]", jsonString);
    }

    /** Ensures a float array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testFloatArrayToString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((float[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new float[0]));
        Assert.assertEquals("[12.8]", JSONValue.toJSONString(new float[]{12.8f}));
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", JSONValue.toJSONString(new float[]{-7.1f, 22.234f, 86.7f, -99.02f}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((float[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new float[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new float[]{12.8f}, writer);
        Assert.assertEquals("[12.8]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new float[]{-7.1f, 22.234f, 86.7f, -99.02f}, writer);
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
    }

    /** Ensures an int array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testIntArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((int[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new int[0]));
        Assert.assertEquals("[12]", JSONValue.toJSONString(new int[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new int[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((int[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new int[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new int[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new int[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures a long array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testLongArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((long[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new long[0]));
        Assert.assertEquals("[12]", JSONValue.toJSONString(new long[]{12}));
        Assert.assertEquals("[-7,22,9223372036854775807,-99]", JSONValue.toJSONString(new long[]{-7, 22, 9223372036854775807L, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((long[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new long[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new long[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new long[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures an Object array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testObjectArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((Object[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new Object[0]));
        Assert.assertEquals("[\"Hello\"]", JSONValue.toJSONString(new Object[]{"Hello"}));
        Assert.assertEquals("[\"Hello\",12,[1,2,3]]", JSONValue.toJSONString(new Object[]{"Hello", new Integer(12), new int[]{1, 2, 3}}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((Object[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new Object[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new Object[]{"Hello"}, writer);
        Assert.assertEquals("[\"Hello\"]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new Object[]{"Hello", new Integer(12), new int[]{1, 2, 3}}, writer);
        Assert.assertEquals("[\"Hello\",12,[1,2,3]]", writer.toString());
    }

    /** Ensures a short array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testShortArrayToJsonString() throws IOException{
        Assert.assertEquals("null", JSONValue.toJSONString((short[])null));
        Assert.assertEquals("[]", JSONValue.toJSONString(new short[0]));
        Assert.assertEquals("[12]", JSONValue.toJSONString(new short[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new short[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONValue.writeJSONString((short[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new short[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new short[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONValue.writeJSONString(new short[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }
}
