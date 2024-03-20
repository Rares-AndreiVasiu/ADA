import com.sun.tools.jconsole.JConsoleContext;

import javax.security.auth.login.CredentialException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.*;

class Node
{
    Integer key;           // sorted by Key
    Integer val;         // associated data
    Node left, right;  // left and right subtrees

    Node(Integer key, Integer val)
    {
        this.key = key;
        this.val = val;
    }
}

/**
 * A simple BST implementation
 * Keys and values are Integer
 */
class IntegerBST {
    private Node root;             // root of BST

    public IntegerBST() {
        root = null;        // initializes empty BST
    }

    /**
     * Print keys in ascending order by
     * doing an inorder tree walk
     */
    public void inorder() {
        inorder(root);
    }

    private void inorder(Node x) {
        if (x == null) return;
        inorder(x.left);
        System.out.print(" " + x.key);
        inorder(x.right);
    }

    public void preorder() {
        preorder(root);
    }

    private void preorder(Node x) {
        if (x == null) {
            return;
        }

        System.out.print(" " + x.key);

        preorder(x.left);

        preorder(x.right);
    }

    public void postorder() {
        postorder(root);
    }

    private void postorder(Node x) {
        if (x == null) {
            return;
        }

        postorder(x.left);

        postorder(x.right);

        System.out.print(" " + x.key);
    }

    /**
     * Searches for a given key in the BST
     * returns true if the key is contained in the BST and false otherwise
     */
    public boolean contains(Integer key) {
        return contains(root, key);
    }

    private boolean contains(Node x, Integer key) {
        if (x == null) return false;
        if (key < x.key) return contains(x.left, key);
        else if (key > x.key) return contains(x.right, key);
        else return true;
    }


