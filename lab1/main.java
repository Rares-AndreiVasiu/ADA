import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

class ReadFile {
  public static void main(String[] args) {
    try {
      File file_to_be_read = new File("read_txt.txt");
      Scanner read_file = new Scanner(file_to_be_read);
      while (read_file.hasNextLine()) {
        String data = read_file.nextLine();
        System.out.println(data);
      }
      read_file.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}

class ReadFileCmd {
  public static void main(String[] args) {
    try {
      File read_the_filename = new File(args[0]);
      Scanner read_from_filename = new Scanner(read_the_filename);
      while (read_from_filename.hasNextLine()) {
        String data = read_from_filename.nextLine();
        System.out.println(data);
      }
      read_from_filename.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
