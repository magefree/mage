package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {

    private static final String SETS_PATH = File.separator + "sets";
    private static final File DEFAULT_OUT_DIR = new File("plugins" + File.separator + "images" + SETS_PATH);
    private static File outDir = DEFAULT_OUT_DIR;

    private static final String[] symbols =     {"10E", "9ED", "8ED", "7ED", "6ED", "5ED", "4ED", "3ED", "2ED", "LEB", "LEA",
                                                 "HOP",
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
                                                 "SHM", "EVE",
                                                 "MED", "ME2", "ME3", "ME4",
                                                 "POR", "PO2", "PTK"};
    
    private static final String[] withMythics = {"M10", "M11", "M12", "M13", "M14", "M15", "ORI",
                                                 "DDF", "DDG", "DDH", "DDI", "DDJ", "DDK", "DDL", "DDM", "DDN", 
                                                 "DD3", "DD3B", "DDO", "DDP",
                                                 "FVD", "FVE", "FVL", "FVR",
                                                 "V12", "V13", "V14", "V15",
                                                 "ALA", "CON", "ARB",
                                                 "ZEN", "WWK", "ROE", 
                                                 "SOM", "MBS", "NPH",
                                                 "CMD", "C13", "C14", "PC2",
                                                 "ISD", "DKA", "AVR", 
                                                 "RTR", "GTC", "DGM",
                                                 "MMA", "MM2",
                                                 "THS", "BNG", "JOU",
                                                 "CNS", "VMA", "TPR",
                                                 "KTK", "FRF", "DTK",
                                                 "BFZ", "EXP", "OGW"};
    private static final HashMap<String, String> symbolsReplacements = new HashMap<>();

    static {
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
        symbolsReplacements.put("3ED", "3E");
        symbolsReplacements.put("2ED", "2U");
        symbolsReplacements.put("LEB", "2E");
        symbolsReplacements.put("LEA", "1E");
        symbolsReplacements.put("DD3A", "DD3");
        symbolsReplacements.put("DD3B", "DD3");
        symbolsReplacements.put("DD3C", "DD3");
        symbolsReplacements.put("DD3D", "DD3");
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
        ArrayList<DownloadJob> jobs = new ArrayList<>();
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
