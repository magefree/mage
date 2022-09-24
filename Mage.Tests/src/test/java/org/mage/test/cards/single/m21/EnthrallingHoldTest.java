package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EnthrallingHoldTest extends CardTestPlayerBase {

    @Test
    public void testTappedTarget_untapped_doesNotFizzle() {
        // Traxos, Scourge of Kroog enters the battlefield tapped and doesn't untap during your untap step.
        addCard(Zone.BATTLEFIELD, playerB, "Traxos, Scourge of Kroog");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        /*
         * {3}{U}{U}
         * Enchant creature
         * You can't choose an untapped creature as this spell's target as you cast it.
         * You control enchanted creature.
         */
        addCard(Zone.HAND, playerA, "Enthralling Hold");
        /*
         * {U}
         * You may tap or untap target artifact, creature, or land.
         */
        addCard(Zone.HAND, playerA, "Twiddle");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enthralling Hold", "Traxos, Scourge of Kroog");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twiddle", "Traxos, Scourge of Kroog");

        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Traxos, Scourge of Kroog", 0);
        assertPermanentCount(playerA, "Traxos, Scourge of Kroog", 1);
        assertPermanentCount(playerA, "Enthralling Hold", 1);
    }

    @Test
    public void testTappedTarget_becomesIllegal_fizzles() {
        // Traxos, Scourge of Kroog enters the battlefield tapped and doesn't untap during your untap step.
        addCard(Zone.BATTLEFIELD, playerB, "Traxos, Scourge of Kroog");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        /*
         * {3}{U}{U}
         * Enchant creature
         * You can't choose an untapped creature as this spell's target as you cast it.
         * You control enchanted creature.
         */
        addCard(Zone.HAND, playerA, "Enthralling Hold");
        /*
         * {1}{B}
         * Destroy target nonblack creature
         */
        addCard(Zone.HAND, playerA, "Doom Blade");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enthralling Hold", "Traxos, Scourge of Kroog");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Traxos, Scourge of Kroog");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, "Traxos, Scourge of Kroog", 0);
        assertPermanentCount(playerA, "Traxos, Scourge of Kroog", 0);

        assertGraveyardCount(playerB, "Traxos, Scourge of Kroog", 1);
        assertGraveyardCount(playerA, "Enthralling Hold", 1);
        assertGraveyardCount(playerA, "Doom Blade", 1);
    }
}
