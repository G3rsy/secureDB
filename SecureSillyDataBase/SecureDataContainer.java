/*Overview:
 *
 *
 * Typical Element =  < OWN, DATA >
 *                    OWN e' l'insieme degli utenti
 *                    DATA e' l'insieme dei dati
 */
import java.util.Iterator;

public interface SecureDataContainer<E> {
    // Crea l’identità un nuovo utente della collezione
    public void createUser(String Id, String passw) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null
    //THROWS:   se Owner == null -> NullPointerException (unchecked)
    //          se passw == null -> NullPointerException (unchecked)
    //          se Owner gia' appartiene a OWN -> InvalidUserCredential (checked)
    //MODIFIES: OWN
    //EFFECTS: Inserisce il nuovo utente con la relativa password ad OWN

    // Rimuove l’utente dalla collezione
    public void removeUser(String Id, String passw) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null
    //THROWS: se Owner == null -> NullPointerException (unchecked)
    //        se passw == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: this
    //EFFECTS: rimuove l'utente e tutti i sui dati dalla collezione

    // Restituisce il numero degli elementi di un utente presenti nella
    // collezione
    public int getSize(String Owner, String passw) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException
    //        se passw == null -> NullPointerException
    //        se la coppia Owner passw non appartiene a OWN -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: restituisce il numero di dati appartenenti a Owner

    // Inserisce il valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    public boolean put(String Owner, String passw, E data) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: inserisce data di proprieta' di Owner nella collezione DATA se data non appartiene a DATA
    //         restituisce true se il dato viene inserito

    // Ottiene una copia del valore del dato nella collezione
    // se vengono rispettati i controlli di identità
    public E get(String Owner, String passw, E data) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad OWN -> InvalidUserCredential (checked)
    //MODIFIES: --
    //EFFECTS: restituisce una copia di data

    // Rimuove il dato nella collezione
    // se vengono rispettati i controlli di identità
    public E remove(String Owner, String passw, E data) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: rimuove tutte le occorrenze di data da DATA

    // Crea una copia del dato nella collezione
    // se vengono rispettati i controlli di identità
    public void copy(String Owner, String passw, E data) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: aggiunge una copia di data in DATA

    // Condivide il dato nella collezione con un altro utente
    // se vengono rispettati i controlli di identità
    public void share(String Owner, String passw, String Other, E data) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null && Other != null && data != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        se Other == null -> NullPointerException (unchecked)
    //        se data == null -> NullPointerException (unchecked)
    //        se non esiste nessun data che apparitiene ad Owner -> IllegalArgumentException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //        Se Owner == Other -> InvalidUserCredential (checked)
    //        Se Other non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES: DATA
    //EFFECTS: Condivide data appartente ad Owner con Other

    // restituisce un iteratore (senza remove) che genera tutti i dati
    // dell’utente in ordine arbitrario
    // se vengono rispettati i controlli di identità
    public Iterator<E> getIterator(String Owner, String passw) throws InvalidUserCredential;
    //REQUIRES: Owner != null && passw != null
    //THROWS: Se Owner == null -> NullPointerException (unchecked)
    //        Se passw == null -> NullPointerException (unchecked)
    //        Se la coppia Owner passw non appartiene ad Own -> InvalidUserCredential (checked)
    //MODIFIES:--
    //EFFECTS: ritorna un iteratore su dati di Owner

    // … altre operazione da definire a scelta
}
