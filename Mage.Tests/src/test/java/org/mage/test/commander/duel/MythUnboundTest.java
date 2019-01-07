package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class MythUnboundTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Karador, Ghost Chieftain costs {1} less to cast for each creature card in your graveyard.
        // During each of your turns, you may cast one creature card from your graveyard.
        setDecknamePlayerA("CommanderOviya.dck"); // Commander = Oviya Pashiri, Sage Lifecrafter {G}
        setDecknamePlayerB("CMDNorinTheWary.dck");

        return super.createNewGameAndPlayers();
    }

    @Test
    public void castCommanderTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        // Your commander costs {1} less to cast for each time it's been cast from the command zone this game.
        // Whenever your commander is put into the command zone from anywhere, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Myth Unbound", 1); // Enchantment {2}{G}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oviya Pashiri, Sage Lifecrafter");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt", "Oviya Pashiri, Sage Lifecrafter");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Oviya Pashiri, Sage Lifecrafter");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Myth Unbound", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Oviya Pashiri, Sage Lifecrafter", 1);
        assertHandCount(playerA, 1);
        assertTappedCount("Forest", false, 1);
    }
}
