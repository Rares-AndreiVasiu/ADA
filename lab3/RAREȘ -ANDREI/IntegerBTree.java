
import java.util.LinkedList;
import java.util.Queue;


/**
 * A simple implementation for B-Tree with Integer keys
 * <p>
 * Implemented operations: insert, displayLevels
 * <p>
 * Implement as exercises:
 *   public boolean contains(int key);
 *   public int height();
 *   public int level(int key);
 *   public int min();
 *   public int max();
 *   public int successor(int key);
 *   public int predecessor(int key);
 *   public void inorder(){;
 */

public class IntegerBTree {

    private final int T; // the mindegree of the B-Tree

    class BTreeNode {
        int n;    // current number of keys contained in node
        Integer[] key = new Integer[2 * T - 1];   //maximum 2T-1 keys
        BTreeNode[] child = new BTreeNode[2 * T]; // maximum 2T children
        boolean leaf = true;

        public String toString(){
            StringBuilder sb =new StringBuilder();
            sb.append(" [ ");
            for (int i=0; i<n; i++)
                sb.append(" ").append(key[i]);
            sb.append(" ] ");
            return sb.toString();
        }
    }

    /**
     * Constructor of an empty B-Tree of mindegree T
     * @param t - degree of B tree
     */
    public IntegerBTree(int t) {
        T = t;
        root = new BTreeNode();
        root.n = 0;
        root.leaf = true;
    }

    private BTreeNode root; // root of tree

    /**
     * Insert a key into a B-Tree in a single pass down the tree
     * see [CLRS] algorithm
     * @param key - new key to be inserted
     */
    public void insert(Integer key) {
        BTreeNode r = root;
        if (r.n == 2 * T - 1) { // if the root node is already full
            BTreeNode s = new BTreeNode(); // preventive splits the root
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            insertNonfullStart(s, key);
        } else {
            insertNonfullStart(r, key);
        }
    }


    /**
     * Splits a node and introduces the new split as a child of the same parent
     * see [CLRS] algorithm
     * @param x - parent of node to split
     * @param pos - position in parent where to link new node
     * @param y - node to be split
     */
    private void split(BTreeNode x, int pos, BTreeNode y) {
        System.out.println("Split node "+y.toString());

        BTreeNode z = new BTreeNode();
        z.leaf = y.leaf; // new node z is leaf only if node y to be splitted was leaf
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) { //copy right half of y into new node
            z.key[j] = y.key[j + T];
        }

        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.child[j] = y.child[j + T];
            }
        }

        y.n = T - 1;
        for (int j = x.n; j >= pos + 1; j--) { //right shift children in parent node
            x.child[j + 1] = x.child[j];
        }
        x.child[pos + 1] = z; //insert new node z as child of parent

        for (int j = x.n - 1; j >= pos; j--) {
            x.key[j + 1] = x.key[j];
        }
        x.key[pos] = y.key[T - 1];
        x.n = x.n + 1;
    }


    /**
     * Inserts key k into node x which is assumed to be non-full when function is called.
     * This function recurses as necessary down the tree, at all times guaranteeing that
     * the node to which it recurses is not full by calling split as necessary
     * see [CLRS] algorithm
     * @param x - root (non-full node) of subtree where insertion is done
     * @param k - new key to be inserted
     */
    final private void insertNonfullStart(BTreeNode x, int k) {

        if (x.leaf) {
            // x is a non-full leaf node, insert key into it
            int i = 0;
            // shift existing keys right to make place for new k
            for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {
                x.key[i + 1] = x.key[i];
            }
            x.key[i + 1] = k;
            x.n = x.n + 1;
        } else { // x is not a leaf
            int i = 0;
            for (i = x.n - 1; i >= 0 && k < x.key[i]; i--) {
            } // search child to continue insertion
            ;
            i++;
            BTreeNode tmp = x.child[i];
            if (tmp.n == 2 * T - 1) {
                // if child is full, split it
                split(x, i, tmp);
                if (k > x.key[i]) { // determines in which split half we insert
                    i++;
                }
            }
            insertNonfullStart(x.child[i], k); // recursive insert
        }

    }

    private class QueuePair {
        BTreeNode node;
        int level;
        QueuePair(BTreeNode node, int level){
            this.node=node;
            this.level=level;
        }
    }
    public void displayLevels() {
        // Use Queue to hold nodes while printing on levels
        Queue<QueuePair> q = new LinkedList<QueuePair>();

        System.out.println("B Tree displayed on levels: ");
        BTreeNode x = root;
        int oldLevel = 0;
        int level;

        q.add(new QueuePair(x, oldLevel));

        while (!q.isEmpty()) {

            QueuePair p = q.remove();
            x = p.node;
            level = p.level;

            if (level > oldLevel) {
                System.out.println(); // level changed
                oldLevel = level;
            }
            System.out.print(x.toString());
            if (!x.leaf) {
                for (int i = 0; i <= x.n; i++) {
                    BTreeNode y = (x.child)[i];
                    q.add(new QueuePair(y, level + 1));
                }
            }
        }
        System.out.println();
    }

//    new requirement implementation

