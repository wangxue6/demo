package algorithm;
public class RBTree {
	Node root;
	Node sentry;
	private final boolean RED = false;
	private final boolean BLACK = false;
	public RBTree() {
		sentry = new Node(-1);
		sentry.color = BLACK;
		root = sentry;
	}
	
	public boolean insert(int val) {
		Node cur = new Node(val);
		cur.left = sentry;
		cur.right = sentry;
		doInsert(root,cur);
		return true;
	}
	
	private Node doInsert(Node root, Node cur) {
		if(root == sentry)root = cur;
		Node parent = sentry;
		Node temp = root;
		while(temp != sentry) {
			parent = temp;
			if(temp.val > cur.val)temp = temp.left;
			else temp = temp.right;
		}
		cur.parent = parent;
		if(root == sentry)root = cur;
		else if(cur.val < parent.val)parent.left = cur;
		else parent.right = cur;
		fix(cur);
		return cur;
	}
	
	private void fix(Node cur) {
		if(cur == root) {
			cur.color = BLACK;
			return;
		}
		while(cur.parent.color == RED) {
			if(cur.parent == cur.parent.parent.left) {
				Node uncle = cur.parent.parent.right;
				//case1
				if(uncle.color == RED) {
					cur.parent.color = BLACK;
					uncle.color = BLACK;
					cur.parent.parent.color = RED;
					cur = cur.parent.parent;
				}
				//case2
				else if(cur == cur.parent.right) {
					cur = cur.parent;
					leftRotate(cur);
				}
				//case3
				else {
					cur.parent.color = BLACK;
					cur.parent.parent.color = RED;
					rightRotate(cur.parent.parent);
				}
			}else {
				Node uncle = cur.parent.parent.left;
				//case1
				if(uncle.color == RED) {
					cur.parent.color = BLACK;
					uncle.color = BLACK;
					cur.parent.parent.color = RED;
					cur = cur.parent.parent;
				}
				//case2
				else if(cur == cur.parent.left) {
					cur = cur.parent;
					rightRotate(cur);
				}
				//case3
				else {
					cur.parent.color = BLACK;
					cur.parent.parent.color = RED;
					leftRotate(cur.parent.parent);
				}
			}
			root.color = BLACK;
		}
	}
	
	private void leftRotate(Node cur) {
		Node right = cur.right;
		right.parent = cur.parent;
		cur.right = right.left;
		if(right.left != sentry)right.left.parent = cur;
		cur.parent = right;
		right.left = cur;
		if(cur == root)root = right;
	}
	
	private void rightRotate(Node cur) {
		Node left = cur.left;
		left.parent = cur.parent;
		cur.left = left.right;
		if(left.right != sentry)left.right.parent = cur;
		cur.parent = left;
		left.right = cur;
		if(cur == root)root = left;
	}
	
	class Node{
		public Node(int val) {
			this.val = val;
		}
		int val;
		boolean color;
		Node left;
		Node right;
		Node parent;
	}
}
