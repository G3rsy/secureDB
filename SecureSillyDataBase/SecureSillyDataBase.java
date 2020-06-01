import java.util.*;
/*
 *   Abstacrt Function: <OWN, DATA> -> {< Owner0, <passw0, Dati0>>, ..., <Owner n-1, <passw n-1, Dati n-1>>}
 *                      Dati -> [Dati0, ...,Dati j-1]
 *
 *   Rapresentation Invariant: Owner, passw e Dati != null
 *                             per ogni n t.c. esiste Owner n, deve esistere passw n
 *                             dato 0< i <N-1, dato 1< j <N-1, i!=j. per ogni i, per ogni j vale (Owner[i] != Owner[j])
 *                             Dati puo' essere vuota e non deve contenere elementi null
 *
 *
 */
public class SecureSillyDataBase<E>implements SecureDataContainer<E> {
    private HashMap<String, Dati<E>> db;


    public SecureSillyDataBase(){
        this.db = new HashMap<String, Dati<E>>();
    }

    @Override
    public void createUser(String Id, String passw) throws InvalidUserCredential {
        if(Id == null || passw == null)
            throw new NullPointerException();

        if(db.containsKey(Id))
            throw new InvalidUserCredential();

        Dati<E> nuovo = new Dati<E>(passw);

        db.put(Id, nuovo);
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS:   se Owner == null -> NullPointerException (unchecked)
    //          se passw == null -> NullPointerException (unchecked)
    //          se Owner gia' appartiene a db -> InvalidUserCredential (checked)
    //MODIFIES: db
    //EFFECTS: Inserisce il nuovo utente con la relativa password ad OWN

    @Override
    public void removeUser(String Id, String passw) throws InvalidUserCredential{
        amI(Id, passw);

        db.remove(Id);
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: se Owner == null -> NullPointerException (unchecked)
    //        se passw == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: this
    //EFFECTS: rimuove l'utente e tutti i sui dati dalla collezione

    @Override
    public int getSize(String Owner, String passw) throws InvalidUserCredential {
        amI(Owner, passw);

        return db.get(Owner).size();
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException
    //        se passw == null -> NullPointerException
    //        se la coppia Owner passw non appartiene a OWN -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: restituisce il numero di dati appartenenti a Owner

    @Override
    public boolean put(String Owner, String passw, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(data == null)
            throw new NullPointerException();

        db.get(Owner).add(data);
        return true;
    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene a db -> InvalidUserCredential (checked)
    //MODIFIES: db
    //EFFECTS:Inserisce data alla collezione di Owner

    @Override
    public E get(String Owner, String passw, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(data==null)
            throw new NullPointerException();

        if(!db.get(Owner).contains(data))
            throw new IllegalArgumentException();

        return db.get(Owner).get(data);

    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene a db -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: restituisce una copia di data

    @Override
    public E remove(String Owner, String passw, E data) throws InvalidUserCredential {
            amI(Owner, passw);
            if(data == null)
                throw new NullPointerException();

            if(!db.get(Owner).contains(data))
                throw new IllegalArgumentException();

            Dati<E> tmp = db.get(Owner);
            E ret = tmp.get(data);
            while(tmp.contains(data)){
                tmp.removeData(data);
            }

            return ret;

    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: se rimuove tutte le occorrenze dell'elemento dalla collezione e lo ritorna.

    @Override
    public void copy(String Owner, String passw, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(data==null)
            throw new NullPointerException();

        if(!db.get(Owner).contains(data))
            throw new IllegalArgumentException();

        put(Owner, passw, data);
    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: aggiunge una copia di data in DATA

    @Override
    public void share(String Owner, String passw, String Other, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(Owner.equals(Other))
            throw new InvalidUserCredential();

        if(Other == null || data == null)
            throw new NullPointerException();

        if(!db.containsKey(Other))
            throw new NullPointerException();

        if(!db.get(Owner).contains(data))
            throw new IllegalArgumentException();

        db.get(Other).add(data);

    }
    //REQUIRES: Owner != null && passw != null && Other != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se Other == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene a db -> InvalidUserCredential (checked)
    //        Se Other non appartiene a db -> InvalidUserCredential (checked)
    //        Se Owner == Other -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: Condivide data appartente ad Owner con Other

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidUserCredential {
        amI(Owner, passw);
        return db.get(Owner).iterator();
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES:--
    //EFFECTS: ritorna un iteratore su dati di Owner



    private void amI(String Id, String passw) throws InvalidUserCredential{
        if(Id == null || passw == null)
            throw new NullPointerException();
        if(!db.containsKey(Id) || !db.get(Id).isMe(passw))
            throw new InvalidUserCredential();

    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        Se Other non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: Non lancia eccezzione se Owner e passw corrispondo ad un utente

}