    /**
     * Inserts the specified key-value pair, overwriting the old
     * value in the node with the new value if the BST already contains the key.
     */
    public void put(Integer key, Integer val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, val);
    }

    private Node put(Node x, Integer key, Integer val) {
        if (x == null) return new Node(key, val);
        if (key < x.key) x.left = put(x.left, key, val);
        else if (key > x.key) x.right = put(x.right, key, val);
        else x.val = val;
        return x;
    }

    /**
     * Returns the smallest key in the BST
     */
    public Integer min() {
        if (root == null) throw new NoSuchElementException("calls min() with empty BST");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the BST
     */
    public Integer max() {
        if (root == null) throw new NoSuchElementException("calls max() with empty BST");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Deletes the node containing the specified key
     */
    public void delete(Integer key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = deleteRecursive(root, key);
    }

    private Node deleteRecursive(Node z, Integer key) {
        if (z == null) return null;
        int cmp = key.compareTo(z.key);
        if (cmp < 0) z.left = deleteRecursive(z.left, key);
        else if (cmp > 0) z.right = deleteRecursive(z.right, key);
        else {
            // node z contains the key to be deleted
            if (z.right == null) return z.left;  // case 1: only 1 child left
            if (z.left == null) return z.right;  // case 1: only 1 child right
            //case 2: node z to be deleted has 2 children
            Node y = min(z.right); // find minimum in its right subtree (successor of z)
            z.right = deleteRecursive(z.right, y.key); //delete minimum node - we KNOW it has max 1 child
            z.key = y.key; //replace current key with minimum  key
        }
        return z;
    }

    private static int binarySearch(int[] array, int x) {
        int index = -1;

        for (int i = 0; i < array.length; ++i) {
            if (array[i] < x) {
                index = i;
            }
        }

        return index;
    }

    private static int findIndex(int[] array) {
        int n = array.length;

        if (n == 1) {
            int[] dest = {array[0]};

            int newRoot = array[n - 1];

            return binarySearch(dest, newRoot);

        } else {
            int[] dest = Arrays.copyOfRange(array, 0, n - 1);

            int newRoot = array[n - 1];

            return binarySearch(dest, newRoot);
        }
    }

    private static boolean checkSubtreeLeft(int[] arr, int index, int target) {
        if (index < arr.length) {
            for (int i = 0; i < index; ++i) {
                if (arr[i] > target) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkSubtreeRight(int[] arr, int index, int target) {
        for (int i = index + 1; i < arr.length; ++i) {
            if (arr[i] < target) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkSubtrees(int[] arr, int index, int target) {
        return checkSubtreeLeft(arr, index, target) &&
                checkSubtreeRight(arr, index, target);
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public void spoilValues() {
        spoilValues(root);
    }

    private void spoilValues(Node x) {
        if (x == null) return;
        x.key = (int) (Math.random() * 100); // Update the key with a random value
        spoilValues(x.left);
        spoilValues(x.right);
    }

    public boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node x, Integer min, Integer max) {
        if (x == null) return true;
        if ((min != null && x.key <= min) || (max != null && x.key >= max)) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    public Node SearchIterative(Integer key) {
        Node current = root;
        while (current != null) {
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                return current; // Key found
            }
        }
        return null; // Key not found
    }

    public Integer MinIterative() {
        if (root == null) {
            throw new NoSuchElementException("BST is empty");
        }
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;
    }

    public void InsertIterative(Integer key) {
        Node newNode = new Node(key, null);
        if (root == null) {
            root = newNode; // Tree is empty, make new node as root
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (key < current.key) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else if (key > current.key) {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            } else {
                // Key already exists, update value or handle as needed
                current.val = newNode.val;
                return;
            }
        }
    }

    public Node SearchClosest(Integer key) {
        Node current = root;
        Node closest = null;
        int minDiff = Integer.MAX_VALUE;

        while (current != null) {
            int diff = Math.abs(key - current.key);
            if (diff == 0) {
                return current; // Found an exact match
            }
            if (diff < minDiff) {
                minDiff = diff;
                closest = current;
            }
            if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return closest;
    }


    public Node successor(Integer key) {
        Node x = SearchIterative(key);
        if (x == null) return null;
        if (x.right != null) return min(x.right);
        Node successor = null;
        Node current = root;
        while (current != null) {
            if (x.key < current.key) {
                successor = current;
                current = current.left;
            } else if (x.key > current.key) {
                current = current.right;
            } else {
                break; // Found the node, no need to continue
            }
        }
        return successor;
    }

    public Node predecessor(Integer key) {
        Node x = SearchIterative(key);
        if (x == null) return null;
        if (x.left != null) return max(x.left);
        Node predecessor = null;
        Node current = root;
        while (current != null) {
            if (x.key > current.key) {
                predecessor = current;
                current = current.right;
            } else if (x.key < current.key) {
                current = current.left;
            } else {
                break; // Found the node, no need to continue
            }
        }
        return predecessor;
    }

    public boolean isPerfectlyBalanced() {
        return isPerfectlyBalanced(root) != -1;
    }

    private int isPerfectlyBalanced(Node x) {
        if (x == null) return 0;
        int leftHeight = isPerfectlyBalanced(x.left);
        if (leftHeight == -1) return -1; // Left subtree is not balanced
        int rightHeight = isPerfectlyBalanced(x.right);
        if (rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1)
            return -1; // Right subtree is not balanced or heights differ by more than 1
        return Math.max(leftHeight, rightHeight) + 1; // Return height of current subtree
    }

    private static Node buildBST(int[] arr, int left, int right) {
        if (left > right || left < 0 || right >= arr.length) {
            return null;
        }

        Node newRoot = new Node(arr[right], arr[right]);

        int index = findIndex(arr); // Pass left and right bounds to findIndex

        if (index < 0) { // Check if index is valid
//            System.out.println(index);
//
//            System.out.println(newRoot.val);
//
//            System.out.println("error index");

            return null;
        }

        // Create the new array without the last element
        int[] dest = Arrays.copyOfRange(arr, 0, right);

        // Check the subtrees if they follow the rules
        if (!checkSubtrees(dest, index, newRoot.val)) {

//            System.out.println(index);
//
//            System.out.println(newRoot.val);
//
//            System.out.println("error subtree");

            return null;
        }

        // Recursively build left and right subtrees
        newRoot.left = buildBST(dest, left, index - 1);

        newRoot.right = buildBST(dest, index, right - 1);

        return newRoot;
    }

    public static IntegerBST CreateBSTFromPostOrderArray(int[] array) {
        IntegerBST newBST = new IntegerBST();

        if (findIndex(array) < 0) {
//            System.out.println(findIndex(array));
//
//            System.out.println("error index");

            return null;
        }

        newBST.root = buildBST(array, 0, array.length - 1);

        return newBST;
    }

    public boolean CheckExistTwoNodesWithSum(int s) {
        return CheckExistTwoNodesWithSum(root, s);
    }

    private boolean CheckExistTwoNodesWithSum(Node node, int s) {
        // Create two pointers for in-order traversal
        Stack<Node> leftStack = new Stack<>();
        Stack<Node> rightStack = new Stack<>();
        Node leftCurr = node;
        Node rightCurr = node;

        // Initialize left pointer to the leftmost node and right pointer to the rightmost node
        while (leftCurr != null) {
            leftStack.push(leftCurr);
            leftCurr = leftCurr.left;
        }
        while (rightCurr != null) {
            rightStack.push(rightCurr);
            rightCurr = rightCurr.right;
        }

        // Traverse in-order from left and right simultaneously
        while (!leftStack.isEmpty() && !rightStack.isEmpty() && leftCurr != rightCurr) {
            int leftVal = leftStack.peek().key;
            int rightVal = rightStack.peek().key;

            // Check if the sum of current left and right nodes is equal to s
            if (leftVal + rightVal == s) {
                return true;
            }

            // If sum is less than s, move left pointer to next greater value
            if (leftVal + rightVal < s) {
                leftCurr = leftStack.pop().right;
                while (leftCurr != null) {
                    leftStack.push(leftCurr);
                    leftCurr = leftCurr.left;
                }
            }
            // If sum is greater than s, move right pointer to next smaller value
            else {
                rightCurr = rightStack.pop().left;
                while (rightCurr != null) {
                    rightStack.push(rightCurr);
                    rightCurr = rightCurr.right;
                }
            }
        }

        return false; // No two nodes found with the sum equal to s
    }

    public void PrintPathFromTo(Integer key1, Integer key2) {
        Node node1 = SearchIterative(key1);
        Node node2 = SearchIterative(key2);

        if (node1 == null || node2 == null) {
            System.out.println("One or both nodes are not present in the BST.");
            return;
        }

        Node lca = lowestCommonAncestor(root, node1, node2);

        if (lca == null) {
            System.out.println("Lowest common ancestor not found.");
            return;
        }

        System.out.print("Path from " + node1.key + " to " + node2.key + ": ");
        printPathToRoot(node1, lca);
        System.out.print(lca.key + " ");
        printPathFromRoot(node2, lca);
        System.out.println();
    }

    // Helper method to find the lowest common ancestor of two nodes in a BST
    private Node lowestCommonAncestor(Node root, Node p, Node q)
    {

        if (root == null || p == null || q == null)
            return null;

        if (root.key > Math.max(p.key, q.key))
        {
            return lowestCommonAncestor(root.left, p, q);
        }
        else
            if (root.key < Math.min(p.key, q.key))
            {
                return lowestCommonAncestor(root.right, p, q);
            }
            else
            {
                return root;
            }
    }

    public void printPathToRoot(Node node, Node root) {
        if (node == null || root == null) return;

        Deque<Node> stack = new ArrayDeque<>();
        Node current = root;

        // Push nodes from root to node onto the stack
        while (current != null && current != node) {
            stack.push(current);
            if (node.key < current.key) {
                current = current.left;
            } else if (node.key > current.key) {
                current = current.right;
            }
        }

        // Print the path using nodes from the stack
        while (!stack.isEmpty()) {
            System.out.print(stack.pop().key + " ");
        }
    }

    public void printPathFromRoot(Node node, Node root) {
        if (node == null || root == null) return;

        Deque<Node> stack = new ArrayDeque<>();
        Node current = root;

        // Push nodes from root to node onto the stack
        while (current != null && current != node) {
            stack.push(current);
            if (node.key < current.key) {
                current = current.left;
            } else if (node.key > current.key) {
                current = current.right;
            }
        }

        // Print the path using nodes from the stack
        while (!stack.isEmpty()) {
            System.out.print(stack.pop().key + " ");
        }

        // Print the key of the node itself
        System.out.print(node.key + " ");
    }

    public void PrintPathsWithSum(int s) {
        List<Integer> path = new ArrayList<>();
        printPathsWithSum(root, s, 0, path);
    }

    private void printPathsWithSum(Node node, int targetSum, int currentSum, List<Integer> path) {
        if (node == null) {
            return;
        }

        currentSum += node.key;

        path.add(node.key);

        if (node.left == null && node.right == null && currentSum == targetSum)
        {
            printPath(path);
        }

        printPathsWithSum(node.left, targetSum, currentSum, path);

        printPathsWithSum(node.right, targetSum, currentSum, path);

        path.remove(path.size() - 1); // Backtrack: remove the current node from the path
    }

    private void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public static IntegerBST CreateBalancedBSTfromSortedArray(int[] array) {
        IntegerBST bst = new IntegerBST();
        bst.root = buildBalancedBST(array, 0, array.length - 1);
        return bst;
    }

    private static Node buildBalancedBST(int[] array, int start, int end) {
        if (start > end) {
            return null; // Base case: empty subtree
        }

        int mid = start + (end - start) / 2; // Calculate middle index

        Node newNode = new Node(array[mid], null); // Create node with middle value

        // Recursively build left and right subtrees
        newNode.left = buildBalancedBST(array, start, mid - 1);
        newNode.right = buildBalancedBST(array, mid + 1, end);

        return newNode;
    }


    public static void main(String[] args)
    {
        int[] a = {8, 2, 15, 1, 5, 10, 20, 4, 7, 18, 20, 3};

        IntegerBST bst1 = new IntegerBST();
    }
}
