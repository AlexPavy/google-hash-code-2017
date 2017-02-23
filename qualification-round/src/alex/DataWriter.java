package alex;

import java.io.IOException;
import java.io.PrintWriter;

public class DataWriter {

    public void write() {
        try{
            PrintWriter writer = new PrintWriter(
                    "qualification-round/src/alex/alex.out", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}
