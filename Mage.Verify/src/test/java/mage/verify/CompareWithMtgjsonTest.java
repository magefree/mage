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
import mage.util.ClassScanner;
import org.junit.Test;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

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
                Card card = CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                        setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()));
                if (card.isSplitCard()) {
                    check(reference, ((SplitCard) card).getLeftHalfCard());
                    check(reference, ((SplitCard) card).getRightHalfCard());
                } else {
                    check(reference, card);
                }
            }
        }
    }

    private String stripAccents(String str) {
        String decomposed = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return decomposed.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    private void check(Map<String, Map<String, Object>> reference, Card card) {
        String name = card.getName();
        Map<String, Object> ref = reference.get(name);
        if (ref == null) {
            name = name.replaceFirst("\\bA[Ee]", "Æ");
            ref = reference.get(name);
        }
        if (ref == null) {
            name = name.replace("'", "\""); // for Kongming, "Sleeping Dragon" & Pang Tong, "Young Phoenix"
            ref = reference.get(name);
        }
        if (ref == null) {
            System.out.println("Missing card reference for " + card);
            return;
        }
        checkAll(card, ref);
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
