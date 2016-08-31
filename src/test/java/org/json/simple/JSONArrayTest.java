package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that JSONArray hasn't regressed in functionality or breaks its API contract. Deprecated warnings are
 * suppressed since it is intentionally testing deprecated code for backwards compatibility. */
@SuppressWarnings("deprecation")
public class JSONArrayTest{
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

    /** Ensures a boolean array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testBooleanArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((boolean[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new boolean[0]));
        Assert.assertEquals("[true]", JSONArray.toJSONString(new boolean[]{true}));
        Assert.assertEquals("[true,false,true]", JSONArray.toJSONString(new boolean[]{true, false, true}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((boolean[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new boolean[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new boolean[]{true}, writer);
        Assert.assertEquals("[true]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new boolean[]{true, false, true}, writer);
        Assert.assertEquals("[true,false,true]", writer.toString());
    }

    /** Ensures a byte array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testByteArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((byte[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new byte[0]));
        Assert.assertEquals("[12]", JSONArray.toJSONString(new byte[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new byte[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((byte[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new byte[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new byte[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new byte[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures a char array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testCharArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((char[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new char[0]));
        Assert.assertEquals("[\"a\"]", JSONArray.toJSONString(new char[]{'a'}));
        Assert.assertEquals("[\"a\",\"b\",\"c\"]", JSONArray.toJSONString(new char[]{'a', 'b', 'c'}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((char[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new char[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new char[]{'a'}, writer);
        Assert.assertEquals("[\"a\"]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new char[]{'a', 'b', 'c'}, writer);
        Assert.assertEquals("[\"a\",\"b\",\"c\"]", writer.toString());
    }

    /** Ensures a double array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testDoubleArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((double[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new double[0]));
        Assert.assertEquals("[12.8]", JSONArray.toJSONString(new double[]{12.8}));
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(new double[]{-7.1, 22.234, 86.7, -99.02}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((double[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new double[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new double[]{12.8}, writer);
        Assert.assertEquals("[12.8]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new double[]{-7.1, 22.234, 86.7, -99.02}, writer);
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
    }

    /** Ensures a float array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testFloatArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((float[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new float[0]));
        Assert.assertEquals("[12.8]", JSONArray.toJSONString(new float[]{12.8f}));
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(new float[]{-7.1f, 22.234f, 86.7f, -99.02f}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((float[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new float[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new float[]{12.8f}, writer);
        Assert.assertEquals("[12.8]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new float[]{-7.1f, 22.234f, 86.7f, -99.02f}, writer);
        Assert.assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
    }

    /** Ensures a int array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testIntArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((int[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new int[0]));
        Assert.assertEquals("[12]", JSONArray.toJSONString(new int[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new int[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((int[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new int[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new int[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new int[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures JSONArray is encode as a JSON string. */
    @Test
    public void testJSONArray(){
        final JSONArray jsonArray = new JSONArray();
        Assert.assertEquals("[]", jsonArray.toJSONString());
    }

    /** Ensures JSONArray is encode as a JSON string. */
    @Test
    public void testJSONArrayCollection(){
        final ArrayList<Object> testList = new ArrayList<Object>();
        testList.add("First item");
        testList.add("Second item");
        final JSONArray jsonArray = new JSONArray(testList);
        Assert.assertEquals("[\"First item\",\"Second item\"]", jsonArray.toJSONString());
    }

    /** Ensures a long array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testLongArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((long[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new long[0]));
        Assert.assertEquals("[12]", JSONArray.toJSONString(new long[]{12}));
        Assert.assertEquals("[-7,22,9223372036854775807,-99]", JSONArray.toJSONString(new long[]{-7, 22, 9223372036854775807L, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((long[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new long[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new long[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new long[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures a object array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testObjectArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((Object[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new Object[0]));
        Assert.assertEquals("[\"Hello\"]", JSONArray.toJSONString(new Object[]{"Hello"}));
        Assert.assertEquals("[\"Hello\",12,[1,2,3]]", JSONArray.toJSONString(new Object[]{"Hello", new Integer(12), new int[]{1, 2, 3}}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((Object[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new Object[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new Object[]{"Hello"}, writer);
        Assert.assertEquals("[\"Hello\"]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new Object[]{"Hello", new Integer(12), new int[]{1, 2, 3}}, writer);
        Assert.assertEquals("[\"Hello\",12,[1,2,3]]", writer.toString());
    }

    /** Ensures a short array can be encoded as a JSON string.
     * @throws IOException if the test failed. */
    @Test
    public void testShortArrayToString() throws IOException{
        Assert.assertEquals("null", JSONArray.toJSONString((short[])null));
        Assert.assertEquals("[]", JSONArray.toJSONString(new short[0]));
        Assert.assertEquals("[12]", JSONArray.toJSONString(new short[]{12}));
        Assert.assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new short[]{-7, 22, 86, -99}));
        StringWriter writer;
        writer = new StringWriter();
        JSONArray.writeJSONString((short[])null, writer);
        Assert.assertEquals("null", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new short[0], writer);
        Assert.assertEquals("[]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new short[]{12}, writer);
        Assert.assertEquals("[12]", writer.toString());
        writer = new StringWriter();
        JSONArray.writeJSONString(new short[]{-7, 22, 86, -99}, writer);
        Assert.assertEquals("[-7,22,86,-99]", writer.toString());
    }

    /** Ensures encoded collections are decoded.
     * @throws Exception if the test failed. */
    @SuppressWarnings("unchecked")
    @Test
    public void testToJSONStringCollection() throws Exception{
        /* The method is unchecked because JSONArray is an unparameterized type. */
        final HashSet<Object> testSet = new HashSet<Object>();
        testSet.add("First item");
        testSet.add("Second item");
        final JSONArray jsonArray = new JSONArray(testSet);
        final JSONParser parser = new JSONParser();
        final JSONArray parsedArray = (JSONArray)parser.parse(jsonArray.toJSONString());
        Assert.assertTrue(parsedArray.containsAll(jsonArray));
        Assert.assertTrue(jsonArray.containsAll(parsedArray));
        Assert.assertEquals(2, jsonArray.size());
    }

    /** Ensures collections are encode as a JSON string.
     * @throws Exception if the test failed. */
    @SuppressWarnings("unchecked")
    @Test
    public void testWriteJSONStringCollectionWriter() throws Exception{
        /* The method is unchecked because JSONArray is an unparameterized type. */
        final HashSet<Object> testSet = new HashSet<Object>();
        testSet.add("First item");
        testSet.add("Second item");
        final JSONArray jsonArray = new JSONArray(testSet);
        final StringWriter writer = new StringWriter();
        jsonArray.writeJSONString(writer);
        final JSONParser parser = new JSONParser();
        final JSONArray parsedArray = (JSONArray)parser.parse(writer.toString());
        Assert.assertTrue(parsedArray.containsAll(jsonArray));
        Assert.assertTrue(jsonArray.containsAll(parsedArray));
        Assert.assertEquals(2, jsonArray.size());
    }
}
