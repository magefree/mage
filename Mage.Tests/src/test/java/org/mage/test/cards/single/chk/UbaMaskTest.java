package org.mage.test.cards.single.chk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UbaMaskTest extends CardTestPlayerBase {

    @Test
    public void test_NormalCard() {
        skipInitShuffling();

        // If a player would draw a card, that player exiles that card face up instead.
        // Each player may play cards they exiled with Uba Mask this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Uba Mask");
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.LIBRARY, playerB, "Bronze Sable"); // {2}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        // turn 1 - no draws
        checkExileCount("no exile on turn 1 for A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkExileCount("no exile on turn 1 for B", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Bronze Sable", 0);

        // turn 2 - B draw and exile
        checkExileCount("no exile on turn 2 for A", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkExileCount("exiled on turn 2 for B", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bronze Sable", 1);
        checkPlayableAbility("A play on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("A play on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bronze Sable", false);
        checkPlayableAbility("B play on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);
        checkPlayableAbility("B play on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Bronze Sable", true);

        // turn 3 - A draw and exile
        checkExileCount("exiled on turn 3 for A", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkExileCount("no exile on turn 3 for B", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Bronze Sable", 1); // exiled on prev turn
        checkPlayableAbility("A play on 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", true);
        checkPlayableAbility("A play on 3", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bronze Sable", false);
        checkPlayableAbility("B play on 3", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);
        checkPlayableAbility("B play on 3", 3, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Bronze Sable", false);

        // turn 3 - A play
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");
        waitStackResolved(3, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_MDF() {
        skipInitShuffling();

        // If a player would draw a card, that player exiles that card face up instead.
        // Each player may play cards they exiled with Uba Mask this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Uba Mask");
        //
        addCard(Zone.LIBRARY, playerB, "Valki, God of Lies"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // turn 2

        // B draw and exile valki
        checkExileCount("exiled", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Valki, God of Lies", 1);
        checkPlayableAbility("exiled", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Valki, God of Lies", true);

        // cast valki
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Valki, God of Lies");
        setChoice(playerB, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Valki, God of Lies", 1);
    }
}
