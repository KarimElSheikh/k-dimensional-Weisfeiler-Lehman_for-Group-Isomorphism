package datastructure;

import java.util.Comparator;

import datastructure.IndexedTreeMap.Entry;
import pair.Pair;

public class IndexedTreeMap2 extends IndexedTreeMap<Pair, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 183140471235285341L;

	public IndexedTreeMap2() {
		super();
	}
	
	public Integer increment(Pair key) {
        Entry<Pair, Integer> t = root;
        if (t == null) {
            // TBD:
            // 5045147: (coll) Adding null to an empty IndexedTreeSet should
            // throw NullPointerException
            //
            // compare(key, key); // type check
            root = new Entry<Pair, Integer>(key, 1, null);
            root.weight = 1;
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Entry<Pair, Integer> parent;
        // split comparator and comparable paths
        Comparator<? super Pair> cpr = comparator;
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
            Comparable<? super Pair> k = (Comparable<? super Pair>) key;
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
        Entry<Pair, Integer> e = new Entry<Pair, Integer>(key, 1, parent);
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
