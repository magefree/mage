package org.mage.test.cards.single.tmc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

public class CaseyAndRaphHotheadsTest extends CardTestMultiPlayerBase {

    // When Casey & Raph enter, choose one or both. Each mode must target a different player.
    // * Target player exiles the top card of their library. Until that player's next end step, they may play that card without paying its mana cost.
    // * Target player creates two Treasure tokens.
    private static final String casey = "Casey & Raph, Hotheads";

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    private void castCasey() {
        addCard(Zone.HAND, playerA, casey);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, casey);
    }

    @Test
    public void testExileModeCastSpellForFree() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Grizzly Bears");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerD);

        // no lands, so it can only be cast for free
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, casey, 1);
        assertPermanentCount(playerD, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Treasure Token", 0);
    }

    @Test
    public void testExileModePlayLand() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Forest");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerD);

        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Forest");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerD, "Forest", 1);
    }

    @Test
    public void testOnlyTargetPlayerCanPlay() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Lightning Bolt");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerD);

        checkPlayableAbility("controller can't cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);
        checkPlayableAbility("target player can cast", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, "Cast Lightning Bolt", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerD, "Lightning Bolt", 1);
    }

    @Test
    public void testExileModeCastOnAnotherPlayersTurn() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerC, "Lightning Bolt");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerC);

        // still playable after the end steps of PlayerA and PlayerD
        castSpell(2, PhaseStep.END_TURN, playerC, "Lightning Bolt", playerD);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerC, "Lightning Bolt", 1);
        assertLife(playerD, 20 - 3);
    }

    @Test
    public void testExileModeEndsAtThatPlayersEndStep() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Lightning Bolt");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerD);

        checkPlayableAbility("before end step", 2, PhaseStep.POSTCOMBAT_MAIN, playerD, "Cast Lightning Bolt", true);
        checkPlayableAbility("after end step", 3, PhaseStep.PRECOMBAT_MAIN, playerD, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerD, "Lightning Bolt", 1);
    }

    @Test
    public void testExileModeOnControllerEndsThisTurn() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Island"); // drawn on turn 1
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerA);

        checkPlayableAbility("before end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("after end step", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void testTreasureMode() {
        castCasey();
        setModeChoice(playerA, "2");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        addTarget(playerA, playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerC, "Treasure Token", 2);
        assertPermanentCount(playerA, "Treasure Token", 0);
        assertExileCount(playerA, 0);
    }

    @Test
    public void testBothModesDifferentPlayers() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Grizzly Bears");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, playerD);
        addTarget(playerA, playerC);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerD, "Grizzly Bears", 1);
        assertPermanentCount(playerC, "Treasure Token", 2);
        assertPermanentCount(playerD, "Treasure Token", 0);
    }

    @Test
    public void testBothModesSamePlayer() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerD, "Grizzly Bears");
        castCasey();
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, playerD);
        // PlayerD is already the target of the first mode
        addTarget(playerA, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Targets list was setup by addTarget with [targetPlayer=PlayerD], but not used")) {
                Assert.fail("must have thrown error about unused target, but got:\n" + e.getMessage());
            }
        }
        assertPermanentCount(playerD, "Treasure Token", 0);
    }
}
