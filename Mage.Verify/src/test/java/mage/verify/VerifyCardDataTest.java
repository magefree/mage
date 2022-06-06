package mage.verify;

import com.google.common.base.CharMatcher;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.*;
import mage.cards.*;
import mage.cards.decks.DeckCardLists;
import mage.cards.decks.importer.DeckImporter;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.CardScanner;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.command.Dungeon;
import mage.game.command.Plane;
import mage.game.draft.DraftCube;
import mage.game.draft.RateCard;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.sets.TherosBeyondDeath;
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
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.images.DownloadPicturesService;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public class VerifyCardDataTest {

    private static final Logger logger = Logger.getLogger(VerifyCardDataTest.class);

    private static final String FULL_ABILITIES_CHECK_SET_CODE = "CLB"; // check all abilities and output cards with wrong abilities texts;
    private static final boolean AUTO_FIX_SAMPLE_DECKS = false; // debug only: auto-fix sample decks by test_checkSampleDecks test run
    private static final boolean ONLY_TEXT = false; // use when checking text locally, suppresses unnecessary checks and output messages

    private static final Set<String> checkedNames = new HashSet<>();
    private static final HashMap<String, Set<String>> skipCheckLists = new HashMap<>();
    private static final Set<String> subtypesToIgnore = new HashSet<>();
    private static final String SKIP_LIST_PT = "PT";
    private static final String SKIP_LIST_COLOR = "COLOR";
    private static final String SKIP_LIST_COST = "COST";
    private static final String SKIP_LIST_SUPERTYPE = "SUPERTYPE";
    private static final String SKIP_LIST_TYPE = "TYPE";
    private static final String SKIP_LIST_SUBTYPE = "SUBTYPE";
    private static final String SKIP_LIST_NUMBER = "NUMBER";
    private static final String SKIP_LIST_MISSING_ABILITIES = "MISSING_ABILITIES";
    private static final String SKIP_LIST_DOUBLE_RARE = "DOUBLE_RARE";
    private static final String SKIP_LIST_UNSUPPORTED_SETS = "UNSUPPORTED_SETS";
    private static final String SKIP_LIST_SCRYFALL_DOWNLOAD_SETS = "SCRYFALL_DOWNLOAD_SETS";
    private static final String SKIP_LIST_WRONG_CARD_NUMBERS = "WRONG_CARD_NUMBERS";
    private static final String SKIP_LIST_SAMPLE_DECKS = "SAMPLE_DECKS";
    private static final List<String> evergreenKeywords = Arrays.asList(
            "flying", "lifelink", "menace", "trample", "haste", "first strike", "hexproof", "fear",
            "deathtouch", "double strike", "indestructible", "reach", "flash", "defender", "vigilance",
            "plainswalk", "islandwalk", "swampwalk", "mountainwalk", "forestwalk", "myriad"
    );

    static {
        // skip lists for checks (example: unstable cards with same name may have different stats)
        // can be full set ignore list or set + cardname

        // power-toughness
        skipListCreate(SKIP_LIST_PT);

        // color
        skipListCreate(SKIP_LIST_COLOR);

        // cost
        skipListCreate(SKIP_LIST_COST);

        // supertype
        skipListCreate(SKIP_LIST_SUPERTYPE);

        // type
        skipListCreate(SKIP_LIST_TYPE);
        skipListAddName(SKIP_LIST_TYPE, "UNH", "Old Fogey"); // uses summon word as a joke card
        skipListAddName(SKIP_LIST_TYPE, "UND", "Old Fogey");
        skipListAddName(SKIP_LIST_TYPE, "UST", "capital offense"); // uses "instant" instead "Instant" as a joke card

        // subtype
        skipListCreate(SKIP_LIST_SUBTYPE);
        skipListAddName(SKIP_LIST_SUBTYPE, "UGL", "Miss Demeanor"); // uses multiple types as a joke card: Lady, of, Proper, Etiquette

        // number
        skipListCreate(SKIP_LIST_NUMBER);

        // missing abilities
        skipListCreate(SKIP_LIST_MISSING_ABILITIES);

        // double rare cards
        skipListCreate(SKIP_LIST_DOUBLE_RARE);

        // Un-supported sets (mtgjson/scryfall contains that set but xmage don't implement it)
        // Example: Non-English or empty sets: Token sets, Archenemy Schemes, Plane-Chase Planes, etc.
        skipListCreate(SKIP_LIST_UNSUPPORTED_SETS);
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
        // Commander Oversized cards.
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OCMD"); // Commander 2011 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC13"); // Commander 2013 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC14"); // Commander 2014 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC15"); // Commander 2015 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC16"); // Commander 2016 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC17"); // Commander 2017 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC18"); // Commander 2018 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OC19"); // Commander 2019 Oversized
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "OCM1"); // Commander's Arsenal Oversized
        //
        // Other
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PCEL"); // Celebration Cards
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PMOA"); // Magic Online Avatar
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PVAN"); // Vanguard Series
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "AMH1"); // Modern Horizons Art Series
        skipListAddName(SKIP_LIST_UNSUPPORTED_SETS, "PTG"); // Ponies: The Galloping

        // wrond card numbers skip list
        skipListCreate(SKIP_LIST_WRONG_CARD_NUMBERS);
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SWS"); // Star Wars
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "UND"); // un-sets don't have full implementation of card variations
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "UST"); // un-sets don't have full implementation of card variations
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SOI", "Tamiyo's Journal"); // not all variations implemented
        skipListAddName(SKIP_LIST_WRONG_CARD_NUMBERS, "SLD", "Zndrsplt, Eye of Wisdom"); // xmage adds additional card for alternative image (second side)


        // scryfall download sets (missing from scryfall website)
        skipListCreate(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS);
        skipListAddName(SKIP_LIST_SCRYFALL_DOWNLOAD_SETS, "SWS"); // Star Wars

        // sample decks checking - some decks can contains unimplemented cards, so ignore it
        // file name must be related to sample-decks folder
        // for linux/windows build system use paths constructor
        skipListCreate(SKIP_LIST_SAMPLE_DECKS);
        skipListAddName(SKIP_LIST_SAMPLE_DECKS, Paths.get("Jumpstart", "jumpstart_custom.txt").toString()); // it's not a deck file
        skipListAddName(SKIP_LIST_SAMPLE_DECKS, Paths.get("Commander", "Commander 2019", "Merciless Rage.dck").toString()); // TODO: delete after Aeon Engine implemented
    }

    private final ArrayList<String> outputMessages = new ArrayList<>();
    private int failed = 0;

    private static void skipListCreate(String listName) {
        skipCheckLists.put(listName, new LinkedHashSet<>());
    }

    private static void skipListAddName(String listName, String set, String cardName) {
        skipCheckLists.get(listName).add(set + " - " + cardName);
    }

    private static void skipListAddName(String listName, String set) {
        skipCheckLists.get(listName).add(set);
    }

    private static boolean skipListHaveName(String listName, String set, String cardName) {
        return skipCheckLists.get(listName).contains(set + " - " + cardName)
                || skipCheckLists.get(listName).contains(set);
    }

    private static boolean skipListHaveName(String listName, String set) {
        return skipCheckLists.get(listName).contains(set);
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
    public void test_verifyCards() throws IOException {
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

        printMessages(outputMessages);
        if (failed > 0) {
            Assert.fail("found " + failed + " errors in cards verify (see errors list above)");
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
                    String error = "Error: found non duplicated rare card -"
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
                            errorsList.add("Error: found wrong class in set " + set.getCode() + " - " + checkCard.getName() + " (" + currentClass + " <> " + needClass + ")");
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
                missingSets = missingSets + 1;
                missingCards = missingCards + refSet.cards.size();
                info.add("Warning: missing set " + refSet.code + " - " + refSet.name + " (cards: " + refSet.cards.size() + ", date: " + refSet.releaseDate + ")");
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

        // CHECK: wrong card numbers
        for (ExpansionSet set : xmageSets) {
            if (skipListHaveName(SKIP_LIST_WRONG_CARD_NUMBERS, set.getCode())) {
                continue;
            }

            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                MtgJsonCard jsonCard = MtgJsonService.cardFromSet(set.getCode(), card.getName(), card.getCardNumber());
                if (jsonCard == null) {
                    // see convertMtgJsonToXmageCardNumber for card number convert notation
                    errorsList.add("Error: unknown card number or set, use standard number notations: "
                            + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                    continue;
                }

                // index for missing cards
                String code = MtgJsonService.xMageToMtgJsonCodes.getOrDefault(set.getCode(), set.getCode()) + " - " + jsonCard.getRealCardName() + " - " + jsonCard.number;
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
                warningsList.add("Warning: found un-implemented set from mtgjson database: " + jsonSet.code + " - " + jsonSet.name + " - " + jsonSet.releaseDate);
                continue;
            }

            for (MtgJsonCard jsonCard : jsonSet.cards) {
                String code = jsonSet.code + " - " + jsonCard.getRealCardName() + " - " + jsonCard.number;
                if (!foundedJsonCards.contains(code)) {
                    if (CardRepository.instance.findCard(jsonCard.getRealCardName()) == null) {
                        // ignore non-implemented cards
                        continue;
                    }
                    errorsList.add("Error: missing card from xmage's set: " + jsonSet.code + " - " + jsonCard.getRealCardName() + " - " + jsonCard.number);
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
            Boolean needLand = set.hasBasicLands();
            Boolean foundLand = false;
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
                errorsList.add("Error: found set with wrong hasBasicLands - it's true, but haven't land cards: " + set.getCode() + " in " + set.getClass().getName());
            }

            if (!needLand && foundLand) {
                errorsList.add("Error: found set with wrong hasBasicLands - it's false, but have land cards: " + set.getCode() + " in " + set.getClass().getName() + ", lands: " + landNames);
            }

            // TODO: add test to check num cards (hasBasicLands and numLand > 0)
        }

        // CHECK: wrong snow land info
        for (ExpansionSet set : sets) {
            boolean needSnow = CardRepository.haveSnowLands(set.getCode());
            boolean haveSnow = false;
            for (ExpansionSet.SetCardInfo card : set.getSetCardInfo()) {
                if (card.getName().startsWith("Snow-Covered ")) {
                    haveSnow = true;
                    break;
                }
            }
            if (needSnow != haveSnow) {
                errorsList.add("Error: found incorrect snow land info in set " + set.getCode() + ": "
                        + (haveSnow ? "set has snow cards" : "set doesn't have snow card")
                        + ", but xmage thinks that it " + (needSnow ? "does" : "doesn't"));
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
                    errorsList.add("Error: founded double card names, but UsesVariousArt = false (missing NON_FULL_USE_VARIOUS, etc): "
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

            boolean containsDoubleSideCards = false;
            for (ExpansionSet.SetCardInfo cardInfo : set.getSetCardInfo()) {
                Card card = CardImpl.createCard(cardInfo.getCardClass(), new CardSetInfo(cardInfo.getName(), set.getCode(),
                        cardInfo.getCardNumber(), cardInfo.getRarity(), cardInfo.getGraphicInfo()));
                Assert.assertNotNull(card);

                if (card.getSecondCardFace() != null) {
                    containsDoubleSideCards = true;
                }

                // CHECK: all planeswalkers must be legendary
                if (card.isPlaneswalker() && !card.getSuperType().contains(SuperType.LEGENDARY)) {
                    errorsList.add("Error: planeswalker must have legendary type: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: getMana must works without NPE errors (it uses getNetMana with empty game param for AI score calcs)
                // https://github.com/magefree/mage/issues/6300
                card.getMana();

                // CHECK: non ascii symbols in card numbers
                if (!CharMatcher.ascii().matchesAllOf(card.getName()) || !CharMatcher.ascii().matchesAllOf(card.getCardNumber())) {
                    errorsList.add("Error: card name or number contains non-ascii symbols: " + set.getCode() + " - " + set.getName() + " - " + card.getName() + " - " + card.getCardNumber());
                }

                // CHECK: second side cards in one set
                // https://github.com/magefree/mage/issues/8081
                /*
                if (card.getSecondCardFace() != null && cardNames.contains(card.getSecondCardFace().getName())) {
                    errorsList.add("Error: set contains second side cards: " + set.getCode() + " - " + set.getName()
                            + " - " + card.getName() + " - " + card.getCardNumber()
                            + " - " + card.getSecondCardFace().getName() + " - " + card.getSecondCardFace().getCardNumber());
                }
                 */
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

        // all tokens must be stores in card-pictures-tok.txt (if not then viewer and image downloader are missing token images)
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

        // tok file's data
        List<CardDownloadData> tokFileTokens = DownloadPicturesService.getTokenCardUrls();
        LinkedHashMap<String, String> tokDataClassesIndex = new LinkedHashMap<>();
        LinkedHashMap<String, String> tokDataNamesIndex = new LinkedHashMap<>();
        for (CardDownloadData tokData : tokFileTokens) {

            String searchName;
            String setsList;

            // by class
            searchName = tokData.getTokenClassName();
            setsList = tokDataClassesIndex.getOrDefault(searchName, "");
            if (!setsList.isEmpty()) {
                setsList += ",";
            }
            setsList += tokData.getSet();
            tokDataClassesIndex.put(searchName, setsList);

            // by name
            searchName = tokData.getName();
            setsList = tokDataNamesIndex.getOrDefault(searchName, "");
            if (!setsList.isEmpty()) {
                setsList += ",";
            }
            setsList += tokData.getSet();
            tokDataNamesIndex.put(searchName, setsList);
        }

        // 1. check token name convention
        for (Class<? extends TokenImpl> tokenClass : tokenClassesList) {
            if (!tokenClass.getName().endsWith("Token")) {
                String className = extractShortClass(tokenClass);
                warningsList.add("warning, token class must ends with Token: " + className + " from " + tokenClass.getName());
            }
        }

        // 2. check store file for public
        for (Class<? extends TokenImpl> tokenClass : publicTokens) {
            String fullClass = tokenClass.getName();
            if (!fullClass.startsWith("mage.game.permanent.token.")) {
                String className = extractShortClass(tokenClass);
                errorsList.add("Error: public token must stores in mage.game.permanent.token package: " + className + " from " + tokenClass.getName());
            }
        }

        // 3. check private tokens (they aren't need at all)
        for (Class<? extends TokenImpl> tokenClass : privateTokens) {
            String className = extractShortClass(tokenClass);
            errorsList.add("Error: no needs in private tokens, replace it with CreatureToken: " + className + " from " + tokenClass.getName());
        }

        // 4. all public tokens must have tok-data (private tokens uses for innner abilities -- no need images for it)
        for (Class<? extends TokenImpl> tokenClass : publicTokens) {
            String className = extractShortClass(tokenClass);
            Token token = (Token) createNewObject(tokenClass);
            if (token == null) {
                errorsList.add("Error: token must have default constructor with zero params: " + tokenClass.getName());
            } else if (tokDataNamesIndex.getOrDefault(token.getName().replace(" Token", ""), "").isEmpty()) {
                errorsList.add("Error: can't find data in card-pictures-tok.txt for token: " + tokenClass.getName() + " -> " + token.getName());
            }
        }

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

        // TODO: all token must have correct availableImageSetCodes (all sets with that token)
        // Some sets have original card, but don't have token card at all. So you must use scryfall tokens list above to find
        // all token's sets and compare with xmage
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
        } else {
            warn(card, "Missing card reference");
        }
    }

    private boolean contains(Collection<String> options, String value) {
        return options != null && options.contains(value);
    }

    private static boolean checkName(MtgJsonCard ref) {
        if (!ONLY_TEXT) {
            return true;
        }
        if (checkedNames.contains(ref.getRealCardName())) {
            return false;
        }
        checkedNames.add(ref.getRealCardName());
        return true;
    }

    private void checkAll(Card card, MtgJsonCard ref, int cardIndex) {
        if (!ONLY_TEXT) {
            checkCost(card, ref);
            checkPT(card, ref);
            checkSubtypes(card, ref);
            checkSupertypes(card, ref);
            checkTypes(card, ref);
            checkColors(card, ref);
            checkBasicLands(card, ref);
            checkMissingAbilities(card, ref);
            checkWrongSymbolsInRules(card);
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

    private void checkSubtypes(Card card, MtgJsonCard ref) {
        if (skipListHaveName(SKIP_LIST_SUBTYPE, card.getExpansionSetCode(), card.getName())) {
            return;
        }

        Collection<String> expected = ref.subtypes;

        // fix names (e.g. Urza’s to Urza's)
        if (expected != null && expected.contains("Urza’s")) {
            expected = new ArrayList<>(expected);
            for (ListIterator<String> it = ((List<String>) expected).listIterator(); it.hasNext(); ) {
                if (it.next().equals("Urza’s")) {
                    it.set("Urza's");
                }
            }
        }

        // Remove subtypes that need to be ignored
        Collection<String> actual = card
                .getSubtype()
                .stream()
                .map(SubType::toString)
                .collect(Collectors.toSet());
        actual.removeIf(subtypesToIgnore::contains);

        if (expected != null) {
            expected.removeIf(subtypesToIgnore::contains);
        }

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

        // special check: Werewolves front ability should only be on front and vice versa
        if (card.getAbilities().containsClass(WerewolfFrontTriggeredAbility.class) && card.isNightCard()) {
            fail(card, "abilities", "card is a back face werewolf with a front face ability");
        }
        if (card.getAbilities().containsClass(WerewolfBackTriggeredAbility.class) && !card.isNightCard()) {
            fail(card, "abilities", "card is a front face werewolf with a back face ability");
        }

        if (card.getSecondCardFace() != null && !card.isNightCard() && !card.getAbilities().containsClass(TransformAbility.class)) {
            fail(card, "abilities", "double-faced cards should have transform ability on the front");
        }

        if (card.getSecondCardFace() != null && card.isNightCard() && card.getAbilities().containsClass(TransformAbility.class)) {
            fail(card, "abilities", "double-faced cards should not have transform ability on the back");
        }

        if (card.getSecondCardFace() != null && !card.getSecondCardFace().isNightCard()) {
            fail(card, "abilities", "the back face of a double-faced card should be nightCard = true");
        }

        // special check: missing or wrong ability/effect hints
        Map<Class, String> hints = new HashMap<>();

        hints.put(FightTargetsEffect.class, "Each deals damage equal to its power to the other");
        hints.put(MenaceAbility.class, "can't be blocked except by two or more");
        hints.put(ScryEffect.class, "Look at the top card of your library. You may put that card on the bottom of your library");
        hints.put(EquipAbility.class, "Equip only as a sorcery.");
        hints.put(WardAbility.class, "becomes the target of a spell or ability an opponent controls");
        hints.put(ProliferateEffect.class, "Choose any number of permanents and/or players, then give each another counter of each kind already there.");

        for (Class objectClass : hints.keySet()) {
            String objectHint = hints.get(objectClass);
            // ability/effect must have description or not
            boolean needHint = ref.text.contains(objectHint);
            boolean haveHint = card.getRules().stream().anyMatch(rule -> rule.contains(objectHint));
            if (needHint != haveHint) {
                fail(card, "abilities", "card have " + objectClass.getSimpleName() + " but hint is wrong (it must be " + (needHint ? "enabled" : "disabled") + ")");
            }
        }

        // spells have only 1 ability
        if (card.isInstantOrSorcery()) {
            return;
        }

        // additional cost go to 1 ability
        if (ref.text.startsWith("As an additional cost to cast")) {
            return;
        }

        // always 1 ability (to cast)
        if (card.getAbilities().toArray().length == 1) { // all cards have 1 inner ability to cast
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

        return newRule.trim();
    }

    @Test
    public void test_showCardInfo() throws Exception {
        // debug only: show direct card info (takes it from class file, not from db repository)
        // can check multiple cards at once, example: name1;name2;name3
        String cardNames = "Spark Double";
        CardScanner.scan();
        Arrays.stream(cardNames.split(";")).forEach(cardName -> {
            cardName = cardName.trim();
            CardSetInfo testSet = new CardSetInfo(cardName, "test", "123", Rarity.COMMON);
            CardInfo cardInfo = CardRepository.instance.findCard(cardName);
            if (cardInfo == null) {
                Assert.fail("Can't find card name: " + cardName);
            }
            Card card = CardImpl.createCard(cardInfo.getClassName(), testSet);
            System.out.println();
            System.out.println(card.getName() + " " + card.getManaCost().getText());
            if (card instanceof SplitCard || card instanceof ModalDoubleFacesCard) {
                card.getAbilities().getRules(card.getName()).forEach(this::printAbilityText);
            } else {
                card.getRules().forEach(this::printAbilityText);
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
    private static final boolean compareText(String cardText, String refText, String name) {
        return cardText.equals(refText)
                || cardText.replace(name, name.split(", ")[0]).equals(refText)
                || cardText.replace(name, name.split(" ")[0]).equals(refText);
    }

    private static final boolean checkForEffect(Card card, Class<? extends Effect> effectClazz) {
        return card.getAbilities()
                .stream()
                .map(Ability::getModes)
                .map(LinkedHashMap::values)
                .flatMap(Collection::stream)
                .map(Mode::getEffects)
                .flatMap(Collection::stream)
                .anyMatch(effectClazz::isInstance);
    }

    private void checkWrongAbilitiesText(Card card, MtgJsonCard ref, int cardIndex) {
        // checks missing or wrong text
        if (!card.getExpansionSetCode().equals(FULL_ABILITIES_CHECK_SET_CODE) || !checkName(ref)) {
            return;
        }

        if (ref.text == null || ref.text.isEmpty()) {
            return;
        }

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
        // mana ability fix
        for (String s : refText.split("[\\$\\\n]")) {
            if (!(s.startsWith("{T}: Add {") || s.startsWith("({T}: Add {"))
                    || !(s.contains("} or {") || s.contains("}, or {"))) {
                continue;
            }
            String newStr = "";
            for (String c : s.split("[\\{\\}]")) {
                if ("WUBRG".contains(c) && c.length() > 0) {
                    newStr += "{T}: Add {" + c + "}.\n";
                }
            }
            if (!newStr.isEmpty()) {
                newStr = newStr.substring(0, newStr.length() - 1);
            }
            refText = refText.replace(s, newStr);
        }


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
                if (!ONLY_TEXT) {
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

    private void checkBasicLands(Card card, MtgJsonCard ref) {

        // basic lands must have Rarity.LAND and SuperType.BASIC
        // other cards can't have that stats
        String name = card.getName();
        if (isBasicLandName(name)) {
            // lands
            if (card.getRarity() != Rarity.LAND) {
                fail(card, "rarity", "basic land must be Rarity.LAND");
            }

            if (!card.getSuperType().contains(SuperType.BASIC)) {
                fail(card, "supertype", "basic land must be SuperType.BASIC");
            }
        } else if (name.equals("Wastes")) {
            // Wastes are SuperType.BASIC but not necessarily Rarity.LAND
            if (!card.getSuperType().contains(SuperType.BASIC)) {
                fail(card, "supertype", "Wastes must be SuperType.BASIC");
            }
        } else {
            // non lands
            if (card.getRarity() == Rarity.LAND) {
                fail(card, "rarity", "only basic land can be Rarity.LAND");
            }

            if (card.getSuperType().contains(SuperType.BASIC)) {
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
        Collection<String> errorsList = new ArrayList<>();
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                // catch cards creation errors and report (e.g. on wrong card code or construction checks fail)
                try {
                    Card card = CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                            setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()));
                    if (card == null) {
                        errorsList.add("Error: broken constructor " + setInfo.getCardClass());
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
                    cardInfo = CardRepository.instance.findCardWPreferredSet(cardId.getName(), cardId.getExtension(), false);
                } else {
                    cardInfo = CardRepository.instance.findPreferredCoreExpansionCard(cardId.getName(), false);
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
}
