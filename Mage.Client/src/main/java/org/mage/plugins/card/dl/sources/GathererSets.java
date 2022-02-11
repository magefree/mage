package org.mage.plugins.card.dl.sources;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.client.constants.Constants;
import mage.constants.Rarity;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.*;

import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * WARNING, unsupported images plugin, last updates from 2018
 */

public class GathererSets implements Iterable<DownloadJob> {

    private class CheckResult {

        String code;
        ExpansionSet set;
        boolean haveCommon;
        boolean haveUncommon;
        boolean haveRare;
        boolean haveMyth;

        private CheckResult(String ACode, ExpansionSet ASet, boolean AHaveCommon, boolean AHaveUncommon, boolean AHaveRare, boolean AHaveMyth) {
            code = ACode;
            set = ASet;
            haveCommon = AHaveCommon;
            haveUncommon = AHaveUncommon;
            haveRare = AHaveRare;
            haveMyth = AHaveMyth;
        }
    }

    private static File outDir;

    private static final int DAYS_BEFORE_RELEASE_TO_DOWNLOAD = +14; // Try to load the symbolsBasic eralies 14 days before release date
    private static final Logger logger = Logger.getLogger(GathererSets.class);

    private static final String[] symbolsBasic = {"10E", "9ED", "8ED", "7ED", "6ED", "5ED", "4ED", "3ED", "2ED", "LEB", "LEA",
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
            "POR", "P02", "PTK",
            "ARC", "DD3EVG",
            "W16", "W17",
            // "PALP" -- Gatherer does not have the set Asia Pacific Land Program
            // "ATH" -- has cards from many sets, symbol does not exist on gatherer
            // "CP", "DPA", "PELP", "PGPX", "PGRU", "H17", "JR", "SWS", // need to fix
            "H09", "PD2", "PD3", "UNH", "CM1", "V11", "A25", "UST", "IMA", "DD2", "EVG", "DDC", "DDE", "DDD", "CHR", "G18", "GVL", "S00", "S99", "UGL" // ok
            // current testing
    };

    private static final String[] symbolsBasicWithMyth = {"M10", "M11", "M12", "M13", "M14", "M15", "ORI",
            "DDF", "DDG", "DDH", "DDI", "DDJ", "DDK", "DDL", "DDM", "DDN",
            "DVD", "JVC", "DDO", "DDP", "DDQ", "DDR", "DDS", "DDT", "DDU",
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
            "KTK", "FRF", "UGIN", "DTK",
            "BFZ", "OGW",
            "SOI", "EMN",
            "KLD", "AER",
            "AKH", "HOU",
            "XLN", "C17",
            "RIX", "DOM", "M19",
            "E01", "CM2", "E02",
            "GS1", "BBD", "C18",
            "GNT", "UMA", "GRN",
            "RNA", "WAR", "MH1",
            "M20"
            // "HHO", "ANA" -- do not exist on gatherer
    };

    private static final String[] symbolsOnlyMyth = {
            "DRB", "V09", "V10", "V12", "V13", "V14", "V15", "V16", "V17", "EXP", "MED"
            // "HTR16" does not exist
    };
    private static final String[] symbolsOnlySpecial = {
            "MPS", "MP2"
    };

    private static final HashMap<String, String> codeReplacements = new HashMap<>();

