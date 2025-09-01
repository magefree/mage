package org.mage.test.cards.cost.additional;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

import java.util.Arrays;

/**
 * @author JayDi85
 */
public class AdjusterCostTest extends CardTestPlayerBaseWithAIHelps {

    private void prepareCustomCardInHand(String cardName, String spellManaCost, CostAdjuster costAdjuster) {
        SpellAbility spellAbility = new SpellAbility(new ManaCostsImpl<>(spellManaCost), cardName);
        if (costAdjuster != null) {
            spellAbility.setCostAdjuster(costAdjuster);
        }
        addCustomCardWithAbility(
                cardName,
                playerA,
                null,
                spellAbility,
                CardType.ENCHANTMENT,
                spellManaCost,
                Zone.HAND
        );
    }

    private void prepareCustomPermanent(String cardName, String abilityName, String abilityManaCost, CostAdjuster costAdjuster) {
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new CreatureToken(1, 1).withName("test token")).setText(abilityName),
                new ManaCostsImpl<>(abilityManaCost)
        );
        if (costAdjuster != null) {
            ability.setCostAdjuster(costAdjuster);
        }
        addCustomCardWithAbility(cardName, playerA, ability);
    }

    @Test
    public void test_DistributeValues() {
        // make sure it can distribute values between min and max and skip useless values (example: mana optimization)

        Assert.assertEquals(Arrays.asList(), CardUtil.distributeValues(0, 0, 0));
        Assert.assertEquals(Arrays.asList(), CardUtil.distributeValues(0, -10, 10));
        Assert.assertEquals(Arrays.asList(), CardUtil.distributeValues(0, Integer.MIN_VALUE, Integer.MAX_VALUE));

        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(1, 0, 0));
        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(1, 0, 1));
        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(1, 0, 2));
        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(1, 0, 3));
        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(1, 0, 9));

        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(2, 0, 0));
        Assert.assertEquals(Arrays.asList(0, 1), CardUtil.distributeValues(2, 0, 1));
        Assert.assertEquals(Arrays.asList(0, 2), CardUtil.distributeValues(2, 0, 2));
        Assert.assertEquals(Arrays.asList(0, 3), CardUtil.distributeValues(2, 0, 3));
        Assert.assertEquals(Arrays.asList(0, 9), CardUtil.distributeValues(2, 0, 9));

        Assert.assertEquals(Arrays.asList(0), CardUtil.distributeValues(3, 0, 0));
        Assert.assertEquals(Arrays.asList(0, 1), CardUtil.distributeValues(3, 0, 1));
        Assert.assertEquals(Arrays.asList(0, 1, 2), CardUtil.distributeValues(3, 0, 2));
        Assert.assertEquals(Arrays.asList(0, 2, 3), CardUtil.distributeValues(3, 0, 3));
        Assert.assertEquals(Arrays.asList(0, 5, 9), CardUtil.distributeValues(3, 0, 9));

        Assert.assertEquals(Arrays.asList(10, 15, 20), CardUtil.distributeValues(3, 10, 20));
        Assert.assertEquals(Arrays.asList(10, 16, 21), CardUtil.distributeValues(3, 10, 21));
        Assert.assertEquals(Arrays.asList(10, 16, 22), CardUtil.distributeValues(3, 10, 22));
        Assert.assertEquals(Arrays.asList(10, 17, 23), CardUtil.distributeValues(3, 10, 23));
        Assert.assertEquals(Arrays.asList(10, 20, 29), CardUtil.distributeValues(3, 10, 29));

        Assert.assertEquals(Arrays.asList(10), CardUtil.distributeValues(5, 10, 10));
        Assert.assertEquals(Arrays.asList(10, 11), CardUtil.distributeValues(5, 10, 11));
        Assert.assertEquals(Arrays.asList(10, 11, 12), CardUtil.distributeValues(5, 10, 12));
        Assert.assertEquals(Arrays.asList(10, 11, 12, 13), CardUtil.distributeValues(5, 10, 13));
        Assert.assertEquals(Arrays.asList(10, 11, 13, 14, 15), CardUtil.distributeValues(5, 10, 15));
        Assert.assertEquals(Arrays.asList(10, 13, 15, 18, 20), CardUtil.distributeValues(5, 10, 20));
    }

    @Test
    public void test_X_SpellAbility() {
        prepareCustomCardInHand("test card", "{X}{1}", null);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "test card");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "test card", 1);
    }

    @Test
    public void test_X_ActivatedAbility() {
        prepareCustomPermanent("test card", "test ability", "{X}{1}", null);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{1}:");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "test token", 1);
    }

    @Test
    public void test_prepareX_SpellAbility_TestFrameworkMustCatchLimits() {
        prepareCustomCardInHand("test card", "{X}{1}", new CostAdjuster() {
            @Override
            public void prepareX(Ability ability, Game game) {
                ability.setVariableCostsMinMax(0, 1);
            }
        });
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "test card");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertTrue("X must have limits: " + e.getMessage(), e.getMessage().contains("Found wrong X value = 2"));
            Assert.assertTrue("X must have limits: " + e.getMessage(), e.getMessage().contains("from 0 to 1"));
            return;
        }
        Assert.fail("test must fail");
    }

    @Test
    @Ignore // TODO: AI must support game simulations for X choice, see announceXMana
    public void test_prepareX_SpellAbility_AI() {
        prepareCustomCardInHand("test card", "{X}{1}", new CostAdjuster() {
            @Override
            public void prepareX(Ability ability, Game game) {
                ability.setVariableCostsMinMax(0, 10);
            }
        });
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // AI must play card and use min good value
        // it's bad to set X=1 for battlefield score cause card will give same score for X=0, X=1
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "test card", 1);
        assertTappedCount("Forest", true, 1); // must choose X=0
    }

    @Test
    public void test_prepareX_ActivatedAbility_TestFrameworkMustCatchLimits() {
        prepareCustomPermanent("test card", "test ability", "{X}{1}", new CostAdjuster() {
            @Override
            public void prepareX(Ability ability, Game game) {
                ability.setVariableCostsMinMax(0, 1);
            }
        });
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{1}:");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
        } catch (AssertionError e) {
            Assert.assertTrue("X must have limits: " + e.getMessage(), e.getMessage().contains("Found wrong X value = 2"));
            Assert.assertTrue("X must have limits: " + e.getMessage(), e.getMessage().contains("from 0 to 1"));
            return;
        }
        Assert.fail("test must fail");
    }

    @Test
    @Ignore // TODO: AI must support game simulations for X choice, see announceXMana
    // TODO: implement AI and add tests for non-mana X values (announceXCost)
    public void test_prepareX_ActivatedAbility_AI() {
        prepareCustomPermanent("test card", "test ability", "{X}{1}", new CostAdjuster() {
            @Override
            public void prepareX(Ability ability, Game game) {
                ability.setVariableCostsMinMax(0, 10);
            }
        });
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // AI must activate ability with min good value for X
        // it's bad to set X=1 for battlefield score cause card will give same score for X=0, X=1
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "test token", 1);
        assertTappedCount("Forest", true, 1); // must choose X=0
    }

    @Test
    public void test_prepareX_SpellAbility_ScorchedEarth_PayZero() {
        // with X announce

        // As an additional cost to cast this spell, discard X land cards.
        // Destroy X target lands.
        addCard(Zone.HAND, playerA, "Scorched Earth"); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 0 + 1);
        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scorched Earth");
        setChoice(playerA, "X=0");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_prepareX_SpellAbility_ScorchedEarth_PaySome() {
        // with X announce

        // As an additional cost to cast this spell, discard X land cards.
        // Destroy X target lands.
        addCard(Zone.HAND, playerA, "Scorched Earth"); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        addCard(Zone.HAND, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scorched Earth");
        setChoice(playerA, "X=2");
        addTarget(playerA, "Forest", 2); // to destroy
        setChoice(playerA, "Island", 2); // discard cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_prepareX_SpellAbility_ScorchedEarth_PayAll() {
        // with X announce

        // As an additional cost to cast this spell, discard X land cards.
        // Destroy X target lands.
        addCard(Zone.HAND, playerA, "Scorched Earth"); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10 + 1);
        addCard(Zone.HAND, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scorched Earth");
        setChoice(playerA, "X=10");
        addTarget(playerA, "Forest", 10); // to destroy
        setChoice(playerA, "Island", 10); // discard cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_prepareX_ActivatedAbility_BargainingTable_PayZero() {
        // with direct X (without announce)

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Bargaining Table");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        // no cards in opponent's hand

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}:");
        setChoice(playerA, playerB.getName());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_prepareX_ActivatedAbility_BargainingTable_PaySome() {
        // with direct X (without announce)

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Bargaining Table");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        addCard(Zone.HAND, playerB, "Forest", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}:");
        setChoice(playerA, playerB.getName());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_prepareX_ActivatedAbility_BargainingTable_CantPay() {
        // with direct X (without announce)

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Bargaining Table");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerB, "Forest", 3);

        // must not request opponent choice because it must see min hand size as 3
        checkPlayableAbility("must not able to activate due lack of mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}:", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    public void test_prepareX_ActivatedAbility_BargainingTable_UnboundFlourishingMustCopy() {
        // with direct X (without announce)

        // {X}, {T}: Draw a card. X is the number of cards in an opponent's hand.
        addCard(Zone.BATTLEFIELD, playerA, "Bargaining Table");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //
        addCard(Zone.HAND, playerB, "Forest", 3);
        //
        // Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X.
        // Whenever you cast an instant or sorcery spell or activate an ability, if that spell’s mana cost or that
        // ability’s activation cost contains {X}, copy that spell or ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing", 1);

        // Unbound Flourishing must see {X} mana cost and duplicate ability on stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}:");
        setChoice(playerA, playerB.getName());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 2); // from original and copied abilities
    }

    @Test
    public void test_prepareX_NecropolisFiend() {
        // {X}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Necropolis Fiend");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Bronze Dragon"); // 7/7

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}, Exile");
        setChoice(playerA, "X=2");
        addTarget(playerA, "Ancient Bronze Dragon"); // to -2/-2
        setChoice(playerA, "Grizzly Bears", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, "Ancient Bronze Dragon", 7 - 2, 7 - 2);
    }

    @Test
    public void test_prepareX_OpenTheWay() {
        skipInitShuffling();

        // X can't be greater than the number of players in the game.
        // Reveal cards from the top of your library until you reveal X land cards.
        // Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order.
        addCard(Zone.HAND, playerA, "Open the Way"); // {X}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2);
        addCard(Zone.LIBRARY, playerA, "Island", 5);

        // min/max test require multiple tests (see above), so just disable setChoice and look at logs for good limits
        // example: Message: Announce the value for {X} (any value)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Open the Way");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Island", 2);
    }

    @Test
    public void test_prepareX_KnollspineInvocation() {
        skipInitShuffling();

        // {X}, Discard a card with mana value X: This enchantment deals X damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Knollspine Invocation");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1); // {1}{G}

        // turn 1 - can't play due empty hand
        checkPlayableAbility("no cards to discard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard", false);

        // turn 3 - can't play due no mana
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {G}", 2);
        checkPlayableAbility("no mana to activate", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard", false);

        // turn 5 - can play
        checkPlayableAbility("must able to activate", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard", true);
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard");
        setChoice(playerA, "Grizzly Bears"); // discard
        addTarget(playerA, playerB); // damage

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_prepareX_EliteArcanist() {
        // When Elite Arcanist enters the battlefield, you may exile an instant card from your hand.
        // {X}, {T}: Copy the exiled card. You may cast the copy without paying its mana cost. X is the converted mana cost of the exiled card.
        addCard(Zone.HAND, playerA, "Elite Arcanist"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4 + 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        // turn 1
        // prepare arcanist
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Arcanist");
        setChoice(playerA, true); // use exile
        setChoice(playerA, "Lightning Bolt"); // to exile and copy later

        // turn 3
        // cast copy
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}: Copy");
        setChoice(playerA, true); // cast copy
        addTarget(playerA, playerB); // damage

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Island", true, 1); // used to cast copy for {1}
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_prepareX_AladdinsLamp() {
        skipInitShuffling();

        // {X}, {T}: The next time you would draw a card this turn, instead look at the top X cards of your library,
        // put all but one of them on the bottom of your library in a random order, then draw a card. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Aladdin's Lamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // {T}: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Archivist");
        addCard(Zone.LIBRARY, playerA, "Island", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 3);

        // prepare effect
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}: The next time");
        setChoice(playerA, "X=5");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // improved draw
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");
        setChoice(playerA, "Grizzly Bears"); // keep on top and draw

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_modifyCost_Fireball() {
        // This spell costs {1} more to cast for each target beyond the first.
        // Fireball deals X damage divided evenly, rounded down, among any number of targets.
        addCard(Zone.HAND, playerA, "Fireball"); // {X}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 1); // 3 for x=2 cast, 1 for x2 targets
        //
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 3); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fireball");
        setChoice(playerA, "X=2");
        addTarget(playerA, "Arbor Elf^Arbor Elf");
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertTappedCount("Mountain", true, 3 + 1); // 3 for x=2 cast, 1 for x2 targets
        assertGraveyardCount(playerA, "Arbor Elf", 2);
        assertPermanentCount(playerA, "Arbor Elf", 1);
    }

    @Test
    public void test_modifyCost_DeepwoodDenizen() {
        // {5}{G}, {T}: Draw a card. This ability costs {1} less to activate for each +1/+1 counter on creatures you control.
        addCard(Zone.BATTLEFIELD, playerA, "Deepwood Denizen");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6 - 3);
        //
        // +1: Distribute three +1/+1 counters among one, two, or three target creatures you control
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Mentor of Heroes", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Arbor Elf", 1);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{G}, {T}: Draw", false);

        // add +3 counters and get -3 cost decrease
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Distribute");
        addTargetAmount(playerA, "Arbor Elf", 2);
        addTargetAmount(playerA, "Deepwood Denizen", 1);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{G}, {T}: Draw", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{G}, {T}: Draw");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1); // +1 from draw ability
    }

    @Test
    public void test_modifyCost_BaruWurmspeaker() {
        // Wurms you control get +2/+2 and have trample.
        // {7}{G}, {T}: Create a 4/4 green Wurm creature token. This ability costs {X} less to activate, where X is the greatest power among Wurms you control.
        addCard(Zone.BATTLEFIELD, playerA, "Baru, Wurmspeaker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        // When this creature dies, create a 3/3 colorless Phyrexian Wurm artifact creature token with deathtouch
        // and a 3/3 colorless Phyrexian Wurm artifact creature token with lifelink.
        addCard(Zone.HAND, playerA, "Wurmcoil Engine", 1); // {6}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G}, {T}: Create", false);

        // turn 1
        // prepare wurm and get -8 cost decrease (6 wurm + 2 boost)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wurmcoil Engine");

        // turn 3
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("after", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G}, {T}: Create", true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G}, {T}: Create");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertTokenCount(playerA, "Wurm Token", 1);
        assertTappedCount("Forest", true, 1);
        assertTappedCount("Mountain", true, 0);
    }

    @Test
    public void test_Other_AbandonHope() {
        // used both modify and cost reduction in one cost adjuster

        // As an additional cost to cast this spell, discard X cards.
        // Look at target opponent's hand and choose X cards from it. That player discards those cards.
        addCard(Zone.HAND, playerA, "Abandon Hope"); // {X}{1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 2);
        addCard(Zone.HAND, playerA, "Forest", 2);
        addCard(Zone.HAND, playerB, "Grizzly Bears", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Abandon Hope");
        setChoice(playerA, "X=2");
        addTarget(playerA, playerB);
        setChoice(playerA, "Forest^Forest"); // discard cost
        setChoice(playerA, "Grizzly Bears^Grizzly Bears"); // discard from hand

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 2);
        assertGraveyardCount(playerB, "Grizzly Bears", 2);
    }

    // additional tasks to improve code base:
    // TODO: OsgirTheReconstructorCostAdjuster - migrate to EarlyTargetCost
    // TODO: SkeletalScryingAdjuster - migrate to EarlyTargetCost
    // TODO: NecropolisFiend - migrate to EarlyTargetCost
    // TODO: KnollspineInvocation - migrate to EarlyTargetCost
    // TODO: ExileCardsFromHandAdjuster - need rework to remove dialog from inside, e.g. migrate to EarlyTargetCost?
    // TODO: CallerOfTheHuntAdjuster - research and add test
    // TODO: VoodooDoll - research and add test
}
