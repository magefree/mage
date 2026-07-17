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
    public void testEarthbendTDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Azusa's Many Journeys");
        addCard(Zone.BATTLEFIELD, playerA, "Ashaya, Soul of the Wild");
        addCard(Zone.HAND, playerA, "Earthbending Lesson");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Fell");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(7, PhaseStep.PRECOMBAT_MAIN, playerA, "Earthbending Lesson");
        addTarget(playerA, "Likeness of the Seeker");
        castSpell(7, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fell");
        addTarget(playerA, "Likeness of the Seeker");

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Azusa's Many Journeys", 1);
    }
}
