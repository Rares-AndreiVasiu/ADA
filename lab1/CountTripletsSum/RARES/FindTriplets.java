import java.io.*;
import java.util.*;

public class FindTriplets {

    public static int countTriplets(int n, int[] v, int s)
    {
        Arrays.sort(v);

        int counter = 0;

        for(int i = 0; i < n - 1; ++ i)
        {
            // we use bin search
            int left = i + 1;

            int right = n - 1;

            int middle = v[i];

            while(left < right)
            {
                if(middle + v[left] + v[right] == s)
                {
                    counter++;

                    System.out.println(middle + " " + v[left] + " "+ v[right]);

                    left++;

                    right--;
                }
                else
                {
                    if(middle + v[left] + v[right] < s)
                    {
                        left++;
                    }
                    else
                    {
                        right--;
                    }
                }
            }
        }

        return counter;
    }
}
