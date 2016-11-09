package lexAnalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author cuihao
 */
public class IOHelper {
    BufferedReader reader;

    public IOHelper(String path) {
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found! class=IOHelper, method=IOHelper(String)");
        }
    }

    public String nextLine() {
        assert reader!=null;
        String next = null;
        try {
            next = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return next;
    }

    public void closeFile() {
        if (reader==null) return;
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IOHelper helper = new IOHelper("testFile/testProgram.txt");
        String temp = "";
        while ((temp = helper.nextLine()) != null) {
            System.out.println(temp);
        }
        helper.closeFile();
    }
}
