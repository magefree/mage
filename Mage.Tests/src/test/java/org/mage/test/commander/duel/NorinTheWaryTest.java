
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
public class NorinTheWaryTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // When a player casts a spell or a creature attacks, exile Norin the Wary. Return it to the battlefield under its owner's control at the beginning of the next end step.
        setDecknamePlayerA("CMDNorinTheWary.dck"); // Commander = Norin the Wary {R}
        return super.createNewGameAndPlayers();
    }

    @Test
    public void castNorinTheWary() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Norin the Wary", 1);
    }

    @Test
    public void castNorinTheWaryToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Norin the Wary", 0);
        assertExileCount("Norin the Wary", 1);

        assertLife(playerB, 37);

    }

    @Test
    public void castNorinTheWaryToExileAndReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, false);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Norin the Wary", 1);
        assertExileCount("Norin the Wary", 0);

        assertLife(playerB, 37);

    }

    @Test
    public void castNorinTheWaryToCommandAndNotReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Norin the Wary");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Norin the Wary", 0);
        assertExileCount("Norin the Wary", 0);
        assertCommandZoneCount(playerA, "Norin the Wary", 1);

        assertLife(playerB, 37);

    }
}
