import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dati<E> {
    List<E> dati;
    String passw;

    public Dati(String pwd){
        this.dati = new ArrayList<E>();
        this.passw = pwd;
    }

    public void setPwd(String pwd){
        this.passw = pwd;
    }

    public void add(E data){
        this.dati.add(data);
    }

    public void removeData(E data){
        while(dati.contains(data)){
            dati.remove(data);
        }
    }
    public boolean isMe(String pwd){
        return passw.equals(pwd);
    }

    public boolean contains(E in){
        return dati.contains(in);
    }

    public E get(E data){
        int i = dati.indexOf(data);
        return dati.get(i);
    }

    public Iterator<E> iterator(){
        return dati.iterator();
    }
    public int size(){
        return dati.size();
    }
}
