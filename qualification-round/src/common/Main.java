package common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhngocnguyen on 23/02/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String inputFilename = args[0];
        String outputFilename = "output_"+inputFilename;
        //Read input
        List<String> listString =  FileUtils.readLines(args[0]);

        //contruction object
        for (String s : listString){

            System.out.println(s.toString());
        }

        List<String> output = new ArrayList<String>();
        output.add("kncdkfjnvdkfjv");
        //Write output
        FileUtils.writeLine(outputFilename,output);
    }
}
