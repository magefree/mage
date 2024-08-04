package org.mage.test.cards.designations;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author Susucr
 */
public class InitiativeTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // reason: must use MultiplayerAttackOption.MULTIPLE
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_InitiativeByCards() {
        // Player order: A -> D -> C -> B

        // When Aarakocra Sneak enters the battlefield, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.HAND, playerD, "Aarakocra Sneak", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerD, "Island", 4);

        // no initiative before
        checkInitative("no initiative before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, null);

        // A as monarch
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, "Mountain"); // search for "Secret Entrance" room
        checkInitative("initiative 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);

        // D as monarch
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Aarakocra Sneak");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerD, "Mountain"); // search for "Secret Entrance" room
        checkInitative("initiative 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerD);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_InitiativeByDamage() {
        // Player order: A -> D -> C -> B
        // game must use MultiplayerAttackOption.MULTIPLE

        // When Aarakocra Sneak enters the battlefield, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak", 1); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 1);
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 1);

        // A as monarch
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, "Mountain"); // search for "Secret Entrance" room
        checkInitative("initiative to A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);

        // D steal monarch from A
        attack(2, playerD, "Grizzly Bears", playerA);
        addTarget(playerD, "Mountain"); // search for "Secret Entrance" room
        checkInitative("initiative to D", 2, PhaseStep.POSTCOMBAT_MAIN, playerD, playerD);

        // C can't steal from A (nothing to steal)
        attack(3, playerC, "Grizzly Bears", playerA);
        checkInitative("nothing to steal (keep on D)", 3, PhaseStep.POSTCOMBAT_MAIN, playerC, playerD);

        // D 2nd turn, move to another room.
        setChoice(playerD, false); // Go to "Lost Well" in dungeon
        addTarget(playerD, "Mountain"); // for the Scry 2 of the "Lost Well" room.

        // C steal from D
        attack(3 + 4, playerC, "Grizzly Bears", playerD);
        addTarget(playerC, "Mountain"); // search for "Secret Entrance" room
        checkInitative("initiative to C", 3 + 4, PhaseStep.POSTCOMBAT_MAIN, playerC, playerC);

        setStrictChooseMode(true);
        setStopAt(3 + 4, PhaseStep.END_TURN);
        execute();
    }
}
