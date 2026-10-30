package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class AuditoreAmbushTest extends CardTestPlayerBase {

    // Choose one or both --
    // * Return target creature to its owner's hand.
    // * Target player searches their library and/or graveyard for a card named Ezio, Blade of Vengeance, reveals it, and puts it into their hand. If they search their library this way, they shuffle.
    private static final String ambush = "Auditore Ambush"; // {2}{U}{B} sorcery

    private static final String ezio = "Ezio, Blade of Vengeance";
    private static final String bears = "Grizzly Bears"; // 2/2

    @Test
    public void test_BothModes_OpponentSearchesLibrary() {
        addCard(Zone.HAND, playerA, ambush);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.LIBRARY, playerB, ezio);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ambush);
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, bears);
        addTarget(playerA, playerB);
        setChoice(playerB, true); // search library
        addTarget(playerB, ezio);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, bears, 1);
        assertHandCount(playerB, ezio, 1);
        assertLibraryCount(playerB, ezio, 0);
        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, ambush, 1);
    }

    @Test
    public void test_SearchOnly_OpponentSearchesGraveyard() {
        addCard(Zone.HAND, playerA, ambush);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        addCard(Zone.GRAVEYARD, playerB, ezio);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ambush);
        setModeChoice(playerA, "2");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerB);
        setChoice(playerB, false); // don't search library
        setChoice(playerB, true); // search graveyard
        setChoice(playerB, ezio);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, ezio, 1);
        assertGraveyardCount(playerB, ezio, 0);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_SearchOnly_NotInLibrary_ThenGraveyard() {
        addCard(Zone.HAND, playerA, ambush);
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        addCard(Zone.GRAVEYARD, playerB, ezio);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ambush);
        setModeChoice(playerA, "2");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerB);
        setChoice(playerB, true); // search library
        addTarget(playerB, TestPlayer.TARGET_SKIP); // nothing to find
        setChoice(playerB, true); // search graveyard
        setChoice(playerB, ezio);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, ezio, 1);
        assertGraveyardCount(playerB, ezio, 0);
    }
}
