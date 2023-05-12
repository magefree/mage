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
 * @author JayDi85
 */
public class MonarchTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // reason: must use MultiplayerAttackOption.MULTIPLE
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_MonarchByCards() {
        // Player order: A -> D -> C -> B

        // When Thorn of the Black Rose enters the battlefield, you become the monarch.
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.HAND, playerD, "Thorn of the Black Rose", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerD, "Swamp", 4);

        // no monarch before
        checkMonarch("no monarch before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, null);

        // A as monarch
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkMonarch("monarch 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);

        // D as monarch
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Thorn of the Black Rose");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkMonarch("monarch 2", 2, PhaseStep.PRECOMBAT_MAIN, playerD, playerD);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MonarchByDamage() {
        // Player order: A -> D -> C -> B
        // game must use MultiplayerAttackOption.MULTIPLE

        // When Thorn of the Black Rose enters the battlefield, you become the monarch.
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 1);
        //
        // {T} : Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 1);

        // A as monarch
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkMonarch("monarch to A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);

        // D steal monarch from A
        attack(2, playerD, "Grizzly Bears", playerA);
        checkMonarch("monarch to D", 2, PhaseStep.POSTCOMBAT_MAIN, playerD, playerD);

        // C can't steal from A (nothing to steal)
        attack(3, playerC, "Grizzly Bears", playerA);
        checkMonarch("nothing to steal (keep on D)", 3, PhaseStep.POSTCOMBAT_MAIN, playerC, playerD);

        // C steal from D
        attack(3 + 4, playerC, "Grizzly Bears", playerD);
        checkMonarch("monarch to C", 3 + 4, PhaseStep.POSTCOMBAT_MAIN, playerC, playerC);

        setStrictChooseMode(true);
        setStopAt(3 + 4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_MonarchByDies() {
        // Player order: A -> D -> C -> B
        addCustomEffect_TargetDamage(playerA, 100);

        // When Thorn of the Black Rose enters the battlefield, you become the monarch.
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose", 1); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // A as monarch
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkMonarch("monarch to A", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);

        // kill itself, so monarch goes to next - player D
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 100", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkMonarch("monarch to D", 2, PhaseStep.POSTCOMBAT_MAIN, playerD, playerD);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerA);
    }
}
