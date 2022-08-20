package org.mage.test.cards.single.m20;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Jgray1206
 */
public class RepeatedReverberationTest extends CardTestPlayerBase {

    /* Repeated Reverberation {2}{R}{R}
     * When you next cast an instant spell, cast a sorcery spell, or activate a loyalty ability this turn, copy that spell or ability twice.
     * You may choose new targets for the copies.
     */
    String repeatedReverb = "Repeated Reverberation";

    /**
     * https://github.com/magefree/mage/issues/5882
     * Repeated Reverberation was not working with loyalty counter abilities.
     */
    @Test
    public void test_WorksWithLoyaltyAbilities() {
        /* Ajani Goldmane: {2}{W}{W} starts with 4 Loyality counters
         * +1: You gain 2 life.
         * -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
         * -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
         */
        String ajani = "Ajani Goldmane";

        addCard(Zone.HAND, playerA, ajani);
        addCard(Zone.HAND, playerA, repeatedReverb);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ajani);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, repeatedReverb);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: You gain 2 life");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, ajani, 1);
        assertGraveyardCount(playerA, repeatedReverb, 1);
        assertCounterCount(ajani, CounterType.LOYALTY, 5);  // 4 + 1 = 5

        assertLife(playerA, 26);
    }

    @Test
    public void test_WorksWithInstants() {
        /* Soothing Balm - Instant {1}{W}
         * Target player gains 5 life
         * Just an arbitrary instant to test reverb with.
         */
        String soothingBalm = "Soothing Balm";

        addCard(Zone.HAND, playerA, soothingBalm);
        addCard(Zone.HAND, playerA, repeatedReverb);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, repeatedReverb);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, soothingBalm);
        addTarget(playerA, playerA);
        setChoice(playerA, true); //Choose new targets?
        addTarget(playerA, playerB);
        setChoice(playerA, true); //Choose new targets?
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, soothingBalm, 1);
        assertGraveyardCount(playerA, repeatedReverb, 1);

        assertLife(playerA, 30);
        assertLife(playerB, 25);
    }

    @Test
    public void test_WorksWithSorceries() {
        /* Soul Feast - Sorcery {3}{B}{B}
         * Target player loses 4 life. You gain 4 life.
         * Just an arbitrary sorcery to test reverb with.
         */
        String soulFeast = "Soul Feast";

        addCard(Zone.HAND, playerA, soulFeast);
        addCard(Zone.HAND, playerA, repeatedReverb);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, repeatedReverb);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, soulFeast);
        addTarget(playerA, playerB);
        setChoice(playerA, false); //Choose new targets?
        setChoice(playerA, false); //Choose new targets?

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, soulFeast, 1);
        assertGraveyardCount(playerA, repeatedReverb, 1);

        assertLife(playerA, 32);
        assertLife(playerB, 8);
    }

    @Test
    public void test_MustNotWorksWithCreatures() {
        addCard(Zone.HAND, playerA, repeatedReverb); // {2}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        //
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // prepare reverb
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, repeatedReverb);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // cast creature
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertGraveyardCount(playerA, repeatedReverb, 1);
    }
}
