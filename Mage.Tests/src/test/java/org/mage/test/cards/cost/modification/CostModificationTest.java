package org.mage.test.cards.cost.modification;

import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward, JayDi85
 */
public class CostModificationTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Helm of Awakening"); //-1
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben"); //+1
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCardTrinisphere() {
        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben"); //+1
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, 1);
    }

    /**
     * Trinisphere interacts incorrectly with Phyrexian mana.
     * As implemented, Gitaxian Probe gets a required cost of {2}{U/P}, which allows paying 2 life and only 2 mana.
     * This is incorrect: Trinisphere requires that at least 3 mana be paid, and payment through life doesn't count.
     * (Source: http://blogs.magicjudges.org/rulestips/2012/08/how-trinisphere-works-with-phyrexian-mana/)
     */
    @Test
    public void testCardTrinispherePhyrexianMana() {
        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Trinisphere");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Look at target player's hand.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Gitaxian Probe"); // Sorcery {U/P}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Gitaxian Probe", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Test that cost reduction also works with mana source restriction Myr
     * Superion Spend only mana produced by creatures to cast Myr Superion
     * <p>
     * Etherium Sculptor {1}{U} Artifact Creature - Vedalken Artificer 1/2
     * Artifact spells you cast cost {1} less to cast.
     */
    @Test
    public void testCostReductionWithManaSourceRestrictionWorking() {
        // Artifact spells you cast cost {1} less to cast
        addCard(Zone.BATTLEFIELD, playerA, "Etherium Sculptor");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        // Myr Superion {2}
        // Spend only mana produced by creatures to cast Myr Superion.
        addCard(Zone.HAND, playerA, "Myr Superion");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Superion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Can be cast because mana was produced by a creature
        assertPermanentCount(playerA, "Myr Superion", 1);
    }

    @Test
    public void testCostReductionWithManaSourceRestrictionNotWorking() {
        addCard(Zone.BATTLEFIELD, playerA, "Etherium Sculptor");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerA, "Myr Superion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Myr Superion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must throw error about having 0 actions, but got:\n" + e.getMessage());
            }
        }
    }

    /*
     I had Goblin Electromancer, but somehow Pyretic Ritual wasn't cheaper however Desperate Ritual was.
     */
    @Test
    public void testCostReductionForPyreticRitual() {
        // Instant and sorcery spells you cast cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Electromancer");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // Add {R}{R}{R}.
        addCard(Zone.HAND, playerA, "Pyretic Ritual");
        // Fated Conflagration deals 5 damage to target creature or planeswalker.
        // If it's your turn, scry 2. (Look at the top two cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)
        addCard(Zone.HAND, playerA, "Fated Conflagration");

        addCard(Zone.BATTLEFIELD, playerB, "Carnivorous Moss-Beast"); // 4/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyretic Ritual");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fated Conflagration", "Carnivorous Moss-Beast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pyretic Ritual", 1);
        assertGraveyardCount(playerA, "Fated Conflagration", 1);
        assertGraveyardCount(playerB, "Carnivorous Moss-Beast", 1);
    }

    /*
     * Reported bug: Grand Arbiter Augustin IV makes moth spells you cast and your opponent cast {1} more. Should only affect opponent's spells costs.
     */
    @Test
    public void testArbiterIncreasingCostBothPlayers() {

        String gArbiter = "Grand Arbiter Augustin IV";
        String divination = "Divination";
        String doomBlade = "Doom Blade";

        /*
        Grand Arbiter Augustin IV {2}{W}{U}
            2/3 Legendary Creature - Human Advisor
            White spells you cast cost 1 less to cast.
            Blue spells you cast cost 1 less to cast.
            Spells your opponents cast cost 1 more to cast.
         */
        addCard(Zone.BATTLEFIELD, playerA, gArbiter);
        addCard(Zone.HAND, playerA, divination); // {2}{U} Sorcery: draw two cards
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.HAND, playerB, doomBlade);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2); // {1}{B} Instant: destroy target non-black creature

        // Divination should only cost {1}{U} now with the cost reduction in place for your blue spells.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, divination);

        // Doom Blade cast by the opponent should cost {2}{B} now with the cost increase in effect for opponent spells.
        checkPlayableAbility("Can't Doom Blade", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Doom", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, divination, 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 2);
        assertPermanentCount(playerA, gArbiter, 1);
        assertHandCount(playerB, doomBlade, 1);
    }

    /**
     * Zoetic Cavern's cast as creature cost is not modified as Animar, Soul of
     * Elements gains counters.
     */
    @Test
    public void ReduceCostToCastZoeticCavern() {

        // Protection from white and from black
        // Whenever you cast a creature spell, put a +1/+1 counter on Animar, Soul of Elements.
        // Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar.
        addCard(Zone.BATTLEFIELD, playerA, "Animar, Soul of Elements");

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        addCard(Zone.HAND, playerA, "Zoetic Cavern");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zoetic Cavern");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertCounterCount(playerA, "Animar, Soul of Elements", CounterType.P1P1, 3);

        assertHandCount(playerA, "Zoetic Cavern", 0);
        assertPermanentCount(playerA, "Zoetic Cavern", 0);

        assertTappedCount("Plains", false, 2); // 2 for 1st Lion 1 for 2nd lion and only 1 mana needed to cast face down Zoetic

    }

    /**
     * Confirm that Animar's cost reduction allows you to play spells that you wouldn't have enough mana for without it.
     */
    @Test
    public void AnimarSoulOfElementsTest() {

        // Protection from white and from black
        // Whenever you cast a creature spell, put a +1/+1 counter on Animar, Soul of Elements.
        // Creature spells you cast cost {1} less to cast for each +1/+1 counter on Animar.
        addCard(Zone.BATTLEFIELD, playerA, "Animar, Soul of Elements");

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.HAND, playerA, "Zoetic Cavern");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
        assertCounterCount(playerA, "Animar, Soul of Elements", CounterType.P1P1, 2);

        assertTappedCount("Plains", true, 3);
    }

    @Test
    public void test_ThatTargetSourceEffect_AccursedWitch_CanPlayWithReduction() {
        // creature 4/2
        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Accursed Witch");
        //
        // {1}{R} SORCERY
        // Grapeshot deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Mountain", 1); // play to add mana

        checkPlayableAbility("0 mana, can't", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", false);

        // add 1 mana, can cast by target
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        checkPlayableAbility("1 mana, can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", true);

        // cast with target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grapeshot", "Accursed Witch");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Grapeshot", 1);
    }

    @Test
    public void test_ThatTargetSourceEffect_AccursedWitch_CantPlayOnProtection() {
        // creature 4/2
        // Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Accursed Witch");
        //
        // {1}{R} SORCERY
        // Grapeshot deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Artifact — Equipment
        // Equip {2}
        // Equipped creature gets +2/+2 and has protection from red and from white.
        addCard(Zone.BATTLEFIELD, playerB, "Sword of War and Peace");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        // 1 mana, can cast by target
        checkPlayableAbility("1 mana, can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", true);

        // add protection from red
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {2}", "Accursed Witch");

        // can't cast cause can't target to red
        checkPlayableAbility("can't cast cause protection", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_ThatTargetSourceEffect_BorealElemental() {
        // use case: cost increase for getPlayable works only for no other targets available
        // so if you can targets another target then allows to cast (don't apply cost increase)

        // creature 3/4
        // Spells your opponents cast that target Boreal Elemental cost {2} more to cast.
        addCard(Zone.BATTLEFIELD, playerB, "Boreal Elemental");
        //
        // {R} instant
        // Engulfing Flames deals 1 damage to target creature. It can't be regenerated this turn.
        addCard(Zone.HAND, playerA, "Engulfing Flames");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // {1}{G}
        addCard(Zone.HAND, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // no second target, so must cost increase
        checkPlayableAbility("one target, can't play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Engulfing Flames", false);

        // prepare second target
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("two targets, can play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Engulfing Flames", true);

        // try to cast (only one target possible to targets/play)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Engulfing Flames");
        //addTarget(playerA, "Boreal Elemental"); // you can't target Boreal Elemental cause it will increase cost
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Engulfing Flames", 1);
    }

    @Test
    public void test_ThatTargetEnchantedPlayer_InfectiousCurse() {
        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        //
        // transformed: Infectious Curse
        // Enchant player
        // Spells you cast that target enchanted player cost {1} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Accursed Witch", 1); // 4/2
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // {1}{R} SORCERY
        // Grapeshot deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Grapeshot");

        checkPlayableAbility("no mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", false);

        // transform
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Accursed Witch");
        addTarget(playerA, playerB); // attach curse to
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("transformed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infectious Curse", 1);
        checkPlayableAbility("reduced, but no mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", false);

        // cast
        // possible bug: transform command can generate duplicated triggers (player get choose trigger dialog but must not)
        checkPlayableAbility("reduced, with mana", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grapeshot", true);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grapeshot", playerB);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkLife("a", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 20 + 1); // +1 from curse on turn 2
        checkLife("b", 3, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 1 - 1); // -1 from grapeshot, -1 from curse on turn 2

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_PlaneswalkerLoyalty_CostModification_Single() {
        // Carth the Lion
        // Planeswalkers' loyalty abilities you activate cost an additional {+1} to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Carth the Lion", 1);
        //
        // Vivien Reid
        // 5 Loyalty
        // +1, -3, -8 Abilities
        addCard(Zone.BATTLEFIELD, playerA, "Vivien Reid", 1);
        //
        // Huatli, Warrior Poet
        // 3 Loyalty
        // Testing X Ability
        // −X: Huatli, Warrior Poet deals X damage divided as you choose among any number of target creatures. Creatures dealt damage this way can’t block this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Huatli, Warrior Poet", 1);
        //
        // 2 toughness creatures for Huatli to kill
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ghitu Lavarunner", 1);

        // Vivien: make cost +2 instead +1 (total 7 counters)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Look at the top four");
        setChoice(playerA, false);
        checkPermanentCounters("Vivien Reid counter check", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vivien Reid", CounterType.LOYALTY, 7);

        // loyalty cost modification doesn't affect card rule's text, so it still shown an old cost value for a user

        // Vivien: make cost -7 instead -8
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-8: You get an emblem");

        // Huatli: check x cost changes
        runCode("check x cost", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent huatli = game.getBattlefield().getAllActivePermanents().stream().filter(p -> p.getName().equals("Huatli, Warrior Poet")).findFirst().orElse(null);
            Assert.assertNotNull("must have huatli on battlefield", huatli);
            LoyaltyAbility ability = (LoyaltyAbility) huatli.getAbilities(game).stream().filter(a -> a.getRule().startsWith("-X: ")).findFirst().orElse(null);
            Assert.assertNotNull("must have loyalty ability", ability);
            // counters: 3
            // cost modification: +1
            // max possible X to pay: 3 + 1 = 4
            PayVariableLoyaltyCost cost = (PayVariableLoyaltyCost) ability.getCosts().get(0);
            Assert.assertEquals("must have max possible X as 4", 4, cost.getMaxValue(ability, game));
        });

        // Huatli: make x cost -3 instead -4
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-X: {this} deals X damage divided as you choose");
        setChoice(playerA, "X=4");
        addTargetAmount(playerA, "Grizzly Bears", 2);
        addTargetAmount(playerA, "Ghitu Lavarunner", 2);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Vivien Reid", 1);
        assertGraveyardCount(playerA, "Huatli, Warrior Poet", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Ghitu Lavarunner", 1);
    }

    @Test
    public void test_PlaneswalkerLoyalty_CostModification_Multiple() {
        // Carth the Lion
        // Planeswalkers' loyalty abilities you activate cost an additional {+1} to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Carth the Lion", 1);
        //
        // Vivien Reid
        // 5 Loyalty
        // +1, -3, -8 Abilities
        addCard(Zone.BATTLEFIELD, playerA, "Vivien Reid", 1);
        //
        // You may have Spark Double enter the battlefield as a copy of a creature or planeswalker you control...
        addCard(Zone.HAND, playerA, "Spark Double", 2); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 * 4);

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8:", false);

        // prepare duplicates
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, true); // copy
        setChoice(playerA, "Carth the Lion"); // copy target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");
        setChoice(playerA, true); // copy
        setChoice(playerA, "Carth the Lion"); // copy target

        // x3 lions gives +3 in cost reduction (-8 -> -5)
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "-8:", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }
}
