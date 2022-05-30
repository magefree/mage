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
 * Enchantment {3}{B}
 * At the beginning of your upkeep, each player discards a card.
 * Each opponent who discarded a card that shares a card type with the card you discarded loses 3 life.
 * (Players reveal the discarded cards simultaneously.)
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com
 */
public class CreepingDreadTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * Discard creature and all opponents who discard creature lose 3 life
     */
    @Test
    public void basicTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Creeping Dread");
        addCard(Zone.HAND, playerA, "Merfolk Looter");
        addCard(Zone.HAND, playerB, "Hill Giant");
        addCard(Zone.HAND, playerC, "Elite Vanguard");
        addCard(Zone.HAND, playerD, "Bone Saw");

        setChoice(playerA, "Merfolk Looter");
        setChoice(playerB, "Hill Giant");
        setChoice(playerC, "Elite Vanguard");
        setChoice(playerD, "Bone Saw");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Merfolk Looter", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerC, "Elite Vanguard", 1);
        assertGraveyardCount(playerD, "Bone Saw", 1);

        assertLife(playerA, 40); // 4-player commander so 40 life starting
        assertLife(playerB, 37); // matched creature type
        assertLife(playerC, 37);
        assertLife(playerD, 40); // no match
    }

    /**
     * Discard Artifact Creature and all opponents who discard either an Artifact or Creature lose 3 life
     */
    @Test
    public void twoTypesTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Creeping Dread");
        addCard(Zone.HAND, playerA, "Bronze Sable");
        addCard(Zone.HAND, playerB, "Hill Giant");
        addCard(Zone.HAND, playerC, "Swamp");
        addCard(Zone.HAND, playerD, "Bone Saw");

        setChoice(playerA, "Bronze Sable");
        setChoice(playerB, "Hill Giant");
        setChoice(playerC, "Swamp");
        setChoice(playerD, "Bone Saw");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Bronze Sable", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerC, "Swamp", 1);
        assertGraveyardCount(playerD, "Bone Saw", 1);

        assertLife(playerA, 40); // artifact-creature discarded
        assertLife(playerB, 37); // creature
        assertLife(playerC, 40); // neither
        assertLife(playerD, 37); // artifact
    }

    /**
     * Discard enchantment and no opponents discard an enchantment, so no one loses life
     */
    @Test
    public void noMatchesTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Creeping Dread");
        addCard(Zone.HAND, playerA, "Moat"); // enchantment
        addCard(Zone.HAND, playerB, "Hill Giant");
        addCard(Zone.HAND, playerC, "Swamp");
        addCard(Zone.HAND, playerD, "Bone Saw");

        setChoice(playerA, "Moat");
        setChoice(playerB, "Hill Giant");
        setChoice(playerC, "Swamp");
        setChoice(playerD, "Bone Saw");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Moat", 1);
        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerC, "Swamp", 1);
        assertGraveyardCount(playerD, "Bone Saw", 1);

        assertLife(playerA, 40); // no matches
        assertLife(playerB, 40);
        assertLife(playerC, 40);
        assertLife(playerD, 40);
    }

    /**
     * Upkeep player has no cards to discard, so no matches
     */
    @Test
    public void noDiscardNoMatches() {

        addCard(Zone.BATTLEFIELD, playerA, "Creeping Dread");
        addCard(Zone.HAND, playerB, "Hill Giant");
        addCard(Zone.HAND, playerC, "Swamp");
        addCard(Zone.HAND, playerD, "Bone Saw");

        setChoice(playerB, "Hill Giant");
        setChoice(playerC, "Swamp");
        setChoice(playerD, "Bone Saw");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerB, "Hill Giant", 1);
        assertGraveyardCount(playerC, "Swamp", 1);
        assertGraveyardCount(playerD, "Bone Saw", 1);

        assertLife(playerA, 40); // no matches
        assertLife(playerB, 40);
        assertLife(playerC, 40);
        assertLife(playerD, 40);
    }
}
