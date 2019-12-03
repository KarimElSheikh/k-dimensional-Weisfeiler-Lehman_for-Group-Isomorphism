package datastructure;

import java.util.Comparator;

public class IndexedTreeMap3 extends IndexedTreeMap<Integer, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6889144432595566635L;
	
	public IndexedTreeMap3() {
		super();
	}
	
	public Integer increment(Integer key) {
        Entry<Integer, Integer> t = root;
        if (t == null) {
            // TBD:
            // 5045147: (coll) Adding null to an empty IndexedTreeSet should
            // throw NullPointerException
            //
            // compare(key, key); // type check
            root = new Entry<Integer, Integer>(key, 1, null);
            root.weight = 1;
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Entry<Integer, Integer> parent;
        // split comparator and comparable paths
        Comparator<? super Integer> cpr = comparator;
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
            Comparable<? super Integer> k = (Comparable<? super Integer>) key;
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
        Entry<Integer, Integer> e = new Entry<Integer, Integer>(key, 1, parent);
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
