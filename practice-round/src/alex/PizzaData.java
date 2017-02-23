package alex;

public class PizzaData {

    public int R;
    public int C;
    public int L;
    public int H;
    public int[][] initialPizza; // 0 = T; 1 = M

    public PizzaData() {}

    public PizzaData(final PizzaData pizzaData) {
        this.R = pizzaData.R;
        this.C = pizzaData.C;
        this.L = pizzaData.L;
        this.H = pizzaData.H;
        this.initialPizza = pizzaData.initialPizza.clone();
    }

}