    static {
        codeReplacements.put("2ED", "2U");
        codeReplacements.put("3ED", "3E");
        codeReplacements.put("4ED", "4E");
        codeReplacements.put("5ED", "5E");
        codeReplacements.put("6ED", "6E");
        codeReplacements.put("7ED", "7E");
        codeReplacements.put("ALL", "AL");
        codeReplacements.put("APC", "AP");
        codeReplacements.put("ARN", "AN");
        codeReplacements.put("ATQ", "AQ");
        codeReplacements.put("CMA", "CM1");
        codeReplacements.put("CHR", "CH");
        codeReplacements.put("DVD", "DD3_DVD");
        codeReplacements.put("EVG", "DD3_EVG");
        codeReplacements.put("GVL", "DD3_GVL");
        codeReplacements.put("JVC", "DD3_JVC");
        codeReplacements.put("DRK", "DK");
        codeReplacements.put("EXO", "EX");
        codeReplacements.put("FEM", "FE");
        codeReplacements.put("HML", "HM");
        codeReplacements.put("ICE", "IA");
        codeReplacements.put("INV", "IN");
        codeReplacements.put("LEA", "1E");
        codeReplacements.put("LEB", "2E");
        codeReplacements.put("LEG", "LE");
        codeReplacements.put("MED", "MPS_WAR");
        codeReplacements.put("MPS", "MPS_KLD");
        codeReplacements.put("MP2", "MPS_AKH");
        codeReplacements.put("MIR", "MI");
        codeReplacements.put("MMQ", "MM");
        codeReplacements.put("NEM", "NE");
        codeReplacements.put("ODY", "OD");
        codeReplacements.put("PCY", "PR");
        codeReplacements.put("PLS", "PS");
        codeReplacements.put("POR", "PO");
        codeReplacements.put("P02", "P2");
        codeReplacements.put("PTK", "PK");
        codeReplacements.put("S00", "P4");
        codeReplacements.put("S99", "P3");
        codeReplacements.put("STH", "ST");
        codeReplacements.put("TMP", "TE");
        codeReplacements.put("UDS", "CG");
        codeReplacements.put("UGIN", "FRF_UGIN");
        codeReplacements.put("UGL", "UG");
        codeReplacements.put("ULG", "GU");
        codeReplacements.put("USG", "UZ");
        codeReplacements.put("VIS", "VI");
        codeReplacements.put("WTH", "WL");
    }

    public GathererSets() {

        outDir = new File(getImagesDir() + Constants.RESOURCE_PATH_SYMBOLS);

        if (!outDir.exists()) {
            outDir.mkdirs();
        }
    }

    // checks for wrong card settings and support (easy to control what all good)
    private static final HashMap<String, CheckResult> setsToDownload = new HashMap<>();

    private void CheckSearchResult(String searchCode, ExpansionSet foundedExp, boolean canDownloadTask,
                                   boolean haveCommon, boolean haveUncommon, boolean haveRare, boolean haveMyth) {
        // duplicated in settings
        CheckResult res = setsToDownload.get(searchCode);

        if (res != null) {
            logger.error(String.format("Symbols: found duplicate code: %s", searchCode));
        } else {
            res = new CheckResult(searchCode, foundedExp, haveCommon, haveUncommon, haveRare, haveMyth);
            setsToDownload.put(searchCode, res);
        }

        // not found
        if (foundedExp == null) {
            logger.error(String.format("Symbols: can't find set by code: %s", searchCode));
            return;
        }

        // checks for founded sets only
        // too early to download
        if (!canDownloadTask) {
            Calendar c = Calendar.getInstance();
            c.setTime(foundedExp.getReleaseDate());
            c.add(Calendar.DATE, -1 * DAYS_BEFORE_RELEASE_TO_DOWNLOAD);
            logger.warn(String.format("Symbols: early to download: %s (%s), available after %s",
                    searchCode, foundedExp.getName(), c.getTime()));
        }
    }

