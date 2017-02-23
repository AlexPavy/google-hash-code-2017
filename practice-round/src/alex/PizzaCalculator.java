package alex;

public class PizzaCalculator {

    void cutParts() {

    }

    /**
     * Limited by int i1, int i2, int j1, int j2
     */
    int enumeratePizzas(
            PizzaData data, int i1, int i2, int j1, int j2) {
        int n = 0;
        for (int i=i1;i<=i2;i++) {
            for (int j=j1;j<j2;j++) {
                if (data.initialPizza[i][j] != -1) {
                    n += enumerateStartingOn(data, i, i2, j, j2, 0, 0);
                }
            }
        }
        return 0;
    }

    private int enumerateStartingOn(
            PizzaData data, int i1, int i2, int j1, int j2,
            int mush, int toma) {

        PizzaData copy = new PizzaData(data);
        int n = 0;
        for (int i=i1;i<=i2;i++) {
            for (int j=j1;j<j2;j++) {
                if (copy.initialPizza[i][j] == 0) {
                    copy.initialPizza[i][j] = -1;
                    toma++;
                    if (toma + mush <= data.H) {
                        if (toma >= copy.L && mush >= copy.L) {
                            n++;
                        }
                    }
                    break;

                }
            }
        }
        return 0;
    }

}
