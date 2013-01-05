package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {

    private final static String SETS_PATH = File.separator + "sets";
    private final static File DEFAULT_OUT_DIR = new File("plugins" + File.separator + "images" + SETS_PATH);
    private static File outDir = DEFAULT_OUT_DIR;

    private static final String[] symbols =     {"10E", "9ED", "8ED", "7ED", "6ED", "5ED", "4ED", "3ED", "2ED", "LEB", "LEA",
                                                 "HOP","PC2",
                                                 "ARN", "ATQ", "LEG", "DRK", "FEM", "HML",
                                                 "ICE", "ALL", "CSP",
                                                 "MIR", "VIS", "WTH",
                                                 "TMP", "STH", "EXO", 
                                                 "USG", "ULG", "UDS",
                                                 "MMQ", "NMS", "PCY",
                                                 "INV", "PLS", "APC",
                                                 "ODY", "TOR", "JUD",
                                                 "ONS", "LGN", "SCG",
                                                 "MRD", "DST", "5DN", 
                                                 "CHK", "BOK", "SOK", 
                                                 "RAV", "GPT", "DIS", 
                                                 "TSP", "TSB", "PLC", "FUT", 
                                                 "LRW", "MOR", 
                                                 "SHM", "EVE"};
    
    private static final String[] withMythics = {"M10", "M11", "M12", "M13", 
                                                 "DDF", 
                                                 "ALA", "CFX", "ARB", 
                                                 "ZEN", "WWK", "ROE", 
                                                 "SOM", "MBS", "NPH", 
                                                 "ISD", "DKA", "AVR", 
                                                 "RTR", "GTC"};
    private static final HashMap<String, String> symbolsReplacements = new HashMap<String, String>();

    static {
        symbolsReplacements.put("CFX", "CON");
        symbolsReplacements.put("ARN", "AN");
        symbolsReplacements.put("ATQ", "AQ");
        symbolsReplacements.put("LEG", "LE");
        symbolsReplacements.put("DRK", "DK");
        symbolsReplacements.put("FEM", "FE");
        symbolsReplacements.put("HML", "HM");
        symbolsReplacements.put("ICE", "IA");
        symbolsReplacements.put("ALL", "AL");
        symbolsReplacements.put("APC", "AP");
        symbolsReplacements.put("TMP", "TE");
        symbolsReplacements.put("INV", "IN");
        symbolsReplacements.put("PLS", "PS");
        symbolsReplacements.put("WTH", "WL");
        symbolsReplacements.put("ULG", "GU");
        symbolsReplacements.put("USG", "UZ");
        symbolsReplacements.put("UDS", "CG");
        symbolsReplacements.put("ODY", "OD");
        symbolsReplacements.put("MMQ", "MM");
        symbolsReplacements.put("NMS", "NE");
        symbolsReplacements.put("PCY", "PR");
        symbolsReplacements.put("STH", "ST");
        symbolsReplacements.put("EXO", "EX");
        symbolsReplacements.put("VIS", "VI");
        symbolsReplacements.put("MIR", "MI");
        symbolsReplacements.put("7ED", "7E");
        symbolsReplacements.put("6ED", "6E");
        symbolsReplacements.put("5ED", "5E");
        symbolsReplacements.put("4ED", "4E");
        symbolsReplacements.put("3ED", "RV");
        symbolsReplacements.put("2ED", "UN");
        symbolsReplacements.put("LEB", "BE");
        symbolsReplacements.put("LEA", "AL");
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
