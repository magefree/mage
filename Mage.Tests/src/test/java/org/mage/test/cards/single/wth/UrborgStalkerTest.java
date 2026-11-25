package org.mage.test.cards.single.wth;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UrborgStalkerTest extends CardTestPlayerBase {

    @Test
    public void test_OpponentUpkeep() {
        // At the beginning of each player's upkeep, if that player controls a nonblack, nonland permanent, this
        // creature deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Urborg Stalker"); // black
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2, green

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20); // no nonblack permanents on turns 1 and 3
        assertLife(playerB, 20 - 1); // turn 2
    }

    @Test
    public void test_YourUpkeep() {
        // At the beginning of each player's upkeep, if that player controls a nonblack, nonland permanent, this
        // creature deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Urborg Stalker"); // black
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // colorless 1/1

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1);
        assertLife(playerB, 20);
    }
}
