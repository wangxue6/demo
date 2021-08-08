package algorithm;

public class SkipList{
	Node head;
	int maxLevel;
	int count;
	public SkipList(int maxLevel) {
		this.maxLevel = maxLevel;
		count = 0;
		head = new Node(Integer.MIN_VALUE, 0);
		initial(head);
	}
	private void initial(Node head) {
		Node temp = head;
		for(int i=1;i<maxLevel;i++) {
			temp.down = new Node(head.key, head.value);
			temp = temp.down;
		}
	}
	
	public int size() {
		return count;
	}
	
	public int get(int key) {
		Node[] path = doGet(key);
		if(path[maxLevel-1].key == key)return path[maxLevel-1].value;
		return -1;
	}
	
	public boolean put(int key, int value) {
		Node[] path = doGet(key);
		if(path[maxLevel-1].key == key) {
			changeVal(path,key,value);
			return false;
		}
		int level = (int)(maxLevel*Math.random());
		Node cur = new Node(key,value);
		for(int i=maxLevel-level-1;i<maxLevel;i++) {
			Node pre = path[i];
			Node next = path[i].right;
			if(i < maxLevel-1)cur.down = new Node(key,value);
			pre.right = cur;
			cur.right = next;
			cur = cur.down;
		}
		count++;
		return true;
	}
	
	public boolean remove(int key) {
		Node[] path = doGet1(key);
		Node target = path[maxLevel-1].right;
		if(target == null || target.key != key) return false;
		for(int i=maxLevel-1;i>=0;i--) {
			Node pre = path[i];
			if(pre.right == null || pre.right.key != key)continue;
			else pre.right = pre.right.right;
		}
		count--;
		return true;
	}
	
	private void changeVal(Node[] path, int key, int value) {
		for(int i=0;i<maxLevel;i++) {
			if(path[i].key == key)path[i].value = value;
		}
	}
	
	private Node[] doGet(int key) {
		Node cur = head;
		Node[] res = new Node[maxLevel];
		for(int i=0;i<maxLevel;i++) {
			while(cur.right != null && cur.right.key <= key) {
				cur = cur.right;
			}
			res[i] = cur;
			cur = cur.down;
		}
		return res;
	}
	
	private Node[] doGet1(int key) {
		Node cur = head;
		Node[] res = new Node[maxLevel];
		for(int i=0;i<maxLevel;i++) {
			while(cur.right != null && cur.right.key < key) {
				cur = cur.right;
			}
			res[i] = cur;
			cur = cur.down;
		}
		return res;
	}
	
	class Node{
		int key;
		int value;
		Node right;
		Node down;
		public Node(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}
}
