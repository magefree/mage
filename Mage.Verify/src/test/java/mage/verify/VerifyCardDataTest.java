package mage.verify;

import mage.ObjectColor;
import mage.cards.*;
import mage.cards.basiclands.BasicLand;
import mage.constants.CardType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VerifyCardDataTest {

    // right now this is very noisy, and not useful enough to make any assertions on
    private static final boolean CHECK_SOURCE_TOKENS = false;

    private static final HashMap<String, Set<String>> skipCheckLists = new HashMap<>();
    private static void skipListCreate(String listName){ skipCheckLists.put(listName, new LinkedHashSet<>()); }
    private static void skipListAddName(String listName, String name){ skipCheckLists.get(listName).add(name); }
    private static boolean skipListHaveName(String listName, String name){ return skipCheckLists.get(listName).contains(name); }

    static {
        // skip lists for checks (example: unstable cards with same name may have different stats)

        // power-toughness
        skipListCreate("PT");
        skipListAddName("PT", "Garbage Elemental"); // UST
        skipListAddName("PT", "Infinity Elemental"); // UST

        // color
        skipListCreate("COLOR");
        //skipListAddName("COLOR", "Ulrich, Uncontested Alpha"); // gatherer is missing the color indicator on one card and json has wrong data (16.12.2017: not actual)

        // cost
        skipListCreate("COST");

        // supertype
        skipListCreate("SUPERTYPE");

        // type
        skipListCreate("TYPE");

        // subtype
        skipListCreate("SUBTYPE");

        // number
        skipListCreate("NUMBER");
    }

    public static List<Card> allCards() {
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        List<Card> cards = new ArrayList<>();
        for (ExpansionSet set : sets) {
            if (set.isCustomSet()) {
                continue;
            }
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                cards.add(CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                        setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo())));
            }
        }
        return cards;
    }

    private void warn(Card card, String message) {
        System.out.println("Warning: " + message + " for " + card.getName() + " (" + card.getExpansionSetCode() + ")");
    }

    private void fail(Card card, String category, String message) {
        failed++;
        System.out.println("Error: (" + category + ") " + message + " for " + card.getName());
    }

    private int failed = 0;

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
        if (failed > 0) {
            Assert.fail(failed + " Errors");
        }
    }

    @Test
    public void checkDuplicateCardNumbersInDB(){
        Collection<String> doubleErrors = new ArrayList<>();

        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            Map<String, ExpansionSet.SetCardInfo> cardsList = new HashMap<>();
            for (ExpansionSet.SetCardInfo checkCard: set.getSetCardInfo()) {
                String cardNumber = checkCard.getCardNumber();

                // ignore double faced
                Card realCard = CardImpl.createCard(checkCard.getCardClass(), new CardSetInfo(checkCard.getName(), set.getCode(),
                        checkCard.getCardNumber(), checkCard.getRarity(), checkCard.getGraphicInfo()));
                if (realCard.isNightCard()){
                    continue;
                }

                if (cardsList.containsKey(cardNumber)){
                    ExpansionSet.SetCardInfo prevCard = cardsList.get(cardNumber);

                    String errorType;
                    if (checkCard.getName().equals(prevCard.getName())){
                        errorType = " founded DUPLICATED cards"
                                + " set (" + set.getCode() + " - " + set.getName() + ")"
                                + " (" + checkCard.getCardNumber() + " - " + checkCard.getName() + ")";
                    }else{
                        errorType = " founded TYPOS in card numbers"
                                + " set (" + set.getCode() + " - " + set.getName() + ")"
                                + " (" + prevCard.getCardNumber() + " - " + prevCard.getName() + ")"
                                + " and"
                                + " (" + checkCard.getCardNumber() + " - " + checkCard.getName() + ")";
                    }

                    String error = "Error: " + errorType;

                    doubleErrors.add(error);
                }else{
                    cardsList.put(cardNumber, checkCard);
                }
            }
        }

        for (String error: doubleErrors) {
            System.out.println(error);
        }

        if (doubleErrors.size() > 0){
            Assert.fail("DB have duplicated card numbers, founded errors: " + doubleErrors.size());
        }
    }

    @Test
    public void checkWrongCardClasses(){
        Collection<String> errorsList = new ArrayList<>();
        Map<String, String> classesIndex = new HashMap<>();
        int totalCards = 0;

        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo checkCard : set.getSetCardInfo()) {
                totalCards = totalCards + 1;

                String currentClass = checkCard.getCardClass().toString();
                if (classesIndex.containsKey(checkCard.getName())) {
                    String needClass = classesIndex.get(checkCard.getName());
                    if (!needClass.equals(currentClass)) {
                        // workaround to star wars set with same card names
                        if(!checkCard.getName().equals("Syndicate Enforcer")) {
                            errorsList.add("Error: founded wrong class in set " + set.getCode() + " - " + checkCard.getName() + " (" + currentClass + " <> " + needClass + ")");
                        }
                    }
                } else {
                    classesIndex.put(checkCard.getName(), currentClass);
                }
            }
        }

        for (String error: errorsList) {
            System.out.println(error);
        }

        // unique cards stats
        System.out.println("Total unique cards: " + classesIndex.size() + ", total non unique cards (reprints): " + totalCards);

        if (errorsList.size() > 0){
            Assert.fail("DB have wrong card classes, founded errors: " + errorsList.size());
        }
    }

    @Test
    public void checkMissingSets(){

        Collection<String> errorsList = new ArrayList<>();

        int totalMissingSets = 0;
        int totalMissingCards = 0;
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for(Map.Entry<String, JsonSet> refEntry: MtgJson.sets().entrySet()){
            JsonSet refSet = refEntry.getValue();

            // replace codes for aliases
            String searchSet = MtgJson.mtgJsonToXMageCodes.getOrDefault(refSet.code, refSet.code);

            ExpansionSet mageSet = Sets.findSet(searchSet);
            if(mageSet == null){
                totalMissingSets = totalMissingSets + 1;
                totalMissingCards = totalMissingCards + refSet.cards.size();
                errorsList.add("Warning: missing set " + refSet.code + " - " + refSet.name + " (cards: " + refSet.cards.size() + ")");
            }
        }
        if(errorsList.size() > 0){
            errorsList.add("Warning: total missing sets: " + totalMissingSets + ", with missing cards: " + totalMissingCards);
        }

        // only warnings
        for (String error: errorsList) {
            System.out.println(error);
        }
    }

    private static final Pattern SHORT_JAVA_STRING = Pattern.compile("(?<=\")[A-Z][a-z]+(?=\")");

    private Set<String> findSourceTokens(Class c) throws IOException {
        if (!CHECK_SOURCE_TOKENS || BasicLand.class.isAssignableFrom(c)) {
            return null;
        }
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
            return null;
        }
    }

    private void check(Card card, Set<String> tokens) {
        JsonCard ref = MtgJson.card(card.getName());
        if (ref == null) {
            warn(card, "Missing card reference");
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
                        || (ref2 != null && (containsInTypesOrText(ref2, token) || containsInTypesOrText(ref2, token.toLowerCase()))))) {
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
        //checkNumbers(card, ref); // TODO: load data from allsets.json and check it (allcards.json do not have card numbers)
    }

    private void checkColors(Card card, JsonCard ref) {
        if (skipListHaveName("COLOR", card.getName())){ return; }

        Collection<String> expected = ref.colors;
        ObjectColor color = card.getColor(null);
        if (expected == null) {
            expected = Collections.emptyList();
        }
        if (expected.size() != color.getColorCount()
                || (color.isBlack() && !expected.contains("Black"))
                || (color.isBlue() && !expected.contains("Blue"))
                || (color.isGreen() && !expected.contains("Green"))
                || (color.isRed() && !expected.contains("Red"))
                || (color.isWhite() && !expected.contains("White"))) {
            fail(card, "colors", color + " != " + expected);
        }
    }

    private void checkSubtypes(Card card, JsonCard ref) {
        if (skipListHaveName("SUBTYPE", card.getName())){ return; }

        Collection<String> expected = ref.subtypes;

        // fix names (e.g. Urza’s to Urza's)
        if (expected != null && expected.contains("Urza’s")) {
            expected = new ArrayList<>(expected);
            for (ListIterator<String> it = ((List<String>) expected).listIterator(); it.hasNext();) {
                if (it.next().equals("Urza’s")) {
                    it.set("Urza's");
                }
            }
        }

        if (!eqSet(card.getSubtype(null).stream().map(p -> p.toString()).collect(Collectors.toSet()), expected)) {
            fail(card, "subtypes", card.getSubtype(null) + " != " + expected);
        }
    }

    private void checkSupertypes(Card card, JsonCard ref) {
        if (skipListHaveName("SUPERTYPE", card.getName())){ return; }

        Collection<String> expected = ref.supertypes;
        if (!eqSet(card.getSuperType().stream().map(s -> s.toString()).collect(Collectors.toList()), expected)) {
            fail(card, "supertypes", card.getSuperType() + " != " + expected);
        }
    }

    private void checkTypes(Card card, JsonCard ref) {
        if (skipListHaveName("TYPE", card.getName())){ return; }

        Collection<String> expected = ref.types;
        List<String> type = new ArrayList<>();
        for (CardType cardType : card.getCardType()) {
            type.add(cardType.toString());
        }
        if (!eqSet(type, expected)) {
            fail(card, "types", type + " != " + expected);
        }
    }

    private static <T> boolean eqSet(Collection<T> a, Collection<T> b) {
        if (a == null || a.isEmpty()) {
            return b == null || b.isEmpty();
        }
        return b != null && a.size() == b.size() && a.containsAll(b);
    }

    private void checkPT(Card card, JsonCard ref) {
        if (skipListHaveName("PT", card.getName())){ return; }

        if (!eqPT(card.getPower().toString(), ref.power) || !eqPT(card.getToughness().toString(), ref.toughness)) {
            String pt = card.getPower() + "/" + card.getToughness();
            String expected = ref.power + '/' + ref.toughness;
            fail(card, "pt", pt + " != " + expected);
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
        if (skipListHaveName("COST", card.getName())){ return; }

        String expected = ref.manaCost;
        String cost = join(card.getManaCost().getSymbols());
        if (cost != null && cost.isEmpty()) {
            cost = null;
        }
        if (cost != null) {
            cost = cost.replaceAll("P\\}", "P}");
        }
        if (!Objects.equals(cost, expected)) {
            fail(card, "cost", cost + " != " + expected);
        }
    }

    private void checkNumbers(Card card, JsonCard ref) {
        if (skipListHaveName("NUMBER", card.getName())){ return; }

        String expected = ref.number;
        String current = card.getCardNumber();
        if (!eqPT(current, expected)) {
            warn(card, "card number " + current + " != " + expected);
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
