package mage.verify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

public final class MtgJson {

    public static Map<String, String> mtgJsonToXMageCodes = new HashMap<>();
    public static Map<String, String> xMageToMtgJsonCodes = new HashMap<>();

    public static final boolean MTGJSON_IGNORE_NEW_PROPERTIES = true; // set it to false for full mtgjson checks and research (new fields finds or mtgjson updates)

    static {
        mtgJsonToXMageCodes.put("pWCQ", "WMCQ");
        mtgJsonToXMageCodes.put("pSUS", "SUS");
        mtgJsonToXMageCodes.put("pPRE", "PTC");
        mtgJsonToXMageCodes.put("pMPR", "MPRP");
        mtgJsonToXMageCodes.put("pMEI", "MBP");
        mtgJsonToXMageCodes.put("pGTW", "GRC"); // pGTW - Gateway = GRC (WPN + Gateway in one inner set)
        mtgJsonToXMageCodes.put("pWPN", "GRC"); // pWPN - Wizards Play Network = GRC (WPN + Gateway in one inner set)
        mtgJsonToXMageCodes.put("pGRU", "GUR");
        mtgJsonToXMageCodes.put("pGPX", "GPX");
        mtgJsonToXMageCodes.put("pFNM", "FNMP");
        mtgJsonToXMageCodes.put("pELP", "EURO");
        mtgJsonToXMageCodes.put("pARL", "ARENA");
        mtgJsonToXMageCodes.put("pALP", "APAC");
        mtgJsonToXMageCodes.put("PO2", "P02");
        mtgJsonToXMageCodes.put("DD3_JVC", "DD3JVC");
        mtgJsonToXMageCodes.put("DD3_GVL", "DDD");
        mtgJsonToXMageCodes.put("DD3_EVG", "DD3EVG");
        mtgJsonToXMageCodes.put("DD3_DVD", "DDC");
        mtgJsonToXMageCodes.put("NMS", "NEM");
        mtgJsonToXMageCodes.put("MPS_AKH", "MPS-AKH");
        mtgJsonToXMageCodes.put("FRF_UGIN", "UGIN");
        mtgJsonToXMageCodes.put("pCMP", "CP");


        // revert search
        for (Map.Entry<String, String> entry : mtgJsonToXMageCodes.entrySet()) {
            xMageToMtgJsonCodes.put(entry.getValue(), entry.getKey());
        }
    }

    private MtgJson() {
    }

    private static final class CardHolder {
        private static final Map<String, JsonCard> cards;

        static {
            try {
                cards = loadAllCards();

                List<String> keysToDelete = new ArrayList<>();

                // fix names
                Map<String, JsonCard> newKeys = new HashMap<>();
                for (String key : cards.keySet()) {
                    if (key.contains("(")) {
                        newKeys.put(key.replaceAll("\\(.*\\)", "").trim(), cards.get(key));
                        keysToDelete.add(key);
                    }
                }
                cards.putAll(newKeys);
                cards.keySet().removeAll(keysToDelete);

                // remove wrong data (tokens)
                keysToDelete.clear();
                for (Map.Entry<String, JsonCard> record : cards.entrySet()) {
                    if (record.getValue().layout.equals("token") || record.getValue().layout.equals("double_faced_token")) {
                        keysToDelete.add(record.getKey());
                    }
                }
                cards.keySet().removeAll(keysToDelete);

                addAliases(cards);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class SetHolder {
        private static final Map<String, JsonSet> sets;

        static {
            try {
                sets = loadAllSets();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Map<String, JsonCard> loadAllCards() throws IOException {
        return readFromZip("AllCards.json.zip", new TypeReference<Map<String, JsonCard>>() {
        });
    }

    private static Map<String, JsonSet> loadAllSets() throws IOException {
        return readFromZip("AllSets.json.zip", new TypeReference<Map<String, JsonSet>>() {
        });
    }

    private static <T> T readFromZip(String filename, TypeReference<T> ref) throws IOException {
        InputStream stream = MtgJson.class.getResourceAsStream(filename);
        if (stream == null) {
            File file = new File(filename);
            if (!file.exists()) {
                URLConnection connection = new URL("https://mtgjson.com/json/" + filename).openConnection();
                connection.setRequestProperty("user-agent", "xmage");
                InputStream download = connection.getInputStream();
                Files.copy(download, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Downloaded " + filename + " to " + file.getAbsolutePath());
            } else {
                System.out.println("Using " + filename + " from " + file.getAbsolutePath());
            }
            stream = new FileInputStream(file);
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(stream)) {
            zipInputStream.getNextEntry();
            return new ObjectMapper().readValue(zipInputStream, ref);
        }

    }

    public static Map<String, JsonSet> sets() {
        return SetHolder.sets;
    }

    public static JsonCard card(String name) {
        return findReference(CardHolder.cards, name);
    }

    private static <T> T findReference(Map<String, T> reference, String name) {
        T ref = reference.get(name);
        if (ref == null) {
            name = name.replaceFirst("\\bA[Ee]", "Æ");
            ref = reference.get(name);
        }
        if (ref == null) {
            name = name.replace("'", "\""); // for Kongming, "Sleeping Dragon" & Pang Tong, "Young Phoenix"
            ref = reference.get(name);
        }

        return ref;
    }

    private static <T> void addAliases(Map<String, T> reference) {
        Map<String, String> aliases = new HashMap<>();
        for (String name : reference.keySet()) {
            String unaccented = stripAccents(name);
            if (!name.equals(unaccented)) {
                aliases.put(name, unaccented);
            }
        }
        for (Map.Entry<String, String> mapping : aliases.entrySet()) {
            reference.put(mapping.getValue(), reference.get(mapping.getKey()));
        }
    }

    private static String stripAccents(String str) {
        String decomposed = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return decomposed.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

}
