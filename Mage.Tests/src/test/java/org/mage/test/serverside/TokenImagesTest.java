package org.mage.test.serverside;

import mage.MageObject;
import mage.MageObjectImpl;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.repository.TokenRepository;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.HumanToken;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.custom.CreatureToken;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.view.CardView;
import mage.view.GameView;
import mage.view.PermanentView;
import mage.view.PlayerView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * All tests logic: create a tokens list from specific cards and check a used settings (set code, image number)
 *
 * @author JayDi85
 */
public class TokenImagesTest extends CardTestPlayerBase {

    // TODO: add tests for Planes, Dungeons and other command objects (when it gets additional sets)

    private static final Pattern checkPattern = Pattern.compile("(\\w+)([<=>])(\\d+)"); // code=12, code>0

    static class TestToken extends TokenImpl {

        TestToken(String name, String description) {
            super(name, description);
        }

        TestToken(final TestToken token) {
            super(token);
        }

        @Override
        public Token copy() {
            return new TestToken(this);
        }
    }

    private void prepareCards_MemorialToGlory(String... cardsList) {
        // {3}{W}, {T}, Sacrifice Memorial to Glory: Create two 1/1 white Soldier creature tokens.
        prepareCards_Inner(Zone.BATTLEFIELD, "Memorial to Glory", 4, cardsList);
    }

    private void prepareCards_TheHive(String... cardsList) {
        // {5}, {T}: Create a 1/1 colorless Insect artifact creature token with flying named Wasp.
        // (It can’t be blocked except by creatures with flying or reach.)
        prepareCards_Inner(Zone.BATTLEFIELD, "The Hive", 5, cardsList);
    }

    private void prepareCards_SacredCat(String... cardsList) {
        // Embalm {W} ({W}, Exile this card from your graveyard: Create a token that's a copy of it,
        // except it's a white Zombie Cat with no mana cost. Embalm only as a sorcery.)
        prepareCards_Inner(Zone.GRAVEYARD, "Sacred Cat", 1, cardsList);
    }

    private void prepareCards_Inner(Zone cardZone, String cardName, int plainsAmount, String... cardsList) {
        Arrays.stream(cardsList).forEach(list -> {
            String[] info = list.split("=");
            String needSet = info[0];
            int needAmount = Integer.parseInt(info[1]);
            IntStream.range(0, needAmount).forEach(x -> {
                addCard(cardZone, playerA, String.format("%s-%s", needSet, cardName));
                if (plainsAmount > 0) {
                    addCard(Zone.BATTLEFIELD, playerA, "Plains", plainsAmount);
                }
            });
        });
    }

    private void activate_MemorialToGlory(int amount) {
        activate_Inner(amount, "{3}{W}, {T}, Sacrifice");
    }

    private void activate_TheHive(int amount) {
        activate_Inner(amount, "{5}, {T}: Create a 1/1 colorless Insect");
    }

    private void activate_SacredCat(int amount) {
        activate_Inner(amount, "Embalm {W}");
    }

