/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A JSON array. JSONObject supports java.util.List interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONArray extends ArrayList implements JSONAware, JSONStreamAware {
	private static final long serialVersionUID = 3957988303675231981L;
	
	/**
	 * Constructs an empty JSONArray.
	 */
	public JSONArray(){
		super();
	}
	
	/**
	 * Constructs a JSONArray containing the elements of the specified
	 * collection, in the order they are returned by the collection's iterator.
	 * 
	 * @param c the collection whose elements are to be placed into this JSONArray
	 */
	public JSONArray(Collection c){
		super(c);
	}
	
    /**
     * Encode a list into JSON text and write it to out. 
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param collection
     * @param out
     */
	public static void writeJSONString(Collection collection, Writer out) throws IOException{
		if(collection == null){
			out.write("null");
			return;
		}
		
		boolean first = true;
		Iterator iter=collection.iterator();
		
        out.write('[');
		while(iter.hasNext()){
            if(first)
                first = false;
            else
                out.write(',');
            
			Object value=iter.next();
			if(value == null){
				out.write("null");
				continue;
			}
			
			JSONValue.writeJSONString(value, out);
		}
		out.write(']');
	}
	
	public void writeJSONString(Writer out) throws IOException{
		writeJSONString(this, out);
	}
	
	/**
	 * Convert a list to JSON text. The result is a JSON array. 
	 * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
	 * 
	 * @see org.json.simple.JSONValue#toJSONString(Object)
	 * 
	 * @param collection
	 * @return JSON text, or "null" if list is null.
	 */
	public static String toJSONString(Collection collection){
		final StringWriter writer = new StringWriter();
		
		try {
			writeJSONString(collection, writer);
			return writer.toString();
		} catch(IOException e){
			// This should never happen for a StringWriter
			throw new RuntimeException(e);
		}
	}

	private static void writeArray(Object array, Writer out) throws IOException{
		if(array == null){
			out.write("null");
			return;
		}
		
		final int length = Array.getLength(array);
		if(length == 0){
			out.write("[]");
			return;
		}
		
		out.write('[');
		writeArrayElement(array, 0, out);
		for(int i = 1; i < length; i++){
			out.write(',');
			writeArrayElement(array, i, out);
		}
		out.write(']');
	}
	
	private static void writeArrayElement(Object array, int index, Writer out) throws IOException{
		Object value = Array.get(array, index);
		
		if(value instanceof Character){
			JSONValue.writeJSONString(String.valueOf(value), out);
			return;
		}
		
		JSONValue.writeJSONString(value, out);
	}
	
	private static String toJSONStringInternal(Object array){
		final StringWriter writer = new StringWriter();
		
		try {
			writeArray(array, writer);
			return writer.toString();
		} catch(IOException e){
			// This should never happen for a StringWriter
			throw new RuntimeException(e);
		}
	}

	public static void writeJSONString(byte[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(byte[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(short[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(short[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(int[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(int[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(long[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(long[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(float[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(float[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(double[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(double[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(boolean[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(boolean[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(char[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(char[] array){
		return toJSONStringInternal(array);
	}
	
	public static void writeJSONString(Object[] array, Writer out) throws IOException{
		writeArray(array, out);
	}
	
	public static String toJSONString(Object[] array){
		return toJSONStringInternal(array);
	}
	
	public String toJSONString(){
		return toJSONString(this);
	}

	/**
	 * Returns a string representation of this array. This is equivalent to
	 * calling {@link JSONArray#toJSONString()}.
	 */
	public String toString() {
		return toJSONString();
	}
}
