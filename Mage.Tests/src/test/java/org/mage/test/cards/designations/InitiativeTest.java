package org.mage.test.cards.designations;

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
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * 726.2
 * There are three inherent triggered abilities associated with having the initiative. These triggered abilities have no
 * source and are controlled by the player who had the initiative at the time the abilities triggered. This is an
 * exception to rule 113.8. The full text of these abilities are “At the beginning of the upkeep of the player who has
 * the initiative, that player ventures into Undercity,” “Whenever one or more creatures a player controls deal combat
 * damage to the player who has the initiative, the controller of those creatures takes the initiative,” and “Whenever a
 * player takes the initiative, that player ventures into Undercity.” See rule 701.49, “Venture into the Dungeon.”
 * <p>
 * 726.4
 * If the player who has the initiative leaves the game, the active player takes the initiative at the same time that
 * player leaves the game. If the active player is leaving the game or if there is no active player, the next player in
 * turn order takes the initiative.
 *
 * @author DarioBarrabinoDev
 */
public class InitiativeTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Player order: A -> D -> C -> B
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_InitiativeByDies_ActivePlayer() {
        // Player order: A -> D -> C -> B
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerD);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerD, "Plains", 3);
        addCustomEffect_TargetDamage(playerA, 100);

        // When this creature enters, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // A takes the initiative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        addTarget(playerA, "Island"); // venture: Secret Entrance
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // kill itself on own turn, so initiative goes to next - player D
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 100", playerA);
        addTarget(playerD, "Plains"); // venture: Secret Entrance
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerA);
        Assert.assertEquals("initiative must go to next player", playerD.getId(), currentGame.getInitiativeId());
        assertHandCount(playerD, "Plains", 1); // venture on take the initiative
    }

    @Test
    public void test_InitiativeByDies_NotActivePlayer() {
        // Player order: A -> D -> C -> B
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerC);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerC, "Plains", 3);
        addCustomEffect_TargetDamage(playerC, 100);

        // When this creature enters, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // A takes the initiative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        addTarget(playerA, "Island"); // venture: Secret Entrance

        // C kills A on own turn, so initiative goes to active player C (not to next player D)
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerC, "target damage 100", playerA);
        addTarget(playerC, "Plains"); // venture: Secret Entrance
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerC);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerA);
        Assert.assertEquals("initiative must go to active player", playerC.getId(), currentGame.getInitiativeId());
        assertHandCount(playerC, "Plains", 1 + 1); // turn 3 draw + venture on take the initiative
    }

    @Test
    public void test_InitiativeByDamage_AfterFirstPlayerLeft() {
        // Player order: A -> D -> C -> B
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerC, "Plains", 3);
        addCard(Zone.LIBRARY, playerB, "Forest", 3);
        addCustomEffect_TargetDamage(playerC, 100);

        // When this creature enters, you take the initiative.
        addCard(Zone.HAND, playerA, "Aarakocra Sneak"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2

        // A takes the initiative
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aarakocra Sneak");
        addTarget(playerA, "Island"); // venture: Secret Entrance

        // C kills A on own turn, so initiative goes to active player C
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerC, "target damage 100", playerA);
        addTarget(playerC, "Plains"); // venture: Secret Entrance
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerC);

        // B steals the initiative from C by combat damage (initiative triggers must work without A)
        attack(4, playerB, "Grizzly Bears", playerC);
        addTarget(playerB, "Forest"); // venture: Secret Entrance

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerA);
        assertLife(playerC, 20 - 2);
        Assert.assertEquals("initiative must be taken by combat damage", playerB.getId(), currentGame.getInitiativeId());
        assertHandCount(playerB, "Forest", 1 + 1); // turn 4 draw + venture on take the initiative
    }
}
