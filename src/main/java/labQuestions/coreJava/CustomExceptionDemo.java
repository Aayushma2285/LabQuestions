package labQuestions.coreJava;

public class CustomExceptionDemo{
    public static void main(String[] args) {
        BankAccount yourAcc = new BankAccount(10000);

        try{
            yourAcc.withdraw(15000);
        }catch(InsufficientBalanceException e){
            System.out.println("Exception encountered: " + e.getMessage());
        }
    }
}