    private void AnalyseSearchResult() {
        // analyze supported sets and show wrong settings
        Date startedDate = new Date();

        for (ExpansionSet set : Sets.getInstance().values()) {

            CheckResult res = setsToDownload.get(set.getCode());

            // 1. not configured at all
            if (res == null) {
                logger.warn(String.format("Symbols: set is not configured: %s (%s)", set.getCode(), set.getName()));
                continue; // can't do other checks
            }

            if (logger.isDebugEnabled()) {
                // 2. missing rarity icon:
                // WARNING, need too much time (60+ secs), only for debug mode
                ///*
                if (!set.getCardsByRarity(Rarity.COMMON).isEmpty() && !res.haveCommon) {
                    logger.error(String.format("Symbols: set have common cards, but don't download icon: %s (%s)", set.getCode(), set.getName()));
                }
                if (!set.getCardsByRarity(Rarity.UNCOMMON).isEmpty() && !res.haveUncommon) {
                    logger.error(String.format("Symbols: set have uncommon cards, but don't download icon: %s (%s)", set.getCode(), set.getName()));
                }
                if (!set.getCardsByRarity(Rarity.RARE).isEmpty() && !res.haveRare) {
                    logger.error(String.format("Symbols: set have rare cards, but don't download icon: %s (%s)", set.getCode(), set.getName()));
                }
                if (!set.getCardsByRarity(Rarity.MYTHIC).isEmpty() && !res.haveMyth) {
                    logger.error(String.format("Symbols: set have mythic cards, but don't download icon: %s (%s)", set.getCode(), set.getName()));
                }
                //*/

                // 3. info: sets with alternative numbers
                for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                    if (String.valueOf(card.getCardNumberAsInt()).length() != card.getCardNumber().length()) {
                        logger.info(String.format("Symbols: set have alternative card but do not config to it: %s (%s)", set.getCode(), set.getName()));
                        break;
                    }
                }

                // 4. info: sets with missing cards for boosters
                if (set.getMaxCardNumberInBooster() != Integer.MAX_VALUE) {
                    for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                        if (card.getCardNumberAsInt() > set.getMaxCardNumberInBooster()) {
                            if (card.getRarity() == Rarity.LAND) {
                                logger.info(String.format("Symbols: set's booster have land above max card number: %s (%s), %s - %s", set.getCode(), set.getName(), card.getCardNumber(), card.getName()));
                            } else {
                                logger.info(String.format("Symbols: set's booster missing nonland card:: %s (%s), %s - %s", set.getCode(), set.getName(), card.getCardNumber(), card.getName()));
                            }
                        }
                    }
                }
            }
        }

        Date endedDate = new Date();
        long secs = (endedDate.getTime() - startedDate.getTime()) / 1000;
        logger.debug(String.format("Symbols: check completed after %d seconds", secs));
    }

    @Override
    public Iterator<DownloadJob> iterator() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, DAYS_BEFORE_RELEASE_TO_DOWNLOAD);
        Date compareDate = c.getTime();
        List<DownloadJob> jobs = new ArrayList<>();
        boolean canDownload;

        setsToDownload.clear();

        for (String symbol : symbolsBasic) {
            ExpansionSet exp = Sets.findSet(symbol);
            canDownload = false;
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                canDownload = true;
                jobs.add(generateDownloadJob(symbol, "C", "C"));
                jobs.add(generateDownloadJob(symbol, "U", "U"));
                jobs.add(generateDownloadJob(symbol, "R", "R"));
            }
            CheckSearchResult(symbol, exp, canDownload, true, true, true, false);
        }

        for (String symbol : symbolsBasicWithMyth) {
            ExpansionSet exp = Sets.findSet(symbol);
            canDownload = false;
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                canDownload = true;
                jobs.add(generateDownloadJob(symbol, "C", "C"));
                jobs.add(generateDownloadJob(symbol, "U", "U"));
                jobs.add(generateDownloadJob(symbol, "R", "R"));
                jobs.add(generateDownloadJob(symbol, "M", "M"));
            }
            CheckSearchResult(symbol, exp, canDownload, true, true, true, true);
        }

        for (String symbol : symbolsOnlyMyth) {
            ExpansionSet exp = Sets.findSet(symbol);
            canDownload = false;
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                canDownload = true;
                jobs.add(generateDownloadJob(symbol, "M", "M"));
            }
            CheckSearchResult(symbol, exp, canDownload, false, false, false, true);
        }

        for (String symbol : symbolsOnlySpecial) {
            ExpansionSet exp = Sets.findSet(symbol);
            canDownload = false;
            if (exp != null && exp.getReleaseDate().before(compareDate)) {
                canDownload = true;
                jobs.add(generateDownloadJob(symbol, "M", "S"));
            }
            CheckSearchResult(symbol, exp, canDownload, false, false, false, true);
        }

        // check wrong settings
        AnalyseSearchResult();

        return jobs.iterator();
    }

    private DownloadJob generateDownloadJob(String set, String rarity, String urlRarity) {
        File dst = new File(outDir, set + '-' + rarity + ".jpg");
        if (codeReplacements.containsKey(set)) {
            set = codeReplacements.get(set);
        }
        String url = "https://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=" + set + "&size=small&rarity=" + urlRarity;
        return new DownloadJob(set + '-' + rarity, fromURL(url), toFile(dst));
    }
}
