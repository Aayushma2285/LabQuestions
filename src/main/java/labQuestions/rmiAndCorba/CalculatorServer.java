package labQuestions.rmiAndCorba;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry on port 1099
            LocateRegistry.createRegistry(1099);

            // Create remote object
            Calculator calc = new CalculatorImpl();

            // Bind the object to a name in RMI registry
            Naming.rebind("rmi://localhost:1099/CalculatorService", calc);

            System.out.println("Calculator RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
