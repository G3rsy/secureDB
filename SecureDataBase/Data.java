/*
 *   Gabriele Sergi
 *   Corso A
 *   532362
 */

import java.util.Vector;

public class Data<E> {
    private E data;
    private String Owner;
    private Vector<String> shered;

    public Data(String in, E dataIn){
        this.data = dataIn;
        this.Owner = new String(in);
        this.shered = new Vector<String>();
    }
    public boolean isOwn(String in){
        return this.Owner.equals(in);
    }

    public boolean isShared(String in){
        return shered.contains(in);
    }

    public boolean isEqual(E in){
        return data.equals(in);
    }

    public void shareWith(String name){
        shered.add(name);
    }

    public E getData(){
        return data;
    }

    public void print(){
        System.out.println("Owner: "+Owner + " Data: '"+data+"'");
        System.out.println("Shared with: "+shered.toString());
    }
}
