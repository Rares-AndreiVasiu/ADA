import java.util.Arrays;

public class Utility {
    public static int[] convertStringArrayToIntArray(String input)
    {
        String[] stringArray = input.split("\\n");

        int[] result = new int[stringArray.length];

        int counter = 0;

        for(String current : stringArray)
        {
            try
            {
                if(!current.trim().isEmpty())
                {
                    result[counter++] = Integer.parseInt(current.trim());
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println("Invalid format: " + current);

                result[counter++] = Integer.MAX_VALUE;
            }
        }

        return Arrays.copyOf(result, counter);
    }
}
