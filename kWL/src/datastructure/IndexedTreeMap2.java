package datastructure;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class is just a modification of IndexedTreeMap with Integer as the value type forcibly,
 * and the increment method added whose job is to increment the value for the given key in 
 * one go.
 * 
 * @author Karim
 *
 * @param <K>
 */
public class IndexedTreeMap2<K> extends IndexedTreeMap<K, Integer> implements Serializable {
	
	/* version ID for serialized form. */
	private static final long serialVersionUID = 2240876683681077066L;
	
	/** Note: Comments below are from the original author "Vitaly Sazanovich" of IndexedTreeMap
	 *  for the put(K, V) method that this method is based on. [Karim Elsheikh]
	 */
	public Integer increment(K key) {
        Entry<K, Integer> t = root;
        if (t == null) {
            // TBD:
            // 5045147: (coll) Adding null to an empty IndexedTreeSet should
            // throw NullPointerException
            //
            // compare(key, key); // type check
            root = new Entry<K, Integer>(key, 1, null);
            root.weight = 1;
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Entry<K, Integer> parent;
        // split comparator and comparable paths
        Comparator<? super K> cpr = comparator;
        if (cpr != null) {
            do {
                parent = t;
                cmp = cpr.compare(key, t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else
                    return t.setValue(t.getValue() + 1);
            } while (t != null);
        } else {
            if (key == null)
                throw new NullPointerException();
            @SuppressWarnings("unchecked")
			Comparable<? super K> k = (Comparable<? super K>) key;
            do {
                parent = t;
                cmp = k.compareTo(t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else
                    return t.setValue(t.getValue() + 1);
            } while (t != null);
        }
        Entry<K, Integer> e = new Entry<K, Integer>(key, 1, parent);
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
        e.updateWeight(1);

        fixAfterInsertion(e);
        size++;
        modCount++;
        return null;
    }
	
}
