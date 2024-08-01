package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GrandWarlordRadhaTest extends CardTestPlayerBase {

    @Test
    public void testMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Zone.BATTLEFIELD, playerA, "Lightning Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Grand Warlord Radha");
        addCard(Zone.HAND, playerA, "Living Twister");

        attack(1, playerA, "Raging Goblin");
        attack(1, playerA, "Lightning Elemental");
        attack(1, playerA, "Grand Warlord Radha");
        setChoiceAmount(playerA, 2, 1); // RRG

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Living Twister");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Living Twister", 2, 5);
    }

}
