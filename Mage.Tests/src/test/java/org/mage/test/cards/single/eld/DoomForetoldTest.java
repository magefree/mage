package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DoomForetoldTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DoomForetold Doom Foretold} {2}{W}{B}
     * Enchantment
     * At the beginning of each player’s upkeep, that player sacrifices a nonland, nontoken permanent. If that player can’t, they discard a card, they lose 2 life, you draw a card, you gain 2 life, you create a 2/2 white Knight creature token with vigilance, then you sacrifice Doom Foretold.
     */
    private static final String doom = "Doom Foretold";

    @Test
    public void test_Simple_Sac_It() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // for sac turn 1
        addCard(Zone.BATTLEFIELD, playerA, doom);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        setChoice(playerA, "Memnite");
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerA, doom);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, doom, 1);
        assertPermanentCount(playerA, 0);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, 0);
    }

    @Test
    public void test_Simple_OpponentCantSacrifice() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // for sac turn 1
        addCard(Zone.BATTLEFIELD, playerA, doom);
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        setChoice(playerA, "Memnite");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1); // drawn for the turn
        assertGraveyardCount(playerA, doom, 1);
        assertPermanentCount(playerA, 1);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, 0);
    }
}
