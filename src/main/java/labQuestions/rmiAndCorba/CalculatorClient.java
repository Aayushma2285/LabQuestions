package labQuestions.rmiAndCorba;

import java.rmi.Naming;
public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost:1099/CalculatorService");

            System.out.println("10 + 5 = " + calc.add(10, 5));
            System.out.println("10 - 5 = " + calc.subtract(10, 5));
            System.out.println("10 * 5 = " + calc.multiply(10, 5));
            System.out.println("10 / 5 = " + calc.divide(10, 5));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
