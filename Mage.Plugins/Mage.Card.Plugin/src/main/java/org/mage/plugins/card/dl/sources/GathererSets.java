package org.mage.plugins.card.dl.sources;

import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {

    private final static String SETS_PATH = File.separator + "sets";
    private final static File DEFAULT_OUT_DIR = new File("plugins" + File.separator + "images" + SETS_PATH);
    private static File outDir  = DEFAULT_OUT_DIR;

    private static final String[] symbols = {"DIS", "DST", "GPT", "RAV", "MRD", "10E", "HOP", "EVE", "APC", "TMP", "CHK"};
    private static final String[] withMythics = {"ALA", "CFX", "ARB", "ZEN", "WWK", "ROE", "SOM", "M10", "M11", "M12", "DDF", "MBS", "NPH"};
    private static final HashMap<String, String> symbolsReplacements = new HashMap<String, String>();

    static {
        symbolsReplacements.put("CFX", "CON");
        symbolsReplacements.put("APC", "AP");
        symbolsReplacements.put("TMP", "TE");
    }

    public GathererSets(String path) {
        if (path == null) {
            useDefaultDir();
        } else {
            changeOutDir(path);
        }
    }

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
        if (symbolsReplacements.containsKey(set)) {
            set = symbolsReplacements.get(set);
        }
        String url = "http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=" + set + "&size=small&rarity=" + rarity;
        return new DownloadJob(set + "-" + rarity, fromURL(url), toFile(dst));
    }

    private void changeOutDir(String path) {
        File file = new File(path + SETS_PATH);
        if (file.exists()) {
            outDir = file;
        } else {
            file.mkdirs();
            if (file.exists()) {
                outDir = file;
            }
        }
    }

    private void useDefaultDir() {
        outDir = DEFAULT_OUT_DIR;
    }
}
