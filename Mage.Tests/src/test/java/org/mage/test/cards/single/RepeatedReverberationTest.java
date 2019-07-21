
package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jgray1206
 */
public class RepeatedReverberationTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/5882
     * Repeated Reverberation was not working with loyalty counter abilities.
     */
    @Test
    public void testRepeatedReverberationWorksWithLoyaltyAbilities() {
        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.HAND, playerA, "Ajani Goldmane"); // {2}{W}{W} starts with 4 Loyality counters
        addCard(Zone.HAND, playerA, "Repeated Reverberation");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani Goldmane");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Repeated Reverberation");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ajani Goldmane", 1);
        assertGraveyardCount(playerA, "Repeated Reverberation", 1);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 5);  // 4 + 1 = 5

        assertLife(playerA, 26);
    }

    @Test
    public void testRepeatedReverberationWorksWithInstants() {
        addCard(Zone.HAND, playerA, "Soothing Balm"); // {1}{W} Target player gains 5 life
        addCard(Zone.HAND, playerA, "Repeated Reverberation");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Repeated Reverberation");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soothing Balm");
        addTarget(playerA, playerA);
        addTarget(playerA, playerB); //Should be able to choose new targets for each copy
        addTarget(playerA, playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Soothing Balm", 1);
        assertGraveyardCount(playerA, "Repeated Reverberation", 1);

        assertLife(playerA, 30);
        assertLife(playerB, 25);
    }

    @Test
    public void testRepeatedReverberationWorksWithSorceries() {
        addCard(Zone.HAND, playerA, "Soul Feast"); // {3}{B}{B} Target player loses 4 life. You gain 4 life.
        addCard(Zone.HAND, playerA, "Repeated Reverberation");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Repeated Reverberation");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Feast");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Soul Feast", 1);
        assertGraveyardCount(playerA, "Repeated Reverberation", 1);

        assertLife(playerA, 32);
        assertLife(playerB, 8);
    }
}
