package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.io.FileNotFoundException;

public class TestPartnerCommanders extends CardTestCommanderDuelBase {


    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_Partners.dck"); // Commander = Ishai, Ojutai Dragonspeaker and Thrasios, Triton Hero
        return super.createNewGameAndPlayers();
    }

    @Test
    public void testSkyfirePhoenix() {
        addCard(Zone.GRAVEYARD, playerA, "Skyfire Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thrasios, Triton Hero", 1);
        assertPermanentCount(playerA, "Skyfire Phoenix", 1);
    }
}
