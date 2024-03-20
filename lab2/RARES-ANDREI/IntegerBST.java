import com.sun.tools.jconsole.JConsoleContext;

import javax.security.auth.login.CredentialException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;


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
class IntegerBST
{
    private Node root;             // root of BST

    public IntegerBST()
    {
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
        System.out.print(" "+x.key);
        inorder(x.right);
    }

    public void preorder()
    {
        preorder(root);
    }

    private void preorder(Node x)
    {
        if(x == null)
        {
            return;
        }

        System.out.print(" " + x.key);

        preorder(x.left);

        preorder(x.right);
    }

    public void postorder()
    {
        postorder(root);
    }

    private void postorder(Node x)
    {
        if(x == null)
        {
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
        root=put(root, key, val);
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

    private static int binarySearch(int[] array, int x)
    {
        int index = -1;

        for(int i = 0; i < array.length; ++ i)
        {
            if(array[i] < x)
            {
                index = i;
            }
        }

        return index;
    }

    private static int findIndex(int[] array)
    {
        int n = array.length;

        if(n == 1)
        {
            int[] dest = {array[0]};

            int newRoot = array[n - 1];

            return binarySearch(dest, newRoot);

        }
        else
        {
            int[] dest = Arrays.copyOfRange(array, 0, n - 1);

            int newRoot = array[n - 1];

            return binarySearch(dest, newRoot);
        }
    }

    private static boolean checkSubtreeLeft(int[] arr, int index, int target)
    {
        if(index < arr.length)
        {
            for (int i = 0; i < index; ++i)
            {
                if (arr[i] > target)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkSubtreeRight(int[] arr, int index, int target)
    {
        for(int i = index + 1; i < arr.length; ++  i)
        {
            if(arr[i] < target)
            {
                return false;
            }
        }
        return true;
    }

    private static boolean checkSubtrees(int[] arr, int index, int target)
    {
        return checkSubtreeLeft(arr, index, target) &&
                checkSubtreeRight(arr, index, target);
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

    public static IntegerBST CreateBSTFromPostOrderArray(int[] array)
    {
        IntegerBST newBST = new IntegerBST();

        if(findIndex(array) < 0)
        {
//            System.out.println(findIndex(array));
//
//            System.out.println("error index");

            return null;
        }

        newBST.root = buildBST(array, 0, array.length - 1);

        return newBST;
    }

    public static void main(String[] args)
    {
//        int[] a = {20, 30, 15, 1, 7, 9, 29, 11, 12, 4};

//        IntegerBST bst = new IntegerBST();

        int[] arr = {1, 5, 2, 15, 8};

//        System.out.println(bst.CreateBSTFromPostOrderArray(arr));

        int[] arr1 = {1, 15, 2, 5, 8};

//        System.out.println(bst.CreateBSTFromPostOrderArray(arr1));

//        IntegerBST bst = CreateBSTFromPostOrderArray(arr1);
//
//        bst.postorder();

        IntegerBST bst2 = CreateBSTFromPostOrderArray(arr);

        bst2.postorder();
    }
}
