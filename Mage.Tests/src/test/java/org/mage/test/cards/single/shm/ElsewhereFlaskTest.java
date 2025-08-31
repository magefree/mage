package org.mage.test.cards.single.shm;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class ElsewhereFlaskTest extends CardTestPlayerBase {

    /*
    Elsewhere Flask
    {2}
    Artifact

    When this artifact enters, draw a card.

    Sacrifice this artifact: Choose a basic land type. Each land you control becomes that type until end of turn.
     */
    private static final String elsewhereFlask = "Elsewhere Flask";

    @Test
    public void testElsewhereFlask() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, elsewhereFlask);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Swamp");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Island", CardType.LAND, SubType.SWAMP);
        assertType("Forest", CardType.LAND, SubType.SWAMP);
    }

    @Test
    public void testElsewhereFlask2() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, elsewhereFlask);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Swamp");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Island", CardType.LAND, SubType.ISLAND);
        assertType("Forest", CardType.LAND, SubType.FOREST);
    }
}
