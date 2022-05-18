package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, JayDi85
 */
public class SuspendTest extends CardTestPlayerBase {

    /**
     * Tests Epochrasite works (give suspend to a exiled card) When Epochrasite
     * dies, exile it with three time counters on it and it gains suspend.
     */
    @Test
    public void test_Single_Epochrasite() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Epochrasite enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand.
        // When Epochrasite dies, exile it with three time counters on it and it gains suspend.
        addCard(Zone.HAND, playerA, "Epochrasite", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Epochrasite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Epochrasite");

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Epochrasite", 1); // returned on turn 7 with 3 +1/+1 Counter
        assertPowerToughness(playerA, "Epochrasite", 4, 4);
        assertAbility(playerA, "Epochrasite", HasteAbility.getInstance(), true);

    }

    /**
     * Tests Jhoira of the Ghitu works (give suspend to a exiled card) {2},
     * Exile a nonland card from your hand: Put four time counters on the exiled
     * card. If it doesn't have suspend, it gains suspend.
     */
    @Test
    public void test_Single_JhoiraOfTheGhitu() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Jhoira of the Ghitu", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Exile a nonland card from your hand: Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(11, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Jhoira of the Ghitu", 1);
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

    }

    /**
     * Tests that a spell countered with delay goes to exile with 3 time
     * counters and can be cast after the 3 counters are removed
     */
    @Test
    public void test_Single_Delay() {

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        // Instant {1}{U}
        // Counter target spell.
        // If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard.
        // If it doesn't have suspend, it gains suspend.
        // (At the beginning of its owner's upkeep, remove a counter from that card.
        // When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Delay", "Silvercoat Lion");

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Delay", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_Single_DeepSeaKraken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Suspend 9-{2}{U}
        // Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.
        addCard(Zone.HAND, playerA, "Deep-Sea Kraken", 1);

        // Instant {1}{U}
        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend. (At the beginning of its owner's upkeep, remove a counter from that card. When the last is removed, the player plays it without paying its mana cost. If it's a creature, it has haste.)
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertExileCount("Deep-Sea Kraken", 1);

        assertCounterOnExiledCardCount("Deep-Sea Kraken", CounterType.TIME, 8); // -1 from spell of player B

    }

    @Test
    public void test_Single_AncestralVisionCantBeCastDirectly() {
        // Suspend 4-{U}
        // Target player draws three cards.
        addCard(Zone.HAND, playerA, "Ancestral Vision", 1);

        checkPlayableAbility("Can't cast directly", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ancestral", false);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Vision", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Ancestral Vision", 1);

    }

    /**
     * Suppression Field incorrectly makes suspend cards cost 2 more to suspend.
     * It made my Rift Bolt cost 2R to suspend instead of R
     */
    @Test
    public void test_CostManipulation() {
        // Rift Bolt deals 3 damage to any target.
        // Suspend 1-{R}
        addCard(Zone.HAND, playerA, "Rift Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Suppression Field", 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Rift Bolt", 0);

    }

    /**
     * Cards cast from other zones that aren't the hand should not trigger
     * Knowledge Pool, as it states that only cards cast from the hand should be
     * exiled afterwards.
     * <p>
     * Example: cards coming off suspend shouldn't trigger Knowledge Pool.
     */
    @Test
    public void test_ThatNotCastFromHand() {

        // Rift Bolt deals 3 damage to any target.
        // Suspend 1-{R}
        addCard(Zone.HAND, playerA, "Rift Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 3);
        // Imprint - When Knowledge Pool enters the battlefield, each player exiles the top three cards of their library
        // Whenever a player casts a spell from their hand, that player exiles it. If the player does, they may cast another nonland card
        // exiled with Knowledge Pool without paying that card's mana cost.
        addCard(Zone.HAND, playerB, "Knowledge Pool", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suspend");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Knowledge Pool");

        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Knowledge Pool", 1);
        assertHandCount(playerA, "Rift Bolt", 0);
        assertGraveyardCount(playerA, "Rift Bolt", 1);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);

    }

    /*
    Delay {1}{U}

    Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting
    it into its owner’s graveyard. If it doesn’t have suspend, it gains suspend. (At the beginning of its owner’s upkeep,
    remove a time counter from that card. When the last is removed, the player plays it without paying its mana cost.
    If it’s a creature, it has haste.)

    Bug: Casting Delay on a fused Wear // Tear resulted in time counters never coming off it. It just sat there with
    three counters every turn. See https://github.com/magefree/mage/issues/6549
    */

    @Test
    public void test_Delay_SimpleSpell() {
        //
        addCard(Zone.HAND, playerA, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // cast spell and counter it with delay
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delay", "Lightning Bolt", "Lightning Bolt");
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20);
        checkExileCount("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        // 3 time counters removes on upkeep (3, 5, 7) and cast again
        addTarget(playerA, playerB);
        checkLife("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 3);
        checkGraveyardCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Delay_SplitSingle() {
        addCard(Zone.HAND, playerA, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.HAND, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // cast spell and counter it with delay
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear", "Bident of Thassa");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delay", "Wear", "Wear");
        //
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Bident of Thassa", 1);
        checkPermanentCount("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Bow of Nylea", 1);
        checkExileCount("after counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear // Tear", 1);

        // 3 time counters removes on upkeep (3, 5, 7) and cast again
        addTarget(playerA, "Bident of Thassa");
        checkPermanentCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerB, "Bident of Thassa", 0);
        checkPermanentCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerB, "Bow of Nylea", 1);
        checkGraveyardCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear // Tear", 1);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_Delay_SplitFused() {
        /*
        Bug: Casting Delay on a fused Wear // Tear resulted in time counters never coming off it. It just sat there with
        three counters every turn. See https://github.com/magefree/mage/issues/6549
        */

        //
        addCard(Zone.HAND, playerA, "Delay", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.HAND, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // cast fused spell and counter it with delay
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Wear // Tear");
        addTarget(playerA, "Bident of Thassa");
        addTarget(playerA, "Bow of Nylea");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delay", "Cast fused Wear // Tear", "Cast fused Wear // Tear");
        //
        checkPermanentCount("after counter", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bident of Thassa", 1);
        checkPermanentCount("after counter", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bow of Nylea", 1);
        checkExileCount("after counter", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Wear // Tear", 1);

        // 3 time counters removes on upkeep (3, 5, 7) and cast again (fused cards can't be played from exile zone, so select split spell only)
        setChoice(playerA, "Cast Wear");
        addTarget(playerA, "Bident of Thassa");
        checkPermanentCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerB, "Bident of Thassa", 0);
        checkPermanentCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerB, "Bow of Nylea", 1);
        checkGraveyardCount("after suspend", 7, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear // Tear", 1);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();
    }

    @Test
    public void test_OnlyOwnerCanActivateSuspend() {
        // bug: you or AI can activate suspend from opponent's hand

        // Suspend 5-{G}
        String suspendA = "Suspend 5"; // owner is player A
        addCard(Zone.HAND, playerA, "Durkwood Baloth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        //
        // Suspend 3—{0}
        String suspendB = "Suspend 3"; // owner is player B
        addCard(Zone.HAND, playerB, "Mox Tantalite", 1);

        // turn 1 - A can own, B can't
        checkPlayableAbility("T1 - Player A can own", 1, PhaseStep.PRECOMBAT_MAIN, playerA, suspendA, true);
        checkPlayableAbility("T1 - Player A can't opponent", 1, PhaseStep.PRECOMBAT_MAIN, playerA, suspendB, false);
        checkPlayableAbility("T1 - Player B can't own", 1, PhaseStep.PRECOMBAT_MAIN, playerB, suspendB, false);
        checkPlayableAbility("T1 - Player B can't opponent", 1, PhaseStep.PRECOMBAT_MAIN, playerB, suspendA, false);

        // turn 2 - A can't, B can own
        checkPlayableAbility("T2 - Player A can't own", 2, PhaseStep.PRECOMBAT_MAIN, playerA, suspendA, false);
        checkPlayableAbility("T2 - Player A can't opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerA, suspendB, false);
        checkPlayableAbility("T2 - Player B can own", 2, PhaseStep.PRECOMBAT_MAIN, playerB, suspendB, true);
        checkPlayableAbility("T2 - Player B can't opponent", 2, PhaseStep.PRECOMBAT_MAIN, playerB, suspendA, false);

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, suspendB);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, "Mox Tantalite", 1); // suspended
    }
}
