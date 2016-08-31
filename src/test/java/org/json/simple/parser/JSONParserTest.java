/* $Id: JSONParserTest.java,v 1.1 2006/04/15 14:40:06 platform Exp $
 * Created on 2006-4-15 */
package org.json.simple.parser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Ensures that JSONParser hasn't regressed in functionality or breaks its API contract. Deprecated warnings are
 * suppressed since it is intentionally testing deprecated code for backwards compatibility. */
@SuppressWarnings("deprecation")
public class JSONParserTest{
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

    /** Ensures a ContainerFactory can be provided.
     * @throws Exception if the test fails. */
    @Test
    public void testDecodeContainerFactory() throws Exception{
        JSONParser parser;
        String input;
        parser = new JSONParser();
        input = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
        final ContainerFactory containerFactory = new ContainerFactory(){
            @Override
            public List<Integer> creatArrayContainer(){
                return new LinkedList<Integer>();
            }

            @Override
            public Map<String, ?> createObjectContainer(){
                return new LinkedHashMap<String, Object>();
            }
        };
        @SuppressWarnings("unchecked")
        final Map<String, Object> json = (Map<String, Object>)parser.parse(input, containerFactory);
        Assert.assertTrue(json instanceof LinkedHashMap);
        Assert.assertTrue(json.get("second") instanceof LinkedList);
    }

    /** Ensures a ContentHander can be used with the parser.
     * @throws Exception if the test failed. */
    @Test
    public void testDecodeContentHandler() throws Exception{
        JSONParser parser;
        String input;
        parser = new JSONParser();
        class KeyFinder implements ContentHandler{
            private boolean end   = false;
            private boolean found = false;
            private String  key;
            private String  matchKey;
            private Object  value;

            @Override
            public boolean endArray() throws ParseException, IOException{
                return false;
            }

            @Override
            public void endJSON() throws ParseException, IOException{
                this.end = true;
            }

            @Override
            public boolean endObject() throws ParseException, IOException{
                return true;
            }

            @Override
            public boolean endObjectEntry() throws ParseException, IOException{
                return true;
            }

            public Object getValue(){
                return this.value;
            }

            public boolean isEnd(){
                return this.end;
            }

            public boolean isFound(){
                return this.found;
            }

            @Override
            public boolean primitive(final Object value) throws ParseException, IOException{
                if(this.key != null){
                    if(this.key.equals(this.matchKey)){
                        this.found = true;
                        this.value = value;
                        this.key = null;
                        return false;
                    }
                }
                return true;
            }

            public void setFound(final boolean found){
                this.found = found;
            }

            public void setMatchKey(final String matchKey){
                this.matchKey = matchKey;
            }

            @Override
            public boolean startArray() throws ParseException, IOException{
                return true;
            }

            @Override
            public void startJSON() throws ParseException, IOException{
                this.found = false;
                this.end = false;
            }

            @Override
            public boolean startObject() throws ParseException, IOException{
                return true;
            }

            @Override
            public boolean startObjectEntry(final String key) throws ParseException, IOException{
                this.key = key;
                return true;
            }
        }
        ;
        input = "{\"first\": 123, \"second\": [{\"k1\":{\"id\":\"id1\"}}, 4, 5, 6, {\"id\": 123}], \"third\": 789, \"id\": null}";
        parser.reset();
        final KeyFinder keyFinder = new KeyFinder();
        keyFinder.setMatchKey("id");
        int i = 0;
        while(!keyFinder.isEnd()){
            parser.parse(input, keyFinder, true);
            if(keyFinder.isFound()){
                i++;
                keyFinder.setFound(false);
                if(i == 1){
                    Assert.assertEquals("id1", keyFinder.getValue());
                }
                if(i == 2){
                    Assert.assertTrue(keyFinder.getValue() instanceof Number);
                    Assert.assertEquals("123", String.valueOf(keyFinder.getValue()));
                }
                if(i == 3){
                    Assert.assertTrue(null == keyFinder.getValue());
                }
            }
        }
    }

