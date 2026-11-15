package org.mage.test.cards.single.fra;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class IdentityEchoTest extends CardTestPlayerBase {

    /**
     * Identity Echo {2}{R}
     * Enchantment
     * {3}{R}: Exile target creature or planeswalker you control. Reveal cards from the top of your library until you
     * reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your
     * library in a random order. Activate only as a sorcery.
     */
    private static final String echo = "Identity Echo";

    @Test
    public void test_ExileAndRevealFirstCreature() {
        addCard(Zone.BATTLEFIELD, playerA, echo);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        //
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // not revealed, must stay on top
        addCard(Zone.LIBRARY, playerA, "Serra Angel"); // revealed, battlefield
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // revealed, bottom

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{R}: Exile", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Serra Angel", 1);
        Assert.assertEquals("Hill Giant", playerA.getLibrary().getFromTop(currentGame).getName());
        Assert.assertEquals("Lightning Bolt", playerA.getLibrary().getFromBottom(currentGame).getName());
    }
}
