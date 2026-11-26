package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class DaredevilFearlessFighterTest extends CardTestPlayerBase {

    /**
     * Daredevil, Fearless Fighter {2}{R}
     * Legendary Creature - Human Hero 3/3
     * Whenever a source you control deals damage to you, Daredevil deals that much damage to target opponent.
     * Whenever Daredevil attacks, exile the top card of your library. Daredevil deals damage to you
     * equal to that card's mana value. You may play it this turn.
     */
    private static final String daredevil = "Daredevil, Fearless Fighter";

    private static final String bears = "Grizzly Bears"; // {1}{G} 2/2
    private static final String bolt = "Lightning Bolt"; // {R} 3 damage to any target

    @Test
    public void test_AttackExilesDealsDamageAndPlayThisTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, daredevil);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, bears);

        attack(1, playerA, daredevil, playerB);
        addTarget(playerA, playerB); // damage trigger from Daredevil's own damage

        checkPlayableAbility("can cast exiled card this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bears, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bears);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 2 damage from the exiled card's mana value
        assertLife(playerA, 20 - 2);
        // 2 reflected damage and 3 combat damage
        assertLife(playerB, 20 - 2 - 3);
        assertPermanentCount(playerA, bears, 1);
    }

    @Test
    public void test_CantPlayNextTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, daredevil);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island"); // draw on turn 3
        addCard(Zone.LIBRARY, playerA, bears); // top card
        skipInitShuffling();

        attack(1, playerA, daredevil, playerB);
        addTarget(playerA, playerB);

        checkPlayableAbility("can cast this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + bears, true);
        checkPlayableAbility("can't cast next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + bears, false);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, bears, 1);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 - 2 - 3);
    }

    @Test
    public void test_OtherSourceYouControl() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, daredevil);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerA);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_ManaValueZeroDealsNoDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, daredevil);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain");

        attack(1, playerA, daredevil, playerB);
        // no damage to playerA, so no target for the damage trigger

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mountain");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerA, "Mountain", 1);
    }
}
