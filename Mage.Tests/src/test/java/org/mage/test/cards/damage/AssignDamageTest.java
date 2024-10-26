package org.mage.test.cards.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class AssignDamageTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_ThornElemental_Manual_DamageToBlock() {
        // 7/7
        // You may have Thorn Elemental assign its combat damage as though it weren't blocked.
        addCard(Zone.BATTLEFIELD, playerA, "Thorn Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // attack and kill bear due block
        attack(1, playerA, "Thorn Elemental");
        block(1, playerB, "Grizzly Bears", "Thorn Elemental");
        setChoice(playerA, false); // use blocked damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_ThornElemental_Manual_DamageToPlayer() {
        // 7/7
        // You may have Thorn Elemental assign its combat damage as though it weren't blocked.
        addCard(Zone.BATTLEFIELD, playerA, "Thorn Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // attack and ignore bear
        attack(1, playerA, "Thorn Elemental");
        block(1, playerB, "Grizzly Bears", "Thorn Elemental");
        setChoice(playerA, true); // use ignored damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 7);
        assertDamageReceived(playerB, "Grizzly Bears", 0);
    }

    @Test
    public void test_ThornElemental_AI() {
        // 7/7
        // You may have Thorn Elemental assign its combat damage as though it weren't blocked.
        addCard(Zone.BATTLEFIELD, playerA, "Thorn Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // AI must attack and decide to ignore block damage (e.g. damage a player)
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);
        block(1, playerB, "Grizzly Bears", "Thorn Elemental");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 7);
        assertDamageReceived(playerB, "Grizzly Bears", 0);
    }
}
