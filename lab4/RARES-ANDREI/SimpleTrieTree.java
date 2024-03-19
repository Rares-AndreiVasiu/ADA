import java.util.*;

public class SimpleTrieTree {
    private final int R = 256; // R - size of alphabet.
    // in this example keys(words) are sequences of characters from extended ASCII

    private TrieNode root;      // root of trie tree

    // R-way trie node
    private class TrieNode {
        private Integer val;  // if current node contains end of a key(end of a word)
        // then it has a non-null val associated
        private final TrieNode[] next = new TrieNode[R]; // may have R children
    }

    /**
     * Initializes an empty trie tree
     */
    public SimpleTrieTree() {
        root = null;
    }


    /**
     * Inserts a word into the trie tree - the recursive implementation
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put(String key, Integer val) {
        if ((key == null) || (val == null)) throw new IllegalArgumentException("key or val argument is null");
        else root = put(root, key, val);
    }

    private TrieNode put(TrieNode x, String key, Integer val) {
        if (x == null) x = new TrieNode();
        if (key.equals("")) {
            x.val = val;
            return x;
        }
        char c = key.charAt(0);
        String restkey = "";
        if (key.length() > 1) restkey = key.substring(1);
        x.next[c] = put(x.next[c], restkey, val);
        return x;
    }

    /**
     * Inserts a word into the trie tree - the iterative version
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put_v2(String key, Integer val) {
        if (root == null) {
            root = new TrieNode();
        }
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (node.next[c] == null) {
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        node.val = val;
    }


    public boolean contains(String key) {
        TrieNode node = getNode(key);
        return node != null && node.val != null;
    }


    private TrieNode getNode(String key) {
        TrieNode node = root;
        for (int i = 0; i < key.length() && node != null; i++) {
            char c = key.charAt(i);
            node = node.next[c];
        }
        return node;
    }

    public void printAllKeys() {
        List<String> keys = new ArrayList<>();
        collectKeys(root, "", keys);
        for (String key : keys) {
            System.out.println(key);
        }
    }

    private void collectKeys(TrieNode x, String prefix, List<String> keys) {
        if (x == null) return;
        if (x.val != null) {
            keys.add(prefix);
        }
        for (char c = 0; c < R; c++) {
            collectKeys(x.next[c], prefix + c, keys);
        }
    }

    public void printAllWithPrefix(String prefix) {
        TrieNode node = getNode(prefix);
        if (node != null) {
            List<String> keys = new ArrayList<>();
            collectKeys(node, prefix, keys);
            for (String key : keys) {
                System.out.println(key);
            }
        }
    }
    private char getNextCharacter(TrieNode node, boolean first) {
        char startChar = first ? ' ' : (char) (R - 1);
        char endChar = first ? (char) (R - 1) : ' ';
        for (char c = startChar; first ? c <= endChar : c >= endChar; c += first ? 1 : -1) {
            if (node.next[c] != null) {
                return c;
            }
        }
        return '\0';
    }

    public String findFirstWord() {
        TrieNode node = root;
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            if (node.val != null) {
                return sb.toString();
            }
            char nextChar = getNextCharacter(node, true);
            if (nextChar == '\0') break;
            sb.append(nextChar);
            node = node.next[nextChar];
        }
        return null;
    }

    public String findLastWord() {
        TrieNode node = root;
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            if (node.val != null) {
                return sb.toString();
            }
            char nextChar = getNextCharacter(node, false);
            if (nextChar == '\0') break;
            sb.append(nextChar);
            node = node.next[nextChar];
        }
        return null;
    }

    public String findShortestWord() {
        String[] shortest = {null};
        findShortestWord(root, "", shortest);
        return shortest[0];
    }

    private void findShortestWord(TrieNode x, String current, String[] shortest) {
        if (x == null) return;
        if (x.val != null) {
            if (shortest[0] == null || current.length() < shortest[0].length()) {
                shortest[0] = current;
            }
        }
        for (char c = 0; c < R; c++) {
            findShortestWord(x.next[c], current + c, shortest);
        }
    }

    public String findLongestWord() {
        String[] longest = {""};
        findLongestWord(root, "", longest);
        return longest[0];
    }

    private void findLongestWord(TrieNode x, String current, String[] longest) {
        if (x == null) return;
        if (x.val != null && current.length() > longest[0].length()) {
            longest[0] = current;
        }
        for (char c = 0; c < R; c++) {
            findLongestWord(x.next[c], current + c, longest);
        }
    }

    public static void main(String[] args) {

        String[] inputs = {"bar", "sea", "sunday", "bark", "ban", "bandage", "sun", "banana", "band"};

        SimpleTrieTree st = new SimpleTrieTree();

        for (int i = 0; i < inputs.length; i++) {
            String key = inputs[i];
            st.put(key, i);
            System.out.println("Have inserted into trie tree key="+key+" with value="+i);
        }

        System.out.println("All  keys in ascending order:");
        st.printAllKeys();

        String testprefix = "ban";
        System.out.println("keysWithPrefix " + testprefix);
        st.printAllWithPrefix(testprefix);   //Keys with prefix ban: ban banana band bandage

         System.out.println(st.contains("banana"));  //true

         System.out.println(st.contains("blabla"));  //false

         System.out.println(st.contains("ban"));     //true

         System.out.println(st.contains("ba"));      //false

        st.printAllKeys();

        System.out.println(st.findFirstWord());

        System.out.println(st.findLastWord());

        System.out.println(st.findShortestWord());

        System.out.println(st.findLongestWord());
    }
}
