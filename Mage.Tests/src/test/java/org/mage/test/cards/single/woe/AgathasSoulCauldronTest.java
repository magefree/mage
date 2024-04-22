package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class AgathasSoulCauldronTest extends CardTestPlayerBase {
    private static final String cauldron = "Agatha's Soul Cauldron";
    private static final String medic = "Stone Haven Medic";
    private static final String bear = "Grizzly Bears";

    @Test
    public void testManaColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, cauldron);
        addCard(Zone.BATTLEFIELD, playerA, medic);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testExileTriggerAddAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, cauldron);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.GRAVEYARD, playerA, medic);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Exile", medic);
        addTarget(playerA, bear);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{W}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(cauldron, true);
        assertTapped(bear, true);
        assertCounterCount(playerA, bear, CounterType.P1P1, 1);
        assertLife(playerA, 20 + 1);
        assertExileCount(playerA, medic, 1);
    }
}
