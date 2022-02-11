package mage.verify.mtgjson;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

public final class MtgJsonService {

    public static Map<String, String> mtgJsonToXMageCodes = new HashMap<>();
    public static Map<String, String> xMageToMtgJsonCodes = new HashMap<>();

    static {
        //mtgJsonToXMageCodes.put("pPRE", "PPRE");

        // revert search
        for (Map.Entry<String, String> entry : mtgJsonToXMageCodes.entrySet()) {
            xMageToMtgJsonCodes.put(entry.getValue(), entry.getKey());
        }
    }

    private static Map<String, MtgJsonCard> loadAllCards() throws IOException {
        AtomicCardsModel json = readFromZip("AtomicCards.json.zip", AtomicCardsModel.class);
        return json.prepareIndex();
    }

    private static AllPrintingsModel loadAllSets() throws IOException {
        return readFromZip("AllPrintings.json.zip", AllPrintingsModel.class);
    }

    private static <T> T readFromZip(String filename, Class<T> clazz) throws IOException {
        InputStream stream = MtgJsonService.class.getResourceAsStream(filename);
        if (stream == null) {
            File file = new File(filename);
            if (!file.exists()) {
                String url = "https://mtgjson.com/api/v5/" + filename;
                System.out.println("Downloading " + url + " to " + file.getAbsolutePath());
                URLConnection connection = new URL(url).openConnection();
                connection.setRequestProperty("user-agent", "xmage");
                InputStream download = connection.getInputStream();
                Files.copy(download, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Downloading DONE");
            } else {
                System.out.println("Found file " + filename + " from " + file.getAbsolutePath());
            }
            stream = new FileInputStream(file);
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(stream)) {
            zipInputStream.getNextEntry();
            return new Gson().fromJson(new InputStreamReader(zipInputStream), clazz);
        }
    }

    public static Map<String, MtgJsonSet> sets() {
        return SetHolder.sets;
    }

    public static MtgJsonMetadata meta() {
        return SetHolder.meta;
    }

    public static MtgJsonCard card(String name) {
        return findReference(CardHolder.cards, name);
    }

    public static List<MtgJsonCard> cardsFromSet(String setCode, String name) {
        MtgJsonSet set = findReference(SetHolder.sets, setCode);
        if (set == null) {
            return new ArrayList<>();
        }

        String needName = convertXmageToMtgJsonCardName(name);
        return set.cards.stream()
                .filter(c -> needName.equals(c.getRealCardName()))
                .collect(Collectors.toList());
    }

    public static MtgJsonCard cardFromSet(String setCode, String name, String number) {
        String jsonSetCode = xMageToMtgJsonCodes.getOrDefault(setCode, setCode);
        List<MtgJsonCard> list = cardsFromSet(jsonSetCode, name);
        return list.stream()
                .filter(c -> convertMtgJsonToXmageCardNumber(c.number).equals(number))
                .findFirst().orElse(null);
    }

    private static <T> T findReference(Map<String, T> reference, String name) {
        T ref = reference.get(name);
        if (ref == null) {
            //name = name.replaceFirst("\\bA[Ee]", "Æ");
            //ref = reference.get(name);
        }
        if (ref == null) {
            //name = name.replace("'", "\""); // for Kongming, "Sleeping Dragon" & Pang Tong, "Young Phoenix"
            //ref = reference.get(name);
        }
        if (ref == null) {
            name = convertXmageToMtgJsonCardName(name);
            ref = reference.get(name);
        }

        return ref;
    }

    private static String convertXmageToMtgJsonCardName(String cardName) {
        return cardName;
        //.replaceFirst("Aether", "Æther")
        //.replace("'", "\""); // for Kongming, "Sleeping Dragon" & Pang Tong, "Young Phoenix"
    }

    private static String convertMtgJsonToXmageCardNumber(String number) {
        // card number notation must be same for all sets (replace non-ascii symbols)
        // so your set generation tools must use same replaces
        return number
                .replace("★", "*")
                .replace("†", "+");
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

    private static final class AtomicCardsModel {

        // list by card names, each name can havem multiple cards (two faces, different cards with same name from un-sets)
        public HashMap<String, ArrayList<MtgJsonCard>> data;

        private boolean containsSameNames(ArrayList<MtgJsonCard> list) {
            Set<String> names = list.stream().map(MtgJsonCard::getRealCardName).collect(Collectors.toSet());
            return names.size() == 1;
        }

        public HashMap<String, MtgJsonCard> prepareIndex() {
            HashMap<String, MtgJsonCard> index = new HashMap<>();
            for (Map.Entry<String, ArrayList<MtgJsonCard>> rec : data.entrySet()) {
                if (rec.getValue().size() == 1) {
                    // normal card
                    index.put(rec.getKey(), rec.getValue().get(0));
                } else {
                    if (containsSameNames(rec.getValue())) {
                        // un-set cards - same name, but different cards (must be ignored)
                    } else {
                        // multi-faces cards
                        MtgJsonCard mainCard = rec.getValue().stream().filter(c -> c.side.equals("a")).findAny().orElse(null);
                        if (mainCard != null) {
                            index.put(mainCard.faceName, mainCard);
                            for (MtgJsonCard card : rec.getValue()) {
                                if (card == mainCard) continue;
                                index.put(card.faceName, card);
                            }
                        }
                    }
                }
            }
            return index;
        }
    }

    private static final class AllPrintingsModel {
        public HashMap<String, MtgJsonSet> data;
        public MtgJsonMetadata meta;
    }

    private static final class CardHolder {
        private static final Map<String, MtgJsonCard> cards;

        static {
            try {
                cards = loadAllCards();

                List<String> keysToDelete = new ArrayList<>();

                // fix names
                Map<String, MtgJsonCard> newKeys = new HashMap<>();
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
                for (Map.Entry<String, MtgJsonCard> record : cards.entrySet()) {
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
        private static final Map<String, MtgJsonSet> sets;
        private static final MtgJsonMetadata meta;

        static {
            try {
                AllPrintingsModel model = loadAllSets();
                sets = model.data;
                meta = model.meta;
                System.out.println("MTGJSON version " + meta.version + ", release date " + meta.date);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
