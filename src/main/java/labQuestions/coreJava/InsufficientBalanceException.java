package labQuestions.coreJava;


public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String message){
        super (message);
    }
}

class BankAccount{
    Integer balance;

    BankAccount(Integer balance){
        this.balance = balance;
    }

    void withdraw(Integer amount) throws InsufficientBalanceException{
        if(amount > balance){
            throw new InsufficientBalanceException("Your balance is insufficient");
        }else{
            balance -= amount;
            System.out.println("Withdrawal Suceesfully. Your remaining balance is: " + balance);
        }
    }

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
}