    /** Ensures booleans, JSONObject, JSONArray, null, Numbers, and Strings can be decoded.
     * @throws Exception if the test failed. */
    @SuppressWarnings("unchecked")
    @Test
    public void testDecodeJsonValues() throws Exception{
        /* The method is unchecked since JSONArray and JSONObject don't have a parameterized type. */
        JSONParser parser;
        Object deserialized;
        String input;
        JSONObject expectedObject;
        JSONArray expectedArray;
        expectedObject = new JSONObject();
        expectedArray = new JSONArray();
        parser = new JSONParser();
        input = "{}";
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedObject, deserialized);
        parser = new JSONParser();
        input = "{,}";
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedObject, deserialized);
        parser = new JSONParser();
        input = "[]";
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedArray, deserialized);
        parser = new JSONParser();
        input = "[,]";
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedArray, deserialized);
        parser = new JSONParser();
        input = "[5,]";
        expectedArray.add(5);
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedArray.toString(), deserialized.toString());
        parser = new JSONParser();
        input = "[5,,2]";
        expectedArray.add(2);
        deserialized = parser.parse(input);
        Assert.assertEquals(expectedArray.toString(), deserialized.toString());
        parser = new JSONParser();
        input = "\"hello\\bworld\\\"abc\\tdef\\\\ghi\\rjkl\\n123\\u4e2d\"";
        deserialized = parser.parse(input);
        Assert.assertEquals("hello\bworld\"abc\tdef\\ghi\rjkl\n123中", deserialized.toString());
        parser = new JSONParser();
        input = "true";
        deserialized = parser.parse(input);
        Assert.assertTrue(Boolean.TRUE == deserialized);
        parser = new JSONParser();
        input = "false";
        deserialized = parser.parse(input);
        Assert.assertTrue(Boolean.FALSE == deserialized);
        parser = new JSONParser();
        input = "null";
        deserialized = parser.parse(input);
        Assert.assertTrue(null == deserialized);
        parser = new JSONParser();
        input = "9001";
        deserialized = parser.parse(input);
        Assert.assertTrue(new Long("9001").equals(deserialized));
        parser = new JSONParser();
        input = "9000.1";
        deserialized = parser.parse(input);
        Assert.assertTrue(new Double("9000.1").equals(deserialized));
    }

    /** Ensures a nested object is decoded.
     * @throws Exception if the test failed. */
    @Test
    public void testDecodeNestedObject() throws Exception{
        JSONParser parser;
        Object deserialized;
        JSONArray array;
        JSONObject object;
        String input;
        parser = new JSONParser();
        input = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
        deserialized = parser.parse(input);
        array = (JSONArray)deserialized;
        Assert.assertEquals("{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}", array.get(1).toString());
        object = (JSONObject)array.get(1);
        Assert.assertEquals("{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}", object.get("1").toString());
    }

    /** Ensures problems are detected in the parse how they ought to. */
    @Test
    public void testDecodeProblems(){
        JSONParser parser;
        String input;
        parser = new JSONParser();
        input = "{\"name\":";
        try{
            parser.parse(input);
        }catch(final ParseException pe){
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_TOKEN, pe.getErrorType());
            Assert.assertEquals(8, pe.getPosition());
        }
        input = "{\"name\":}";
        try{
            parser.parse(input);
        }catch(final ParseException pe){
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_TOKEN, pe.getErrorType());
            Assert.assertEquals(8, pe.getPosition());
        }
        input = "{\"name";
        try{
            parser.parse(input);
        }catch(final ParseException pe){
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_TOKEN, pe.getErrorType());
            Assert.assertEquals(6, pe.getPosition());
        }
        input = "[[null, 123.45, \"a\\\tb c\"}, true]";
        try{
            parser.parse(input);
        }catch(final ParseException pe){
            Assert.assertEquals(ParseException.ERROR_UNEXPECTED_TOKEN, pe.getErrorType());
            Assert.assertEquals(24, pe.getPosition());
        }
    }
}
