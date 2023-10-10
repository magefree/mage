package org.mage.test.cards.single.eve;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PyrrhicRevivalTest extends CardTestPlayerBase {

    // Pyrrhic Revival {3}{W/B}{W/B}{W/B}
    // Sorcery
    // Each player returns each creature card from their graveyard to the battlefield with an additional -1/-1 counter on it.
    private static final String revival = "Pyrrhic Revival";
    // Cathedral Sanctifier {W}
    // Creature — Human Cleric
    //
    // When Cathedral Sanctifier enters the battlefield, you gain 3 life.
    // 1/1
    private static final String sanctifier = "Cathedral Sanctifier";

    @Test
    public void test_PyrrhicRevival() {
        addCard(Zone.GRAVEYARD, playerA, sanctifier, 2);
        addCard(Zone.GRAVEYARD, playerB, sanctifier, 1);
        addCard(Zone.BATTLEFIELD, playerA, "plains", 6);
        addCard(Zone.HAND, playerA, revival);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, revival);
        setChoice(playerA, "When {this} enters the battlefield, you gain 3 life.");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sanctifier, 0);
        assertPermanentCount(playerB, sanctifier, 0);
        assertGraveyardCount(playerA, sanctifier, 2);
        assertGraveyardCount(playerB, sanctifier, 1);
        assertLife(playerA, 20 + 6);
        assertLife(playerB, 20 + 3);
    }
}