    private void activate_Inner(int amount, String abilityText) {
        IntStream.range(0, amount).forEach(x -> {
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, abilityText);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        });
    }

    private void assert_MemorialToGlory(int cardsAmount, String... tokensList) {
        assert_Inner(
                "Memorial to Glory", 0, cardsAmount, 0,
                "Soldier Token", 2 * cardsAmount, false, tokensList
        );
    }

    private void assert_TheHive(int amount, String... tokensList) {
        assert_Inner(
                "The Hive", 0, 0, amount,
                "Wasp", amount, false, tokensList
        );
    }

    private void assert_SacredCat(int amount, String... tokensList) {
        // token has same name as card
        assert_Inner(
                "Sacred Cat", amount, 0, amount,
                "Sacred Cat", amount, true, tokensList
        );
    }

    private void assert_Inner(String cardName, int cardAmountInExile, int cardAmountInGrave, int cardAmountInBattlefield,
                              String tokenName, int tokenAmount, boolean mustStoreAsCard, String... checks) {
        if (!cardName.isEmpty()) {
            assertExileCount(playerA, cardName, cardAmountInExile);
            assertGraveyardCount(playerA, cardName, cardAmountInGrave);
            assertPermanentCount(playerA, cardName, cardAmountInBattlefield);
        }
        assertTokenCount(playerA, tokenName, tokenAmount);

        // collect real server stats
        Map<String, List<Card>> realServerStats = new LinkedHashMap<>();
        currentGame.getBattlefield().getAllPermanents()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
                .filter(card -> card instanceof PermanentToken)
                .sorted(Comparator.comparing(Card::getExpansionSetCode))
                .forEach(card -> {
                    Assert.assertNotNull("must have set code", card.getExpansionSetCode());
                    Assert.assertEquals("must have same set codes in all fields",
                            card.getExpansionSetCode(),
                            ((PermanentToken) card).getToken().getExpansionSetCode()
                    );
                    String realCode = card.getExpansionSetCode();
                    realServerStats.computeIfAbsent(realCode, code -> new ArrayList<>());
                    realServerStats.get(realCode).add(card);
                });

        // collect real client stats
        Map<String, List<PermanentView>> realClientStats = new LinkedHashMap<>();
        GameView gameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        gameView.getMyPlayer().getBattlefield().values()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
                .filter(CardView::isToken)
                .sorted(Comparator.comparing(CardView::getExpansionSetCode))
                .forEach(permanentView -> {
                    String realCode = permanentView.getExpansionSetCode();
                    realClientStats.computeIfAbsent(realCode, code -> new ArrayList<>());
                    realClientStats.get(realCode).add(permanentView);
                });

        // check client data (GameView's objects must get same data as Game's objects)
        Assert.assertEquals(realServerStats.size(), realClientStats.size());
        Assert.assertEquals(
                realServerStats.values().stream().mapToInt(List::size).sum(),
                realClientStats.values().stream().mapToInt(List::size).sum()
        );
        String serverDataInfo = realServerStats.entrySet()
                .stream()
                .map(entry -> String.format("%s-%d", entry.getKey(), entry.getValue().size()))
                .collect(Collectors.joining(", "));
        String clientDataInfo = realClientStats.entrySet()
                .stream()
                .map(entry -> String.format("%s-%d", entry.getKey(), entry.getValue().size()))
                .collect(Collectors.joining(", "));
        Assert.assertEquals(serverDataInfo, clientDataInfo);

        // check stats
        List<String> checkResults = new ArrayList<>();
        Arrays.stream(checks).forEach(check -> {
            Matcher checkMatcher = checkPattern.matcher(check);
            if (!checkMatcher.find()) {
                throw new IllegalArgumentException("Unknown check operation format: " + check);
            }
            Assert.assertEquals(3, checkMatcher.groupCount());
            String checkCode = checkMatcher.group(1);
            String checkOper = checkMatcher.group(2);
            String checkVal = checkMatcher.group(3);

            boolean isFine = true;
            List<String> problems = new ArrayList<>();

            // check: tokens amount
            List<Card> realList = realServerStats.getOrDefault(checkCode, new ArrayList<>());
            switch (checkOper) {
                case "<":
                    isFine = isFine && (realList.size() < Integer.parseInt(checkVal));
                    break;
                case "=":
                    isFine = isFine && (realList.size() == Integer.parseInt(checkVal));
                    break;
                case ">":
                    isFine = isFine && (realList.size() > Integer.parseInt(checkVal));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown check operation: " + check);
            }
            if (!isFine) {
                problems.add("real amount is " + realList.size());
            }

            // check: tokens store type
            // token as card like Embalm ability must have card number, so it will link to card's image instead token's image
            boolean hasCardNumbers = !realList.isEmpty() && realList
                    .stream()
                    .mapToInt(card -> card.getCardNumber().isEmpty() ? 0 : CardUtil.parseCardNumberAsInt(card.getCardNumber()))
                    .allMatch(x -> x > 0);
            if (mustStoreAsCard != hasCardNumbers) {
                isFine = false;
                problems.add("wrong store type");
            }

            checkResults.add(String.format(
                    "%s: %s%s",
                    check,
                    (isFine ? "OK" : "FAIL"),
                    (isFine ? "" : ", " + String.join("; ", problems))
            ));
        });
        boolean allFine = checkResults.stream().noneMatch(s -> s.contains("FAIL"));

        // assert results
        if (!allFine) {
            String realInfo = realServerStats.entrySet()
                    .stream()
                    .map(entry -> String.format("%s-%s", entry.getKey(), entry.getValue().size()))
                    .collect(Collectors.joining(", "));

            System.out.println("Real stats:");
            System.out.println(" - " + realInfo);
            System.out.println();
            System.out.println("Check stats:");
            checkResults.forEach(s -> System.out.println(" - " + s));
            Assert.fail("Found wrong real stats, see logs above");
        }
    }

    private void assert_TokenOrCardImageNumber(String tokenOrCardName, List<Integer> needUniqueImages) {
        Set<Integer> serverStats = currentGame.getBattlefield().getAllPermanents()
                .stream()
                .filter(card -> card.getName().equals(tokenOrCardName))
                .filter(card -> card instanceof MageObjectImpl)
                .sorted(Comparator.comparing(Card::getExpansionSetCode))
                .map(card -> (MageObjectImpl) card)
                .map(MageObjectImpl::getImageNumber)
                .collect(Collectors.toSet());

        GameView gameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        Set<Integer> clientStats = gameView.getMyPlayer().getBattlefield().values()
                .stream()
                .filter(card -> card.getName().equals(tokenOrCardName))
                .sorted(Comparator.comparing(CardView::getExpansionSetCode))
                .map(CardView::getImageNumber)
                .collect(Collectors.toSet());

        // server and client sides must have same data
        String imagesNeed = needUniqueImages.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        String imagesServer = serverStats.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        String imagesClient = clientStats.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        Assert.assertEquals("server side", imagesNeed, imagesServer);
        Assert.assertEquals("client side", imagesNeed, imagesClient);
    }

    private void assertFaceDownCharacteristics(String info, MageObject object, String faceDownTypeName) {
        String prefix = info + " - " + object;

        // image info
        Assert.assertEquals(prefix + " - wrong set code", TokenRepository.XMAGE_TOKENS_SET_CODE, object.getExpansionSetCode());
        Assert.assertEquals(prefix + " - wrong card number", "0", object.getCardNumber());
        Assert.assertEquals(prefix + " - wrong image file name", faceDownTypeName, object.getImageFileName());
        Assert.assertNotEquals(prefix + " - wrong image number", Integer.valueOf(0), object.getImageNumber());

        // characteristic checks instead new test
        Assert.assertEquals(prefix + " - wrong name", EmptyNames.FACE_DOWN_CREATURE.toString(), object.getName());
        Assert.assertEquals(prefix + " - wrong power", 2, object.getPower().getValue());
        Assert.assertEquals(prefix + " - wrong toughness", 2, object.getToughness().getValue());
        Assert.assertEquals(prefix + " - wrong color", "", object.getColor(currentGame).toString());
        Assert.assertEquals(prefix + " - wrong supertypes", "[]", object.getSuperType(currentGame).toString());
        Assert.assertEquals(prefix + " - wrong types", "[Creature]", object.getCardType(currentGame).toString());
        Assert.assertEquals(prefix + " - wrong subtypes", "[]", object.getSubtype(currentGame).toString());
        Assert.assertTrue(prefix + " - wrong abilities", object.getAbilities().stream().anyMatch(a -> !CardUtil.isInformationAbility(a))); // become face down + face up abilities only
    }

    private void assertOriginalData(String info, CardView cardView, int needPower, int needToughness, String needColor) {
        String prefix = info + " - " + cardView;
        int currentPower = cardView.getOriginalPower() == null ? 0 : cardView.getOriginalPower().getValue();
        int currentToughness = cardView.getOriginalToughness() == null ? 0 : cardView.getOriginalToughness().getValue();
        Assert.assertEquals(prefix + " - wrong power", needPower, currentPower);
        Assert.assertEquals(prefix + " - wrong toughness", needToughness, currentToughness);
        if (needColor != null) {
            Assert.assertEquals(prefix + " - wrong color", needColor, cardView.getOriginalColorIdentity());
        }
    }

    private void assert_FaceDownMorphImageNumber(List<Integer> needUniqueImages) {
        Set<Integer> serverStats = currentGame.getBattlefield().getAllPermanents()
                .stream()
                .filter(card -> card.isFaceDown(currentGame))
                .filter(card -> {
                    Assert.assertEquals("server side - wrong set code - " + card, TokenRepository.XMAGE_TOKENS_SET_CODE, card.getExpansionSetCode());
                    return true;
                })
                .sorted(Comparator.comparing(Card::getExpansionSetCode))
                .map(card -> (MageObjectImpl) card)
                .map(MageObjectImpl::getImageNumber)
                .collect(Collectors.toSet());

        // use another player to hide card view names in face down
        GameView gameView = new GameView(currentGame.getState(), currentGame, playerB.getId(), null);
        PlayerView playerView = gameView.getPlayers()
                .stream()
                .filter(p -> p.getName().equals(playerA.getName()))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(playerView);
        Set<Integer> clientStats = playerView.getBattlefield().values()
                .stream()
                .filter(CardView::isFaceDown)
                .filter(card -> {
                    Assert.assertEquals("client side - wrong set code - " + card, TokenRepository.XMAGE_TOKENS_SET_CODE, card.getExpansionSetCode());
                    return true;
                })
                .sorted(Comparator.comparing(CardView::getExpansionSetCode))
                .map(CardView::getImageNumber)
                .collect(Collectors.toSet());

        // server and client sides must have same data
        String imagesNeed = needUniqueImages.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        String imagesServer = serverStats.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        String imagesClient = clientStats.stream().sorted().map(Object::toString).collect(Collectors.joining(", "));
        Assert.assertEquals("server side", imagesNeed, imagesServer);
        Assert.assertEquals("client side", imagesNeed, imagesClient);
    }

    @Test
    public void test_TokenExists_MustGetSameSetCodeAsSourceCard_Soldier() {
        prepareCards_MemorialToGlory("40K=3", "DOM=5");
        activate_MemorialToGlory(3 + 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // x2 tokens
        assert_MemorialToGlory(3 + 5, "40K=6", "DOM=10");
    }

    @Test
    public void test_TokenExists_MustGetSameSetByRandomTypes() {
        prepareCards_MemorialToGlory("40K=20");
        activate_MemorialToGlory(20);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // x2 tokens
        assert_MemorialToGlory(20, "40K=40");
        assert_TokenOrCardImageNumber("Soldier Token", Arrays.asList(1, 2, 3)); // 40K set contains 3 diffrent soldiers
    }

    @Test
    public void test_TokenExists_MustGetSameSetCodeAsSourceCard_Wasp() {
        // Tenth Edition (10E)
        // 30th Anniversary Edition (30A)
        prepareCards_TheHive("10E=3", "30A=5");
        activate_TheHive(3 + 5);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assert_TheHive(3 + 5, "10E=3", "30A=5");
    }

    @Test
    public void test_TokenExists_MustGetRandomSetCodeOnAllUnknownSets() {
        // if a source's set don't have tokens then it must be random
        // https://github.com/magefree/mage/issues/10139

        prepareCards_TheHive("5ED=20");
        activate_TheHive(20);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // 5ED don't have tokens, so must be used another sets (10E, 30A)
        assert_TheHive(20, "5ED=0", "10E>0", "30A>0");
    }

    @Test
    public void test_TokenExists_MustGetSameImageForAllTokenInstances() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new SoldierToken(), 10),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("40K-test", playerA, ability);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create ten");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // workaround to find a real image number (tokens get random image on put to battlefield)
        AtomicInteger realImageNumber = new AtomicInteger(0);
        runCode("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            PermanentToken token = game.getBattlefield().getAllPermanents()
                    .stream()
                    .filter(p -> p instanceof PermanentToken)
                    .map(p -> (PermanentToken) p)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(token);
            Assert.assertTrue(token.getImageNumber() >= 1 && token.getImageNumber() <= 3);
            realImageNumber.set(token.getImageNumber());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 1 + 10); // 1 test card + 10 tokens
        assert_TokenOrCardImageNumber("Soldier Token", Arrays.asList(realImageNumber.get())); // one ability's call must generate tokens with same image
        assert_Inner("test", 0, 0, 1,
                "Soldier Token", 10, false, "40K=10");
    }

    @Test
    public void test_TokenExists_CopyMustGetSameImageAsCopiedCard() {
        // copied cards
        // https://github.com/magefree/mage/issues/10222

        addCard(Zone.BATTLEFIELD, playerA, "NEC-Silver Myr", 3);
        addCard(Zone.BATTLEFIELD, playerA, "MM2-Alloy Myr", 3);
        //
        // Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "BRC-March of Progress", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of Progress with overload");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // +3 new tokens for each
        assertPermanentCount(playerA, "Silver Myr", 3 + 3);
        assertPermanentCount(playerA, "Alloy Myr", 3 + 3);

        // tokens must use same set code as copied card
        assert_Inner("Silver Myr", 0, 0, 3 + 3,
                "Silver Myr", 3, true, "NEC=3");
        assert_Inner("Alloy Myr", 0, 0, 3 + 3,
                "Alloy Myr", 3, true, "MM2=3");
    }

    @Test
    public void test_TokenExists_CopyMustGetSameImageAsCopiedToken() {
        // copied tokens
        // https://github.com/magefree/mage/issues/10222

        // -2: Create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        addCard(Zone.BATTLEFIELD, playerA, "MED-Karn, Scion of Urza", 1);
        //
        // Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "BRC-March of Progress", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // prepare token
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Construct Token", 1);

        // copy token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of Progress with overload");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // +1 new token
        assertPermanentCount(playerA, "Construct Token", 1 + 1);

        // tokens must use same set code as copied token
        assert_Inner("", 0, 0, 0,
                "Construct Token", 2, false, "MED=2");
    }

    @Test
    public void test_TokenExists_CopyMustGetSameImageNumber() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new HumanToken(), 10),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("MOC-test", playerA, ability); // MOC contains only 1 token with image number 2
        //
        // You may have Vesuvan Doppelganger enter the battlefield as a copy of any creature on the battlefield
        addCard(Zone.HAND, playerA, "Vesuvan Doppelganger", 1); // {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // create tokens
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create ten");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Human Token", 10);
        // copy token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vesuvan Doppelganger");
        setChoice(playerA, true);
        setChoice(playerA, "Human Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Human Token", 10 + 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assert_TokenOrCardImageNumber("Human Token", Arrays.asList(2)); // one ability's call must generate tokens with same image
        assert_Inner("test", 0, 0, 1,
                "Human Token", 10, false, "MOC=10");
    }

    @Test
    public void test_TokenExists_CopyEffectMustRestoreOldImageAfterEnd() {
        // check a copy effect for:
        // - a: card
        // - b: token
        // - c: token for card

        // {3}{U}{U}
        // Echoing Equation
        // Choose target creature you control. Each other creature you control becomes a copy of it until end of turn, except those creatures aren’t legendary if the chosen creature is legendary.
        addCard(Zone.HAND, playerA, "STX-Augmenter Pugilist", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "ICE-Balduvian Bears", 1);
        //
        // a: card
        addCard(Zone.BATTLEFIELD, playerA, "NEC-Silver Myr", 1);
        //
        // b: token
        prepareCards_TheHive("10E=1");
        //
        // - c: token for card
        // {2}{U}
        // Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it.
        addCard(Zone.HAND, playerA, "BRC-March of Progress", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // prepare b: token
        activate_TheHive(1);

        // prepare c: token for card
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of Progress");
        addTarget(playerA, "Silver Myr");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // check prepared data
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silver Myr", 2); // card + token
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wasp", 1);

        // copy bear
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echoing Equation");
        addTarget(playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // must get 1 original card + 3 copies (1 from card, 1 from copy of card, 1 from token)
        checkPermanentCount("after copy start", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 4);
        runCode("after copy start", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Set<String> usedSetCodes = game.getBattlefield().getAllPermanents()
                    .stream()
                    .filter(MageObject::isCopy)
                    .map(Card::getExpansionSetCode)
                    .collect(Collectors.toSet());
            Assert.assertEquals(1, usedSetCodes.size());
            Assert.assertEquals("ICE", usedSetCodes.stream().findFirst().orElse(null));
        });

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        // end of copy - must get original images
        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertPermanentCount(playerA, "Silver Myr", 2); // card + token
        assertTokenCount(playerA, "Silver Myr", 1);
        assertPermanentCount(playerA, "Wasp", 1);
        assert_Inner("Silver Myr", 0, 0, 2,
                "Silver Myr", 1, true, "NEC=1");
        assert_Inner("", 0, 0, 0,
                "Wasp", 1, false, "10E=1");
    }

    @Test
    public void test_CreatureToken_MustGetDefaultImage() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new CreatureToken(2, 2, "", SubType.HUMAN), 10),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("40K-test", playerA, ability);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create ten");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 1 + 10); // 1 test card + 10 tokens

        assert_Inner("test", 0, 0, 1,
                "Human Token", 10, false, "XMAGE=10");
    }

    @Test
    public void test_UnknownToken_MustGetDefaultImage() {
        // all unknown tokens must put in XMAGE set
        String xmageSetCode = TokenRepository.XMAGE_TOKENS_SET_CODE;
        TestToken token = new TestToken("Unknown Token", "xxx");
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(token, 10),
                new ManaCostsImpl<>("")
        );
        addCustomCardWithAbility("test", playerA, ability);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "create ten");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, 1 + 10); // 1 test card + 10 tokens

        assert_Inner("test", 0, 0, 1,
                "Unknown Token", 10, false, xmageSetCode + "=10");
    }

    @Test
    public void test_Abilities_Embalm_MustGenerateSameTokenAsCard() {
        prepareCards_SacredCat("AKH=3", "AKR=5", "MB1=1");
        activate_SacredCat(3 + 5 + 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assert_SacredCat(3 + 5 + 1, "AKH=3", "AKR=5", "MB1=1");
    }

    @Test // it's ok for fail in 1 of 50
    // TODO: implement mock or test command to setup "random" images in TokenImpl.generateTokenInfo
    //  (see setFlipCoinResult and setDieRollResult), so no needs in big amount
    public void test_Abilities_Incubator_MustTransformWithSameSettings() {
        // bug with miss image data in transformed incubator token: https://github.com/magefree/mage/issues/11535

        // make sure random images take all 3 diff images
        int needIncubatorTokens = 30;
        int needPhyrexianTokens = 30 / 2;

        // When Sculpted Perfection enters the battlefield, incubate 2. (Create an Incubator token with two +1/+1
        // counters on it and “{2}: Transform this artifact.” It transforms into a 0/0 Phyrexian artifact creature.)
        prepareCards_Inner(Zone.HAND, "Sculpted Perfection", 0, "MOM=" + needIncubatorTokens); // {2}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3 * needIncubatorTokens);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 * needIncubatorTokens);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 * needIncubatorTokens); // for transform

        // prepare incubator tokens
        IntStream.range(0, needIncubatorTokens).forEach(x -> {
            activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3);
            activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1);
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Sculpted Perfection");
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        });

        // transform tokens to Phyrexian
        IntStream.range(0, needPhyrexianTokens).forEach(x -> {
            activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 2);
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: Transform");
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // same set
        assert_Inner("Sculpted Perfection", 0, 0, needIncubatorTokens,
                "Incubator Token", needPhyrexianTokens, false, "MOM=" + needPhyrexianTokens);
        assert_Inner("Sculpted Perfection", 0, 0, needIncubatorTokens,
                "Phyrexian Token", needPhyrexianTokens, false, "MOM=" + needPhyrexianTokens);

        // MOM-Incubator has 1 image (number is 0)
        assert_TokenOrCardImageNumber("Incubator Token", Arrays.asList(0));
        // MOM-Phyrexian has 3 images
        assert_TokenOrCardImageNumber("Phyrexian Token", Arrays.asList(1, 2, 3));
    }

    @Test // it's ok for fail in very rare random
    // TODO: implement mock or test command to setup "random" images in TokenImpl.generateTokenInfo
    //  (see setFlipCoinResult and setDieRollResult), so no needs in big amount
    public void test_FaceDown_CardWithMorph_MustGetDefaultImage() {
        int faceDownAmount = 15;
        addCard(Zone.HAND, playerA, "Ainok Tracker", faceDownAmount); // {5}{R}, Morph {4}{R}, face up {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 * faceDownAmount);

        IntStream.range(0, faceDownAmount).forEach(i -> {
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ainok Tracker using Morph");
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), faceDownAmount);
        assert_FaceDownMorphImageNumber(Arrays.asList(1, 2, 3));
    }

    @Test // it's ok for fail in very rare random
    public void test_FaceDown_LandWithMorph_MustGetDefaultImage() {
        int faceDownAmount = 15;
        addCard(Zone.HAND, playerA, "Zoetic Cavern", faceDownAmount);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 * faceDownAmount);

        IntStream.range(0, faceDownAmount).forEach(i -> {
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern using Morph");
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), faceDownAmount);
        assert_FaceDownMorphImageNumber(Arrays.asList(1, 2, 3));
    }

    @Test
    public void test_FaceDown_Spell() {
        addCard(Zone.HAND, playerA, "Zoetic Cavern", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zoetic Cavern using Morph");
        runCode("stack check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("stack must be active", 1, game.getState().getStack().size());

            // server side spell before resolve contains full info, not empty
            // so real data will be full, but view data will be hidden by face down status
            String cardName = "Zoetic Cavern";
            String needClientControllerName = CardUtil.getCardNameForGUI(cardName, TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH);
            String needClientOpponentName = CardUtil.getCardNameForGUI("", TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH);

            Spell spell = (Spell) game.getState().getStack().stream().findFirst().orElse(null);
            Assert.assertNotNull("server - spell must exists", spell);

            // make sure image from object's id works fine
            IntStream.of(5).forEach(i -> {
                UUID objectId = UUID.randomUUID();
                int objectImageNumber = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH, objectId).getImageNumber();
                Assert.assertNotEquals("wrong image number", 0, objectImageNumber);
                IntStream.of(5).forEach(j -> {
                    int newImageNumber = TokenRepository.instance.findPreferredTokenInfoForXmage(TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH, objectId).getImageNumber();
                    Assert.assertEquals("generated image numbers must be same for same id", objectImageNumber, newImageNumber);
                });
            });

            // debug
            //CardView debugViewOpponent = new CardView(spell, currentGame, false, false);
            //CardView debugViewController = new CardView(spell, currentGame, true, false);

            // server side (full data)
            Assert.assertTrue("server - wrong face down status", spell.isFaceDown(game));
            Assert.assertEquals("server - wrong color", spell.getColor(game), new ObjectColor());
            Assert.assertEquals("server - wrong name", cardName, spell.getName());
            //
            // workaround to find image number (from id) - it must be same on each generate
            int serverImageNumber = spell.getSpellAbility().getCharacteristics(game).getImageNumber();
            Assert.assertNotEquals("server - wrong set code", TokenRepository.XMAGE_TOKENS_SET_CODE, spell.getExpansionSetCode());
            Assert.assertNotEquals("server - wrong image number", 0, serverImageNumber);

            // client side - controller (hidden + card name)
            GameView gameView = getGameView(playerA);
            CardView spellView = gameView.getStack().values().stream().findFirst().orElse(null);
            Assert.assertNotNull("client, controller - spell must exists", spellView);
            Assert.assertTrue("client, controller - wrong face down status", spellView.isFaceDown());
            Assert.assertEquals("client, controller - wrong color", spellView.getColor(), new ObjectColor());
            Assert.assertEquals("client, controller - wrong spell name", needClientControllerName, spellView.getName());
            //
            Assert.assertEquals("client, controller - wrong set code", TokenRepository.XMAGE_TOKENS_SET_CODE, spellView.getExpansionSetCode());
            Assert.assertEquals("client, controller - wrong card number", "0", spellView.getCardNumber());
            Assert.assertEquals("client, controller - wrong image file", TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH, spellView.getImageFileName());
            Assert.assertEquals("client, controller - wrong image number", serverImageNumber, spellView.getImageNumber());

            // client side - opponent (hidden)
            gameView = getGameView(playerB);
            spellView = gameView.getStack().values().stream().findFirst().orElse(null);
            Assert.assertNotNull("client, opponent - spell must exists", spellView);
            Assert.assertTrue("client, opponent - wrong face down status", spellView.isFaceDown());
            Assert.assertEquals("client, opponent - wrong color", spellView.getColor(), new ObjectColor());
            Assert.assertEquals("client, opponent - wrong spell name", needClientOpponentName, spellView.getName());
            //
            Assert.assertEquals("client, opponent - wrong set code", TokenRepository.XMAGE_TOKENS_SET_CODE, spellView.getExpansionSetCode());
            Assert.assertEquals("client, opponent - wrong card number", "0", spellView.getCardNumber());
            Assert.assertEquals("client, opponent - wrong image file", TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MORPH, spellView.getImageFileName());
            Assert.assertEquals("client, opponent - wrong image number", serverImageNumber, spellView.getImageNumber());
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

    @Test
    public void test_FaceDown_Megamorph_MustGetDefaultImage() {
        addCard(Zone.HAND, playerA, "Aerie Bowmasters", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 + 3);

        // prepare face down permanent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aerie Bowmasters using Megamorph");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("on face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
            assertPermanentCount(playerA, "Aerie Bowmasters", 0);
            Permanent permanent = getPermanent(EmptyNames.FACE_DOWN_CREATURE.toString(), playerA);
            assertFaceDownCharacteristics("permanent", permanent, TokenRepository.XMAGE_IMAGE_NAME_FACE_DOWN_MEGAMORPH);
        });

        // face up it and find counter
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{G}: Turn this");
        runCode("on face up", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
            assertPermanentCount(playerA, "Aerie Bowmasters", 1);
            assertCounterCount(playerA, "Aerie Bowmasters", CounterType.P1P1, 1);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_FaceDown_ExileZone_MustGetDefaultImage() {
        // {T}: Draw a card, then exile a card from your hand face down.
        addCard(Zone.BATTLEFIELD, playerA, "Bane Alley Broker", 1);
        addCard(Zone.HAND, playerA, "Forest", 1);

        // exile face down
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card");
        addTarget(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // check face down card in exile
        runCode("on face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card card = currentGame.getExile().getAllCards(currentGame, playerA.getId()).get(0);
            GameView gameView = getGameView(playerA);
            CardView controllerCardView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);
            gameView = getGameView(playerB);
            CardView opponentCardView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);

            // server side (full data)
            // TODO: possible bugged?! Other abilities must not see faced-down card as real on server side!
            String needName = "Forest";
            Assert.assertTrue("server side - must be face down", card.isFaceDown(currentGame));
            Assert.assertEquals("server side - wrong name", needName, card.getName());
            Assert.assertTrue("server side - wrong abilities", card.getAbilities(currentGame).stream().anyMatch(a -> !CardUtil.isInformationAbility(a))); // play + add mana

            // client side - controller (hidden data + original name)
            needName = "Face Down: Forest";
            Assert.assertEquals("controller - wrong name", needName, controllerCardView.getName());
            Assert.assertTrue("controller - must be face down", controllerCardView.isFaceDown());
            Assert.assertEquals("controller - must not have abilities", 0, controllerCardView.getRules().size());
            assertOriginalData("controller, original data", controllerCardView, 0, 0, "");

            // client side - opponent (hidden data)
            needName = "Face Down";
            Assert.assertTrue("opponent - must be face down", opponentCardView.isFaceDown());
            Assert.assertEquals("opponent - wrong name", needName, opponentCardView.getName());
            Assert.assertEquals("opponent - must not have abilities", 0, opponentCardView.getRules().size());
            assertOriginalData("opponent, original data", opponentCardView, 0, 0, "");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_FaceDown_ForetellInExile_MustGetDefaultImage() {
        // Foretell {1}{U}
        addCard(Zone.HAND, playerA, "Behold the Multiverse", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // exile face down as foretell
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // check face down card
        runCode("on face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card card = currentGame.getExile().getAllCards(currentGame, playerA.getId()).get(0);
            GameView gameView = getGameView(playerA);
            CardView controllerCardView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);
            gameView = getGameView(playerB);
            CardView opponentCardView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);

            // server side (full data)
            // TODO: possible bugged?! Other abilities must not see faced-down card as real on server side!
            String needName = "Behold the Multiverse";
            Assert.assertTrue("server side - must be face down", card.isFaceDown(currentGame));
            Assert.assertEquals("server side - wrong name", needName, card.getName());
            Assert.assertTrue("server side - wrong abilities", card.getAbilities(currentGame).stream().anyMatch(a -> !CardUtil.isInformationAbility(a)));

            // client side - controller (hidden data + original name)
            needName = "Foretell: Behold the Multiverse";
            Assert.assertEquals("controller - wrong name", needName, controllerCardView.getName());
            Assert.assertTrue("controller - must be face down", controllerCardView.isFaceDown());
            Assert.assertEquals("controller - must not have abilities", 0, controllerCardView.getRules().size());
            assertOriginalData("controller, original data", controllerCardView, 0, 0, "");

            // client side - opponent (hidden data)
            needName = "Foretell";
            Assert.assertTrue("opponent - must be face down", opponentCardView.isFaceDown());
            Assert.assertEquals("opponent - wrong name", needName, opponentCardView.getName());
            Assert.assertEquals("opponent - must not have abilities", 0, opponentCardView.getRules().size());
            assertOriginalData("opponent, original data", opponentCardView, 0, 0, "");
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
