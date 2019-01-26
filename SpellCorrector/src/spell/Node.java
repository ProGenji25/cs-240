package spell;

public class Node implements ITrie.INode {
	
	public int frequency;
	public Node[] node;
	
	public Node() {								//constructs node as array of nodes with frequency value
		frequency = 0;
		node = new Node[26];				//node length equals alphabet length
		for(int i = 0; i < 26; i++) {
			node[i] = null;
		}
	}

	@Override
	public int getValue() {
		return frequency;						//returns number of times word appeared in dictionary
	}
	
	public Node getNode(char c) {
		return node[c - 'a'];					//returns contents at node index[letter - 'a'] 
	}
	
	public void setNode(char c, Node n) {
		node[c - 'a'] = n;						//makes new node at node index[letter - 'a']
	}

}