package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GrafdiggersCageTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.GRAVEYARD, playerA, "Lingering Souls");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 0);
        assertGraveyardCount(playerA, "Lingering Souls", 1);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Rise from the Grave", 1);
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise from the Grave", "Craw Wurm");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertGraveyardCount(playerA, "Craw Wurm", 1);
        assertGraveyardCount(playerA, "Rise from the Grave", 1);
    }

}