//    the contains() method
    // start from the root node
    //
    private boolean contains(BTreeNode x, int key)
    {
        int index = 0;

        while(index < x.n && key > x.key[index])
        {
            ++index;
        }

        if(x.key[index] == key)
        {
            return true;
        }

        if(x.leaf)
        {
            return false;
        }

        return contains(x.child[index], key);
    }

    public boolean contains(int key)
    {
        return contains(root, key);
    }

    private int height(BTreeNode x)
    {
        if(null == x)
        {
            return 0;
        }
        else
        {
            int mxHeight = 0;

            for(int i = 0; i <= x.n; ++ i)
            {
                int currentHeight = height(x.child[i]);

                mxHeight = Math.max(mxHeight, currentHeight);
            }

            return mxHeight + 1;
        }
    }

    public int height()
    {
        return height(root);
    }

    public int min()
    {
        if(null == root)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            BTreeNode newRoot = root;

            while(!newRoot.leaf)
            {
                newRoot = newRoot.child[0];
            }

            return newRoot.key[0];
        }
    }

    public int max()
    {
        if(null == root)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            BTreeNode newRoot = root;

            while(!newRoot.leaf)
            {
                newRoot = newRoot.child[newRoot.n];
            }

            return newRoot.key[newRoot.n - 1];
        }
    }

    public void inorder() {
        inorderTraversal(root);
    }

    private void inorderTraversal(BTreeNode node) {
        if (node != null) {
            // Traverse the left subtree
            for (int i = 0; i < node.n; i++) {
                inorderTraversal(node.child[i]);
                // Print the key
                System.out.print(node.key[i] + " ");
            }
            // Traverse the right subtree
            inorderTraversal(node.child[node.n]);
        }
    }

    public int successor(int key) {
        BTreeNode node = search(root, key);
        if (node == null) {
            return Integer.MIN_VALUE; // Key not found, return minimum value
        }

        if (node.leaf && node.key[node.n - 1] > key) {
            return node.key[node.n - 1]; // Successor is the largest key in the node
        }

        int index = findKeyIndex(node, key);
        if (index < node.n && node.key[index] == key) {
            // If key is found in the node, find the leftmost key of the right subtree
            return findLeftmostKey(node.child[index + 1]);
        } else {
            // Key not found in the node, recursively find in the appropriate subtree
            return findSuccessor(root, key);
        }
    }

    private int findSuccessor(BTreeNode node, int key) {
        int index = findKeyIndex(node, key);
        if (index < node.n && node.key[index] == key) {
            // If key is found in the node, find the leftmost key of the right subtree
            return findLeftmostKey(node.child[index + 1]);
        } else if (node.leaf) {
            return Integer.MIN_VALUE; // Key not found in tree
        } else {
            // Recursively find in the appropriate subtree
            return findSuccessor(node.child[index], key);
        }
    }

    private int findLeftmostKey(BTreeNode node) {
        if (node == null) {
            return Integer.MIN_VALUE; // Invalid node
        }
        BTreeNode current = node;
        while (!current.leaf) {
            current = current.child[0];
        }
        return current.key[0];
    }

    public int predecessor(int key) {
        BTreeNode node = search(root, key);
        if (node == null) {
            return Integer.MAX_VALUE; // Key not found, return maximum value
        }

        if (node.leaf && node.key[0] < key) {
            return node.key[0]; // Predecessor is the smallest key in the node
        }

        int index = findKeyIndex(node, key);
        if (index > 0 && node.key[index] == key) {
            // If key is found in the node, find the rightmost key of the left subtree
            return findRightmostKey(node.child[index]);
        } else {
            // Key not found in the node, recursively find in the appropriate subtree
            return findPredecessor(root, key);
        }
    }

    private int findPredecessor(BTreeNode node, int key) {
        int index = findKeyIndex(node, key);
        if (index > 0 && node.key[index] == key) {
            // If key is found in the node, find the rightmost key of the left subtree
            return findRightmostKey(node.child[index]);
        } else if (node.leaf) {
            return Integer.MAX_VALUE; // Key not found in tree
        } else {
            // Recursively find in the appropriate subtree
            return findPredecessor(node.child[index], key);
        }
    }

    private int findRightmostKey(BTreeNode node) {
        if (node == null) {
            return Integer.MAX_VALUE; // Invalid node
        }
        BTreeNode current = node;
        while (!current.leaf) {
            current = current.child[current.n];
        }
        return current.key[current.n - 1];
    }

    private BTreeNode search(BTreeNode x, int key) {
        int i = 0;
        while (i < x.n && key > x.key[i]) {
            i++;
        }
        if (i < x.n && key == x.key[i]) {
            return x;
        } else if (x.leaf) {
            return null;
        } else {
            return search(x.child[i], key);
        }
    }

    private int findKeyIndex(BTreeNode node, int key) {
        int index = 0;
        while (index < node.n && key > node.key[index]) {
            index++;
        }
        return index;
    }


    public static void main(String[] args)
    {
        IntegerBTree b = new IntegerBTree(3);

        int[] a={8,9,10,11,15,20, 17, 22, 25,16, 12, 13, 14, 26, 27, 29, 33, 40, 50, 51, 52, 53};

        for (int i = 0; i<a.length; i++)
        {
            System.out.println("Will insert "+a[i]);

            b.insert(a[i]);

            b.displayLevels();
        }

        System.out.println("The tree contains: " + b.contains(9));

        System.out.println("BTree height: " + b.height());

        System.out.println("BTree min value: " + b.min());

        System.out.println("BTree max value: " + b.max());

    }
}
