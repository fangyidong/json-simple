/* Copyright 2006 FangYidong

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package org.json.simple.parser;

/**
 * ParseException explains why and where the error occurs in source JSON text.
 * 
 * @author FangYidong&lt;fangyidong@yahoo.com.cn&gt;
 * 
 * @deprecated since 2.0.0, copied to a new package.
 */
@Deprecated
public class ParseException extends Exception {
	private static final long serialVersionUID = -7880698968187728547L;
	
	/**
	 * description omitted.
	 */
	public static final int ERROR_UNEXPECTED_CHAR = 0;
	/**
	 * description omitted.
	 */
	public static final int ERROR_UNEXPECTED_TOKEN = 1;
	/**
	 * description omitted.
	 */
	public static final int ERROR_UNEXPECTED_EXCEPTION = 2;

	private int errorType;
	private Object unexpectedObject;
	private int position;
	
	/**
	 * @param errorType description omitted.
	 */
	public ParseException(int errorType){
		this(-1, errorType, null);
	}
	
	/**
	 * @param errorType description omitted.
	 * @param unexpectedObject description omitted.
	 */
	public ParseException(int errorType, Object unexpectedObject){
		this(-1, errorType, unexpectedObject);
	}
	
	/**
	 * @param position description omitted.
	 * @param errorType description omitted.
	 * @param unexpectedObject description omitted.
	 */
	public ParseException(int position, int errorType, Object unexpectedObject){
		this.position = position;
		this.errorType = errorType;
		this.unexpectedObject = unexpectedObject;
	}
	
	/**
	 * Action on data for a result.
	 *
	 * @return description omitted.
	 */
	public int getErrorType() {
		return errorType;
	}
	
	/**
	 * Action on data for a result.
	 *
	 * @param errorType description omitted.
	 */
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * @see org.json.simple.parser.JSONParser#getPosition()
	 * 
	 * @return The character position (starting with 0) of the input where the error occurs.
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Action on data for a result.
	 *
	 * @param position description omitted.
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * @see org.json.simple.parser.Yytoken
	 * 
	 * @return One of the following base on the value of errorType:
	 * 		   	ERROR_UNEXPECTED_CHAR		java.lang.Character
	 * 			ERROR_UNEXPECTED_TOKEN		org.json.simple.parser.Yytoken
	 * 			ERROR_UNEXPECTED_EXCEPTION	java.lang.Exception
	 */
	public Object getUnexpectedObject() {
		return unexpectedObject;
	}
	
	/**
	 * Action on data for a result.
	 *
	 * @param unexpectedObject description omitted.
	 */
	public void setUnexpectedObject(Object unexpectedObject) {
		this.unexpectedObject = unexpectedObject;
	}
	
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		
		switch(errorType){
		case ERROR_UNEXPECTED_CHAR:
			sb.append("Unexpected character (").append(unexpectedObject).append(") at position ").append(position).append(".");
			break;
		case ERROR_UNEXPECTED_TOKEN:
			sb.append("Unexpected token ").append(unexpectedObject).append(" at position ").append(position).append(".");
			break;
		case ERROR_UNEXPECTED_EXCEPTION:
			sb.append("Unexpected exception at position ").append(position).append(": ").append(unexpectedObject);
			break;
		default:
			sb.append("Unkown error at position ").append(position).append(".");
			break;
		}
		return sb.toString();
	}
}
