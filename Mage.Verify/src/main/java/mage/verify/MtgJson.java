package mage.verify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class MtgJson {
    private MtgJson() {}

    private static class CardHolder {
        private static final Map<String, JsonCard> cards;
        static {
            try {
                cards = loadAllCards();
                addAliases(cards);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class SetHolder {
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
        return readFromZip("AllCards.json.zip", new TypeReference<Map<String, JsonCard>>() {});
    }

    private static Map<String, JsonSet> loadAllSets() throws IOException {
        return readFromZip("AllSets.json.zip", new TypeReference<Map<String, JsonSet>>() {});
    }

    private static <T> T readFromZip(String filename, TypeReference<T> ref) throws IOException {
        InputStream stream = MtgJson.class.getResourceAsStream(filename);
        if (stream == null) {
            File file = new File(filename);
            if (!file.exists()) {
                InputStream download = new URL("http://mtgjson.com/json/" + filename).openStream();
                Files.copy(download, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Downloaded " + filename + " to " + file.getAbsolutePath());
            } else {
                System.out.println("Using " + filename + " from " + file.getAbsolutePath());
            }
            stream = new FileInputStream(file);
        }
        ZipInputStream zipInputStream = new ZipInputStream(stream);
        zipInputStream.getNextEntry();
        return new ObjectMapper().readValue(zipInputStream, ref);
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
