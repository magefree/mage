package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;
import java.io.FileNotFoundException;

/**
 * @author tamaroth on 14.07.2017.
 */
public class CurseOfTheSwineTest  extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        return super.createNewGameAndPlayers();
    }

    /**
     *  In a Commander game, if a commander is put into the command zone
     *  instead of being exiled by Curse of the Swine, its controller will
     *  still get a Boar token.
     */
    @Test
    public void TestCurseOfTheSwine() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Curse of the Swine");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Daxos of Meletis");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of the Swine");
        setChoice(playerA, "X=1");
        // Daxos of Meletis is auto-chosen since only target

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCommandZoneCount(playerA, "Daxos of Meletis", 1);
        assertExileCount("Daxos of Meletis", 0);
        assertPermanentCount(playerA, "Boar Token", 1);
    }
}
