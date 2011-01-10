package org.mage.plugins.card.dl.sources;

import com.google.common.collect.AbstractIterator;
import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.Iterator;

import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {
    private static final File     outDir  = new File("plugins/images/sets");
    private static final String[] symbols = { "M10", "M11", "ARB", "DIS", "GPT", "RAV", "ALA", "MRD",
                                              "ZEN", "WWK", "ROE", "SOM", "10E", "CFX", "HOP", };

    @Override
    public Iterator<DownloadJob> iterator() {
        return new AbstractIterator<DownloadJob>() {
            private int idx = 0;

            @Override
            protected DownloadJob computeNext() {
                if (idx == symbols.length) return endOfData();
                String symbol = symbols[idx];
                File dst = new File(outDir, symbol + ".jpg");
                if (symbol.equals("CFX")) { // hack for special reserved filaname "CON" in Windows
                    symbol = "CON";
                }
                String url = "http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=" + symbol + "&size=small&rarity=R";
                idx++;
                return new DownloadJob(symbol, fromURL(url), toFile(dst));
            }
        };
    }
}
