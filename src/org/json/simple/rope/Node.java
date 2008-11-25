package org.json.simple.rope;

class Node {
	static final int TYPE_STR 		= 0;
	static final int TYPE_CH 		= 1;
	static final int TYPE_UNKNOWN 	= -1;
	
	int type = TYPE_UNKNOWN;
	Node next;
	char content_ch;
	String content_str;
}
