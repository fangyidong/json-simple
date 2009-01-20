/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONArray extends ArrayList {
	public String toString(){
		Iterator iter=iterator();

        boolean first = true;
        StringBuffer sb = new StringBuffer();
        sb.append('[');
		while(iter.hasNext()){
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            JSONObject.escapeValue(iter.next(), sb);
		}
        sb.append(']');
        return sb.toString();
	}
		
}
