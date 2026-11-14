package org.mage.test.cards.single.msc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * 508.3e
 * An ability that reads "Whenever [a player] attacks [another player], . . ." triggers if one or more creatures the
 * first player controls are declared as attackers attacking the second player. It won't trigger if a creature is put
 * onto the battlefield attacking or if a creature attacks a planeswalker or a battle.
 *
 * @author JayDi85
 */
public class ArchnemesisTest extends CardTestMultiPlayerBase {

    /**
     * Archnemesis {1}{U}{B}
     * Enchantment - Aura
     * Enchant opponent
     * Whenever you attack enchanted player, that player loses 2 life. You draw a card and gain 2 life.
     * Whenever a player attacks you, you may attach this Aura to that player.
     */
    private static final String archnemesis = "Archnemesis";

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

    @Test
    public void test_AttackEnchantedPlayer() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);
        attack(1, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2 - 2); // trigger + bear
        assertHandCount(playerA, 1 + 1); // turn draw + trigger draw
    }

    @Test
    public void test_AttackOtherPlayerOrEnchantedPlayersPlaneswalker_NoTrigger() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Goldmane"); // planeswalker, loyalty 4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);
        attack(1, playerA, "Grizzly Bears", playerC);
        attack(1, playerA, "Silvercoat Lion", "Ajani Goldmane");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20 - 2);
        assertCounterCount(playerB, "Ajani Goldmane", CounterType.LOYALTY, 4 - 2);
        assertHandCount(playerA, 1); // turn draw only
    }

    @Test
    public void test_OtherPlayerAttacksEnchantedPlayer_NoTrigger() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);
        attack(3, playerC, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2); // bear only
    }

    @Test
    public void test_PlayerAttacksYou_AttachToThatPlayer() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);

        // turn 2: playerD attacks playerA, Archnemesis moves from playerB to playerD
        attack(2, playerD, "Grizzly Bears", playerA);
        setChoice(playerA, true); // attach to playerD

        // turn 5: attacking playerD triggers it now
        attack(5, playerA, "Silvercoat Lion", playerD);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2 + 2);
        assertLife(playerB, 20);
        assertLife(playerD, 20 - 2 - 2); // trigger + lion
        // the client shows player's auras by the player's attachments
        Assert.assertTrue("playerB must lose the aura", playerB.getAttachments().isEmpty());
        Assert.assertEquals("playerD must get the aura", 1, playerD.getAttachments().size());
    }

    @Test
    public void test_PlayerAttacksYou_Decline() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);

        attack(2, playerD, "Grizzly Bears", playerA);
        setChoice(playerA, false); // keep it on playerB

        attack(5, playerA, "Silvercoat Lion", playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2 + 2);
        assertLife(playerB, 20 - 2 - 2); // trigger + lion
        assertLife(playerD, 20);
    }

    @Test
    public void test_PlayerAttacksYourPlaneswalkerOrOtherPlayer_NoTrigger() {
        addCard(Zone.HAND, playerA, archnemesis); // {1}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // planeswalker, loyalty 4
        //
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion"); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archnemesis, playerB);

        // no attach choice: playerA isn't attacked
        attack(2, playerD, "Grizzly Bears", "Ajani Goldmane");
        attack(2, playerD, "Silvercoat Lion", playerC);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Ajani Goldmane", CounterType.LOYALTY, 4 - 2);
        assertLife(playerC, 20 - 2);
    }
}
