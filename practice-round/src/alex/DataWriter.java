package alex;

import java.io.IOException;
import java.io.PrintWriter;

public class DataWriter {

    public void write() {
        try{
            PrintWriter writer = new PrintWriter("alex.out", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }

}
