import java.util.PriorityQueue;

public class HufmanEncoderPrototype {
    private static int R = 256;

    private static class HufmanNode implements Comparable<HufmanNode>
    {
        char ch;
        int freq;
        HufmanNode left, right;

        HufmanNode(char ch, int freq, HufmanNode left, HufmanNode right)
        {
            this.ch = ch;

            this.freq = freq;

            this.left = left;

            this.right = right;
        }

        boolean isLeaf()
        {
            return (left == null) &&
                        (right == null);
        }

        public int compareTo(HufmanNode other)
        {
            return this.freq - other.freq;
        }
    }

    private static class CompressionResult
    {
        HufmanNode huffmanCodes;
        String bits;

        public CompressionResult(String bits, HufmanNode tree)
        {
            this.huffmanCodes = tree;
            this.bits = bits;
        }
    }

    public static CompressionResult encode(String s)
    {
        char[] input = s.toCharArray();

        int[] freq = new int[R];

        for (int i = 0; i < input.length; ++i)
        {
            freq[input[i]]++;
        }

        HufmanNode root = buildHuffmanTree(freq);

        String[] st = new String[R];

        buildCodeTable(st, root, "");

        StringBuilder encodedBits = new StringBuilder();

        for (char c : input)
        {
            encodedBits.append(st[c]);
        }

        System.out.println("Huffman Codes:");

        printCodeTable(st);

        return new CompressionResult(encodedBits.toString(), root);
    }

    private static HufmanNode buildHuffmanTree(int[] freq)
    {
        PriorityQueue<HufmanNode> pq = new PriorityQueue<>();

        for (char c = 0; c < R; ++c)
        {
            if (freq[c] > 0)
            {
                pq.add(new HufmanNode(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1)
        {
            HufmanNode left = pq.remove();

            HufmanNode right = pq.remove();

            HufmanNode parent = new HufmanNode('\0', left.freq + right.freq, left, right);

            pq.add(parent);
        }
        return pq.remove();
    }

    private static void buildCodeTable(String[] st, HufmanNode x, String s)
    {
        if (x == null)
        {
            return;
        }

        if (x.isLeaf())
        {
            st[x.ch] = s;
        }

        buildCodeTable(st, x.left, s + '0');

        buildCodeTable(st, x.right, s + '1');
    }

    private static void printCodeTable(String[] st)
    {
        for (int i = 0; i < R; ++ i)
        {
            if (st[i] != null)
            {
                System.out.println("Code " + (char) i + " is " + st[i]);
            }
        }
    }

    public static void main(String[] args)
    {
        String original = "ABRACABABRA";

        System.out.println("Original message: " + original);

        CompressionResult result = encode(original);

        System.out.println("Encoded message specs: " + result.bits.length() + " bits");

        System.out.println("Encoded message: : " + result.bits);
    }
}
