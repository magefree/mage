package org.mage.test.cards.single.dmc;

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

public class RamsesAssassinLordTest extends CardTestMultiPlayerBase {

    private static final String ramses = "Ramses, Assassin Lord";
    private static final String royalAssassin = "Royal Assassin";
    private static final String lion = "Silvercoat Lion";
    private static final String bolt = "Lightning Bolt";

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_RamsesAttacks_DefenderLoses_Win() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        setLife(playerD, 4);

        attack(1, playerA, ramses, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerD);
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
        assertLostTheGame(playerC);
    }

    @Test
    public void test_OtherAssassinAttacks_DefenderLoses_Win() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        // {T}: Destroy target tapped creature.
        addCard(Zone.BATTLEFIELD, playerA, royalAssassin); // 1/1 Human Assassin
        setLife(playerD, 2);

        attack(1, playerA, royalAssassin, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerD);
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
        assertLostTheGame(playerC);
    }

    @Test
    public void test_NonAssassinAttacks_DefenderLoses_NoWin() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, lion); // 2/2 Cat
        setLife(playerD, 2);

        attack(1, playerA, lion, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerD);
        assertHasNotWonTheGame(playerA);
        assertHasNotLostTheGame(playerB);
        assertHasNotLostTheGame(playerC);
    }

    @Test
    public void test_RamsesAttacks_DefenderLosesLaterToSpell_Win() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, bolt);
        setLife(playerD, 7);

        attack(1, playerA, ramses, playerD);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerD);
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
        assertLostTheGame(playerC);
    }

    @Test
    public void test_AttackingAssassinLeftBattlefield_DefenderLoses_Win() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        // {T}: Destroy target tapped creature.
        addCard(Zone.BATTLEFIELD, playerA, royalAssassin); // 1/1 Human Assassin
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, bolt, 2);
        setLife(playerD, 5);

        attack(1, playerA, royalAssassin, playerD);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, royalAssassin);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, royalAssassin, 1);
        assertLostTheGame(playerD);
        assertWonTheGame(playerA);
        assertLostTheGame(playerB);
        assertLostTheGame(playerC);
    }

    @Test
    public void test_PlayerNotAttackedLoses_NoWin() {
        // Deathtouch
        // Other Assassins you control get +1/+1.
        // Whenever a player loses the game, if they were attacked this turn by an Assassin you controlled, you win the game.
        addCard(Zone.BATTLEFIELD, playerA, ramses); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, bolt);
        setLife(playerC, 3);

        attack(1, playerA, ramses, playerD);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerD, 16);
        assertLostTheGame(playerC);
        assertHasNotWonTheGame(playerA);
        assertHasNotLostTheGame(playerB);
        assertHasNotLostTheGame(playerD);
    }
}
