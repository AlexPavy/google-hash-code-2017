package alex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PizzaReader {

    public static void main(String[] args) throws IOException {
        new DataWriter().write();
        try (BufferedReader br = new BufferedReader(new FileReader("practice-round/big.in"))) {
            String line = br.readLine();

            String[] splitted = line.split(" ");
            PizzaData pizzaData = new PizzaData();
            pizzaData.R = Integer.parseInt(splitted[0]);
            pizzaData.C = Integer.parseInt(splitted[1]);
            pizzaData.L = Integer.parseInt(splitted[2]);
            pizzaData.H = Integer.parseInt(splitted[3]);

            pizzaData.initialPizza = new int[pizzaData.R][pizzaData.C];

            int j = 0;
            while (line != null) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'M') {
                        pizzaData.initialPizza[j][i] = 1;
                    } else {
                        pizzaData.initialPizza[j][i] = 0;
                    }
                }
                j++;
            }
        }
    }

}
