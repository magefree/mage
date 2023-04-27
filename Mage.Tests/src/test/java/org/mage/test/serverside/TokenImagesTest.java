package org.mage.test.serverside;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.repository.TokenRepository;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.custom.CreatureToken;
import mage.util.CardUtil;
import mage.view.CardView;
import mage.view.GameView;
import mage.view.PermanentView;
import mage.view.PlayerView;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.*;
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
        assertExileCount(playerA, cardName, cardAmountInExile);
        assertGraveyardCount(playerA, cardName, cardAmountInGrave);
        assertPermanentCount(playerA, cardName, cardAmountInBattlefield);
        assertPermanentCount(playerA, tokenName, tokenAmount);

        // collect real server stats
        Map<String, List<Card>> realServerStats = new LinkedHashMap<>();
        currentGame.getBattlefield().getAllPermanents()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
                .sorted(Comparator.comparing(Card::getExpansionSetCode))
                .forEach(card -> {
                    Assert.assertNotNull("must have set code", card.getExpansionSetCode());
                    Assert.assertEquals("must have same set codes in all fields",
                            card.getExpansionSetCode(),
                            ((PermanentToken) card).getToken().getOriginalExpansionSetCode()
                    );
                    String realCode = card.getExpansionSetCode();
                    realServerStats.computeIfAbsent(realCode, code -> new ArrayList<>());
                    realServerStats.get(realCode).add(card);
                });

        // collect real client stats
        Map<String, List<PermanentView>> realClientStats = new LinkedHashMap<>();
        GameView gameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        PlayerView playerView = gameView.getPlayers().stream().filter(p -> p.getName().equals(playerA.getName())).findFirst().orElse(null);
        Assert.assertNotNull(playerView);
        playerView.getBattlefield().values()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
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
                    .mapToInt(card -> card.getCardNumber() == null ? 0 : CardUtil.parseCardNumberAsInt(card.getCardNumber()))
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

    private void assert_TokenTypes(String tokenName, int needTokenTypes) {
        Set<Integer> serverStats = currentGame.getBattlefield().getAllPermanents()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
                .sorted(Comparator.comparing(Card::getExpansionSetCode))
                .map(card -> (PermanentToken) card)
                .map(perm -> perm.getToken().getTokenType())
                .collect(Collectors.toSet());

        GameView gameView = new GameView(currentGame.getState(), currentGame, playerA.getId(), null);
        PlayerView playerView = gameView.getPlayers()
                .stream()
                .filter(p -> p.getName().equals(playerA.getName()))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(playerView);
        Set<Integer> clientStats = playerView.getBattlefield().values()
                .stream()
                .filter(card -> card.getName().equals(tokenName))
                .sorted(Comparator.comparing(CardView::getExpansionSetCode))
                .map(CardView::getType)
                .collect(Collectors.toSet());

        // server and client sides must have same data
        Assert.assertEquals(serverStats.size(), clientStats.size());
        Assert.assertEquals(needTokenTypes, serverStats.size());
        Assert.assertEquals(needTokenTypes, clientStats.size());
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
        assert_TokenTypes("Soldier Token", 3); // 40K set contains 3 diffrent soldiers
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
    @Ignore
    // TODO: implement auto-generate creature token images from public tokens (by name, type, color, PT, abilities)
    public void test_CreatureToken_MustGetDefaultImage() {
        Ability ability = new SimpleActivatedAbility(
                Zone.ALL,
                new CreateTokenEffect(new CreatureToken(2, 2), 10),
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
                "", 10, false, "XXX=10");
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
}
