/*
*   Gabriele Sergi
*   Corso A
*   532362
 */
import java.security.acl.Owner;
import java.util.*;
/*
*   Abstacrt Function: <OWN, DATA> -> < OWN, DATA > t.c.
*                       OWN -> { <Owner, passw> } Insieme di coppie Owner, password t.c.
*                                                 Owner != null && passw != null && <Owner, passw> != null &&
*                                                 Non esistono due Owner uguali.
*
*                       DATA -> [ <data, Owner, {shared}> ] collezione di dati con un Owner e una lista di persone
*                                                           con cui si condivide il dato t.c.
*                                                           data != null && Owner != null &&
*                                                           {shared} non contiene elemnti null &&
*                                                           <data, Owner, {shared}> != null
*
*   Rapresentation Invariant: <OWN, DATA> -> t.c. OWN != null && DATA != null
*                             { <Owner[0], passw[0]>,... ,<Owner[N-1], passw[N-1]> }
*                              Insieme di coppie Owner, password t.c.
*                              dato i tra 0 ed N-1, dato j da 1 ad N-1, i!=j. per ogni i, per ogni j vale (Owner[i] != Owner[j])
*                              qualsiasi Owner != null && qualsiasi passw != null &&
*                              <Owner, passw> != null
*
*                             [ <data[0], Owner[0], {shared}[0]>,... , <data[k-1], Owner[k-1], {shared}[k-1]> ]
*                              collezione di triple data, Owner e {shared} t.c.
*                              data != null && Owner != null && {shared} non contiene elementi null && {shared} != null
*                              <data, Owner, {shared}> != null && Owner non appartiene a shared
*
*
 */
public class SecureDataBase<E>implements SecureDataContainer<E> {
    private HashMap<String, String> OWN;
    private List<Data<E>> DATA;

    public SecureDataBase(){
        OWN = new HashMap<String, String>();
        DATA = new ArrayList<Data<E>>();
    }

    @Override
    public void createUser(String Id, String passw) throws InvalidUserCredential {
        if(Id == null || passw == null)
            throw new NullPointerException();
        if(OWN.containsKey(Id))
            throw new InvalidUserCredential();

        OWN.put(Id, passw);
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS:   se Owner == null -> NullPointerException (unchecked)
    //          se passw == null -> NullPointerException (unchecked)
    //          se Owner gia' appartiene a OWN -> InvalidUserCredential (checked)
    //MODIFIES: OWN
    //EFFECTS: Inserisce il nuovo utente con la relativa password ad OWN

    @Override
    public void removeUser(String Id, String passw) throws InvalidUserCredential{
        if(Id == null || passw == null)
            throw new NullPointerException();
        if(!OWN.containsKey(Id))
            throw new InvalidUserCredential();

        Data<E> tmp;
        for(int i =0; i<DATA.size(); i++){
            tmp = DATA.get(i);
            if(tmp.isOwn(Id)){
                DATA.remove(tmp);
                i--;
            }
        }

        OWN.remove(Id);
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

        int counter = 0;
/*
        //Contando solo i dati di cui sono Owner
        for(int i=0; i<DATA.size(); i++){
            Data<E> tmp = DATA.get(i);
            if(tmp.isOwn(Owner))
                counter++;
        }
        return counter;
*/

        //Contando i dati condivisi
        for(int i=0; i<DATA.size(); i++){
            Data<E> tmp = DATA.get(i);
            if(tmp.isOwn(Owner) || tmp.isShared(Owner))
                counter++;
        }
        return counter;

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

        Data<E> nuovo = new Data<E>(Owner, data);
        DATA.add(nuovo);

        return true;
    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: inserisce data di proprieta' di Owner nella collezione DATA

    @Override
    public E get(String Owner, String passw, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(data == null)
            throw new NullPointerException();
        int i = 0;
        boolean found = false;
        E out = null;

        while(i < DATA.size() && !found){
            if(DATA.get(i).getData().equals(data) && (DATA.get(i).isOwn(Owner) || DATA.get(i).isShared(Owner))){
                out = DATA.get(i).getData();
                found = true;
            }
            i++;
        }

        if(!found)
            throw new IllegalArgumentException();

        return out;
    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad OWN -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: restituisce una copia di data

    @Override
    public E remove(String Owner, String passw, E data) throws InvalidUserCredential {
        amI(Owner, passw);
        if(data == null)
            throw new NullPointerException();
        Data<E> ret = null;
        Data<E> tmp;

        for(int i =0; i<DATA.size(); i++){
            tmp = DATA.get(i);
            if(tmp.isEqual(data) && tmp.isOwn(Owner)){
                ret = tmp;
                DATA.remove(tmp);
                i--;
            }
        }

        if(ret==null)
            throw new IllegalArgumentException();
        else
            return ret.getData();
    }
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: se rimuove tutte le occorrenze dell'elemento dalla collezione e lo ritorna

    @Override
    public void copy(String Owner, String passw, E data) throws InvalidUserCredential {
        E obj = get(Owner, passw, data);
        put(Owner, passw, obj);
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
        if(!OWN.containsKey(Other) || Owner.equals(Other))
            throw new InvalidUserCredential();
        if(data == null)
            throw new NullPointerException();

        int i = 0;
        boolean found = false;

        while(i < DATA.size() && !found){
            Data<E> tmp = DATA.get(i);
            if(tmp.isEqual(data) && tmp.isOwn(Owner)){
                tmp.shareWith(Other);
                found = true;
            }
            i++;
        }
        if(!found)
            throw new IllegalArgumentException();
    }
    //REQUIRES: Owner != null && passw != null && Other != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se Other == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //        Se Other non appartiene ad Own -> InvalidUserCredential (checked)
    //        Se Owner == Other -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: Condivide data appartente ad Owner con Other

    @Override
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidUserCredential {
        amI(Owner, passw);

        Set<E> daRestituire= new TreeSet<E>();

        for(int i=0;i < DATA.size(); i++){
            if(DATA.get(i).isShared(Owner) || DATA.get(i).isOwn(Owner)){
                daRestituire.add(DATA.get(i).getData());
            }
        }

        return daRestituire.iterator();
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES:--
    //EFFECTS: ritorna un iteratore su dati di Owner

    private void amI(String ownIn, String passw) throws InvalidUserCredential{
        if(ownIn == null || passw == null)
            throw new NullPointerException();
        if(OWN.containsKey(ownIn)){
            if(!OWN.get(ownIn).equals(passw))
                throw new InvalidUserCredential();
        }else{
            throw new InvalidUserCredential();
        }
    }
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        Se Other non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: Non lancia eccezzione se Owner e passw corrispondo ad un utente

}


