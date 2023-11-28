package mage.verify;

import com.google.common.base.CharMatcher;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.Mode;
import mage.abilities.common.*;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.InitiativeHint;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.*;
import mage.cards.*;
import mage.cards.decks.CardNameUtil;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.*;
import mage.choices.Choice;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.Filter;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.command.Dungeon;
import mage.game.command.Plane;
import mage.game.draft.DraftCube;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.custom.CreatureToken;
import mage.utils.SystemUtil;
import mage.sets.TherosBeyondDeath;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;
import mage.verify.mtgjson.MtgJsonCard;
import mage.verify.mtgjson.MtgJsonService;
import mage.verify.mtgjson.MtgJsonSet;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.plugins.card.dl.sources.ScryfallImageSupportCards;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class VerifyCardDataTest {

    private static final Logger logger = Logger.getLogger(VerifyCardDataTest.class);

    private static final String FULL_ABILITIES_CHECK_SET_CODES = "WHO;LTC;LCI;LCC;REX"; // check ability text due mtgjson, can use multiple sets like MAT;CMD or * for all
    private static final boolean CHECK_ONLY_ABILITIES_TEXT = false; // use when checking text locally, suppresses unnecessary checks and output messages

    private static final boolean AUTO_FIX_SAMPLE_DECKS = false; // debug only: auto-fix sample decks by test_checkSampleDecks test run

    private static final Set<String> checkedNames = new HashSet<>(); // skip already checked cards
    private static final HashMap<String, Set<String>> skipCheckLists = new HashMap<>();
    private static final Set<String> subtypesToIgnore = new HashSet<>();
    private static final String SKIP_LIST_PT = "PT";
    private static final String SKIP_LIST_LOYALTY = "LOYALTY";
    private static final String SKIP_LIST_DEFENSE = "DEFENSE";
    private static final String SKIP_LIST_COLOR = "COLOR";
    private static final String SKIP_LIST_COST = "COST";
    private static final String SKIP_LIST_SUPERTYPE = "SUPERTYPE";
    private static final String SKIP_LIST_TYPE = "TYPE";
    private static final String SKIP_LIST_SUBTYPE = "SUBTYPE";
    private static final String SKIP_LIST_NUMBER = "NUMBER";
    private static final String SKIP_LIST_RARITY = "RARITY";
    private static final String SKIP_LIST_MISSING_ABILITIES = "MISSING_ABILITIES";
    private static final String SKIP_LIST_DOUBLE_RARE = "DOUBLE_RARE";
    private static final String SKIP_LIST_UNSUPPORTED_SETS = "UNSUPPORTED_SETS";
    private static final String SKIP_LIST_SCRYFALL_DOWNLOAD_SETS = "SCRYFALL_DOWNLOAD_SETS";
    private static final String SKIP_LIST_WRONG_CARD_NUMBERS = "WRONG_CARD_NUMBERS";
    private static final String SKIP_LIST_SAMPLE_DECKS = "SAMPLE_DECKS";
    private static final List<String> evergreenKeywords = Arrays.asList(
            "flying", "lifelink", "menace", "trample", "haste", "first strike", "hexproof", "fear",
            "deathtouch", "double strike", "indestructible", "reach", "flash", "defender", "vigilance",
            "plainswalk", "islandwalk", "swampwalk", "mountainwalk", "forestwalk", "myriad", "prowess", "convoke"
    );

    private static final List<String> doubleWords = new ArrayList<>();

    static {
        // numbers
        for (int i = 1; i <= 9; i++) {
            String s = CardUtil.numberToText(i).toLowerCase(Locale.ENGLISH);
            doubleWords.add(s + " " + s);
        }

        // additional checks
        doubleWords.add(" an an ");
        doubleWords.add(" a a ");
    }

    static {
        // skip lists for checks (example: unstable cards with same name may have different stats)
        // can be full set ignore list or set + cardname

        // power-toughness
        // skipListAddName(SKIP_LIST_PT, set, cardName);

        // loyalty
        // skipListAddName(SKIP_LIST_LOYALTY, set, cardName);

        // defense
        // skipListAddName(SKIP_LIST_DEFENSE, set, cardName);

        // color
        // skipListAddName(SKIP_LIST_COLOR, set, cardName);

        // cost
        // skipListAddName(SKIP_LIST_COST, set, cardName);

        // supertype
        // skipListAddName(SKIP_LIST_SUPERTYPE, set, cardName);

        // type
        // skipListAddName(SKIP_LIST_TYPE, set, cardName);
        skipListAddName(SKIP_LIST_TYPE, "UNH", "Old Fogey"); // uses summon word as a joke card
        skipListAddName(SKIP_LIST_TYPE, "UND", "Old Fogey");
        skipListAddName(SKIP_LIST_TYPE, "UST", "capital offense"); // uses "instant" instead "Instant" as a joke card

        // subtype
        // skipListAddName(SKIP_LIST_SUBTYPE, set, cardName);
        skipListAddName(SKIP_LIST_SUBTYPE, "UGL", "Miss Demeanor"); // uses multiple types as a joke card: Lady, of, Proper, Etiquette
        skipListAddName(SKIP_LIST_SUBTYPE, "UGL", "Elvish Impersonators"); // subtype is "Elves" pun
        skipListAddName(SKIP_LIST_SUBTYPE, "UND", "Elvish Impersonators");

        // number
        // skipListAddName(SKIP_LIST_NUMBER, set, cardName);

        // rarity
        // skipListAddName(SKIP_LIST_RARITY, set, cardName);
        skipListAddName(SKIP_LIST_RARITY, "CMR", "The Prismatic Piper"); // Collation is not yet set up for CMR https://www.lethe.xyz/mtg/collation/cmr.html

        // missing abilities
        // skipListAddName(SKIP_LIST_MISSING_ABILITIES, set, cardName);

        // double rare cards
        // skipListAddName(SKIP_LIST_DOUBLE_RARE, set, cardName);

        // Un-supported sets (mtgjson/scryfall contains that set but xmage don't implement it)
        // Example: Non-English sets, Token sets, Archenemy Schemes, Plane-Chase Planes, etc.
        // Sets with no cards or with "Oversized" in the name are automatically skipped.
        //
        // Non-English-only sets should not be added. https://github.com/magefree/mage/pull/6190#issuecomment-582354790
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "4BB"); // 4th Edition Foreign black border.
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "FBB"); // Foreign Black Border. Not on Scryfall, but other sources use this to distinguish non-English Revised cards
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PHJ"); // Hobby Japan Promos
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PJJT"); // Japan Junior Tournament
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PRED"); // Redemption Program
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PSAL"); // Salvat 2005
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PS11"); // Salvat 2011
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS"); // Magic Premiere Shop 2005, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS06"); // Magic Premiere Shop 2006, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS07"); // Magic Premiere Shop 2007, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS08"); // Magic Premiere Shop 2008, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS09"); // Magic Premiere Shop 2009, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS10"); // Magic Premiere Shop 2010, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMPS11"); // Magic Premiere Shop 2011, Japanese Basic lands
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "REN"); // Renaissance
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "RIN"); // Rinascimento
        //
        // Archenemy Schemes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OARC"); // Archenemy Schemes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OE01"); // Archenemy: Nicol Bolas Schemes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PARC"); // Promotional Schemes
        //
        // Plane-chase Planes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OHOP"); // Planechase Planes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OPC2"); // Planechase 2012 Plane
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OPCA"); // Planechase Anthology Planes
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PHOP"); // Promotional Planes
        //
        // Token sets TODO: implement tokens only sets
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L12"); // League Tokens 2012
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L13"); // League Tokens 2013
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L14"); // League Tokens 2014
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L15"); // League Tokens 2015
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L16"); // League Tokens 2016
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "L17"); // League Tokens 2017
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PLNY"); // 2018 Lunar New Year
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "F18"); // Friday Night Magic 2018
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PR2"); // Magic Player Rewards 2002
        //
        // PvE sets containing non-traditional cards. These enable casual PvE battles against a "random AI"-driven opponent.
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PPC1"); // M15 Prerelease Challenge
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "TBTH"); // Battle the Horde
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "TDAG"); // Defeat a God
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "TFTH"); // Face the Hydra
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "THP1"); // Theros Hero's Path
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "THP2"); // Born of the Gods Hero's Path
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "THP3"); // Journey into Nyx Hero's Path
        //
        // Other
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PCEL"); // Celebration Cards
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMOA"); // Magic Online Avatar
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PVAN"); // Vanguard Series
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PTG"); // Ponies: The Galloping

        // wrong card numbers skip list
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SWS"); // Star Wars
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "UND"); // un-sets don't have full implementation of card variations
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "UST"); // un-sets don't have full implementation of card variations
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SOI", "Tamiyo's Journal"); // not all variations implemented
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Zndrsplt, Eye of Wisdom"); // has alternative image as second side
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Krark's Thumb"); // has alternative image as second side
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Okaun, Eye of Chaos"); // has alternative image as second side
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Propaganda"); // has alternative image as second side
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Stitch in Time"); // has alternative image as second side
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Zndrsplt, Eye of Wisdom"); // has alternative image as second side


        // scryfall download sets (missing from scryfall website)
        skipListAddName(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS, "SWS"); // Star Wars

        // sample decks checking - some decks can contains unimplemented cards, so ignore it
        // file name must be related to sample-decks folder
        // for linux/windows build system use paths constructor
        skipListAddName(SKIP_LIST_SAMPLE_DECKS, Paths.get("Jumpstart", "jumpstart_custom.txt").toString()); // it's not a deck file
    }

    private final ArrayList<String> outputMessages = new ArrayList<>();
    private int failed = 0;
    private int wrongAbilityStatsTotal = 0;
    private int wrongAbilityStatsGood = 0;
    private int wrongAbilityStatsBad = 0;

    private static Set<String> skipListGet(String listName) {
        return skipCheckLists.computeIfAbsent(listName, x -> new LinkedHashSet<>());
    }

    private static void skipListAddName(String listName, String set, String cardName) {
        skipListGet(listName).add(set + " - " + cardName);
    }

    private static void skipListAddName(String listName, String set) {
        skipListGet(listName).add(set);
    }

    private static boolean skipListHaveName(String listName, String set, String cardName) {
        return skipListGet(listName).contains(set + " - " + cardName)
                || skipListGet(listName).contains(set);
    }

    private static boolean skipListHaveName(String listName, String set) {
        return skipListGet(listName).contains(set);
    }

    private static boolean evergreenCheck(String s) {
        return evergreenKeywords.contains(s) || s.startsWith("protection from") || s.startsWith("hexproof from") || s.startsWith("ward ");
    }

    private static <T> boolean eqSet(Collection<T> a, Collection<T> b) {
        if (a == null || a.isEmpty()) {
            return b == null || b.isEmpty();
        }
        return b != null && a.size() == b.size() && a.containsAll(b);
    }

    private void warn(Card card, String message) {
        outputMessages.add("Warning: " + message + " for " + card.getExpansionSetCode() + " - " + card.getName() + " - " + card.getCardNumber());
    }

    private void fail(Card card, String category, String message) {
        failed++;
        outputMessages.add("Error: (" + category + ") " + message + " for " + card.getExpansionSetCode() + " - " + card.getName() + " - " + card.getCardNumber());
    }

    @Test
    public void test_verifyCards() {
        checkWrongAbilitiesTextStart();

        int cardIndex = 0;
        for (Card card : CardScanner.getAllCards()) {
            cardIndex++;
            if (card instanceof CardWithHalves) {
                check(((CardWithHalves) card).getLeftHalfCard(), cardIndex);
                check(((CardWithHalves) card).getRightHalfCard(), cardIndex);
            } else if (card instanceof AdventureCard) {
                check(card, cardIndex);
                check(((AdventureCard) card).getSpellCard(), cardIndex);
            } else {
                check(card, cardIndex);
            }
        }

        checkWrongAbilitiesTextEnd();

        printMessages(outputMessages);
        if (failed > 0) {
            Assert.fail(String.format("found %d errors in %d cards verify (see errors list above)", failed, CardScanner.getAllCards().size()));
        }
    }

    @Test
    public void test_checkDuplicateCardNumbersInDB() {
        Collection<String> doubleErrors = new ArrayList<>();

        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            Map<String, ExpansionSet.SetCardInfo> cardsList = new HashMap<>();
            for (ExpansionSet.SetCardInfo checkCard : set.getSetCardInfo()) {
                String cardNumber = checkCard.getCardNumber();

                // ignore double faced
                Card realCard = CardImpl.createCard(checkCard.getCardClass(), new CardSetInfo(checkCard.getName(), set.getCode(),
                        checkCard.getCardNumber(), checkCard.getRarity(), checkCard.getGraphicInfo()));
                if (realCard.isNightCard()) {
                    continue;
                }

                if (cardsList.containsKey(cardNumber)) {
                    ExpansionSet.SetCardInfo prevCard = cardsList.get(cardNumber);

                    String errorType;
                    if (checkCard.getName().equals(prevCard.getName())) {
                        errorType = " found DUPLICATED cards"
                                + " set (" + set.getCode() + " - " + set.getName() + ")"
                                + " (" + checkCard.getCardNumber() + " - " + checkCard.getName() + ")";
                    } else {
                        errorType = " found TYPOS in card numbers"
                                + " set (" + set.getCode() + " - " + set.getName() + ")"
                                + " (" + prevCard.getCardNumber() + " - " + prevCard.getName() + ")"
                                + " and"
                                + " (" + checkCard.getCardNumber() + " - " + checkCard.getName() + ")";
                    }

                    String error = "Error: " + errorType;

                    doubleErrors.add(error);
                } else {
                    cardsList.put(cardNumber, checkCard);
                }
            }
        }

        for (String error : doubleErrors) {
            System.out.println(error);
        }

        if (doubleErrors.size() > 0) {
            Assert.fail("DB has duplicated card numbers, found errors: " + doubleErrors.size());
        }
    }

    @Test
    @Ignore // TODO: enable it after THB set will be completed
    public void test_checkDoubleRareCardsInSets() {
        // all basic sets after THB must have double rare cards (one normal, one bonus)
        // ELD can have same rules, but xmage stores it as different sets (ELD and CELD)
        Date startCheck = TherosBeyondDeath.getInstance().getReleaseDate();
        Calendar cal = Calendar.getInstance();
        cal.set(2050, Calendar.JANUARY, 1); // optimistic
        Date endCheck = cal.getTime();

        Collection<String> doubleErrors = new ArrayList<>();

        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            // only post THB sets must have double versions
            if (set.getReleaseDate().before(startCheck)
                    || set.getReleaseDate().after(endCheck)
                    || !set.getSetType().isStandardLegal()) {
                continue;
            }

            if (skipListHaveName(SKIP_LIST_DOUBLE_RARE, set.getCode())) {
                continue;
            }

            Map<String, Integer> cardsList = new HashMap<>();
            for (ExpansionSet.SetCardInfo checkCard : set.getSetCardInfo()) {
                // only rare cards must have double versions
                if (!Objects.equals(checkCard.getRarity(), Rarity.RARE) && !Objects.equals(checkCard.getRarity(), Rarity.MYTHIC)) {
                    continue;
                }

                if (skipListHaveName(SKIP_LIST_DOUBLE_RARE, set.getCode(), checkCard.getName())) {
                    continue;
                }

                String cardName = checkCard.getName();
                cardsList.putIfAbsent(cardName, 0);
                cardsList.compute(cardName, (k, v) -> v + 1);
            }

            cardsList.forEach((cardName, amount) -> {
                if (amount != 2) {
                    String error = "Error: non duplicated rare card -"
                            + " set (" + set.getCode() + " - " + set.getName() + ")"
                            + " card (" + cardName + ")";
                    doubleErrors.add(error);
                }
            });
        }

        for (String error : doubleErrors) {
            System.out.println(error);
        }

        if (doubleErrors.size() > 0) {
            Assert.fail("DB has non duplicated rare cards, found errors: " + doubleErrors.size());
        }
    }

    @Test
    public void test_checkWrongCardClasses() {
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
                        // workaround to star wars and unstable set with same card names
                        if (!checkCard.getName().equals("Syndicate Enforcer")
                                && !checkCard.getName().equals("Everythingamajig")
                                && !checkCard.getName().equals("Garbage Elemental")
                                && !checkCard.getName().equals("Very Cryptic Command")) {
                            errorsList.add("Error: wrong class in set " + set.getCode() + " - " + checkCard.getName() + " (" + currentClass + " <> " + needClass + ")");
                        }
                    }
                } else {
                    classesIndex.put(checkCard.getName(), currentClass);
                }
            }
        }

        printMessages(errorsList);

        // unique cards stats
        System.out.println("Total unique cards: " + classesIndex.size() + ", total non unique cards (reprints): " + totalCards);

        if (errorsList.size() > 0) {
            Assert.fail("DB has wrong card classes, found errors: " + errorsList.size());
        }
    }

    @Test
    public void test_checkMissingSets() {
        // generate unimplemented sets list
        Collection<String> info = new ArrayList<>();

        int missingSets = 0;
        int missingCards = 0;
        int unsupportedSets = 0;
        int unsupportedCards = 0;
        int mtgCards = 0;
        int mtgSets = 0;
        int xmageCards = 0;
        int xmageUnofficialSets = 0;
        int xmageUnofficialCards = 0;
        Collection<ExpansionSet> sets = Sets.getInstance().values();

        Assert.assertFalse("XMage data must contains sets list", sets.isEmpty());
        Assert.assertFalse("MtgJson data must contains sets list", MtgJsonService.sets().isEmpty());

        // official sets
        for (Map.Entry<String, MtgJsonSet> refEntry : MtgJsonService.sets().entrySet()) {
            MtgJsonSet refSet = refEntry.getValue();
            mtgCards += refSet.totalSetSize;

            // replace codes for aliases
            String searchSet = MtgJsonService.mtgJsonToXMageCodes.getOrDefault(refSet.code, refSet.code);
            if (skipListHaveName(SKIP_LIST_UNSUPPORTED_SETS, searchSet)) {
                unsupportedSets++;
                unsupportedCards += refSet.totalSetSize;
                continue;
            }

            ExpansionSet mageSet = Sets.findSet(searchSet.toUpperCase(Locale.ENGLISH));
            if (mageSet == null) {
                if (refSet.cards.isEmpty() || refSet.name.contains("Oversized")) {
                    unsupportedSets++;
                    unsupportedCards += refSet.totalSetSize;
                } else {
                    missingSets++;
                    missingCards = missingCards + refSet.cards.size();
                    if (!CHECK_ONLY_ABILITIES_TEXT) {
                        info.add("Warning: missing set " + refSet.code + " - " + refSet.name + " (cards: " + refSet.cards.size() + ", date: " + refSet.releaseDate + ")");
                    }
                }
                continue;
            }

            mtgSets++;
            xmageCards += mageSet.getSetCardInfo().size();
        }
        if (info.size() > 0) {
            info.add("Warning: total missing sets: " + missingSets + ", with missing cards: " + missingCards);
        }

        // unofficial sets info
        for (ExpansionSet set : sets) {
            if (MtgJsonService.sets().containsKey(set.getCode())) {
                continue;
            }

            xmageUnofficialSets++;
            xmageUnofficialCards += set.getSetCardInfo().size();
            info.add("Unofficial set: " + set.getCode() + " - " + set.getName() + ", cards: " + set.getSetCardInfo().size() + ", year: " + set.getReleaseYear());
        }

        printMessages(info);
        System.out.println();
        System.out.println("Official sets implementation stats:");
        System.out.println("* MTG sets: " + MtgJsonService.sets().size() + ", cards: " + mtgCards);
        System.out.println("* Implemented sets: " + mtgSets + ", cards: " + xmageCards);
        System.out.println("* Unsupported sets: " + unsupportedSets + ", cards: " + unsupportedCards);
        System.out.println("* TODO sets: " + (MtgJsonService.sets().size() - mtgSets - unsupportedSets) + ", cards: " + (mtgCards - xmageCards - unsupportedCards));
        System.out.println();
        System.out.println("Unofficial sets implementation stats:");
        System.out.println("* Implemented sets: " + xmageUnofficialSets + ", cards: " + xmageUnofficialCards);
        System.out.println();
    }

    @Test
    public void test_checkSampleDecks() {
        Collection<String> errorsList = new ArrayList<>();

        // workaround to run verify test from IDE or from maven's project root folder
        Path rootPath = Paths.get("Mage.Client", "release", "sample-decks");
        if (!Files.exists(rootPath)) {
            rootPath = Paths.get("..", "Mage.Client", "release", "sample-decks");
        }
        if (!Files.exists(rootPath)) {
            Assert.fail("Sample decks: unknown root folder " + rootPath.toAbsolutePath());
        }

        // collect all files in all root's folders
        Collection<Path> filesList = new ArrayList<>();
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    filesList.add(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            errorsList.add("Error: sample deck - can't get folder content - " + e.getMessage());
        }
        Assert.assertTrue("Sample decks: can't find any deck files in " + rootPath.toAbsolutePath(), filesList.size() > 0);

        // try to open deck files
        int totalErrorFiles = 0;
        for (Path deckFile : filesList) {
            String deckName = rootPath.relativize(deckFile).toString();
            if (!deckName.endsWith(".dck") || skipListHaveName(SKIP_LIST_SAMPLE_DECKS, deckName)) {
                continue;
            }

            StringBuilder deckErrors = new StringBuilder();
            DeckCardLists deckCards = DeckImporter.importDeckFromFile(deckFile.toString(), deckErrors, AUTO_FIX_SAMPLE_DECKS);

            if (!deckErrors.toString().isEmpty()) {
                errorsList.add("Error: sample deck contains errors " + deckName);
                System.out.println("Errors in sample deck " + deckName + ":\n" + deckErrors);
                totalErrorFiles++;
                continue;
            }

            if ((deckCards.getCards().size() + deckCards.getSideboard().size()) < 10) {
                // note: does not check whether cards are extra deck cards (contraptions/attractions);
                // may succeed for decks with such cards when it should fail
                errorsList.add("Error: sample deck contains too little cards (" + deckCards.getSideboard().size() + ") " + deckName);
                totalErrorFiles++;
                continue;
            }
        }

        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found sample decks: " + filesList.size() + "; with errors: " + totalErrorFiles);
        }
    }

    @Test
    public void test_checkMissingSecondSideCardsInSets() {
        Collection<String> errorsList = new ArrayList<>();

        // CHECK: if card have second side (flip, transform) then it must have all sides in that set
        for (ExpansionSet set : Sets.getInstance().values()) {
            for (ExpansionSet.SetCardInfo info : set.getSetCardInfo()) {
                CardInfo cardInfo = CardRepository.instance.findCardsByClass(info.getCardClass().getCanonicalName()).stream().findFirst().orElse(null);
                Assert.assertNotNull(cardInfo);

                Card card = cardInfo.getCard();
                Card secondCard = card.getSecondCardFace();
                if (secondCard != null) {
                    if (set.findCardInfoByClass(secondCard.getClass()).isEmpty()) {
                        errorsList.add("Error: missing second face card from set: " + set.getCode() + " - " + set.getName() + " - main: " + card.getName() + "; second: " + secondCard.getName());
                    }
                }
            }
        }

        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found missing second side cards in sets, errors: " + errorsList.size());
        }
    }

    @Test
    @Ignore // TODO: enable after all missing cards and settings fixes
    public void test_checkWrongCardsDataInSets() {
        Collection<String> errorsList = new ArrayList<>();
        Collection<String> warningsList = new ArrayList<>();
        Collection<ExpansionSet> xmageSets = Sets.getInstance().values();
        Set<String> foundedJsonCards = new HashSet<>();

        // fast check instead card's db search (only main side card)
        Set<String> implementedIndex = new HashSet<>();
        CardRepository.instance.findCards(new CardCriteria()).forEach(card -> {
            implementedIndex.add(card.getName());
        });

        // CHECK: wrong card numbers
        for (ExpansionSet set : xmageSets) {
            if (skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, set.getCode())) {
                continue;
            }

            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                MtgJsonCard jsonCard = MtgJsonService.cardFromSet(set.getCode(), card.getName(), card.getCardNumber());
                if (jsonCard == null) {
                    // see convertMtgJsonToXmageCardNumber for card number convert notation
                    errorsList.add("Error: unknown mtgjson's card number or set, use standard number notations: "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                    continue;
                }

                // index for missing cards
                String code = MtgJsonService.xMageToMtgJsonCodes.getOrDefault(set.getCode(), set.getCode()) + " - " + jsonCard.getNameAsFull() + " - " + jsonCard.number;
                foundedJsonCards.add(code);

                // CHECK: only lands can use full art in current version;
                // Another cards must be in text render mode as normal, example: https://scryfall.com/card/sld/76/athreos-god-of-passage
                boolean isLand = card.getRarity().equals(Rarity.LAND);
                if (card.isFullArt() && !isLand) {
                    errorsList.add("Error: only lands can use full art setting: "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: must use full art setting
                if (jsonCard.isFullArt && isLand && !card.isFullArt()) {
                    errorsList.add("Error: card must use full art setting: "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: must not use full art setting
                if (!jsonCard.isFullArt && card.isFullArt()) {
                    errorsList.add("Error: card must NOT use full art setting: "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }
            }
        }

        // CHECK: missing cards from set
        for (MtgJsonSet jsonSet : MtgJsonService.sets().values()) {
            if (skipListHaveName(SKIP_LIST_UNSUPPORTED_SETS, jsonSet.code)
                    || skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, jsonSet.code)) {
                continue;
            }

            ExpansionSet xmageSet = Sets.findSet(jsonSet.code);
            if (xmageSet == null) {
                ExpansionSet sameSet = Sets.findSetByName(jsonSet.name);
                if (sameSet != null) {
                    warningsList.add("Warning: implemented set with different set code: "
                            + jsonSet.code + " - " + jsonSet.name + " - " + jsonSet.releaseDate
                            + " (xmage's set under code: " + sameSet.getCode() + ")"
                    );
                } else {
                    warningsList.add("Warning: un-implemented set from mtgjson: " + jsonSet.code + " - " + jsonSet.name + " - " + jsonSet.releaseDate);
                }
                continue;
            }

            for (MtgJsonCard jsonCard : jsonSet.cards) {
                String code = jsonSet.code + " - " + jsonCard.getNameAsFull() + " - " + jsonCard.number;
                if (!foundedJsonCards.contains(code)) {
                    if (!implementedIndex.contains(jsonCard.getNameAsFull())) {
                        warningsList.add("Warning: un-implemented card from mtgjson: " + jsonSet.code + " - " + jsonCard.getNameAsFull() + " - " + jsonCard.number);
                    } else {
                        errorsList.add("Error: missing card from xmage's set: " + jsonSet.code + " - " + jsonCard.getNameAsFull() + " - " + jsonCard.number);
                    }
                }
            }
        }

        printMessages(warningsList);
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found wrong cards data in sets, errors: " + errorsList.size());
        }
    }

    @Test
    @Ignore // TODO: enable after all missing cards and settings fixes
    public void test_checkMissingScryfallSettingsAndCardNumbers() {
        Collection<String> errorsList = new ArrayList<>();

        Collection<ExpansionSet> xmageSets = Sets.getInstance().values();
        Set<String> scryfallSets = ScryfallImageSupportCards.getSupportedSets();

        // CHECK: missing sets in supported list
        for (ExpansionSet set : xmageSets) {
            if (skipListHaveName(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS, set.getCode())) {
                continue;
            }

            if (!scryfallSets.contains(set.getCode())) {
                errorsList.add("Error: scryfall download missing setting: " + set.getCode() + " - " + set.getName());
            }
        }

        // CHECK: unknown sets in supported list
        for (String scryfallCode : scryfallSets) {
            if (xmageSets.stream().noneMatch(e -> e.getCode().equals(scryfallCode))) {
                errorsList.add("Error: scryfall download unknown setting: " + scryfallCode);
            }
        }

        // card numbers
        // all cards with non-ascii numbers must be downloaded by direct links (api)
        Set<String> foundedDirectDownloadKeys = new HashSet<>();
        for (ExpansionSet set : xmageSets) {
            if (skipListHaveName(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS, set.getCode())
                    || skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, set.getCode())) {
                continue;
            }

            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                if (skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, set.getCode(), card.getName())) {
                    continue;
                }

                MtgJsonCard jsonCard = MtgJsonService.cardFromSet(set.getCode(), card.getName(), card.getCardNumber());
                if (jsonCard == null) {
                    // see convertMtgJsonToXmageCardNumber for card number convert notation
                    if (!skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, set.getCode(), card.getName())) {
                        errorsList.add("Error: scryfall download can't find card from mtgjson " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                    }
                    continue;
                }

                // CHECK: non-ascii numbers and direct download list
                if (!CharMatcher.ascii().matchesAllOf(jsonCard.number)) {
                    // non-ascii numbers
                    // xmage card numbers can't have non-ascii numbers (it checked by test_checkMissingCardData)
                    String key = ScryfallImageSupportCards.findDirectDownloadKey(set.getCode(), card.getName(), card.getCardNumber());
                    if (key != null) {
                        foundedDirectDownloadKeys.add(key);
                    } else {
                        // how-to fix: add miss images links in ScryfallImageSupportCards->directDownloadLinks
                        errorsList.add("Error: scryfall download can't find non-ascii card link in direct download list " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + jsonCard.number);
                    }
                }

                // CHECK: reversible_card must be in direct download list (xmage must have 2 cards with diff image face)
                if (jsonCard.layout.equals("reversible_card")) {
                    String key = ScryfallImageSupportCards.findDirectDownloadKey(set.getCode(), card.getName(), card.getCardNumber());
                    if (key != null) {
                        foundedDirectDownloadKeys.add(key);
                    } else {
                        errorsList.add("Error: scryfall download can't find face image of reversible_card in direct download list " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + jsonCard.number);
                    }
                }
            }
        }

        // CHECK: unknown direct download links
        // TODO: add same for tokens
        for (Map.Entry<String, String> direct : ScryfallImageSupportCards.getDirectDownloadLinks().entrySet()) {
            // skip custom sets
            String setCode = ScryfallImageSupportCards.extractSetCodeFromDirectKey(direct.getKey());
            String cardName = ScryfallImageSupportCards.extractCardNameFromDirectKey(direct.getKey());
            if (skipListHaveName(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS, setCode)
                    || skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, setCode)) {
                continue;
            }

            // skip non-implemented cards list
            if (CardRepository.instance.findCard(cardName) == null) {
                continue;
            }

            if (!foundedDirectDownloadKeys.contains(direct.getKey())) {
                errorsList.add("Error: scryfall download found unknown direct download link " + direct.getKey() + " - " + direct.getValue());
            }
        }

        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found scryfall download errors: " + errorsList.size());
        }
    }

    private Object createNewObject(Class<?> clazz) {
        try {
            Constructor<?> cons = clazz.getConstructor();
            return cons.newInstance();
        } catch (InvocationTargetException ex) {
            Throwable e = ex.getTargetException();
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printMessages(Collection<String> list, boolean sorted) {

        ArrayList<String> sortedList = new ArrayList<>(list);
        if (sorted) {
            sortedList.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    // show errors in the end of the list (after warnings, near the final assert fail)
                    boolean e1 = o1.toLowerCase(Locale.ENGLISH).startsWith("error");
                    boolean e2 = o2.toLowerCase(Locale.ENGLISH).startsWith("error");
                    if (e1 != e2) {
                        return Boolean.compare(e1, e2);
                    } else {
                        return o1.compareTo(o2);
                    }
                }
            });
        }

        for (String mes : sortedList) {
            System.out.println(mes);
        }
    }

    private void printMessages(Collection<String> list) {
        printMessages(list, true);
    }

    private String extractShortClass(Class<? extends Object> tokenClass) {
        String origin = tokenClass.getName();
        if (origin.contains("$")) {
            // inner classes, example: mage.cards.f.FigureOfDestiny$FigureOfDestinyToken3
            return origin.replaceAll(".+\\$(.+)", "$1");
        } else {
            // public classes, example: mage.game.permanent.token.FigureOfDestinyToken
            return origin.replaceAll(".+\\.(.+)", "$1");
        }
    }

    @Test
    public void test_checkMissingSetData() {
        Collection<String> errorsList = new ArrayList<>();
        Collection<String> warningsList = new ArrayList<>();

        Collection<ExpansionSet> sets = Sets.getInstance().values();

        // CHECK: wrong set class names
        for (ExpansionSet set : sets) {
            String className = extractShortClass(set.getClass());
            String needClassName = Arrays.stream(
                    set.getName()
                            .replaceAll("&", "And")
                            .replace("-", " ")
                            .replaceAll("[.+-/:\"']", "")
                            .split(" ")
            ).map(CardUtil::getTextWithFirstCharUpperCase).reduce("", String::concat);

            if (!className.equals(needClassName)) {
                errorsList.add("Error: set's class name must be equal to set name: "
                        + className + " from " + set.getClass().getName() + ", caption: " + set.getName() + ", need name: " + needClassName);
            }
        }

        // CHECK: wrong basic lands settings (it's for lands search, not booster construct)
        for (ExpansionSet set : sets) {
            boolean needLand = set.hasBasicLands();
            boolean foundLand = false;
            Map<String, Integer> foundLandsList = new HashMap<>();
            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                if (isBasicLandName(card.getName())) {
                    foundLand = true;
                    int count = foundLandsList.getOrDefault(card.getName(), 0);
                    foundLandsList.put(card.getName(), count + 1);
                }
            }

            String landNames = foundLandsList.entrySet().stream()
                    .map(p -> (p.getKey() + " - " + p.getValue().toString()))
                    .sorted().collect(Collectors.joining(", "));

            if (needLand && !foundLand) {
                errorsList.add("Error: set with wrong hasBasicLands - it's true, but haven't land cards: " + set.getCode() + " in " + set.getClass().getName());
            }

            if (!needLand && foundLand) {
                errorsList.add("Error: set with wrong hasBasicLands - it's false, but have land cards: " + set.getCode() + " in " + set.getClass().getName() + ", lands: " + landNames);
            }

            // TODO: add test to check num cards (hasBasicLands and numLand > 0)
        }

        // CHECK: wrong snow land info - set needs to have exclusively snow basics to qualify
        for (ExpansionSet set : sets) {
            boolean needSnow = CardRepository.haveSnowLands(set.getCode());
            boolean haveSnow = false;
            boolean haveNonSnow = false;
            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                if (card.getName().startsWith("Snow-Covered ")) {
                    haveSnow = true;
                }
                if (isNonSnowBasicLandName(card.getName())) {
                    haveNonSnow = true;
                    break;
                }
            }
            if (needSnow != (haveSnow && !haveNonSnow)) {
                errorsList.add("Error: incorrect snow land info in set " + set.getCode() + ": "
                        + ((haveSnow && !haveNonSnow) ? "set has exclusively snow basics" : "set doesn't have exclusively snow basics")
                        + ", but xmage thinks that it " + (needSnow ? "does" : "doesn't"));
            }
        }

        // CHECK: wrong set name
        for (ExpansionSet set : sets) {
            if (true) continue; // TODO: enable after merge of 40k's cards pull requests (needs before set rename)
            MtgJsonSet jsonSet = MtgJsonService.sets().getOrDefault(set.getCode().toUpperCase(Locale.ENGLISH), null);
            if (jsonSet == null) {
                // unofficial or inner set
                continue;
            }
            if (!Objects.equals(set.getName(), jsonSet.name)) {
                // how-to fix: rename xmage set to the json version or fix a set's code
                // also don't forget to change names in mtg-cards-data.txt
                errorsList.add(String.format("Error: wrong set name or set code: %s (mtgjson set for same code: %s)",
                        set.getCode() + " - " + set.getName(),
                        jsonSet.name
                ));
            }
        }

        // CHECK: parent and block info
        for (ExpansionSet set : sets) {
            if (true) continue; // TODO: comments it and run to find a problems
            MtgJsonSet jsonSet = MtgJsonService.sets().getOrDefault(set.getCode().toUpperCase(Locale.ENGLISH), null);
            if (jsonSet == null) {
                continue;
            }

            // parent set
            MtgJsonSet jsonParentSet = jsonSet.parentCode == null ? null : MtgJsonService.sets().getOrDefault(jsonSet.parentCode, null);
            ExpansionSet mageParentSet = set.getParentSet();
            String jsonParentCode = jsonParentSet == null ? "null" : jsonParentSet.code;
            String mageParentCode = mageParentSet == null ? "null" : mageParentSet.getCode();

            String needMageClass = "";
            if (!jsonParentCode.equals("null")) {
                needMageClass = sets
                        .stream()
                        .filter(exp -> exp.getCode().equals(jsonParentCode))
                        .map(exp -> " - " + exp.getClass().getSimpleName() + ".getInstance()")
                        .findFirst()
                        .orElse("- error, can't find class");
            }

            if (!Objects.equals(jsonParentCode, mageParentCode)) {
                errorsList.add(String.format("Error: set with wrong parentSet settings: %s (parentSet = %s, but must be %s%s)",
                        set.getCode() + " - " + set.getName(),
                        mageParentCode,
                        jsonParentCode,
                        needMageClass
                ));
            }

            // block info
            if (!Objects.equals(set.getBlockName(), jsonSet.block)) {
                if (true) continue; // TODO: comments it and run to find a problems
                errorsList.add(String.format("Error: set with wrong blockName settings: %s (blockName = %s, but must be %s)",
                        set.getCode() + " - " + set.getName(),
                        set.getBlockName(),
                        jsonSet.block
                ));
            }
        }

        // TODO: add test to check num cards for rarity (rarityStats > 0 and numRarity > 0)
        printMessages(warningsList);
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found set errors: " + errorsList.size());
        }
    }

    @Test
    public void test_checkMissingCardData() {
        Collection<String> errorsList = new ArrayList<>();
        Collection<String> warningsList = new ArrayList<>();

        Collection<ExpansionSet> sets = Sets.getInstance().values();

        // CHECK: wrong UsesVariousArt settings (set have duplicated card name without that setting -- e.g. cards will have same image)
        for (ExpansionSet set : sets) {

            Map<String, Integer> doubleNames = new HashMap<>();
            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                int count = doubleNames.getOrDefault(card.getName(), 0);
                doubleNames.put(card.getName(), count + 1);
            }

            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                boolean cardHaveDoubleName = (doubleNames.getOrDefault(card.getName(), 0) > 1);
                boolean cardHaveVariousSetting = card.getGraphicInfo() != null && card.getGraphicInfo().getUsesVariousArt();

                if (cardHaveDoubleName && !cardHaveVariousSetting) {
                    errorsList.add("Error: double faced card, but UsesVariousArt = false (missing NON_FULL_USE_VARIOUS, etc): "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }
            }
        }

        for (ExpansionSet set : sets) {
            // additional info
            Set<String> cardNames = new HashSet<>();
            for (ExpansionSet.SetCardInfo cardInfo : set.getSetCardInfo()) {
                cardNames.add(cardInfo.getName());
            }

            // CHECK: set code must be compatible with tests commands format like "SET-card"
            // how-to fix: increase lookup lenth
            if (set.getCode().length() + 1 > CardUtil.TESTS_SET_CODE_LOOKUP_LENGTH) {
                errorsList.add("Error: set code too big for test commads lookup: " + set.getCode() + ", lookup length: " + CardUtil.TESTS_SET_CODE_LOOKUP_LENGTH);
            }

            boolean containsDoubleSideCards = false;
            Map<String, String> cardNumbers = new HashMap<>();
            for (ExpansionSet.SetCardInfo cardInfo : set.getSetCardInfo()) {
                Card card = CardImpl.createCard(cardInfo.getCardClass(), new CardSetInfo(cardInfo.getName(), set.getCode(),
                        cardInfo.getCardNumber(), cardInfo.getRarity(), cardInfo.getGraphicInfo()));
                Assert.assertNotNull(card);

                if (card.getSecondCardFace() != null) {
                    containsDoubleSideCards = true;
                }

                // CHECK: all planeswalkers must be legendary
                if (card.isPlaneswalker() && !card.isLegendary()) {
                    errorsList.add("Error: planeswalker must have legendary type: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: getMana must works without NPE errors (it uses getNetMana with empty game param for AI score calcs)
                // https://github.com/magefree/mage/issues/6300
                card.getMana();

                // CHECK: non ascii symbols in card name and number
                if (!CharMatcher.ascii().matchesAllOf(card.getName()) || !CharMatcher.ascii().matchesAllOf(card.getCardNumber())) {
                    errorsList.add("Error: card name or number contains non-ascii symbols: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: set code and card name must be parseable for test and cheat commands XLN-Mountain
                List<String> cardCommand = SystemUtil.parseSetAndCardNameCommand(set.getCode() + CardUtil.TESTS_SET_CODE_DELIMETER + card.getName());
                if (!Objects.equals(set.getCode(), cardCommand.get(0))
                        || !Objects.equals(card.getName(), cardCommand.get(1))) {
                    // if you catch it then parser logic must be changed to support problem set-card combination
                    errorsList.add("Error: card name can't be used in tests and cheats with set code: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: card number must start with 09-aZ symbols (wrong symbol example: *123)
                // if you found card with number like *123 then report it to scryfall to fix to 123*
                if (!Character.isLetterOrDigit(card.getCardNumber().charAt(0))) {
                    errorsList.add("Error: card number must start with digit: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: second side cards in one set
                // https://github.com/magefree/mage/issues/8081
                /*
                if (card.getSecondCardFace() != null && cardNames.contains(card.getSecondCardFace().getName())) {
                    errorsList.add("Error: set contains second side cards: " + set.getCode() + " - " + set.getName()
                            + " - " + card.getName() + " - " + card.getCardNumber()
                            + " - " + card.getSecondCardFace().getName() + " - " + card.getSecondCardFace().getCardNumber());
                }
                //*/

                // CHECK: set contains both card sides
                // related to second side cards usage
                /*
                String existedCardName = cardNumbers.getOrDefault(card.getCardNumber(), null);
                if (existedCardName != null && !existedCardName.equals(card.getName())) {
                    String info = card.isNightCard() ? existedCardName + " -> " + card.getName() : card.getName() + " -> " + existedCardName;
                    errorsList.add("Error: set contains both card sides instead main only: "
                            + set.getCode() + " - " + set.getName() + " - " + info + " - " + card.getCardNumber());
                } else {
                    cardNumbers.put(card.getCardNumber(), card.getName());
                }
                //*/
            }

            // CHECK: double side cards must be in boosters
            boolean hasBoosterSettings = (set.getNumBoosterDoubleFaced() > 0);
            if (set.hasBoosters()
                    && (set.getNumBoosterDoubleFaced() != -1) // -1 must ignore double cards in booster
                    && containsDoubleSideCards
                    && !hasBoosterSettings) {
                errorsList.add("Error: set with boosters contains second side cards, but numBoosterDoubleFaced is not set - "
                        + set.getCode() + " - " + set.getName());
            }
        }

        printMessages(warningsList);
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found card errors: " + errorsList.size());
        }
    }

    @Test
    public void test_checkWatcherCopyMethods() {

        Collection<String> errorsList = new ArrayList<>();
        Collection<String> warningsList = new ArrayList<>();

        Reflections reflections = new Reflections("mage.");
        Set<Class<? extends Watcher>> watcherClassesList = reflections.getSubTypesOf(Watcher.class);

        for (Class<? extends Watcher> watcherClass : watcherClassesList) {

            // only watcher class can be extended (e.g. final)
            if (!watcherClass.getSuperclass().equals(Watcher.class)) {
                errorsList.add("Error: only Watcher class can be extended: " + watcherClass.getName());
            }

            // no copy methods
            try {
                Method m = watcherClass.getMethod("copy");
                if (!m.getGenericReturnType().getTypeName().equals("T")) {
                    errorsList.add("Error: copy() method must be deleted from watcher class: " + watcherClass.getName());
                }
            } catch (NoSuchMethodException e) {
                errorsList.add("Error: can't find copy() method in watcher class: " + watcherClass.getName());
            }

            // no constructor for copy
            try {
                Constructor<? extends Watcher> constructor = watcherClass.getDeclaredConstructor(watcherClass);
                errorsList.add("Error: copy constructor is not allowed in watcher class: " + watcherClass.getName());
            } catch (NoSuchMethodException e) {
                // all fine, no needs in copy constructors
            }

            // errors on create
            try {
                List<?> constructors = Arrays.asList(watcherClass.getDeclaredConstructors());

                Constructor<? extends Watcher> constructor = (Constructor<? extends Watcher>) constructors.get(0);

                Object[] args = new Object[constructor.getParameterCount()];
                for (int index = 0; index < constructor.getParameterTypes().length; index++) {
                    Class<?> parameterType = constructor.getParameterTypes()[index];
                    if (parameterType.getSimpleName().equalsIgnoreCase("boolean")) {
                        args[index] = false;
                    } else {
                        args[index] = null;
                    }

                }

                constructor.setAccessible(true);
                Watcher w1 = constructor.newInstance(args);

                // errors on copy
                try {
                    Watcher w2 = w1.copy();
                    if (w2 == null) {
                        errorsList.add("Error: can't copy watcher with unknown error, look at error logs above: " + watcherClass.getName());
                    }
                } catch (Exception e) {
                    errorsList.add("Error: can't copy watcher: " + watcherClass.getName() + " (" + e.getMessage() + ")");
                }
            } catch (Exception e) {
                errorsList.add("Error: can't create watcher: " + watcherClass.getName() + " (" + e.getMessage() + ")");
            }

        }

        printMessages(warningsList);
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found watcher errors: " + errorsList.size());
        }
    }

    @Test
    @Ignore  // TODO: enable test after massive token fixes
    public void test_checkMissingTokenData() {

        Collection<String> errorsList = new ArrayList<>();
        Collection<String> warningsList = new ArrayList<>();

        // all tokens must be stores in tokens-database.txt (if not then viewer and image downloader are missing token images)
        // https://github.com/ronmamo/reflections
        Reflections reflections = new Reflections("mage.");
        Set<Class<? extends TokenImpl>> tokenClassesList = reflections.getSubTypesOf(TokenImpl.class);

        // xmage tokens
        Set<Class<? extends TokenImpl>> privateTokens = new HashSet<>();
        Set<Class<? extends TokenImpl>> publicTokens = new HashSet<>();
        for (Class<? extends TokenImpl> tokenClass : tokenClassesList) {
            if (Modifier.isPublic(tokenClass.getModifiers())) {
                publicTokens.add(tokenClass);
            } else {
                privateTokens.add(tokenClass);
            }
        }
        // xmage sets
        Set<String> allSetCodes = Sets.getInstance().values().stream().map(ExpansionSet::getCode).collect(Collectors.toSet());
        allSetCodes.add(TokenRepository.XMAGE_TOKENS_SET_CODE); // reminder tokens


        // tok file's data
        List<TokenInfo> tokFileTokens = TokenRepository.instance.getAll();
        LinkedHashMap<String, String> tokDataClassesIndex = new LinkedHashMap<>();
        LinkedHashMap<String, String> tokDataNamesIndex = new LinkedHashMap<>();
        LinkedHashMap<String, List<TokenInfo>> tokDataTokensBySetIndex = new LinkedHashMap<>();
        for (TokenInfo tokData : tokFileTokens) {

            String searchName;
            String setsList;

            // by set
            List<TokenInfo> tokensInSet = tokDataTokensBySetIndex.getOrDefault(tokData.getSetCode(), null);
            if (tokensInSet == null) {
                tokensInSet = new ArrayList<>();
                tokDataTokensBySetIndex.put(tokData.getSetCode(), tokensInSet);
            }
            tokensInSet.add(tokData);

            // by class
            searchName = tokData.getFullClassFileName();
            setsList = tokDataClassesIndex.getOrDefault(searchName, "");
            if (!setsList.isEmpty()) {
                setsList += ",";
            }
            setsList += tokData.getSetCode();
            tokDataClassesIndex.put(searchName, setsList);

            // by name
            searchName = tokData.getName();
            setsList = tokDataNamesIndex.getOrDefault(searchName, "");
            if (!setsList.isEmpty()) {
                setsList += ",";
            }
            setsList += tokData.getSetCode();
            tokDataNamesIndex.put(searchName, setsList);
        }

        // CHECK: token's class name convention
        for (Class<? extends TokenImpl> tokenClass : tokenClassesList) {
            if (!tokenClass.getName().endsWith("Token")) {
                String className = extractShortClass(tokenClass);
                warningsList.add("warning, token class must ends with Token: " + className + " from " + tokenClass.getName());
            }
        }

        // CHECK: public class for downloadable tokens
        for (Class<? extends TokenImpl> tokenClass : publicTokens) {
            String fullClass = tokenClass.getName();
            if (!fullClass.startsWith("mage.game.permanent.token.")) {
                String className = extractShortClass(tokenClass);
                errorsList.add("Error: public token must stores in mage.game.permanent.token package: " + className + " from " + tokenClass.getName());
            }
        }

        // CHECK: private class for inner tokens (no needs at all -- all private tokens must be replaced by CreatureToken)
        for (Class<? extends TokenImpl> tokenClass : privateTokens) {
            String className = extractShortClass(tokenClass);
            errorsList.add("Warning: no needs in private tokens, replace it with CreatureToken: " + className + " from " + tokenClass.getName());
        }

        // CHECK: all public tokens must have tok-data (private tokens uses for innner abilities -- no need images for it)
        for (Class<? extends TokenImpl> tokenClass : publicTokens) {
            Token token = (Token) createNewObject(tokenClass);
            if (token == null) {
                // how-to fix:
                // - create empty param
                // - fix error in token's constructor
                errorsList.add("Error: token must have default constructor with zero params: " + tokenClass.getName());
            } else if (tokDataNamesIndex.getOrDefault(token.getName().replace(" Token", ""), "").isEmpty()) {
                if (token instanceof CreatureToken) {
                    // ignore custom token builders
                    continue;
                }
                // how-to fix:
                // - public token must be downloadable, so tok-data must contain miss set
                //   (also don't forget to add new set to scryfall download)
                errorsList.add("Error: can't find data in tokens-database.txt for token: " + tokenClass.getName() + " -> " + token.getName());
            }
        }

        // 111.4. A spell or ability that creates a token sets both its name and its subtype(s).
        // If the spell or ability doesn’t specify the name of the token, its name is the same
        // as its subtype(s) plus the word “Token.” Once a token is on the battlefield, changing
        // its name doesn’t change its subtype(s), and vice versa.
        for (Class<? extends TokenImpl> tokenClass : publicTokens) {
            Token token = (Token) createNewObject(tokenClass);
            if (token == null) {
                // error in constructor, see checks above
                continue;
            }

            // CHECK: tokens must have Token word in the name
            if (token.getDescription().startsWith(token.getName() + ", ")
                    || token.getDescription().contains("named " + token.getName())
                    || (token instanceof CreatureToken)) {
                // ignore some names:
                // - Boo, a legendary 1/1 red Hamster creature token with trample and haste
                // - 1/1 green Insect creature token with flying named Butterfly
                // - custom token builders
            } else {
                if (!token.getName().endsWith("Token")) {
                    errorsList.add("Error: token's name must ends with Token: "
                            + tokenClass.getName() + " - " + token.getName());
                }
            }

            // CHECK: named tokens must not have Token in the name
            if (token.getDescription().contains("named") && token.getName().contains("Token")) {
                // ignore ability text like Return a card named Deathpact Angel from
                if (!token.getDescription().contains("card named")) {
                    errorsList.add("Error: named token must not have Token in the name: "
                            + tokenClass.getName() + " - " + token.getName() + " - " + token.getDescription());
                }
            }
        }

        // CHECK: wrong set codes in tok-data
        tokDataTokensBySetIndex.forEach((setCode, setTokens) -> {
            if (!allSetCodes.contains(setCode)) {
                errorsList.add("error, tokens-database.txt contains unknown set code: "
                        + setCode + " - " + setTokens.stream().map(TokenInfo::getName).collect(Collectors.joining(", ")));
            }
        });

        // CHECK: find possible sets without tokens
        List<Card> cardsList = new ArrayList<>(CardScanner.getAllCards());
        Map<String, List<Card>> setsWithTokens = new HashMap<>();
        for (Card card : cardsList) {
            // must check all card parts (example: Mila, Crafty Companion with Lukka Emblem)
            String allRules = CardUtil.getObjectPartsAsObjects(card)
                    .stream()
                    .map(obj -> (Card) obj)
                    .map(Card::getRules)
                    .flatMap(Collection::stream)
                    .map(r -> r.toLowerCase(Locale.ENGLISH))
                    .collect(Collectors.joining("; "));
            if ((allRules.contains("create") && allRules.contains("token"))
                    || (allRules.contains("get") && allRules.contains("emblem"))) {
                List<Card> sourceCards = setsWithTokens.getOrDefault(card.getExpansionSetCode(), null);
                if (sourceCards == null) {
                    sourceCards = new ArrayList<>();
                    setsWithTokens.put(card.getExpansionSetCode(), sourceCards);
                }
                sourceCards.add(card);
            }
        }
        // set uses tokens, but tok data miss it
        setsWithTokens.forEach((setCode, sourceCards) -> {
            List<TokenInfo> setTokens = tokDataTokensBySetIndex.getOrDefault(setCode, null);
            if (setTokens == null) {
                // it's not a problem -- just find set's cards without real tokens for image tests
                // Possible reasons:
                // - promo sets with cards without tokens (nothing to do with it)
                // - miss set from tok-data (must add new set to tok-data and scryfall download)
                // - wizards miss some paper printed token, see https://www.mtg.onl/mtg-missing-tokens/
                warningsList.add("info, set's cards uses tokens but tok-data haven't it: "
                        + setCode + " - " + sourceCards.stream().map(MageObject::getName).collect(Collectors.joining(", ")));
            } else {
                // Card can be checked on scryfall like "set:set_code oracle:token_name oracle:token"
                // Possible reasons for un-used tokens:
                // - normal use case: tok-data contains wrong token data and must be removed
                // - normal use case: card uses wrong rules text (must contain "create" and "token" words)
                // - rare use case: un-implemented card that uses a token
                setTokens.forEach(token -> {
                    if (token.getName().contains("Plane - ")) {
                        // cards don't put it to the game, so no related cards
                        return;
                    }
                    String needTokenName = token.getName()
                            .replace(" Token", "")
                            .replace("Emblem ", "");
                    // cards with emblems don't use emblem's name, so check it in card name itself (example: Sarkhan, the Dragonspeaker)
                    // also must check all card parts (example: Mila, Crafty Companion with Lukka Emblem)
                    if (sourceCards.stream()
                            .map(CardUtil::getObjectPartsAsObjects)
                            .flatMap(Collection::stream)
                            .map(obj -> (Card) obj)
                            .map(card -> card.getName() + " - " + String.join(", ", card.getRules()))
                            .noneMatch(s -> s.contains(needTokenName))) {
                        warningsList.add("info, tok-data has un-used tokens: "
                                + token.getSetCode() + " - " + token.getName());
                    }
                });
            }
        });
        // tok data have tokens, but cards from set are miss
        tokDataTokensBySetIndex.forEach((setCode, setTokens) -> {
            if (setCode.equals(TokenRepository.XMAGE_TOKENS_SET_CODE)) {
                // ignore reminder tokens
                return;
            }
            if (!setsWithTokens.containsKey(setCode)) {
                // Possible reasons:
                // - outdated set code in tokens database (must be fixed by new set code, another verify check it)
                // - promo set contains additional tokens for main set (it's ok and must be ignored, example: Saproling in E02)
                warningsList.add("warning, tok-data has tokens, but real set haven't cards with it: "
                        + setCode + " - " + setTokens.stream().map(TokenInfo::getName).collect(Collectors.joining(", ")));
            }
        });

        // CHECK: token and class names must be same in all sets
        TokenRepository.instance.getAllByClassName().forEach((className, list) -> {
            // ignore reminder tokens
            Set<String> names = list.stream()
                    .filter(token -> !token.getTokenType().equals(TokenType.XMAGE))
                    .map(TokenInfo::getName)
                    .collect(Collectors.toSet());
            if (names.size() > 1) {
                errorsList.add("error, tokens-database.txt contains different names for same class: "
                        + className + " - " + String.join(", ", names));
            }
        });

        Set<String> usedNames = new HashSet<>();
        TokenRepository.instance.getByType(TokenType.XMAGE).forEach(token -> {
            // CHECK: xmage's tokens must be unique
            // how-to fix: edit TokenRepository->loadXmageTokens
            String needName = String.format("%s.%d", token.getName(), token.getImageNumber());
            if (usedNames.contains(needName)) {
                errorsList.add("error, xmage token's name and image number must be unique: "
                        + token.getName() + " - " + token.getImageNumber());
            } else {
                usedNames.add(needName);
            }

            // CHECK: xmage's tokens must be downloadable
            // how-to fix: edit TokenRepository->loadXmageTokens
            if (token.getDownloadUrl().isEmpty()) {
                errorsList.add("error, xmage token's must have download url: "
                        + token.getName() + " - " + token.getImageNumber());
            }
        });

        // TODO: all sets must have full tokens data in tok file (token in every set)
        // 1. Download scryfall tokens list: https://api.scryfall.com/cards/search?q=t:token
        // 2. Proccess each token with all prints: read "prints_search_uri" field from token data and go to link like
        // https://api.scryfall.com/cards/search?order=set&q=%21%E2%80%9CAngel%E2%80%9D&unique=prints
        // 3. Collect all strings in "set@name"
        // 4. Proccess tokens data and find missing strings from "set@name" list
        printMessages(warningsList);
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found token errors: " + errorsList.size());
        }
    }

    @Test
    public void test_checkMissingPlanesData() {
        Collection<String> errorsList = new ArrayList<>();

        Reflections reflections = new Reflections("mage.");
        Set<Class<? extends Plane>> planesClassesList = reflections.getSubTypesOf(Plane.class);

        // 1. correct class name
        for (Class<? extends Plane> planeClass : planesClassesList) {
            if (!planeClass.getName().endsWith("Plane")) {
                String className = extractShortClass(planeClass);
                errorsList.add("Error: plane class must ends with Plane: " + className + " from " + planeClass.getName());
            }
        }

        // 2. correct package
        for (Class<? extends Plane> planeClass : planesClassesList) {
            String fullClass = planeClass.getName();
            if (!fullClass.startsWith("mage.game.command.planes.")) {
                String className = extractShortClass(planeClass);
                errorsList.add("Error: plane must be stored in mage.game.command.planes package: " + className + " from " + planeClass.getName());
            }
        }

        // 3. correct constructor
        for (Class<? extends Plane> planeClass : planesClassesList) {
            String className = extractShortClass(planeClass);
            Plane plane;
            try {
                plane = (Plane) createNewObject(planeClass);

                // 4. must have type/name
                if (plane.getPlaneType() == null) {
                    errorsList.add("Error: plane must have plane type: " + className + " from " + planeClass.getName());
                }
            } catch (Throwable e) {
                errorsList.add("Error: can't create plane with default constructor: " + className + " from " + planeClass.getName());
            }
        }

        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found plane errors: " + errorsList.size());
        }
    }

    @Test
    public void test_checkMissingDungeonsData() {
        Collection<String> errorsList = new ArrayList<>();

        Reflections reflections = new Reflections("mage.");
        Set<Class<? extends Dungeon>> dungeonClassesList = reflections.getSubTypesOf(Dungeon.class);

        // 1. correct class name
        for (Class<? extends Dungeon> dungeonClass : dungeonClassesList) {
            if (!dungeonClass.getName().endsWith("Dungeon")) {
                String className = extractShortClass(dungeonClass);
                errorsList.add("Error: dungeon class must ends with Dungeon: " + className + " from " + dungeonClass.getName());
            }
        }

        // 2. correct package
        for (Class<? extends Dungeon> dungeonClass : dungeonClassesList) {
            String fullClass = dungeonClass.getName();
            if (!fullClass.startsWith("mage.game.command.dungeons.")) {
                String className = extractShortClass(dungeonClass);
                errorsList.add("Error: dungeon must be stored in mage.game.command.dungeons package: " + className + " from " + dungeonClass.getName());
            }
        }

        // 3. correct constructor
        for (Class<? extends Dungeon> dungeonClass : dungeonClassesList) {
            String className = extractShortClass(dungeonClass);
            Dungeon dungeon;
            try {
                dungeon = (Dungeon) createNewObject(dungeonClass);
            } catch (Throwable e) {
                errorsList.add("Error: can't create dungeon with default constructor: " + className + " from " + dungeonClass.getName());
            }
        }

        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail("Found dungeon errors: " + errorsList.size());
        }
    }

    private void check(Card card, int cardIndex) {
        MtgJsonCard ref = MtgJsonService.cardFromSet(card.getExpansionSetCode(), card.getName(), card.getCardNumber());
        if (ref != null) {
            checkAll(card, ref, cardIndex);
        } else if (!CHECK_ONLY_ABILITIES_TEXT) {
            warn(card, "Can't find card in mtgjson to verify");
        }
    }

    private boolean contains(Collection<String> options, String value) {
        return options != null && options.contains(value);
    }

    private static boolean wasCheckedByAbilityText(MtgJsonCard ref) {
        // ignore already checked cards, so no bloated logs from duplicated cards
        if (checkedNames.contains(ref.getNameAsFace())) {
            return true;
        }
        checkedNames.add(ref.getNameAsFace());
        return false;
    }

    private void checkAll(Card card, MtgJsonCard ref, int cardIndex) {
        if (!CHECK_ONLY_ABILITIES_TEXT) {
            checkCost(card, ref);
            checkPT(card, ref);
            checkLoyalty(card, ref);
            checkDefense(card, ref);
            checkSubtypes(card, ref);
            checkSupertypes(card, ref);
            checkTypes(card, ref);
            checkColors(card, ref);
            checkRarityAndBasicLands(card, ref);
            checkMissingAbilities(card, ref);
            checkWrongSymbolsInRules(card);
            checkCardCanBeCopied(card);
        }
        checkWrongAbilitiesText(card, ref, cardIndex);
    }

    private void checkColors(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_COLOR, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        Set<String> expected = new HashSet<>();
        if (ref.colors != null) {
            expected.addAll(ref.colors);
        }
        if (card.isFlipCard()) {
            expected.addAll(ref.colorIdentity);
        }

        ObjectColor color = card.getColor(null);

        if (expected.size() != color.getColorCount()
                || (color.isBlack() && !expected.contains("B"))
                || (color.isBlue() && !expected.contains("U"))
                || (color.isGreen() && !expected.contains("G"))
                || (color.isRed() && !expected.contains("R"))
                || (color.isWhite() && !expected.contains("W"))) {
            fail(card, "colors", color + " != " + expected);
        }
    }

    // "copy" fails means that the copy constructor are not correct inside a card.
    // To fix those, try to find the class that did trigger the copy failure, and check
    // that copy() exists, a copy constructor exists, and the copy constructor is right. 
    private void checkCardCanBeCopied(Card card1) {
        Card card2;
        try {
            card2 = card1.copy();
        } catch (Error err) {
            fail(card1, "copy", "throws on copy : " + err.getClass() + " : " + err.getMessage() + " : ");
            return;
        }

        compareClassRecursive(card1, card2, card1, "[Card", 10, new HashSet<>(), true);
    }

    /**
     * @param obj1           first object to compare. Initially the original card.
     * @param obj2           second object to compare. Initially the copy of the original card.
     * @param originalCard   the original card, used to print a nice message on fail.
     * @param msg            the recursively built message to explain what is different.
     * @param maxDepth       the maximum allowed recursion. A safety mesure for the test to end.
     * @param alreadyChecked Map of all Cards obj1 already compared.
     * @param useRecursive   When false, do not recursively compare Cards.
     */
    private void compareClassRecursive(Object obj1, Object obj2, Card originalCard, String msg, int maxDepth,
                                       Set<Card> alreadyChecked, boolean useRecursive) {
        if (obj1 == null && obj2 == null) {
            return;
        } else if (obj1 == null || obj2 == null) {
            fail(originalCard, "copy", "not same class for " + msg + "]");
        } else if (obj1.getClass() != obj2.getClass()) {
            fail(originalCard, "copy", "not same class for " + msg + "<" + obj1.getClass() + ">" + "]");
        } else if (obj1 == obj2) { // for instances mostly
            return;
        } else {
            // Only recurse so much.
            if (maxDepth == 0) {
                return;
            }
            // Only recurse on those objects
            if (obj1 instanceof MageObject || obj1 instanceof Filter || obj1 instanceof Condition || obj1 instanceof Effect
                    || obj1 instanceof Ability || obj1 instanceof Mana || obj1 instanceof Cost || obj1 instanceof DynamicValue
                    || obj1 instanceof Choice || obj1 instanceof TargetPointer) {

                boolean doRecurse = useRecursive;
                if (obj1 instanceof Card) {
                    if (alreadyChecked.contains(obj1)) {
                        if (!doRecurse) {
                            return; // we already checked that obj1 and do not want to recurse. stop there.
                        } else {
                            doRecurse = false;
                        }
                    } else {
                        alreadyChecked.add((Card) obj1);
                    }
                }

                //System.out.println(msg);
                Class class1 = obj1.getClass();
                Class class2 = obj2.getClass();
                do {
                    if (class1 == null && class2 == null) {
                        return;
                    }
                    if (class1 == null || class2 == null) {
                        fail(originalCard, "copy", "not same class for " + msg + "<" + obj1.getClass() + ">" + "]");
                        return;
                    }
                    if (class1.equals(ArrayList.class)) {
                        List list1 = (ArrayList) obj1;
                        List list2 = (ArrayList) obj2;
                        Iterator it1 = list1.iterator();
                        Iterator it2 = list2.iterator();
                        int i = 0;
                        while (it1.hasNext() && it2.hasNext()) {
                            compareClassRecursive(it1.next(), it2.next(), originalCard, msg + "<" + obj1.getClass() + ">" + "[" + i++ + "]", maxDepth - 1, alreadyChecked, useRecursive);
                        }
                        if (it1.hasNext() || it2.hasNext()) {
                            fail(originalCard, "copy", "not same size for (ArrayList) " + msg + "]");
                        }
                        return;
                    }


                    List<Field> ability2Fields = Arrays.stream(class2.getDeclaredFields()).collect(Collectors.toList());

                    // Special fields for CardImpl.class
                    boolean hasSpellAbilityField = false;
                    boolean hasMeldField = false;
                    boolean hasSecondSideCardField = false;
                    // Special fields for AbilityImpl.class
                    boolean hasWatchersField = false;
                    boolean hasModesField = false;

                    int fieldIndex = 0;
                    for (Field field1 : class1.getDeclaredFields()) {
                        Field field2 = ability2Fields.get(fieldIndex);

                        field1.setAccessible(true);
                        field2.setAccessible(true);
                        try {
                            Object value1 = field1.get(obj1);
                            Object value2 = field2.get(obj2);

                            boolean doFieldRecurse = true;
                            if (class1 == CardImpl.class) {
                                if (field1.getName().equals("spellAbility")) {
                                    compareClassRecursive(((CardImpl) obj1).getSpellAbility(), ((CardImpl) obj2).getSpellAbility(), originalCard, msg + "<" + obj1.getClass() + ">" + "::" + field1.getName(), maxDepth - 1, alreadyChecked, doRecurse);
                                    doFieldRecurse = false;
                                    hasSpellAbilityField = true;
                                } else if (field1.getName().equals("meldsToCard")) {
                                    compareClassRecursive(((CardImpl) obj1).getMeldsToCard(), ((CardImpl) obj2).getMeldsToCard(), originalCard, msg + "::" + field1.getName(), maxDepth - 1, alreadyChecked, doRecurse);
                                    doFieldRecurse = false;
                                    hasMeldField = true;
                                } else if (field1.getName().equals("secondSideCard")) {
                                    compareClassRecursive(((CardImpl) obj1).getSecondCardFace(), ((CardImpl) obj2).getSecondCardFace(), originalCard, msg + "::" + field1.getName(), maxDepth - 1, alreadyChecked, doRecurse);
                                    doFieldRecurse = false;
                                    hasSecondSideCardField = true;
                                }
                            }
                            if (class1 == AbilityImpl.class) {
                                if (field1.getName().equals("watchers")) {
                                    // Watchers are only used on initialization, they are not copied.
                                    doFieldRecurse = false;
                                    hasWatchersField = true;
                                }
                                if (field1.getName().equals("modes")) {
                                    //compareClassRecursive(((AbilityImpl) obj1).getModes(), ((AbilityImpl) obj2).getModes(), originalCard, msg + "<" + obj1.getClass() + ">" + "::" + field1.getName(), maxDepth - 1);
                                    compareClassRecursive(((AbilityImpl) obj1).getEffects(), ((AbilityImpl) obj2).getEffects(), originalCard, msg + "<" + obj1.getClass() + ">" + "::" + field1.getName(), maxDepth - 1, alreadyChecked, doRecurse);
                                    doFieldRecurse = false;
                                    hasModesField = true;
                                }
                            }
                            if (doFieldRecurse) {
                                compareClassRecursive(value1, value2, originalCard, msg + "<" + obj1.getClass() + ">" + "::" + field1.getName(), maxDepth - 1, alreadyChecked, doRecurse);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                        }
                        fieldIndex++;
                    }

                    // Do check that the expected special fields were encountered.
                    // If those field are no relevant anymore, or were renamed, please modify the matching code
                    // block above on how to loop into those fields.
                    if (class1 == CardImpl.class) {
                        if (!hasSpellAbilityField) {
                            fail(originalCard, "copy", "was expecting a spellAbility field, but found none " + msg + "]");
                        }
                        if (!hasMeldField) {
                            fail(originalCard, "copy", "was expecting a meldsToCard field, but found none " + msg + "]");
                        }
                        if (!hasSecondSideCardField) {
                            fail(originalCard, "copy", "was expecting a secondSideCard field, but found none " + msg + "]");
                        }
                    } else if (class1 == AbilityImpl.class) {
                        if (!hasWatchersField) {
                            fail(originalCard, "copy", "was expecting a watchers field, but found none " + msg + "]");
                        }
                        if (!hasModesField) {
                            fail(originalCard, "copy", "was expecting a modes field, but found none " + msg + "]");
                        }
                    }

                    class1 = class1.getSuperclass();
                    class2 = class2.getSuperclass();
                } while (class1 != Object.class && class1 != null);
            } else if (obj1 instanceof Collection) {
                Collection col1 = (Collection) obj1;
                Collection col2 = (Collection) obj2;
                Iterator it1 = col1.iterator();
                Iterator it2 = col2.iterator();
                int i = 0;
                while (it1.hasNext() && it2.hasNext()) {
                    compareClassRecursive(it1.next(), it2.next(), originalCard, msg + "<" + obj1.getClass() + ">" + "[" + i++ + "]", maxDepth - 1, alreadyChecked, useRecursive);
                }
                if (it1.hasNext() || it2.hasNext()) {
                    fail(originalCard, "copy", "not same size for (Collection) " + msg + "]");
                }
            } else if (obj1 instanceof Map) {
                Map map1 = (Map) obj1;
                Map map2 = (Map) obj2;
                map1.forEach((i, el1) -> {
                    compareClassRecursive(el1, ((Map<?, ?>) obj2).get(i), originalCard, msg + "<" + obj1.getClass() + ">" + ".(" + i + ")", maxDepth - 1, alreadyChecked, useRecursive);
                });
                if (map1.size() != map2.size()) {
                    fail(originalCard, "copy", "not same size for (Map) " + msg + "]");
                }
            }
        }
    }

    private void checkSubtypes(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_SUBTYPE, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        List<String> expected = new ArrayList<>(ref.subtypes);

        // fix names (e.g. Urza’s to Urza's)
        for (ListIterator<String> it = expected.listIterator(); it.hasNext(); ) {
            switch (it.next()) {
                case "Urza’s":
                    it.set("Urza's");
                    break;
                case "C’tan":
                    it.set("C'tan");
            }
        }

        if (expected.contains("Time") && expected.contains("Lord")) {
            expected.remove("Time");
            expected.remove("Lord");
            expected.add("Time Lord");
        }

        // Remove subtypes that need to be ignored
        Collection<String> actual = card
                .getSubtype()
                .stream()
                .map(SubType::toString)
                .collect(Collectors.toSet());

        actual.removeIf(subtypesToIgnore::contains);
        expected.removeIf(subtypesToIgnore::contains);

        for (SubType subType : card.getSubtype()) {
            if (!subType.isCustomSet() && !subType.canGain(card)) {
                String cardTypeString = card
                        .getCardType()
                        .stream()
                        .map(CardType::toString)
                        .reduce((a, b) -> a + " " + b)
                        .orElse("");
                fail(card, "subtypes", "card has subtype "
                        + subType.getDescription() + " (" + subType.getSubTypeSet() + ')'
                        + " that doesn't match its card type(s) (" + cardTypeString + ')');
            }
        }

        if (!eqSet(actual, expected)) {
            fail(card, "subtypes", actual + " != " + expected);
        }
    }

    private void checkSupertypes(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_SUPERTYPE, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        Collection<String> expected = ref.supertypes;
        if (!eqSet(card.getSuperType().stream().map(s -> s.toString()).collect(Collectors.toList()), expected)) {
            fail(card, "supertypes", card.getSuperType() + " != " + expected);
        }
    }

    private void checkMissingAbilities(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_MISSING_ABILITIES, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        // search missing abilities from card source
        if (ref.text == null || ref.text.isEmpty()) {
            return;
        }

        String refLowerText = ref.text.toLowerCase(Locale.ENGLISH);

        // special check: kicker ability must be in rules
        if (card.getAbilities().containsClass(MultikickerAbility.class) && card.getRules().stream().noneMatch(rule -> rule.contains("Multikicker"))) {
            fail(card, "abilities", "card have Multikicker ability, but missing it in rules text");
        }

        // special check: Auras need to have enchant ability added
        if (card.hasSubtype(SubType.AURA, null) && !card.getAbilities().containsClass(EnchantAbility.class)) {
            fail(card, "abilities", "card is an Aura but is missing this.addAbility(EnchantAbility)");
        }

        // special check: Sagas need to have saga ability added
        if (card.hasSubtype(SubType.SAGA, null) && !card.getAbilities().containsClass(SagaAbility.class)) {
            fail(card, "abilities", "card is a Saga but is missing this.addAbility(SagaAbility)");
        }

        // special check: backup ability should be set up correctly
        if (card.getAbilities().containsClass(BackupAbility.class) && CardUtil.castStream(card.getAbilities().stream(), BackupAbility.class).noneMatch(BackupAbility::hasAbilities)) {
            fail(card, "abilities", "card has backup but is missing this.addAbility(backupAbility)");
        }

        // special check: Werewolves front ability should only be on front and vice versa
        if (card.getAbilities().containsClass(WerewolfFrontTriggeredAbility.class) && card.isNightCard()) {
            fail(card, "abilities", "card is a back face werewolf with a front face ability");
        }
        if (card.getAbilities().containsClass(WerewolfBackTriggeredAbility.class) && !card.isNightCard()) {
            fail(card, "abilities", "card is a front face werewolf with a back face ability");
        }

        // special check: transform ability in TDFC should only be on front and vice versa
        if (card.getSecondCardFace() != null && !card.isNightCard() && !card.getAbilities().containsClass(TransformAbility.class)) {
            fail(card, "abilities", "double-faced cards should have transform ability on the front");
        }
        if (card.getSecondCardFace() != null && card.isNightCard() && card.getAbilities().containsClass(TransformAbility.class)) {
            fail(card, "abilities", "double-faced cards should not have transform ability on the back");
        }

        // special check: back side in TDFC must be only night card
        if (card.getSecondCardFace() != null && !card.getSecondCardFace().isNightCard()) {
            fail(card, "abilities", "the back face of a double-faced card should be nightCard = true");
        }

        // special check: siege ability must be used in double faced cards only
        if (card.getAbilities().containsClass(SiegeAbility.class) && card.getSecondCardFace() == null) {
            fail(card, "abilities", "miss second side settings in card with siege ability");
        }

        // special check: legendary spells need to have legendary spell ability
        if (card.isLegendary() && !card.isPermanent() && !card.getAbilities().containsClass(LegendarySpellAbility.class)) {
            fail(card, "abilities", "legendary nonpermanent cards need to have LegendarySpellAbility");
        }

        if (card.getAbilities().containsClass(MutateAbility.class)) {
            fail(card, "abilities", "mutate cards aren't implemented and shouldn't be available");
        }

        // special check: duplicated words in ability text (wrong target/filter usage)
        // example: You may exile __two two__ blue cards
        // possible fixes:
        //  - remove numbers from filter's text
        //  - use target.getDescription() in ability instead target.getTargetName()
        for (String rule : card.getRules()) {
            for (String doubleNumber : doubleWords) {
                if (rule.toLowerCase(Locale.ENGLISH).contains(doubleNumber)) {
                    fail(card, "abilities", "duplicated numbers/words: " + rule);
                }
            }
        }

        // special check: wrong targeted ability
        // possible fixes:
        //  * on "must set withNotTarget(true)":
        //    - check card's ability constructors and fix missing withNotTarget(true) param/field
        //    - it's can be a keyword action (only mtg rules contains a target word), so add it to the targetedKeywords
        // * on "must be targeted":
        //    - TODO: enable and research checkMissTargeted - too much errors with it (is it possible to use that checks?)
        boolean checkMissNonTargeted = true; // must set withNotTarget(true)
        boolean checkMissTargeted = false; // must be targeted
        List<String> targetedKeywords = Arrays.asList(
                "target",
                "enchant",
                "equip",
                "backup",
                "modular",
                "partner"
        );
        // card can contain rules text from both sides, so must search ref card for all sides too
        String additionalName;
        if (card instanceof AdventureCard) {
            additionalName = ((AdventureCard) card).getSpellCard().getName();
        } else if (card.isTransformable() && !card.isNightCard()) {
            additionalName = card.getSecondCardFace().getName();
        } else {
            additionalName = null;
        }
        if (additionalName != null) {
            MtgJsonCard additionalRef = MtgJsonService.cardFromSet(card.getExpansionSetCode(), additionalName, card.getCardNumber());
            if (additionalRef == null) {
                // how-to fix: add new card type processing for an additionalName searching above
                fail(card, "abilities", "can't find second side info for target check");
            } else {
                if (additionalRef.text != null && !additionalRef.text.isEmpty()) {
                    refLowerText += "\r\n" + additionalRef.text.toLowerCase(Locale.ENGLISH);
                }
            }
        }
        boolean needTargetedAbility = targetedKeywords.stream().anyMatch(refLowerText::contains);
        boolean foundTargetedAbility = card.getAbilities()
                .stream()
                .map(Ability::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(target -> !target.isNotTarget());
        boolean foundProblem = needTargetedAbility != foundTargetedAbility;
        if (checkMissTargeted && needTargetedAbility && foundProblem) {
            fail(card, "abilities", "wrong target settings (must be targeted, but it not)");
        }
        if (checkMissNonTargeted && !needTargetedAbility && foundProblem) {
            fail(card, "abilities", "wrong target settings (must set withNotTarget(true), but it not)");
        }

        // special check: missing or wrong ability/effect rules hint
        Map<Class, String> ruleHints = new HashMap<>();
        ruleHints.put(FightTargetsEffect.class, "Each deals damage equal to its power to the other");
        ruleHints.put(MenaceAbility.class, "can't be blocked except by two or more");
        ruleHints.put(ScryEffect.class, "Look at the top card of your library. You may put that card on the bottom");
        ruleHints.put(EquipAbility.class, "Equip only as a sorcery.");
        ruleHints.put(WardAbility.class, "becomes the target of a spell or ability an opponent controls");
        ruleHints.put(ProliferateEffect.class, "Choose any number of permanents and/or players, then give each another counter of each kind already there.");
        for (Class objectClass : ruleHints.keySet()) {
            String needText = ruleHints.get(objectClass);
            // ability/effect must have description or not
            boolean needHint = ref.text.contains(needText);
            boolean haveHint = card.getRules().stream().anyMatch(rule -> rule.contains(needText));
            if (needHint != haveHint) {
                warn(card, "card have " + objectClass.getSimpleName() + " but hint is wrong (it must be " + (needHint ? "enabled" : "disabled") + ")");
            }
        }

        // special check: missing card hints like designation
        Map<Class, String> cardHints = new HashMap<>();
        cardHints.put(CitysBlessingHint.class, "city's blessing");
        cardHints.put(MonarchHint.class, "the monarch");
        cardHints.put(InitiativeHint.class, "the initiative");
        for (Class hintClass : cardHints.keySet()) {
            String lookupText = cardHints.get(hintClass);
            boolean needHint = ref.text.contains(lookupText);
            if (needHint) {
                boolean haveHint = card.getAbilities()
                        .stream()
                        .flatMap(ability -> ability.getHints().stream())
                        .anyMatch(h -> h.getClass().equals(hintClass));
                if (!haveHint) {
                    fail(card, "abilities", "miss card hint: " + hintClass.getSimpleName());
                }
            }
        }

        // special check: equip abilities must have controlled predicate due rules
        List<EquipAbility> equipAbilities = card.getAbilities()
                .stream()
                .filter(a -> a instanceof EquipAbility)
                .map(a -> (EquipAbility) a)
                .collect(Collectors.toList());
        equipAbilities.forEach(a -> {
            List<Predicate> allPredicates = new ArrayList<>();
            a.getTargets().forEach(t -> Predicates.collectAllComponents(t.getFilter().getPredicates(), t.getFilter().getExtraPredicates(), allPredicates));
            boolean hasControlledFilter = allPredicates
                    .stream()
                    .filter(p -> p instanceof TargetController.ControllerPredicate)
                    .map(p -> ((TargetController.ControllerPredicate) p).getController())
                    .anyMatch(tc -> tc.equals(TargetController.YOU));
            if (!hasControlledFilter) {
                fail(card, "abilities", "card has equip ability, but it doesn't use controllered filter - " + a.getRule());
            }
        });

        // spells have only 1 ability
        if (card.isInstantOrSorcery()) {
            return;
        }

        // additional cost go to 1 ability
        if (refLowerText.startsWith("as an additional cost to cast")) {
            return;
        }

        // must have 1+ abilities all the time (to cast)
        if (card.getAbilities().toArray().length <= 1) { // all cards have 1 inner ability to cast
            fail(card, "abilities", "card's abilities is empty, but ref have text");
        }
    }

    private static final String[] wrongSymbols = {"’", "“", "”"};

    private void checkWrongSymbolsInRules(Card card) {
        for (String s : wrongSymbols) {
            if (card.getName().contains(s)) {
                fail(card, "card name", "card's name contains restricted symbol " + s);
            }
        }

        for (String rule : card.getRules()) {
            for (String s : wrongSymbols) {
                if (rule.contains(s)) {
                    fail(card, "rules", "card's rules contains restricted symbol " + s);
                }
            }
            if (rule.contains("&mdash ")) {
                fail(card, "rules", "card's rules contains restricted test [&mdash ] instead [&mdash;]");
            }
        }
    }

    private void checkLegalityFormats(Card card, MtgJsonCard ref) {
        if (skipListHaveName("LEGALITY", card.getExpansionSetCode(), card.getName())) {
            return;
        }

        // TODO: add legality checks (by sets and cards, by banned)
    }

    private String prepareRule(String cardName, String rule) {
        // remove and optimize rule text for analyze
        String newRule = rule;

        // remove reminder text
        newRule = newRule.replaceAll("(?i) <i>\\(.+\\)</i>", "");
        newRule = newRule.replaceAll("(?i) \\(.+\\)", "");

        // fix specifically for mana abilities
        if (newRule.startsWith("({T}: Add")) {
            newRule = newRule
                    .replace("(", "")
                    .replace(")", "");
        }

        // replace special text and symbols
        newRule = newRule
                .replace("{this}", cardName)
                .replace("−", "-")
                .replace("—", "-")
                .replace("&mdash;", "-");

        // remove html marks
        newRule = newRule
                .replace("<i>", "")
                .replace("</i>", "");

        newRule = CardNameUtil.normalizeCardName(newRule);

        return newRule.trim();
    }

    @Test
    public void test_showCardInfo() {
        // debug only: show direct card rules from a class file without db-recreate
        //  - search by card name: Spark Double
        //  - search by class name: SparkDouble
        //  - multiple searches: name1;class2;name3
        String cardSearches = "Spark Double;AbandonedSarcophagus";

        // prepare DBs
        CardScanner.scan();
        MtgJsonService.cards();

        Arrays.stream(cardSearches.split(";")).forEach(searchName -> {
            // original card
            searchName = searchName.trim();
            CardInfo cardInfo = CardRepository.instance.findCard(searchName);
            if (cardInfo == null) {
                String searchClass = String.format("mage.cards.%s.%s",
                        searchName.substring(0, 1).toLowerCase(Locale.ENGLISH),
                        searchName);
                cardInfo = CardRepository.instance.findCardsByClass(searchClass)
                        .stream()
                        .findFirst()
                        .orElse(null);
            }
            if (cardInfo == null) {
                Assert.fail("Can't find card by name or class: " + searchName);
            }
            CardSetInfo testSet = new CardSetInfo(cardInfo.getName(), "test", "123", Rarity.COMMON);
            Card card = CardImpl.createCard(cardInfo.getClassName(), testSet);

            System.out.println();
            System.out.println(card.getName() + " " + card.getManaCost().getText());
            if (card instanceof SplitCard || card instanceof ModalDoubleFacedCard) {
                card.getAbilities().getRules(card.getName()).forEach(this::printAbilityText);
            } else {
                card.getRules().forEach(this::printAbilityText);
            }

            // ref card
            System.out.println();
            MtgJsonCard ref = MtgJsonService.card(card.getName());
            if (ref != null) {
                System.out.println("ref: " + ref.getNameAsFace() + " " + ref.manaCost);
                System.out.println(ref.text);
            } else {
                System.out.println("WARNING, can't find mtgjson ref for " + card.getName());
            }
        });
    }

    private void printAbilityText(String text) {
        text = text.replace("<br>", "\n");
        System.out.println(text);
    }


    /*
        for(String rule : card.getRules()) {
            rule = rule.replaceAll("(?i)<i>.+</i>", ""); // Ignoring reminder text in italic
            // TODO: add Equip {3} checks
            // TODO: add Raid and other words checks
            String[] sl = rule.split(":");
            if (sl.length == 2 && !sl[0].isEmpty()) {
                String cardCost = sl[0]
                        .replace("{this}", card.getName())
                        //.replace("<i>", "")
                        //.replace("</i>", "")
                        .replace("&mdash;", "—");
                String cardAbility = sl[1]
                        .trim()
                        .replace("{this}", card.getName())
                        //.replace("<i>", "")
                        //.replace("</i>", "")
                        .replace("&mdash;", "—");

                boolean found = false;
                for (String refRule : refRules) {
                    refRule = refRule.replaceAll("(?i)<i>.+</i>", ""); // Ignoring reminder text in italic

                    // fix for ref mana: ref card have xxx instead {T}: Add {xxx}, example: W
                    if (refRule.length() == 1) {
                        refRule = "{T}: Add {" + refRule + "}";
                    }
                    refRule = refRule
                            .trim()
                            //.replace("<i>", "")
                            //.replace("</i>", "")
                            .replace("&mdash;", "—");

                    // normal
                    if (refRule.startsWith(cardCost)) {
                        found = true;
                        break;
                    }

                    // ref card have (xxx) instead xxx, example: ({T}: Add {G}.)
                    // ref card have <i>(xxx) instead xxx, example: <i>({T}: Add {G}.)</i>
                    // TODO: delete?
                    if (refRule.startsWith("(" + cardCost)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    fail(card, "abilities", "card ability have cost, but can't find in ref [" + cardCost + ": " + cardAbility + "]");
                }
            }

        }
    }*/
    private static boolean compareText(String cardText, String refText, String name) {
        return cardText.equals(refText)
                || cardText.replace(name, name.split(", ")[0])
                .equals(refText.replace(name, name.split(", ")[0]))
                || cardText.replace(name, name.split(" ")[0])
                .equals(refText.replace(name, name.split(" ")[0]));
    }

    private static boolean checkForEffect(Card card, Class<? extends Effect> effectClazz) {
        return card.getAbilities()
                .stream()
                .map(Ability::getModes)
                .map(LinkedHashMap::values)
                .flatMap(Collection::stream)
                .map(Mode::getEffects)
                .flatMap(Collection::stream)
                .anyMatch(effectClazz::isInstance);
    }

    private void checkWrongAbilitiesTextStart() {
        System.out.println("Ability text checks started for " + FULL_ABILITIES_CHECK_SET_CODES);
        wrongAbilityStatsTotal = 0;
        wrongAbilityStatsGood = 0;
        wrongAbilityStatsBad = 0;
    }

    private void checkWrongAbilitiesTextEnd() {
        // TODO: implement tests result/stats by github actions to show in check message compared to prev version
        System.out.println(String.format(""));
        System.out.println(String.format("Stats for %d cards checked for abilities text:", wrongAbilityStatsTotal));
        System.out.println(String.format(" - Cards with correct text:  %5d (%.2f)", wrongAbilityStatsGood, wrongAbilityStatsGood * 100.0 / wrongAbilityStatsTotal));
        System.out.println(String.format(" - Cards with text errors:   %5d (%.2f)", wrongAbilityStatsBad, wrongAbilityStatsBad * 100.0 / wrongAbilityStatsTotal));
        System.out.println(String.format(""));
    }

    private void checkWrongAbilitiesText(Card card, MtgJsonCard ref, int cardIndex) {
        // checks missing or wrong text
        if (!FULL_ABILITIES_CHECK_SET_CODES.equals("*") && !FULL_ABILITIES_CHECK_SET_CODES.contains(card.getExpansionSetCode())) {
            return;
        }
        if (wasCheckedByAbilityText(ref)) {
            return;
        }

        if (ref.text == null || ref.text.isEmpty()) {
            return;
        }

        wrongAbilityStatsTotal++;
        String refText = ref.text;
        // planeswalker fix [-7]: xxx
        refText = refText.replaceAll("\\[([\\−\\+]?\\d*)\\]\\: ", "$1: ").replaceAll("\\[\\−X\\]\\: ", "-X: ");

        // evergreen keyword fix
        for (String s : refText.replaceAll(" \\(.+?\\)", "").split("[\\$\\\n]")) {
            if (Arrays
                    .stream(s.split("[,;] "))
                    .map(String::toLowerCase)
                    .allMatch(VerifyCardDataTest::evergreenCheck)) {
                String replacement = Arrays
                        .stream(s.split("[,;] "))
                        .map(CardUtil::getTextWithFirstCharUpperCase)
                        .collect(Collectors.joining("\n"));
                refText = refText.replace(s, replacement);
            }
        }
        // modal spell fix
        if (refText.contains("•")) {
            refText = refText
                    .replace("—\n•", " —\n•")
                    .replace("  —", " —")
                    .replace("—\n•", "-<br>&bull ")
                    .replace("\n•", "<br>&bull ");
            refText += "<br>";
            refText = refText.replace("<br>", "\n");
        }

        // remove unnecessary reminder text
        refText = refText.replaceAll("^\\(.+(can be paid with|ransforms from|represents).+\\)\n", "");

        // mana ability fix
        // Current implementation makes one Activated Ability per kind of color.
        // We split such abilities in the reference text.
        // For instance "{T}: Add {G} or {W}."
        //      becomes "{T}: Add {G}.\n{T}: Add {W}."
        //
        // The regex down handle more complex situations.
        refText = splitManaAbilities(refText);

        // cycling fix
        // Current implementation makes one CyclingAbility per quality,
        // We split such abilities in the reference text.
        //
        // For instance "Swampcycling {2}, mountaincycling {2}"
        //      becomes "Swampcycling {2}\nMountaincycling {2}"
        refText = splitCyclingAbilities(refText);

        String[] refRules = refText.split("[\\$\\\n]"); // ref card's abilities can be splited by \n or $ chars
        for (int i = 0; i < refRules.length; i++) {
            refRules[i] = prepareRule(card.getName(), refRules[i]);
        }

        if (ref.subtypes.contains("Adventure")) {
            for (int i = 0; i < refRules.length; i++) {
                refRules[i] = new StringBuilder("Adventure ")
                        .append(ref.types.get(0))
                        .append(" - ")
                        .append(ref.faceName)
                        .append(' ')
                        .append(ref.manaCost)
                        .append(" - ")
                        .append(refRules[i])
                        .toString();
            }
        }


        String[] cardRules = card
                .getRules()
                .stream()
                .filter(s -> !(card instanceof AdventureCard) || !s.startsWith("Adventure "))
                .collect(Collectors.joining("\n"))
                .replace("<br>", "\n")
                .replace("<br/>", "\n")
                .replace("<b>", "")
                .replace("</b>", "")
                .split("[\\$\\\n]");
        for (int i = 0; i < cardRules.length; i++) {
            cardRules[i] = prepareRule(card.getName(), cardRules[i]);
        }

        boolean isFine = true;
        for (int i = 0; i < cardRules.length; i++) {
            if (cardRules[i].startsWith("Use the Special button")) {
                // This is a rules text for GUI implication like Quenchable Fire
                cardRules[i] = "+ " + cardRules[i];
                continue;
            }

            boolean isAbilityFounded = false;
            for (int j = 0; j < refRules.length; j++) {
                String refRule = refRules[j];
                if (compareText(cardRules[i], refRule, card.getName())) {
                    cardRules[i] = "+ " + cardRules[i];
                    refRules[j] = "+ " + refRules[j];
                    isAbilityFounded = true;
                    break;
                }
            }

            if (!isAbilityFounded && cardRules[i].length() > 0) {
                isFine = false;
                if (!CHECK_ONLY_ABILITIES_TEXT) {
                    warn(card, "card ability can't be found in ref [" + card.getName() + ": " + cardRules[i] + "]");
                }
                cardRules[i] = "- " + cardRules[i];
            }
        }

        // mark ref rules as unknown
        for (int j = 0; j <= refRules.length - 1; j++) {
            String refRule = refRules[j];
            if (!refRule.startsWith("+ ")) {
                isFine = false;
                refRules[j] = "- " + refRules[j];
            }
        }

        if (isFine) {
            wrongAbilityStatsGood++;
        } else {
            wrongAbilityStatsBad++;
        }

        // extra message for easy checks
        if (!isFine) {
            System.out.println();

            System.out.println("Wrong card " + cardIndex + ": " + card.getName());
            Arrays.sort(cardRules);
            for (String s : cardRules) {
                System.out.println(s);
            }

            System.out.println("ref:");
            Arrays.sort(refRules);
            for (String s : refRules) {
                System.out.println(s);
            }

            System.out.println();
        }
    }

    private String splitCyclingAbilities(String refText) {
        for (String s : refText.split("[\\$\\\n]")) {
            if (!Pattern.matches("^[a-zA-Z]*cycling .*, [a-zA-Z]*cycling.*", s)) {
                continue;
            }
            String newStr = "";
            Pattern p = Pattern.compile(", [a-zA-Z]*cycling");
            Matcher m = p.matcher(s);
            int start = 0;
            while (m.find()) {
                String group = m.group();
                int newStart = m.start();
                newStr += s.substring(start, newStart) + "\n" + group.substring(2, 3).toUpperCase() + group.substring(3);
                start = newStart + group.length();
            }
            newStr += s.substring(start);
            refText = refText.replace(s, newStr);
        }
        return refText;
    }

    @Test
    public void checkSplitCyclingAbilities() {
        // Test the function splitting cycling abilities is correct.

        Assert.assertEquals(
                "Swampcycling {2}\nMountaincycling {2}",
                splitCyclingAbilities("Swampcycling {2}, mountaincycling {2}")
        );
    }

    private String splitManaAbilities(String refText) {
        for (String s : refText.split("[\\$\\\n]")) {
            if (!Pattern.matches("[^\"']*: Add [^\\.]* or.*\\..*", s)) {
                continue;
            }
            // Loyalty Abilities
            if (s.startsWith("0") || s.startsWith("+") || s.startsWith("-")) {
                continue;
            }
            // Leafkin Avenger
            if (s.contains("} for")) {
                continue;
            }

            // Splitting the ability into three segments:
            //
            // {G/W}, {T}: Add {G}{G}, {G}{W}, or {W}{W}. This mana can only be used to cast multicolor spells.
            // ^^^^^^^^^^^^^^^^                         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            //     before      ^^^^^^^^^^^^^^^^^^^^^^^^^                    after
            //                       middle
            int beforeLength = s.indexOf(": Add ");
            String before = s.substring(0, beforeLength + 6);
            int middleIndex = s.indexOf('.', beforeLength);
            String middle = s.substring(beforeLength + 6, middleIndex);
            String after = s.substring(middleIndex);

            //making life easier on the split
            middle = middle
                    .replace(", or ", "|")
                    .replace(" or ", "|")
                    .replace(", ", "|");

            // This now looks like "{G}{G}|{G}{W}|{W}{W}".
            // for each part, make a new line with 'before + part + end'
            String newStr = "";
            for (String part : middle.split("[|]")) {
                newStr += before + part + after + "\n";
            }
            if (!newStr.isEmpty()) {
                newStr = newStr.substring(0, newStr.length() - 1);
            }
            refText = refText.replace(s, newStr);
        }
        return refText;
    }

    @Test
    public void checkSplitManaAbilities() {
        // Test the function splitting mana abilities is correct.

        Assert.assertEquals(
                "{T}: Add {G}.\n{T}: Add {W}.",
                splitManaAbilities("{T}: Add {G} or {W}.")
        );
        Assert.assertEquals(
                "{T}: Add {G}.\n{T}: Add {W}.\n{T}: Add {R}.",
                splitManaAbilities("{T}: Add {G}, {W}, or {R}.")
        );
        Assert.assertEquals(
                "{G/W}, {T}: Add {G}{G}.\n{G/W}, {T}: Add {G}{W}.\n{G/W}, {T}: Add {W}{W}.",
                splitManaAbilities("{G/W}, {T}: Add {G}{G}, {G}{W}, or {W}{W}.")
        );
        Assert.assertEquals(
                "{T}: Add {R}.\n{T}: Add one mana of the chosen color.",
                splitManaAbilities("{T}: Add {R} or one mana of the chosen color.")
        );
        Assert.assertEquals(
                "{T}: Add {B}. Activate only if you control a swamp.\n{T}: Add {U}. Activate only if you control a swamp.",
                splitManaAbilities("{T}: Add {B} or {U}. Activate only if you control a swamp.")
        );


        // Not splitting those:
        Assert.assertEquals(
                "{T}: Each player creates a colorless artifact token named Banana with \"{T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life.\"",
                splitManaAbilities("{T}: Each player creates a colorless artifact token named Banana with \"{T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life.\"")
        );
        Assert.assertEquals(
                "+1: Add {R} or {G}. Creature spells you cast this turn can't be countered.",
                splitManaAbilities("+1: Add {R} or {G}. Creature spells you cast this turn can't be countered.")
        );
        Assert.assertEquals(
                "0: Add {R} or {G}. Creature spells you cast this turn can't be countered.",
                splitManaAbilities("0: Add {R} or {G}. Creature spells you cast this turn can't be countered.")
        );
        Assert.assertEquals(
                "-1: Add {R} or {G}. Creature spells you cast this turn can't be countered.",
                splitManaAbilities("-1: Add {R} or {G}. Creature spells you cast this turn can't be countered.")
        );
        Assert.assertEquals(
                "{T}: Add {G} for each creature with power 4 or greater you control.",
                splitManaAbilities("{T}: Add {G} for each creature with power 4 or greater you control.")
        );
    }

    private void checkTypes(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_TYPE, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        Collection<String> expected = ref.types;
        List<String> type = new ArrayList<>();
        for (CardType cardType : card.getCardType()) {
            type.add(cardType.toString());
        }
        if (!eqSet(type, expected)) {
            fail(card, "types", type + " != " + expected);
        }
    }

    private void checkPT(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_PT, card.getExpansionSetCode(), card.getName())) {
            return;
        }

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

    private void checkLoyalty(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_LOYALTY, card.getExpansionSetCode(), card.getName())) {
            return;
        }
        if (ref.loyalty == null) {
            if (card.getStartingLoyalty() == -1) {
                return;
            }
        } else if (ref.loyalty.equals("X")) {
            if (card.getStartingLoyalty() == -2) {
                return;
            }
        } else if (ref.loyalty.equals("" + card.getStartingLoyalty())) {
            return;
        }
        fail(card, "loyalty", card.getStartingLoyalty() + " != " + ref.loyalty);
    }

    private void checkDefense(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_DEFENSE, card.getExpansionSetCode(), card.getName())) {
            return;
        }
        if (ref.defense == null) {
            if (card.getStartingDefense() == -1) {
                return;
            }
        } else if (ref.defense.equals("X")) {
            if (card.getStartingDefense() == -2) {
                return;
            }
        } else if (ref.defense.equals("" + card.getStartingDefense())) {
            return;
        }
        fail(card, "defense", card.getStartingDefense() + " != " + ref.defense);
    }

    private void checkCost(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_COST, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        String expected = ref.manaCost;
        String cost = String.join("", card.getManaCostSymbols());
        if (cost.isEmpty()) {
            cost = null;
        }
        if (cost != null) {
            cost = cost.replaceAll("P\\}", "P}");
        }
        if (!Objects.equals(cost, expected)) {
            fail(card, "cost", cost + " != " + expected);
        }
    }

    private boolean isBasicLandName(String name) {

        String checkName = name;
        if (name.startsWith("Snow-Covered ")) {
            // snow lands is basic lands too
            checkName = name.replace("Snow-Covered ", "");
        }

        return checkName.equals("Island")
                || checkName.equals("Forest")
                || checkName.equals("Swamp")
                || checkName.equals("Plains")
                || checkName.equals("Mountain");
    }

    private boolean isNonSnowBasicLandName(String name) {
        return name.equals("Island")
                || name.equals("Forest")
                || name.equals("Swamp")
                || name.equals("Plains")
                || name.equals("Mountain");
    }

    private void checkRarityAndBasicLands(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_RARITY, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        // basic lands must have Rarity.LAND and SuperType.BASIC
        // other cards can't have that stats
        String name = card.getName();
        if (isBasicLandName(name)) {
            // lands
            if (card.getRarity() != Rarity.LAND) {
                fail(card, "rarity", "basic land must be Rarity.LAND");
            }

            if (!card.isBasic()) {
                fail(card, "supertype", "basic land must be SuperType.BASIC");
            }
        } else if (name.equals("Wastes")) {
            // Wastes are SuperType.BASIC but not necessarily Rarity.LAND
            if (!card.isBasic()) {
                fail(card, "supertype", "Wastes must be SuperType.BASIC");
            }
        } else {
            // non lands
            if (card.getRarity() == Rarity.LAND) {
                fail(card, "rarity", "only basic land can be Rarity.LAND");
            } else if (!card.getRarity().equals(ref.getRarity())) {
                fail(card, "rarity", "mismatched. MtgJson has " + ref.getRarity() + " while set file has " + card.getRarity());
            }

            if (card.isBasic()) {
                fail(card, "supertype", "only basic land can be SuperType.BASIC");
            }
        }
    }

    @Test
    public void test_checkCardRatingConsistency() {
        // all cards with same name must have same rating (see RateCard.rateCard)
        // cards rating must be consistency (same) for card sorting
        List<Card> cardsList = new ArrayList<>(CardScanner.getAllCards());
        Map<String, Integer> cardRates = new HashMap<>();
        for (Card card : cardsList) {
            int curRate = RateCard.rateCard(card, null, false);
            int prevRate = cardRates.getOrDefault(card.getName(), 0);
            if (prevRate == 0) {
                cardRates.putIfAbsent(card.getName(), curRate);
            } else {
                if (curRate != prevRate) {
                    Assert.fail("Card with same name have different ratings: " + card.getName());
                }
            }
        }
    }

    @Test
    public void test_checkCardConstructors() {
        // create all cards, can catch additional verify and runtime checks from abilities and effects
        // example: wrong code usage errors
        Collection<String> errorsList = new ArrayList<>();
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                try {
                    Card card = CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                            setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()));
                    if (card == null) {
                        errorsList.add("Error: broken constructor " + setInfo.getCardClass());
                        continue;
                    }
                    if (!card.getExpansionSetCode().equals(set.getCode())) {
                        errorsList.add("Error: card constructor have custom expansionSetCode, must be removed " + setInfo.getCardClass());
                    }
                } catch (Throwable e) {
                    // CardImpl.createCard don't throw exceptions (only error logs), so that logs are useless here
                    logger.error("Error: can't create card " + setInfo.getName() + ": " + e.getMessage(), e);
                }
            }
        }

        if (!errorsList.isEmpty()) {
            printMessages(errorsList);
            Assert.fail("Found " + errorsList.size() + " broken cards, look at logs above for more details");
        }
    }

    @Test
    public void test_checkCardsInCubes() {
        Reflections reflections = new Reflections("mage.tournament.cubes.");
        Set<Class<? extends DraftCube>> cubesList = reflections.getSubTypesOf(DraftCube.class);
        Assert.assertFalse("Can't find any cubes", cubesList.isEmpty());

        CardScanner.scan();
        Collection<String> errorsList = new ArrayList<>();
        for (Class<? extends DraftCube> cubeClass : cubesList) {
            // need drafts with fixed cards list (constructor with zero params)
            if (Arrays.stream(cubeClass.getConstructors()).noneMatch(c -> c.getParameterCount() == 0)) {
                continue;
            }

            DraftCube cube = (DraftCube) createNewObject(cubeClass);
            if (cube.getCubeCards().isEmpty()) {
                errorsList.add("Error: broken cube, empty cards list: " + cube.getClass().getCanonicalName());
            }

            for (DraftCube.CardIdentity cardId : cube.getCubeCards()) {
                // same find code as original cube
                CardInfo cardInfo;
                if (!cardId.getExtension().isEmpty()) {
                    cardInfo = CardRepository.instance.findCardWithPreferredSetAndNumber(cardId.getName(), cardId.getExtension(), null);
                } else {
                    cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(cardId.getName());
                }
                if (cardInfo == null) {
                    errorsList.add("Error: broken cube, can't find card: " + cube.getClass().getCanonicalName() + " - " + cardId.getName());
                }
            }
        }

        if (!errorsList.isEmpty()) {
            printMessages(errorsList);
            Assert.fail("Found " + errorsList.size() + " errors in the cubes, look at logs above for more details");
        }
    }

    @Test
    public void test_checkUnicodeCardNamesForImport() {
        // deck import can catch real card names with non-ascii symbols like Arwen Undómiel, so it must be able to process it

        // check unicode card
        MtgJsonCard card = MtgJsonService.cardFromSet("LTR", "Arwen Undomiel", "194");
        Assert.assertNotNull("test card must exists", card);
        Assert.assertTrue(card.isUseUnicodeName());
        Assert.assertEquals("Arwen Undomiel", card.getNameAsASCII());
        Assert.assertEquals("Arwen Undómiel", card.getNameAsUnicode());
        Assert.assertEquals("Arwen Undomiel", card.getNameAsFull());

        // mtga format can contain /// in the names, so check it too
        // see https://github.com/magefree/mage/pull/9855
        Assert.assertEquals("Dusk // Dawn", CardNameUtil.normalizeCardName("Dusk /// Dawn"));

        // check all converters
        Collection<String> errorsList = new ArrayList<>();
        MtgJsonService.sets().values().forEach(jsonSet -> {
            jsonSet.cards.forEach(jsonCard -> {
                if (jsonCard.isUseUnicodeName()) {
                    String inName = jsonCard.getNameAsUnicode();
                    String outName = CardNameUtil.normalizeCardName(inName);
                    String needOutName = jsonCard.getNameAsFace();
                    if (!outName.equals(needOutName)) {
                        // how-to fix: add new unicode symbol in CardNameUtil.normalizeCardName
                        errorsList.add(String.format("error, found unsupported unicode symbol in %s - %s", inName, jsonSet.code));
                    }
                }
            });
        });
        printMessages(errorsList);
        if (errorsList.size() > 0) {
            Assert.fail(String.format("Card name converters contains unsupported unicode symbols in %d cards, see logs above", errorsList.size()));
        }
    }

    /**
     * Not really a test. Used to make the changelog diff list
     * for sets that are heavily worked on.
     */
    @Ignore
    @Test
    public void list_ChangelogHelper() {

        String setCode = "LCC";
        int maxCards = 100; // don't want to look above that card number, as those are alternate prints.
        boolean doExclude = false; // use the excluded array or not.

        // Excluded in that list, either already listed in a previous changelog, or exceptions.
        Set<Integer> excluded = new HashSet<>(Arrays.asList(
                8, 9, 49, 71, 76, 79, 80, 89, 90, 92, 96, 97
        ));

        for (int i = 17; i <= 68; ++i) {
            excluded.add(i); // alternates version of the new cards.
        }

        /*
        String setCode = "LCI";
        int maxCards = 285; // don't want to look above that card number, as those are alternate prints.
        boolean doExclude = false; // use the excluded array or not.

        // Excluded in that list, either already listed in a previous changelog, or exceptions.
        Set<Integer> excluded = new HashSet<>(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 127, 128, 129, 130, 131, 132, 133, 134, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284
        ));
        */

        Set<Integer> listChangelog = new HashSet<>();
        List<ExpansionSet.SetCardInfo> setInfo = Sets.getInstance().get(setCode).getSetCardInfo();
        for (ExpansionSet.SetCardInfo sci : setInfo) {
            int cn = sci.getCardNumberAsInt();
            if (cn > maxCards) continue;
            if (doExclude && excluded.contains(cn)) continue;
            listChangelog.add(cn);
        }

        List<Integer> sorted = listChangelog.stream().sorted().collect(Collectors.toList());

        // to manual update the excluded array
        System.out.println(sorted.stream().map(cn -> cn + "").collect(Collectors.joining(", ")));
        // Scryfall list of all the new cards for that set
        System.out.println(
                "https://scryfall.com/search?q=s%3A" + setCode + "+-is%3Areprint+%28"
                        + sorted.stream().map(cn -> "cn%3A" + cn).collect(Collectors.joining("+or+"))
                        + "%29&order=set&as=grid&unique=cards"
        );
    }
}
