package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class GenesisUltimatumTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromHand(playerA);

        // Look at the top five cards of your library. Put any number of permanent cards from among them onto
        // the battlefield and the rest into your hand. Exile Genesis Ultimatum.
        addCard(Zone.HAND, playerA, "Genesis Ultimatum"); // {G}{G}{U}{U}{U}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);
        addCard(Zone.LIBRARY, playerA, "Alpha Tyrranax", 1);
        addCard(Zone.LIBRARY, playerA, "Kitesail Corsair", 1);
        addCard(Zone.LIBRARY, playerA, "Riverglide Pathway", 1); // mdf card

        // cast spell and put 3 cards to battle
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Genesis Ultimatum");
        addTarget(playerA, "Grizzly Bears^Kitesail Corsair^Riverglide Pathway");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerA, "Genesis Ultimatum", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Kitesail Corsair", 1);
        assertPermanentCount(playerA, "Riverglide Pathway", 1);
        assertHandCount(playerA, "Alpha Tyrranax", 1);
        assertLibraryCount(playerA, 0);
    }
}
