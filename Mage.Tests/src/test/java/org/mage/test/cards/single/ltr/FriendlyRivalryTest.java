package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class FriendlyRivalryTest extends CardTestPlayerBase {

    @Test
    public void test_target_both() {
        // Target creature you control and up to one other target legendary creature you control each deal damage
        // equal to their power to target creature you don't control.
        addCard(Zone.HAND, playerA, "Friendly Rivalry"); // {R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Aesi, Tyrant of Gyre Strait"); // legendary, 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Agonasaur Rex"); // 8/8

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Friendly Rivalry");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Aesi, Tyrant of Gyre Strait");
        addTarget(playerA, "Agonasaur Rex");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, "Agonasaur Rex", 2 + 5);
    }

    @Test
    public void test_target_single() {
        // Target creature you control and up to one other target legendary creature you control each deal damage
        // equal to their power to target creature you don't control.
        addCard(Zone.HAND, playerA, "Friendly Rivalry"); // {R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Aesi, Tyrant of Gyre Strait"); // legendary, 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Agonasaur Rex"); // 8/8

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Friendly Rivalry");
        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // skip second target due "up to"
        addTarget(playerA, "Agonasaur Rex");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerB, "Agonasaur Rex", 2);
    }
}