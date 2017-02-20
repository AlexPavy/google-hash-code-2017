package alex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class pizza_alex {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("practice-round/big.in"))) {
            String line = br.readLine();

            String[] splitted = line.split(" ");
            PizzaCalculator pizzaCalculator = new PizzaCalculator();
            pizzaCalculator.R = Integer.parseInt(splitted[0]);
            pizzaCalculator.C = Integer.parseInt(splitted[1]);
            pizzaCalculator.L = Integer.parseInt(splitted[2]);
            pizzaCalculator.H = Integer.parseInt(splitted[3]);

            pizzaCalculator.initialPizza = new boolean[pizzaCalculator.R][pizzaCalculator.C];

            int j = 0;
            while (line != null) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'M') {
                        pizzaCalculator.initialPizza[j][i] = true;
                    } else {
                        pizzaCalculator.initialPizza[j][i] = false;
                    }
                }
                j++;
            }
            pizzaCalculator.cutParts();
        }
    }

}
