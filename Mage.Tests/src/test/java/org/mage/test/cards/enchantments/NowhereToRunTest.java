package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author markort147
 */
public class NowhereToRunTest extends CardTestPlayerBase {

    // Prevent ward from triggering on opponent's creatures
    @Test
    public void testWardPreventingOnOpponentsCreatures() {

        addCard(Zone.BATTLEFIELD, playerB, "Waterfall Aerialist", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Nowhere to Run", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nowhere to Run");
        addTarget(playerA, "Waterfall Aerialist");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Waterfall Aerialist", 0);
    }

    // Does not prevent ward from triggering on own creatures
    @Test
    public void testWardOnOwnCreatures() {

        addCard(Zone.BATTLEFIELD, playerA, "Waterfall Aerialist", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Nowhere to Run", 1);
        addCard(Zone.HAND, playerB, "Swords to Plowshares", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Swords to Plowshares");
        addTarget(playerB, "Waterfall Aerialist");
        setChoice(playerB, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Waterfall Aerialist", 1);
        assertGraveyardCount(playerB, "Swords to Plowshares", 1);
    }
}
