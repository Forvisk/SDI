/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinalsdi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author gustavo
 */
public abstract class IBinPacking {
    protected final List<Integer> in;
    protected int binSize;

    public IBinPacking(List<Integer> in, int binSize) {
        this.in = in;
        this.binSize = binSize;
    }
    
    public static <T> T mostCommon(List<T> list) {
    Map<T, Integer> map = new HashMap<>();

    for (T t : list) {
        Integer val = map.get(t);
        map.put(t, val == null ? 1 : val + 1);
    }

    Entry<T, Integer> max = null;

    for (Entry<T, Integer> e : map.entrySet()) {
        if (max == null || e.getValue() > max.getValue())
            max = e;
    }

    return max.getKey();
}

    /**
     * runs algorithm and returns minimum number of needed bins.
     *
     * @return minimum number of needed bins
     */
    public abstract int getResult();

    /**
     * print the best bin packing determined by getResult().
     */
    public abstract void printBestBins();

    public List<Bin> deepCopy(List<Bin> bins) {
        ArrayList<Bin> copy = new ArrayList<Bin>();
        for (int i = 0; i < bins.size(); i++) {
            copy.add(bins.get(i).deepCopy());
        }
        return copy;
    }
}
