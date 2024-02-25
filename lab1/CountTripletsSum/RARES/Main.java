import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

//sofer pe camion coding

public class Main{
    public static void main(String[] args)
    {
        int defaultSum = 0;

        if (args.length == 0)
        {
            System.out.println("Error: Invalid number of arguments");

            return;
        }
        else
        {
            InputDevice id;

            try {
                id = new InputDevice(args[0]);
            }
            catch (FileNotFoundException e)
            {
                id = null;

                System.out.println("Error with the file! " + e.getMessage());
            }

            String inputs = id.getInputs();

            int[] arr = Utility.convertStringArrayToIntArray(inputs);

            for(int i = 0; i < arr.length; ++ i)
            {
                System.out.println(arr[i]);
            }

            System.out.println(FindTriplets.countTriplets(arr.length, arr, defaultSum));
        }
    }
}
