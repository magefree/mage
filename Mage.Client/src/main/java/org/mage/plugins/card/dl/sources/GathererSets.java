package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

public class GathererSets implements Iterable<DownloadJob> {

    private static final String SETS_PATH = File.separator + "sets";
    private static final File DEFAULT_OUT_DIR = new File("plugins" + File.separator + "images" + SETS_PATH);
    private static File outDir = DEFAULT_OUT_DIR;

    private static final String[] symbols = {"10E", "9ED", "8ED", "7ED", "6ED", "5ED", "4ED", "3ED", "2ED", "LEB", "LEA",
        "HOP",
        "ARN", "ATQ", "LEG", "DRK", "FEM", "HML",
        "ICE", "ALL", "CSP",
        "MIR", "VIS", "WTH",
        "TMP", "STH", "EXO",
        "USG", "ULG", "UDS",
        "MMQ", "NEM", "PCY",
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
        "POR", "PO2", "PTK",
        "ARC", "DD3EVG",
        "W16"};

    private static final String[] withMythics = {"M10", "M11", "M12", "M13", "M14", "M15", "ORI",
        "ANB",
        "DDF", "DDG", "DDH", "DDI", "DDJ", "DDK", "DDL", "DDM", "DDN",
        "DD3DVD", "DD3GLV", "DD3JVC", "DDO", "DDP", "DDQ", "DDR", "DDS",
        "ALA", "CON", "ARB",
        "ZEN", "WWK", "ROE",
        "SOM", "MBS", "NPH",
        "CMD", "C13", "C14", "C15", "C16", "CMA",
        "PC2", "PCA",
        "ISD", "DKA", "AVR",
        "RTR", "GTC", "DGM",
        "MMA", "MM2", "EMA", "MM3",
        "THS", "BNG", "JOU",
        "CNS", "CN2",
        "VMA", "TPR",
        "KTK", "FRF", "DTK",
        "BFZ", "OGW",
        "SOI", "EMN",
        "KLD", "AER",
        "AKH", "HOU"
    };

    private static final String[] onlyMythics = {
        "DRB", "V09", "V12", "V12", "V13", "V14", "V15", "V16", "EXP"
    };
    private static final String[] onlyMythicsAsSpecial = {
        "MPS"
    };

    private static final HashMap<String, String> symbolsReplacements = new HashMap<>();

    static {
        symbolsReplacements.put("2ED", "2U");
        symbolsReplacements.put("3ED", "3E");
        symbolsReplacements.put("4ED", "4E");
        symbolsReplacements.put("5ED", "5E");
        symbolsReplacements.put("6ED", "6E");
        symbolsReplacements.put("7ED", "7E");
        symbolsReplacements.put("ALL", "AL");
        symbolsReplacements.put("APC", "AP");
        symbolsReplacements.put("ARN", "AN");
        symbolsReplacements.put("ATQ", "AQ");
        symbolsReplacements.put("CMA", "CM1");
        symbolsReplacements.put("DD3DVD", "DD3_DVD");
        symbolsReplacements.put("DD3EVG", "DD3_EVG");
        symbolsReplacements.put("DD3GLV", "DD3_GLV");
        symbolsReplacements.put("DD3JVC", "DD3_JVC");
        symbolsReplacements.put("DRK", "DK");
        symbolsReplacements.put("EXO", "EX");
        symbolsReplacements.put("FEM", "FE");
        symbolsReplacements.put("HML", "HM");
        symbolsReplacements.put("ICE", "IA");
        symbolsReplacements.put("INV", "IN");
        symbolsReplacements.put("LEA", "1E");
        symbolsReplacements.put("LEB", "2E");
        symbolsReplacements.put("LEG", "LE");
        symbolsReplacements.put("MPS", "MPS_KLD");
        symbolsReplacements.put("MIR", "MI");
        symbolsReplacements.put("MMQ", "MM");
        symbolsReplacements.put("NEM", "NE");
        symbolsReplacements.put("ODY", "OD");
        symbolsReplacements.put("PCY", "PR");
        symbolsReplacements.put("PLS", "PS");
        symbolsReplacements.put("POR", "PO");
        symbolsReplacements.put("PO2", "P2");
        symbolsReplacements.put("PTK", "PK");
        symbolsReplacements.put("STH", "ST");
        symbolsReplacements.put("TMP", "TE");
        symbolsReplacements.put("UDS", "CG");
        symbolsReplacements.put("ULG", "GU");
        symbolsReplacements.put("USG", "UZ");
        symbolsReplacements.put("VIS", "VI");
        symbolsReplacements.put("WTH", "WL");
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
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -14);
        Date compareDate = c.getTime();
        ArrayList<DownloadJob> jobs = new ArrayList<>();
        for (String symbol : symbols) {
            ExpansionSet exp = Sets.findSet(symbol);
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                jobs.add(generateDownloadJob(symbol, "C", "C"));
                jobs.add(generateDownloadJob(symbol, "U", "U"));
                jobs.add(generateDownloadJob(symbol, "R", "R"));
            }
        }
        for (String symbol : withMythics) {
            ExpansionSet exp = Sets.findSet(symbol);
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                jobs.add(generateDownloadJob(symbol, "C", "C"));
                jobs.add(generateDownloadJob(symbol, "U", "U"));
                jobs.add(generateDownloadJob(symbol, "R", "R"));
                jobs.add(generateDownloadJob(symbol, "M", "M"));
            }
        }
        for (String symbol : onlyMythics) {
            ExpansionSet exp = Sets.findSet(symbol);
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                jobs.add(generateDownloadJob(symbol, "M", "M"));
            }
        }
        for (String symbol : onlyMythicsAsSpecial) {
            ExpansionSet exp = Sets.findSet(symbol);
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                jobs.add(generateDownloadJob(symbol, "M", "S"));
            }
        }
        return jobs.iterator();
    }

    private DownloadJob generateDownloadJob(String set, String rarity, String urlRarity) {
        File dst = new File(outDir, set + "-" + rarity + ".jpg");
        if (symbolsReplacements.containsKey(set)) {
            set = symbolsReplacements.get(set);
        }
        String url = "http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=" + set + "&size=small&rarity=" + urlRarity;
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
