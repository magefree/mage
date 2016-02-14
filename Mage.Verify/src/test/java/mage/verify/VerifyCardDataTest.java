package mage.verify;

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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyCardDataTest {

    @Test
    public void verifySets() throws IOException {
        Map<String, JsonSet> reference = MtgJson.sets();

        for (ExpansionSet set : Sets.getInstance().values()) {
            JsonSet ref = reference.get(set.getCode());
            if (ref == null) {
                for (JsonSet js : reference.values()) {
                    if (set.getCode().equals(js.oldCode) || set.getCode().toLowerCase().equals(js.magicCardsInfoCode)) {
                        ref = js;
                        break;
                    }
                }
                if (ref == null) {
                    System.out.println("missing reference for " + set);
                    continue;
                }
            }
            if (!String.format("%tF", set.getReleaseDate()).equals(ref.releaseDate)) {
                System.out.printf("%40s %-20s %20tF %20s%n", set, "release date", set.getReleaseDate(), ref.releaseDate);
            }
            if (set.hasBoosters() != (ref.booster != null)) {
                System.out.printf("%40s %-20s %20s %20s%n", set, "has boosters", set.hasBoosters(), ref.booster != null);
            }
            boolean refHasBasicLands = false;
            for (JsonCard card : ref.cards) {
                if ("Mountain".equals(card.name)) {
                    refHasBasicLands = true;
                    break;
                }
            }
            if (set.hasBasicLands() != refHasBasicLands) {
                System.out.printf("%40s %-20s %20s %20s%n", set, "has basic lands", set.hasBasicLands(), refHasBasicLands);
            }
        }
    }

    public static List<Card> allCards() {
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        List<Card> cards = new ArrayList<>();
        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                cards.add(CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                        setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo())));
            }
        }
        return cards;
    }

    @Test
    public void verifyCards() throws IOException {
        for (Card card : allCards()) {
            Set<String> tokens = findSourceTokens(card.getClass());
            if (card.isSplitCard()) {
                check(((SplitCard) card).getLeftHalfCard(), null);
                check(((SplitCard) card).getRightHalfCard(), null);
            } else {
                check(card, tokens);
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

    private void check(Card card, Set<String> tokens) {
        JsonCard ref = MtgJson.card(card.getName());
        if (ref == null) {
            System.out.println("Missing card reference for " + card);
            return;
        }
        checkAll(card, ref);
        if (tokens != null) {
            JsonCard ref2 = null;
            if (card.isFlipCard()) {
                ref2 = MtgJson.card(card.getFlipCardName());
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

    private boolean containsInTypesOrText(JsonCard ref, String token) {
        return contains(ref.types, token)
                || contains(ref.subtypes, token)
                || contains(ref.supertypes, token)
                || ref.text.contains(token);
    }

    private boolean contains(Collection<String> options, String value) {
        return options != null && options.contains(value);
    }

    private void checkAll(Card card, JsonCard ref) {
        checkCost(card, ref);
        checkPT(card, ref);
        checkSubtypes(card, ref);
        checkSupertypes(card, ref);
        checkTypes(card, ref);
        checkColors(card, ref);
    }

    private void checkColors(Card card, JsonCard ref) {
        Collection<String> expected = ref.colors;
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

    private void checkSubtypes(Card card, JsonCard ref) {
        Collection<String> expected = ref.subtypes;
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

    private void checkSupertypes(Card card, JsonCard ref) {
        Collection<String> expected = ref.supertypes;
        if (!eqSet(card.getSupertype(), expected)) {
            System.out.println(card.getSupertype() + " != " + expected + " for " + card);
        }
    }

    private void checkTypes(Card card, JsonCard ref) {
        Collection<String> expected = ref.types;
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

    private void checkPT(Card card, JsonCard ref) {
        if (!eqPT(card.getPower().toString(), ref.power) || !eqPT(card.getToughness().toString(), ref.toughness)) {
            String pt = card.getPower() + "/" + card.getToughness();
            String expected = ref.power + "/" + ref.toughness;
            System.out.println(pt + " != " + expected + " for " + card);
        }
    }

    private boolean eqPT(String found, String expected) {
        if (expected == null) {
            return "0".equals(found);
        } else {
            return found.equals(expected) || expected.contains("*");
        }
    }

    private void checkCost(Card card, JsonCard ref) {
        String expected = ref.manaCost;
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
