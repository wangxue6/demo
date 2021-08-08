package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LRU {
	private ListNode head;
	private ListNode tail;
	private Map<Integer,ListNode> hash;
	private int size;
	public LRU(int size) {
		this.size = size;
		hash = new HashMap<>();
		head = new ListNode(0,0);
		tail = new ListNode(0,0);
		head.next = tail;
		tail.pre = head;
	}
	public int get(int key) {
		ListNode cur = hash.get(key);
		moveToFirst(cur);
		return cur.val;
	}
	public void put(int key, int val) {
		if(hash.containsKey(key)) {
			ListNode cur = hash.get(key);
			cur.val = val;
			moveToFirst(cur);
		}else {
			ListNode cur = new ListNode(key,val);
			if(hash.size() >= size) {
				int del = removeLast();
				hash.remove(del);
			}
			addFirst(cur);
			hash.put(key,cur);
		}
	}
	public Set<Integer> keySet(){
		return hash.keySet();
	}
	private void remove(ListNode cur) {
		ListNode pre = cur.pre;
		ListNode next = cur.next;
		pre.next = next;
		next.pre = pre;
	}
	private void addFirst(ListNode cur) {
		ListNode next = head.next;
		head.next = cur;
		cur.pre = head;
		cur.next = next;
		next.pre = cur;
	}
	private void moveToFirst(ListNode cur) {
		remove(cur);
		addFirst(cur);
	}
	private int removeLast() {
		ListNode target = tail.pre;
		remove(target);
		return target.key;
	}
	class ListNode{
		int key;
		int val;
		ListNode next;
		ListNode pre;
		public ListNode(int key, int val) {
			this.key = key;
			this.val = val;
		}
	}
}
