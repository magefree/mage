package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.t.ThreefoldSignal Threefold Signal}
 * <p>
 * Each spell you cast that’s exactly three colors has replicate {3}.
 * @author Alex-Vasile
 */
public class ThreefoldSignalTest extends CardTestPlayerBase {

    private static final String threefoldSignal = "Threefold Signal";
    // R
    private static final String lightningBolt = "Lightning Bolt";
    // WUBRG
    private static final String atogatog = "Atogatog";
    // WUB
    private static final String esperSojourners = "Esper Sojourners";

    /**
     * Check that it works for three-colored spells
     */
    @Test
    public void testShouldWork() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, esperSojourners);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, esperSojourners);
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, esperSojourners, 2);
    }

    /**
     * Check that it does not trigger for spells with less than three colors.
     */
    @Test
    public void testShouldNotWork1Color() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, lightningBolt);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerB, 17);
    }

    /**
     * Check that it does not trigger for spells with more than three colors.
     */
    @Test
    public void testShouldNotWork5Color() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, atogatog);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, atogatog);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, atogatog, 1);
    }

    /**
     * Check that it does not trigger for spells opponents control.
     */
    @Test
    public void testShouldNotWorkOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, esperSojourners);

        setStrictChooseMode(true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, esperSojourners);

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerB, esperSojourners, 1);
    }

    /**
     * Check that casting one half of a split card doesn't trigger it even if the whole split card has 3 colors.
     * Relevant ruling:
     *      709.3a Only the chosen half is evaluated to see if it can be cast.
     *             Only that half is considered to be put onto the stack.
     *      709.3b While on the stack, only the characteristics of the half being cast exist.
     *             The other half’s characteristics are treated as though they didn’t exist.
     */
    @Test
    public void oneHalfOfSplitCardDoesntTrigger() {
        // {G}{U} / {4}{W}{U}
        // Beck: Whenever a creature enters the battlefield this turn, you may draw a card.
        // Call: Create four 1/1 white Bird creature tokens with flying.
        // Fuse
        String beckCall = "Beck // Call";
        addCard(Zone.HAND, playerA, beckCall);

        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4 + 3); // For generic costs and to have enough for replicate

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Call");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Bird Token", 4);
    }

    /**
     * Test that casting a split card with fuse triggers if both halves together have 3 colors
     */
    @Test
    public void fusedSplitCardTriggers() {
        // {G}{U} / {4}{W}{U}
        // Beck: Whenever a creature enters the battlefield this turn, you may draw a card.
        // Call: Create four 1/1 white Bird creature tokens with flying.
        // Fuse
        String beckCall = "Beck // Call";
        addCard(Zone.HAND, playerA, beckCall);

        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4 + 3); // For generic costs and to have enough for replicate

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Beck // Call");
        setChoice(playerA, true); // Pay replicate once
        setChoice(playerA, false); // Don't pay replicate twice

        // Copy resolves, first Beck then call
        setChoice(playerA, "Whenever", 3); // 4 triggers total, pick order for 3 and the 4th is auto-chosen
        setChoice(playerA, true, 4); // Draw cards 4 times

        // Original resolves
        // There will be 8 ETB triggers. 4 creatures enter but there are 2 instances of Beck that were cast
        setChoice(playerA, "Whenever", 7); // 8 triggers total, pick order for 7 and the 8th is auto-chosen
        setChoice(playerA, true, 8); // Draw cards 8 times


        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Bird Token", 4 + 4);
        assertHandCount(playerA, 4 + (4+4));
    }

    /**
     * Test that casting a split card with fuse triggers if both halves together have 3 colors
     */
    @Test
    public void fusedSplitCardTriggers2() {
        // {3}{B}{G} / {R}{G}
        // Flesh: Exile target creature card from a graveyard.
        //        Put X +1/+1 counters on target creature, where X is the power of the card you exiled.
        // Blood: Target creature you control deals damage equal to its power to any target.
        // Fuse
        String fleshBlood = "Flesh // Blood";
        addCard(Zone.HAND, playerA, fleshBlood);

        addCard(Zone.BATTLEFIELD, playerA, threefoldSignal);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1+3 + 3); // For red + generic costs and to have enough for replicate

        // All are 2/2
        String lion = "Silvercoat Lion";
        addCard(Zone.BATTLEFIELD, playerA, lion);
        String griffin = "Abbey Griffin";
        addCard(Zone.GRAVEYARD, playerA, griffin); // Exile with original cast
        String centaur = "Accursed Centaur";
        addCard(Zone.GRAVEYARD, playerA, centaur); // Exile with copy

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Flesh // Blood");
        setChoice(playerA, true); // Pay replicate once
        setChoice(playerA, false); // Don't pay replicate twice
        // Flesh
        addTarget(playerA, griffin);
        addTarget(playerA, lion);
        // Blood
        addTarget(playerA, lion);
        addTarget(playerA, playerB);
        // Copy of Flesh
        setChoice(playerA, true); // Change the exile card from the Griffin
        addTarget(playerA, centaur);
        setChoice(playerA, false); // Don't change target from the lion
        // Copy of Blood
        setChoice(playerA, false); // Don't change target from lion
        setChoice(playerA, false); // Don't change target from PlayerB

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(lion, CounterType.P1P1, 4); // 2 from the copy and two from the original cast
        assertLife(playerB, 20 - (2+2) - (2+2+2));
        assertExileCount(playerA, griffin, 1);
        assertExileCount(playerA, centaur, 1);
    }
}
