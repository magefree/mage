package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EarthbendTest extends CardTestPlayerBase {

    @Test
    public void testMultipleEarthbend() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        addCard(Zone.HAND, playerA, "Dai Li Agents");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dai Li Agents");
        addTarget(playerA, "Swamp");
        addTarget(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertType("Swamp", CardType.CREATURE, true);
        assertType("Forest", CardType.CREATURE, true);
    }

    @Test
    public void testEarthbendDifferentTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Fatal Fissure");
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fatal Fissure");
        addTarget(playerA, "Balduvian Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fell");
        addTarget(playerA, "Balduvian Bears");
        addTarget(playerA, "Wastes");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Wastes", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Wastes", 4, 4);
    }

}
