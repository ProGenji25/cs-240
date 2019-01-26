package spell;

public class Trie implements ITrie {
	
	private int wordCount;
	private int nodeCount;
	public Node root;
	
	public Trie() {
		root = new Node();							//initialize Trie with root node
		nodeCount = 1;
		wordCount = 0;
	}

	@Override
	public void add(String word) {					//add words by adding new Nodes
		Node temp = root;
		word = word.toLowerCase();
		char c;
		for (int i = 0; i < word.length(); i++) {	// go through every letter in string
			c = word.charAt(i);
			if(temp.getNode(c) == null) {			//if node doesn't exist, add new node
				temp.setNode(c, new Node());
				nodeCount++;
			}
			temp = temp.getNode(c);					//traverse to node[c] 
		}
		wordCount++;								//add new word
		temp.frequency++;
	}

	@Override
	public ITrie.INode find(String word) {			//find if word exists and return last word node
		Node temp = root;
		word = word.toLowerCase();
		char c;
		for (int i = 0; i < word.length(); i++) {	// go through every letter in string
			c = word.charAt(i);
			if(temp.getNode(c) == null) {			//traverse to node[c] 
				return null;
			}
			temp = temp.getNode(c);
		}
		if(temp.getValue() == 0) {					// return final node if word exists
			return null;
		}
		else {
			return temp;
		}
	}

	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	@Override
	public int hashCode() {
		return getWordCount() * getNodeCount();			//makes hash code from word and node count
	}
	
	@Override
	public String toString() {							//builds stringbuilders for word and trie
		StringBuilder builder = new StringBuilder();
		StringBuilder word_builder = new StringBuilder();
		recToString(builder, word_builder, root);
		return builder.toString();
	}
	
	public void recToString(StringBuilder builder, StringBuilder word_builder, Node root) {
		if(root == null) {						//base case
			return;
		}
		if(root.getValue() > 0) {				//appends finished word
			builder.append(word_builder);
			builder.append("\n");
		}
		for(char c = 'a'; c <= 'z'; c++) {		//recursively appends letter from current node
			word_builder.append(c);
			recToString(builder, word_builder, root.getNode(c));	//traverse child nodes
			word_builder.deleteCharAt(word_builder.length() - 1);	//delete content of final node from builder
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {						//checks if object is null
			return false;
		}
		if(o.getClass() != this.getClass()) {	//checks if object is trie
			return false;
		}
		Trie trie = (Trie)o;
		if(trie.getWordCount() != getWordCount() && trie.getNodeCount() != getNodeCount()) {
			return false;						//compare word and node counts of object and trie
		}
		return recEquals(root, trie.root);
	}
	
	public boolean recEquals(Node node, Node node_o) {
		if (node == null || node_o == null) {			//if either node is null, make sure both nodes are null
			return (node == null) && (node_o == null);
		}
		if (node_o.frequency != node.frequency) {		//compare node frequency
			return false;
		}
		for(char c = 'a'; c <= 'z'; c++) {				//traverse all children nodes
			if (!recEquals(node.getNode(c), node_o.getNode(c))){
				return false;
			}
		}
		return true;	
	}
}