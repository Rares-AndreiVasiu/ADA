import java.io.*;

public class InputDevice
{
    String fileName;

    FileReader fileReader;

    InputDevice(String _fileName) throws FileNotFoundException
    {
        this.fileName = _fileName;

        this.fileReader = new FileReader(this.fileName);
    }

    public String getInputs()
    {
        String lines;

        try(BufferedReader br = new BufferedReader(this.fileReader))
        {
            StringBuilder sb = new StringBuilder();

            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);

                sb.append(System.lineSeparator());

                line = br.readLine();
            }
            lines = sb.toString();
        }
        catch (IOException e)
        {
            System.out.println("Error with i/o " + e.getMessage());

            lines = null;
        }

        return lines;
    }

}
