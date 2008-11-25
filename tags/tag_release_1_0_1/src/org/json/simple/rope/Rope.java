package org.json.simple.rope;

public class Rope {
	private Node first;
	private Node current;
	private int count = 0;
	
	public void clear(){
		count = 0;
		first = null;
		current = null;
	}
	
	public int length(){
		return count;
	}
	
	public String toString(){
		if(count == 0)
			return "";
		
		char[] chs = new char[count];
		int pos = 0;
		
		Node p = first;
		do{
			if(p.type == Node.TYPE_CH){
				chs[pos] = p.content_ch;
				pos++;
			}
			else{
				int len = p.content_str.length();
				p.content_str.getChars(0, len, chs, pos);
				pos += len;
			}
			p = p.next;
		}while(p != null);
		
		return new String(chs);
	}
	
	public void append(char ch){
		Node node = new Node();
		node.type = Node.TYPE_CH;
		node.content_ch = ch;
		if(first == null)
			first = node;
		if(current != null)
			current.next = node;
		current = node;
		count++;
	}
	
	public void append(String s){
		if(s == null || s.equals(""))
			return;
		
		Node node = new Node();
		node.type = Node.TYPE_STR;
		node.content_str = s;
		if(first == null)
			first = node;
		if(current != null)
			current.next = node;
		current = node;
		count += s.length();
	}
}
