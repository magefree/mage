package org.mage.test.cards.single.tla;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class DestinedConfrontationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DestinedConfrontation Destined Confrontation} {2}{W}{W}
     * Sorcery
     * Each player chooses any number of creatures they control with total power 4 or less,
     * then sacrifices all other creatures they control.
     */
    private static final String confrontation = "Destined Confrontation";

    @Test
    public void test_SacrificesCreaturesNotChosen() {
        addCard(Zone.HAND, playerA, confrontation);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, confrontation);
        setChoice(playerA, "Silvercoat Lion");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerB, TestPlayer.CHOICE_SKIP);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Centaur Courser", 1);
    }
}
