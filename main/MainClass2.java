import java.util.*;

public class MainClass2 {
    public static void main(String[] args) throws InvalidUserCredential {
        SecureDataBase<String> aba = new SecureDataBase<String>();

        String[] own = {"Ciccio 0", "Ciccio 1", "Ciccio 2", "Ciccio 3", "Ciccio 4", "Ciccio 5", "Ciccio 6", "Ciccio 7"};
        String[] pwd = {"bello", "pluto", "zio", "persefone", "4&99", "4formaggi", "Frank", "nio"};

        //inseirisco 8 utenti
        try {
            aba.createUser(own[0], pwd[0]);
            aba.createUser(own[1], pwd[1]);
            aba.createUser(own[2], pwd[2]);
            aba.createUser(own[3], pwd[3]);
            aba.createUser(own[4], pwd[4]);
            aba.createUser(own[5], pwd[5]);
            aba.createUser(own[6], pwd[6]);
            aba.createUser(own[7], pwd[7]);
        }catch(InvalidUserCredential e){
            e.printStackTrace();
        }


        //Rimuovo i primi 5
        try{
            aba.removeUser(own[0], pwd[0]);
            aba.removeUser(own[1], pwd[1]);
            aba.removeUser(own[2], pwd[2]);
            aba.removeUser(own[3], pwd[3]);
            aba.removeUser(own[4], pwd[4]);
        }catch(InvalidUserCredential e){
            e.printStackTrace();
        }

        //Sui restanti tre effettuo le varie operazioni sui dati:
        //PUT:
        //Inserisco dieci elementi per ogni utente rimasto nella struttura
        try{
            for(Integer i=0; i<30;i++){
                if(i<10){
                    aba.put(own[5], pwd[5], i.toString());
                }
                if (i > 9 && i < 20) {
                    aba.put(own[6], pwd[6], i.toString());
                }
                if(i>19){
                    aba.put(own[7], pwd[7], i.toString());
                }
            }

        }catch(InvalidUserCredential e){
            e.printStackTrace();
        }

        //SHARE:
        //Condivido ogni elemento di ogni utente con gli altri
        try{
            for(Integer i=0; i<30; i++){
                if(i<10){
                    aba.share(own[5], pwd[5], own[6], i.toString());
                    aba.share(own[5], pwd[5], own[7], i.toString());
                }
                if (i > 9 && i < 20) {
                    aba.share(own[6], pwd[6], own[5], i.toString());
                    aba.share(own[6], pwd[6], own[7], i.toString());
                }
                if(i>19){
                    aba.share(own[7], pwd[7], own[5], i.toString());
                    aba.share(own[7], pwd[7], own[6], i.toString());
                }
            }
        }catch (InvalidUserCredential e){
            e.printStackTrace();
        }

        System.out.println("Dati di "+ own[5] + "-> "+aba.getSize(own[5], pwd[5]));
        System.out.println("Dati di "+ own[6] + "-> "+aba.getSize(own[6], pwd[6]));
        System.out.println("Dati di "+ own[7] + "-> "+aba.getSize(own[7], pwd[7]));

    }
}
