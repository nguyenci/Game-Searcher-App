// --== CS400 Project Two File Header ==--
// Name: Trevor Johnson
// CSL Username: trevorj
// Email: tmjohnson32@wisc.edu
// Lecture #: 002
// Notes to Grader: <N/A>

import java.util.*;

/**
 * Hashtable used to input a game title then retrieve the ID for that game
 *
 */
public class GameHashTable<K, V> {

    private Hashtable<K, V> table;

    /**
     * Constructor for the hashtable
     *
     */
    public GameHashTable() {
        table = new Hashtable<>();
    }

    /**
     * Method used to add objects to the hashtable
     *
     * @param key represents the title of a game
     * @param value represents the ID of a game
     *
     */
    public void addKV(K key, V value) {
        table.put(key, value);
    }

    /**
     * Helper method to extract gameID
     *
     * @param key represents the title of a game
     * @return the title of a game (key)
     */
    public V getID(K key) {
        return table.get(key);
    }
}
