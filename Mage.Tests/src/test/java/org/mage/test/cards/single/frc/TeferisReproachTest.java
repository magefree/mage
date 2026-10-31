package org.mage.test.cards.single.frc;

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

public class TeferisReproachTest extends CardTestMultiPlayerBase {

    // Choose target opponent. Until that player's next turn, they gain protection from everything and their life total can't change. All nonland permanents they control phase out.
    // Exile Teferi's Reproach.
    private static final String reproach = "Teferi's Reproach";

    // {B}: Pestilence deals 1 damage to each creature and each player.
    private static final String pestilence = "Pestilence";

    // Each opponent loses 3 life. You gain life equal to the life lost this way.
    private static final String tithe = "Blood Tithe";

    // Indestructible, keeps Pestilence around
    private static final String myr = "Darksteel Myr";

    private static final String bears = "Grizzly Bears";

    // Creatures you control get +1/+1.
    private static final String anthem = "Glorious Anthem";

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
    public void test_ProtectionLifeAndPhasingUntilThatPlayersNextTurn() {
        addCard(Zone.HAND, playerA, reproach);
        addCard(Zone.HAND, playerA, tithe);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, pestilence);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerB, anthem);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        // turn 1 (A): B's nonland permanents phase out, B can't lose life or be dealt damage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, reproach, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("bears phased out", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, bears, 0);
        checkPermanentCount("anthem phased out", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, anthem, 0);
        checkPermanentCount("land stays", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, "Forest", 1);
        checkExileCount("reproach exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, reproach, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, tithe);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{B}:");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // turn 3 (C): still protected
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}:");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 3 - bears still phased out", 3, PhaseStep.PRECOMBAT_MAIN, playerA, playerB, bears, 0);

        // turn 4 (B): effect is over and permanents phased in
        checkPermanentCount("turn 4 - bears phased in", 4, PhaseStep.UPKEEP, playerA, playerB, bears, 1);
        checkPermanentCount("turn 4 - anthem phased in", 4, PhaseStep.UPKEEP, playerA, playerB, anthem, 1);
        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}:");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertLife(playerC, 20 - 3 - 3);
        assertLife(playerD, 20 - 3 - 3);
        assertLife(playerA, 20 + 6 - 3);
        assertPowerToughness(playerB, bears, 3, 3);
        assertExileCount(playerA, reproach, 1);
    }

    @Test
    public void test_CastDuringThatPlayersTurn_LastsUntilTheirNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, pestilence);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.HAND, playerA, reproach);
        addCard(Zone.BATTLEFIELD, playerD, bears);

        // turn 2 (D): cast on D during D's own turn
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, reproach, playerD);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("bears phased out", 2, PhaseStep.PRECOMBAT_MAIN, playerA, playerD, bears, 0);

        // turn 5 (A): caster's next turn doesn't end it
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}:");
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("turn 5 - bears still phased out", 5, PhaseStep.PRECOMBAT_MAIN, playerA, playerD, bears, 0);

        // turn 6 (D): D's next turn ends it
        checkPermanentCount("turn 6 - bears phased in", 6, PhaseStep.UPKEEP, playerA, playerD, bears, 1);
        activateAbility(6, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}:");

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();

        assertLife(playerD, 20 - 1);
        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertPermanentCount(playerD, bears, 1);
    }

    @Test
    public void test_SpellTargetingThatPlayerFizzles() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, reproach);
        addCard(Zone.BATTLEFIELD, playerC, "Mountain");
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerC, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerC, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, reproach, "targetPlayer=PlayerB", "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerC, "Lightning Bolt", 1);
        assertExileCount(playerA, reproach, 1);
    }
}
