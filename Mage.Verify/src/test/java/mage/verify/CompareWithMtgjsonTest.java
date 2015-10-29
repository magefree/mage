package mage.verify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.SplitCard;
import mage.constants.CardType;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompareWithMtgjsonTest {

    @Test
    public void testSets() throws IOException {
        Collection<ExpansionSet> sets = Sets.getInstance().values();

        Map<String, Map<String, Object>> reference = new ObjectMapper().readValue(
                CompareWithMtgjsonTest.class.getResourceAsStream("AllCards.json"),
                new TypeReference<Map<String, Map<String, Object>>>() {});

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

        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                Set<String> tokens = findSourceTokens(setInfo.getCardClass());
                Card card = CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                        setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()));
                if (card.isSplitCard()) {
                    check(reference, ((SplitCard) card).getLeftHalfCard(), null);
                    check(reference, ((SplitCard) card).getRightHalfCard(), null);
                } else {
                    check(reference, card, tokens);
                }
            }
        }
    }

    private static final Pattern SHORT_JAVA_STRING = Pattern.compile("(?<=\")[A-Z][a-z]+(?=\")");

    private Set<String> findSourceTokens(Class c) throws IOException {
        String path = "../Mage.Sets/src/" + c.getName().replace(".", "/") + ".java";
        try {
            String source = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            Matcher matcher = SHORT_JAVA_STRING.matcher(source);
            Set<String> tokens = new HashSet<>();
            while (matcher.find()) {
                tokens.add(matcher.group());
            }
            return tokens;
        } catch (NoSuchFileException e) {
            System.out.println("failed to read " + path);
            return Collections.emptySet();
        }
    }

    private String stripAccents(String str) {
        String decomposed = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return decomposed.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    private void check(Map<String, Map<String, Object>> reference, Card card, Set<String> tokens) {
        Map<String, Object> ref = findReference(reference, card.getName());
        if (ref == null) {
            System.out.println("Missing card reference for " + card);
            return;
        }
        checkAll(card, ref);
        if (tokens != null) {
            Map<String, Object> ref2 = null;
            if (card.isFlipCard()) {
                ref2 = findReference(reference, card.getFlipCardName());
            }
            for (String token : tokens) {
                if (!(token.equals(card.getName())
                        || containsInTypesOrText(ref, token)
                        || containsInTypesOrText(ref, token.toLowerCase())
                        || (ref2 != null && (containsInTypesOrText(ref2, token) || containsInTypesOrText(ref2, token.toLowerCase())))
                )) {
                    System.out.println("unexpected token " + token + " in " + card);
                }
            }
        }
    }

    private Map<String, Object> findReference(Map<String, Map<String, Object>> reference, String name) {
        Map<String, Object> ref = reference.get(name);
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

    private boolean containsInTypesOrText(Map<String, Object> ref, String token) {
        return contains(ref, "types", token)
                || contains(ref, "subtypes", token)
                || contains(ref, "supertypes", token)
                || ((String) ref.get("text")).contains(token);
    }

    private boolean contains(Map<String, Object> ref, String key, String value) {
        Collection<String> options = (Collection<String>) ref.get(key);
        return options != null && options.contains(value);
    }

    private void checkAll(Card card, Map<String, Object> ref) {
        checkCost(card, ref);
        checkPT(card, ref);
        checkSubtypes(card, ref);
        checkSupertypes(card, ref);
        checkTypes(card, ref);
        checkColors(card, ref);
    }

    private void checkColors(Card card, Map<String, Object> ref) {
        Collection<String> expected = (Collection<String>) ref.get("colors");
        ObjectColor color = card.getColor(null);
        if (expected == null) {
            expected = Collections.emptyList();
        }
        if (expected.size() != color.getColorCount() ||
                (color.isBlack() && !expected.contains("Black")) ||
                (color.isBlue() && !expected.contains("Blue")) ||
                (color.isGreen() && !expected.contains("Green")) ||
                (color.isRed() && !expected.contains("Red")) ||
                (color.isWhite() && !expected.contains("White"))) {
            System.out.println(color + " != " + expected + " for " + card);
        }
    }

    private void checkSubtypes(Card card, Map<String, Object> ref) {
        Collection<String> expected = (Collection<String>) ref.get("subtypes");
        if (expected != null && expected.contains("Urza’s")) {
            expected = new ArrayList<>(expected);
            for (ListIterator<String> it = ((List<String>) expected).listIterator(); it.hasNext();) {
                if (it.next().equals("Urza’s")) {
                    it.set("Urza's");
                }
            }
        }
        if (!eqSet(card.getSubtype(null), expected)) {
            System.out.println(card.getSubtype(null) + " != " + expected + " for " + card);
        }
    }

    private void checkSupertypes(Card card, Map<String, Object> ref) {
        Collection<String> expected = (Collection<String>) ref.get("supertypes");
        if (!eqSet(card.getSupertype(), expected)) {
            System.out.println(card.getSupertype() + " != " + expected + " for " + card);
        }
    }

    private void checkTypes(Card card, Map<String, Object> ref) {
        Collection<String> expected = (Collection<String>) ref.get("types");
        List<String> type = new ArrayList<>();
        for (CardType cardType : card.getCardType()) {
            type.add(cardType.toString());
        }
        if (!eqSet(type, expected)) {
            System.out.println(type + " != " + expected + " for " + card);
        }
    }

    private static <T> boolean eqSet(Collection<T> a, Collection<T> b) {
        if (a == null || a.isEmpty()) {
            return b == null || b.isEmpty();
        }
        return b != null && a.size() == b.size() && a.containsAll(b);
    }

    private void checkPT(Card card, Map<String, Object> ref) {
        String pt = card.getPower() + "/" + card.getToughness();
        String expected = ref.get("power") + "/" + ref.get("toughness");
        if ("0/0".equals(pt) && ("null/null".equals(expected) || "*/*".equals(expected))) {
            // ok
        } else if (!Objects.equals(pt, expected.replace("*", "0"))) {
            System.out.println(pt + " != " + expected + " for " + card);
        }
    }

    private void checkCost(Card card, Map<String, Object> ref) {
        String expected = (String) ref.get("manaCost");
        String cost = join(card.getManaCost().getSymbols());
        if ("".equals(cost)) {
            cost = null;
        }
        if (cost != null) {
            cost = cost.replaceAll("P\\}", "/P}");
        }
        if (!Objects.equals(cost, expected)) {
            System.out.println(cost + " != " + expected + " for " + card);
        }
    }

    private String join(Iterable<?> items) {
        StringBuilder result = new StringBuilder();
        for (Object item : items) {
            result.append(item);
        }
        return result.toString();
    }

}
