package org.mage.plugins.card.dl.sources;

import com.google.common.collect.AbstractIterator;
import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {
    private static final File     outDir  = new File("plugins/images/sets");
    private static final String[] symbols = {"DIS", "GPT", "RAV", "MRD", "10E", "HOP"};
    private static final String[] withMythics = {"ALA", "CFX", "ARB", "ZEN", "WWK", "ROE", "SOM", "M10", "M11", "DDF", "MBS"};

    @Override
    public Iterator<DownloadJob> iterator() {
        ArrayList<DownloadJob> jobs = new ArrayList<DownloadJob>();
        for (String symbol : symbols) {
            jobs.add(generateDownloadJob(symbol, "C"));
            jobs.add(generateDownloadJob(symbol, "U"));
            jobs.add(generateDownloadJob(symbol, "R"));
        }
         for (String symbol : withMythics) {
            jobs.add(generateDownloadJob(symbol, "C"));
            jobs.add(generateDownloadJob(symbol, "U"));
            jobs.add(generateDownloadJob(symbol, "R"));
            jobs.add(generateDownloadJob(symbol, "M"));
         }
        return jobs.iterator();
    }

    private DownloadJob generateDownloadJob(String set, String rarity) {
        File dst = new File(outDir, set + "-" + rarity + ".jpg");
        if (set.equals("CFX")) { // hack for special reserved filaname "CON" in Windows
                set = "CON";
        }
        String url = "http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=" + set + "&size=small&rarity=" + rarity;
        return new DownloadJob(set + "-" + rarity, fromURL(url), toFile(dst));
    }
}
