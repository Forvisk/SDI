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

/**
 *
 * @author gustavo
 */
public class BinPackingFFD extends IBinPacking {

    private List<Bin> bins = new ArrayList<Bin>();

    public BinPackingFFD(List<Integer> in, int binSize) {
        super(in, binSize);
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
                    Random ran = new Random();
                    Integer dRand = ran.nextInt() % 1000;

                    if (dRand > 9000) {
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
        TrabalhoFinalSDI.logger.info("Bins:");
        if (bins.size() == in.size()) {
            TrabalhoFinalSDI.logger.info("each item is in its own bin");
        } else {
            bins.forEach((bin) -> {
                TrabalhoFinalSDI.logger.info(bin.toString());
            });
        }
    }
}
