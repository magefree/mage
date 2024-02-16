package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChromaticOrreryTest extends CardTestPlayerBase {

    @Test
    public void testSpendManyAsThoughAnyColor(){
        // You may spend mana as though it were mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery");
        // {W}{U}{B}{R}{G}
        addCard(Zone.HAND, playerA, "Sliver Overlord");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sliver Overlord");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Sliver Overlord", 1);
    }

    @Test
    public void testDrawCards(){
        // {5}, {T}: Draw a card for each color among permanents you control.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Orrery");
        // {W}{U}{B}{R}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Sliver Overlord");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.LIBRARY, playerA, "Swamp",5);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 5);
    }
}
