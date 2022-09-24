package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChaosWandTest extends CardTestPlayerBase {

    @Test
    public void testChaosWant() {
        removeAllCardsFromLibrary(playerB);

        // {4}, {T}: Target opponent exiles cards from the top of their library until they exile an instant or sorcery card.
        // You may cast that card without paying its mana cost. Then put the exiled cards that weren't cast this way
        // on the bottom of that library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Chaos Wand");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        // Each opponent loses 3 life. You gain life equal to the life lost this way.
        addCard(Zone.LIBRARY, playerB, "Island", 1); // save after exile
        addCard(Zone.LIBRARY, playerB, "Blood Tithe");
        addCard(Zone.LIBRARY, playerB, "Swamp", 3); // choose for exile

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}: ");
        addTarget(playerA, playerB);
        setChoice(playerA, true); // cast for free

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 3); // +3 from blood
        assertLife(playerB, 20 - 3); // -3 from blood
        assertLibraryCount(playerB, "Island", 1);
    }
}
