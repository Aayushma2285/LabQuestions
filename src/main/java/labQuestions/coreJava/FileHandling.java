package labQuestions.coreJava;


import java.io.*;

class Items implements Serializable{
    int id;
    String itemName;

    public Items(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    void display(){
        System.out.println(id + " " + itemName);
    }
}
public class FileHandling {
    public static void main(String[] args) {
        try{
            Items i1 = new Items(100, "Pencils");

            FileOutputStream fos = new FileOutputStream("Itemlist.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(i1);
            oos.close();
            fos.close();

            System.out.println("Object serialized successfully");
        }catch (Exception e){
            System.out.println(e);
        }

        try{
            FileInputStream fis = new FileInputStream("Itemlist.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Items i2 = (Items) ois.readObject();
            i2.display();

            ois.close();
            fis.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
