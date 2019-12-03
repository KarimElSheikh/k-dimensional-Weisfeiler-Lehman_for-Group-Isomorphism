package datastructure;

import java.util.Comparator;

import Colour.Colour;

public class IndexedTreeMap4 extends IndexedTreeMap<Colour, Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2544673713934911153L;

	public IndexedTreeMap4() {
		super();
	}
	
	public Integer increment(Colour key) {
        Entry<Colour, Integer> t = root;
        if (t == null) {
            // TBD:
            // 5045147: (coll) Adding null to an empty IndexedTreeSet should
            // throw NullPointerException
            //
            // compare(key, key); // type check
            root = new Entry<Colour, Integer>(key, 1, null);
            root.weight = 1;
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Entry<Colour, Integer> parent;
        // split comparator and comparable paths
        Comparator<? super Colour> cpr = comparator;
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
            Comparable<? super Colour> k = (Comparable<? super Colour>) key;
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
        Entry<Colour, Integer> e = new Entry<Colour, Integer>(key, 1, parent);
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
