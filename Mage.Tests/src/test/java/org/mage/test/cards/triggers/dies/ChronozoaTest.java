package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 *
 * @author LevelX2
 */
public class ChronozoaTest extends CardTestPlayerBase {

    /**
     * Chronozoa's duplicating ability is triggering whenever any card is put
     * into a graveyard from play, not just Chronozoa itself. Here's an excerpt
     * from the log: As you can see, I sacrificed Viscera Seer and for some
     * reason that triggered Chronozoa's ability.
     */
    @Test
    public void testTriggerOtherCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Flying
        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        // When Chronozoa is put into a graveyard from play, if it had no time counters on it, create two tokens that are copies of it.
        addCard(Zone.HAND, playerA, "Chronozoa"); // {3}{U}
        addCard(Zone.GRAVEYARD, playerA, "Chronozoa");
        // Sacrifice a creature: Scry 1. (To scry 1, look at the top card of your library, then you may put that card on the bottom of your library.)
        addCard(Zone.BATTLEFIELD, playerA, "Viscera Seer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chronozoa");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice a creature");
        addTarget(playerA, "Viscera Seer");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Viscera Seer", 1);
        assertGraveyardCount(playerA, "Chronozoa", 1);
        assertPermanentCount(playerA, "Chronozoa", 1);
        assertPermanentCount(playerA, 5);

        assertHandCount(playerA, 0);
    }
}
