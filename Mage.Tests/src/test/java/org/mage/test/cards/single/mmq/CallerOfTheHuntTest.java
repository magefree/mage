package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */

public class CallerOfTheHuntTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_Play_Manual() {
        // As an additional cost to cast Caller of the Hunt, choose a creature type.
        // Caller of the Hunt's power and toughness are each equal to the number of creatures of the chosen type on the battlefield.
        addCard(Zone.HAND, playerA, "Caller of the Hunt"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Archaeologist", 2);

        // cast Caller of the Hunt and choose bear as a type (+3 boost)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Caller of the Hunt");
        setChoice(playerA, "Bear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Caller of the Hunt", 1);
        assertPowerToughness(playerA, "Caller of the Hunt", 3, 3); // +3 boost
    }

    @Test
    public void test_Play_AI() {
        // As an additional cost to cast Caller of the Hunt, choose a creature type.
        // Caller of the Hunt's power and toughness are each equal to the number of creatures of the chosen type on the battlefield.
        addCard(Zone.HAND, playerA, "Caller of the Hunt"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Archaeologist", 2);

        // ai must cast Caller of the Hunt and choose bear as a type (+3 boost)
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Caller of the Hunt", 1);
        assertPowerToughness(playerA, "Caller of the Hunt", 3, 3); // +3 boost
    }
}