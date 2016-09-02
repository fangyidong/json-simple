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
package org.json.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * |a:b:c| =&gt; |a|,|b|,|c|
 * |:| =&gt; ||,||
 * |a:| =&gt; |a|,||
 * @author FangYidong&lt;fangyidong@yahoo.com.cn&gt;
 * @deprecated since 2.0.0 all of the functionality provided by the class seems to be already provided in the JDK.
 */
@Deprecated
public class ItemList {
	private String sp=",";
	List items=new ArrayList();
	
	
	/**
	 * description omitted.
	 */
	public ItemList(){}
	
	
	/**
	 * @param s description omitted.
	 */
	public ItemList(String s){
		this.split(s,sp,items);
	}
	
	/**
	 * @param s description omitted.
	 * @param sp description omitted.
	 */
	public ItemList(String s,String sp){
		this.sp=s;
		this.split(s,sp,items);
	}
	
	/**
	 * @param s description omitted.
	 * @param sp description omitted.
	 * @param isMultiToken description omitted.
	 */
	public ItemList(String s,String sp,boolean isMultiToken){
		split(s,sp,items,isMultiToken);
	}
	
	/**
	 * description omitted.
	 *
	 * @return description omitted.
	 */
	public List getItems(){
		return this.items;
	}
	
	/**
	 * description omitted.
	 *
	 * @return description omitted.
	 */
	public String[] getArray(){
		return (String[])this.items.toArray();
	}
	
	/**
	 * description omitted.
	 *
	 * @param s description omitted.
	 * @param sp description omitted.
	 * @param append description omitted.
	 * @param isMultiToken description omitted.
	 */
	public void split(String s,String sp,List append,boolean isMultiToken){
		if(s==null || sp==null)
			return;
		if(isMultiToken){
			StringTokenizer tokens=new StringTokenizer(s,sp);
			while(tokens.hasMoreTokens()){
				append.add(tokens.nextToken().trim());
			}
		}
		else{
			this.split(s,sp,append);
		}
	}
	
	/**
	 * description omitted.
	 *
	 * @param s description omitted.
	 * @param sp description omitted.
	 * @param append description omitted.
	 */
	public void split(String s,String sp,List append){
		if(s==null || sp==null)
			return;
		int pos=0;
		int prevPos=0;
		do{
			prevPos=pos;
			pos=s.indexOf(sp,pos);
			if(pos==-1)
				break;
			append.add(s.substring(prevPos,pos).trim());
			pos+=sp.length();
		}while(pos!=-1);
		append.add(s.substring(prevPos).trim());
	}
	
	/**
	 * description omitted.
	 *
	 * @param sp description omitted.
	 */
	public void setSP(String sp){
		this.sp=sp;
	}
	
	/**
	 * description omitted.
	 *
	 * @param i description omitted.
	 * @param item description omitted.
	 */
	public void add(int i,String item){
		if(item==null)
			return;
		items.add(i,item.trim());
	}

	/**
	 * description omitted.
	 *
	 * @param item description omitted.
	 */
	public void add(String item){
		if(item==null)
			return;
		items.add(item.trim());
	}
	
	/**
	 * description omitted.
	 *
	 * @param list description omitted.
	 */
	public void addAll(ItemList list){
		items.addAll(list.items);
	}
	
	/**
	 * description omitted.
	 *
	 * @param s description omitted.
	 */
	public void addAll(String s){
		this.split(s,sp,items);
	}
	
	/**
	 * description omitted.
	 *
	 * @param s description omitted.
	 * @param sp description omitted.
	 */
	public void addAll(String s,String sp){
		this.split(s,sp,items);
	}
	
	/**
	 * description omitted.
	 *
	 * @param s description omitted.
	 * @param sp description omitted.
	 * @param isMultiToken description omitted.
	 */
	public void addAll(String s,String sp,boolean isMultiToken){
		this.split(s,sp,items,isMultiToken);
	}
	
	/**
	 * @param i 0-based
	 * @return description omitted.
	 */
	public String get(int i){
		return (String)items.get(i);
	}
	
	/**
	 * description omitted.
	 *
	 * @return description omitted.
	 */
	public int size(){
		return items.size();
	}
	@Override
	public String toString(){
		return toString(sp);
	}
	
	/**
	 * description omitted.
	 *
	 * @param sp description omitted.
	 * @return description omitted.
	 */
	public String toString(String sp){
		StringBuffer sb=new StringBuffer();
		
		for(int i=0;i<items.size();i++){
			if(i==0)
				sb.append(items.get(i));
			else{
				sb.append(sp);
				sb.append(items.get(i));
			}
		}
		return sb.toString();

	}
	
	/**
	 * description omitted.
	 *
	 */
	public void clear(){
		items.clear();
	}
	
	/**
	 * description omitted.
	 *
	 */
	public void reset(){
		sp=",";
		items.clear();
	}
}
