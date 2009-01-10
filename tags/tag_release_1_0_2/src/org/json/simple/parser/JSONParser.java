/*
 * $Id: JSONParser.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple.parser;

import java.io.Reader;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONParser {
	public static final int S_INIT=0;
	public static final int S_IN_FINISHED_VALUE=1;//string,number,boolean,null,object,array
	public static final int S_IN_OBJECT=2;
	public static final int S_IN_ARRAY=3;
	public static final int S_PASSED_PAIR_KEY=4;
	public static final int S_IN_ERROR=-1;
	
	private LinkedList statusStack = new LinkedList();
	private LinkedList valueStack = new LinkedList();
	private Yylex lexer = new Yylex((Reader)null);
	private Yytoken token = null;
	private int status = S_INIT;
	
	private int peekStatus(LinkedList statusStack){
		if(statusStack.size()==0)
			return -1;
		Integer status=(Integer)statusStack.getFirst();
		return status.intValue();
	}
	
	private void reset(Reader in) throws Exception{
		statusStack.clear();
		valueStack.clear();
		lexer.yyreset(in);
		token = null;
		status = S_INIT;
	}
	
	public Object parse(Reader in) throws Exception{
		reset(in);
			
		try{
			do{
				token=lexer.yylex();
				if(token==null)
					token=new Yytoken(Yytoken.TYPE_EOF,null);
				switch(status){
				case S_INIT:
					switch(token.type){
					case Yytoken.TYPE_VALUE:
						status=S_IN_FINISHED_VALUE;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(token.value);
						break;
					case Yytoken.TYPE_LEFT_BRACE:
						status=S_IN_OBJECT;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(new JSONObject());
						break;
					case Yytoken.TYPE_LEFT_SQUARE:
						status=S_IN_ARRAY;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(new JSONArray());
						break;
					default:
						status=S_IN_ERROR;
					}//inner switch
					break;
					
				case S_IN_FINISHED_VALUE:
					if(token.type==Yytoken.TYPE_EOF)
						return valueStack.removeFirst();
					else
						return null;
					
				case S_IN_OBJECT:
					switch(token.type){
					case Yytoken.TYPE_COMMA:
						break;
					case Yytoken.TYPE_VALUE:
						if(token.value instanceof String){
							String key=(String)token.value;
							valueStack.addFirst(key);
							status=S_PASSED_PAIR_KEY;
							statusStack.addFirst(new Integer(status));
						}
						else{
							status=S_IN_ERROR;
						}
						break;
					case Yytoken.TYPE_RIGHT_BRACE:
						if(valueStack.size()>1){
							statusStack.removeFirst();
							valueStack.removeFirst();
							status=peekStatus(statusStack);
						}
						else{
							status=S_IN_FINISHED_VALUE;
						}
						break;
					default:
						status=S_IN_ERROR;
						break;
					}//inner switch
					break;
					
				case S_PASSED_PAIR_KEY:
					switch(token.type){
					case Yytoken.TYPE_COLON:
						break;
					case Yytoken.TYPE_VALUE:
						statusStack.removeFirst();
						String key=(String)valueStack.removeFirst();
						JSONObject parent=(JSONObject)valueStack.getFirst();
						parent.put(key,token.value);
						status=peekStatus(statusStack);
						break;
					case Yytoken.TYPE_LEFT_SQUARE:
						statusStack.removeFirst();
						key=(String)valueStack.removeFirst();
						parent=(JSONObject)valueStack.getFirst();
						JSONArray newArray=new JSONArray();
						parent.put(key,newArray);
						status=S_IN_ARRAY;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(newArray);
						break;
					case Yytoken.TYPE_LEFT_BRACE:
						statusStack.removeFirst();
						key=(String)valueStack.removeFirst();
						parent=(JSONObject)valueStack.getFirst();
						JSONObject newObject=new JSONObject();
						parent.put(key,newObject);
						status=S_IN_OBJECT;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(newObject);
						break;
					default:
						status=S_IN_ERROR;
					}
					break;
					
				case S_IN_ARRAY:
					switch(token.type){
					case Yytoken.TYPE_COMMA:
						break;
					case Yytoken.TYPE_VALUE:
						JSONArray val=(JSONArray)valueStack.getFirst();
						val.add(token.value);
						break;
					case Yytoken.TYPE_RIGHT_SQUARE:
						if(valueStack.size()>1){
							statusStack.removeFirst();
							valueStack.removeFirst();
							status=peekStatus(statusStack);
						}
						else{
							status=S_IN_FINISHED_VALUE;
						}
						break;
					case Yytoken.TYPE_LEFT_BRACE:
						val=(JSONArray)valueStack.getFirst();
						JSONObject newObject=new JSONObject();
						val.add(newObject);
						status=S_IN_OBJECT;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(newObject);
						break;
					case Yytoken.TYPE_LEFT_SQUARE:
						val=(JSONArray)valueStack.getFirst();
						JSONArray newArray=new JSONArray();
						val.add(newArray);
						status=S_IN_ARRAY;
						statusStack.addFirst(new Integer(status));
						valueStack.addFirst(newArray);
						break;
					default:
						status=S_IN_ERROR;
					}//inner switch
					break;
				case S_IN_ERROR:
					return null;
				}//switch
				if(status==S_IN_ERROR)
					return null;
			}while(token.type!=Yytoken.TYPE_EOF);
		}
		catch(Exception e){
			throw e;
		}
		return null;
	}
}
