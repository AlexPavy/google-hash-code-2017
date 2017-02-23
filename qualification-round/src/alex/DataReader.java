package alex;

import common.dto.Problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    public static void main(String[] args) throws IOException {
        new DataWriter().write(new Problem());
        try (BufferedReader br = new BufferedReader(
                new FileReader("qualification-round/big.in"))) {
            String line = br.readLine();

            String[] splitted = line.split(" ");
            Data data = new Data();

            int j = 0;
            while (line != null) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < line.length(); i++) {
                }
                j++;
            }
        }
    }

}
