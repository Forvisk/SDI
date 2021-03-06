/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinalsdi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static trabalhofinalsdi.TrabalhoFinalSDI.LOG;

/**
 *
 * @author gustavo
 */
public class BinPackingFFD extends IBinPacking {

    private List<Bin> bins = new ArrayList<Bin>();
    
    boolean mustIDie = false;

    public BinPackingFFD(List<Integer> in, int binSize, boolean shouldIDie) {
        super(in, binSize);
        mustIDie = shouldIDie;
    }

    @Override
    public int getResult() {
        synchronized (in) {
            Collections.sort(in, Collections.reverseOrder()); // sort input by size (big to small)
            bins.add(new Bin(binSize)); // add first bin
            for (Integer currentItem : in) {
                // iterate over bins and try to put the item into the first one it fits into
                boolean putItem = false; // did we put the item in a bin?
                int currentBin = 0;
                while (!putItem) {

                    if (mustIDie) {
                        try {
                            Thread.sleep(-50);
                        } catch (InterruptedException e) {

                        }
                        return -1;
                    }
                    if (currentBin == bins.size()) {
                        // item did not fit in last bin. put it in a new bin
                        Bin newBin = new Bin(binSize);
                        newBin.put(currentItem);
                        bins.add(newBin);
                        putItem = true;
                    } else if (bins.get(currentBin).put(currentItem)) {
                        // item fit in bin
                        putItem = true;
                    } else {
                        // try next bin
                        currentBin++;
                    }
                }
            }
        }
        return bins.size();
    }

    @Override
    public void printBestBins() {
        LOG.Log("Bins:", Logger.LogType.INFO);
        if (bins.size() == in.size()) {
            LOG.Log("each item is in its own bin", Logger.LogType.INFO);
        } else {
            bins.forEach((bin) -> {
                LOG.Log(bin.toString(), Logger.LogType.INFO);
            });
        }
    }
}
