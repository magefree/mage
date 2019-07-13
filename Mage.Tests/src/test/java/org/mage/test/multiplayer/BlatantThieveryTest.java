/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.multiplayer;

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
 * @author LevelX2
 */
public class BlatantThieveryTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    @Test
    public void NormalTest() {
        // For each opponent, gain control of target permanent that player controls.
        addCard(Zone.HAND, playerA, "Blatant Thievery"); // Sorcery {4}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // Player order: A -> D -> C -> B
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Walking Corpse", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Pillarfield Ox", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blatant Thievery");
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, "Walking Corpse");
        addTarget(playerA, "Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Blatant Thievery", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Walking Corpse", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerC, "Walking Corpse", 1);
        assertPermanentCount(playerD, "Pillarfield Ox", 1);
    }

    @Test
    public void ControlChangeTest() {
        // For each opponent, gain control of target permanent that player controls.
        addCard(Zone.HAND, playerA, "Blatant Thievery"); // Sorcery {4}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        addCard(Zone.HAND, playerB, "Act of Aggression"); // Instant {3}{M}{M}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);

        // Player order: A -> D -> C -> B
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Walking Corpse", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blatant Thievery");
        addTarget(playerA, "Silvercoat Lion");
        addTarget(playerA, "Walking Corpse");
        addTarget(playerA, "Pillarfield Ox");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Aggression", "Pillarfield Ox", "Blatant Thievery");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Blatant Thievery", 1);
        assertGraveyardCount(playerB, "Act of Aggression", 1);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Walking Corpse", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
    }
}
