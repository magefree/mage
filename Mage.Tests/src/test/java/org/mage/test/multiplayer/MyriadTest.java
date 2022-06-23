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
public class MyriadTest extends CardTestMultiPlayerBase {

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
     * Tests Myriad multiplayer effects Player order: A -> D -> C -> B
     */
    @Test
    public void CallerOfThePackTest() {
        // Trample
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker they control. Exile those tokens at the end of combat.)
        addCard(Zone.BATTLEFIELD, playerD, "Caller of the Pack"); // 8/6

        attack(2, playerD, "Caller of the Pack", playerA);

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertPermanentCount(playerD, "Caller of the Pack", 3);
    }

    @Test
    public void CallerOfThePackTestExile() {
        // Trample
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker they control. Exile those tokens at the end of combat.)
        addCard(Zone.BATTLEFIELD, playerD, "Caller of the Pack"); // 8/6

        attack(2, playerD, "Caller of the Pack", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerD, "Caller of the Pack", 1);

        assertLife(playerA, 32);
        assertLife(playerB, 32);
        assertLife(playerC, 32);
        assertLife(playerD, 40);

    }

    @Test
    public void CallerOfThePackTestExilePlaneswalker() {
        // Trample
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker they control. Exile those tokens at the end of combat.)
        addCard(Zone.BATTLEFIELD, playerD, "Caller of the Pack"); // 8/6

        // turns: A, D, C, B

        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Create a white Avatar creature token. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane");

        // +2 life
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        checkLife("must have +2 life", 2, PhaseStep.PRECOMBAT_MAIN, playerA, 40 + 2);

        // D attack C, create 2 copy of caller (for each opponent exclude defender = 2) and attack to ajani
        attack(2, playerD, "Caller of the Pack", playerC);
        addTarget(playerD, "Ajani Goldmane"); // select ajani instead playerA for pack attack
        checkPermanentCount("must have 3 packs", 2, PhaseStep.END_COMBAT, playerD, "Caller of the Pack", 3);
        checkPermanentCount("ajani must die", 2, PhaseStep.END_COMBAT, playerA, "Ajani Goldmane", 0);
        checkLife("pack must not damage playerA", 2, PhaseStep.END_COMBAT, playerA, 40 + 2);
        checkLife("pack must damage playerB by 8", 2, PhaseStep.END_COMBAT, playerB, 40 - 8);
        checkLife("pack must damage playerC", 2, PhaseStep.END_COMBAT, playerC, 40 - 8);
        checkLife("pack must not damage playerD", 2, PhaseStep.END_COMBAT, playerD, 40);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerD, "Caller of the Pack", 1);
        assertGraveyardCount(playerA, "Ajani Goldmane", 1);
    }

    /**
     * 4 player commander game, Man-o-War equipped with Blade of Selves attacks.
     * The myriad trigger creates copies for each opponent, including the
     * defending player, rather than the just two not being attacked.
     */
    @Test
    public void BladeOfSelves() {
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerC, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 1);

        // Equipped creature has myriad. (Whenever this creature attacks, for each opponent other than the defending
        //                                player, put a token that's a copy of this creature onto the battlefield tapped
        //                                and attacking that player or a planeswalker they control.
        //                                Exile those tokens at the end of combat.)
        // Equip {4}
        addCard(Zone.BATTLEFIELD, playerD, "Blade of Selves");
        addCard(Zone.BATTLEFIELD, playerD, "Island", 4);

        // When Man-o'-War enters the battlefield, return target creature to its owner's hand.
        addCard(Zone.HAND, playerD, "Man-o'-War"); // {2}{U}  2/2

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Man-o'-War");
        addTarget(playerD, "Silvercoat Lion");

        activateAbility(6, PhaseStep.PRECOMBAT_MAIN, playerD, "Equip", "Man-o'-War");

        attack(6, playerD, "Man-o'-War", playerC);
        setChoice(playerD, "Yes"); // for playerB
        setChoice(playerD, "Yes"); // for playerA
        addTarget(playerD, "Silvercoat Lion");
        addTarget(playerD, "Pillarfield Ox");

        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Tokens exiled, only original left
        assertPermanentCount(playerD, "Man-o'-War", 1);

        // Returned to hand by Man-o'-War
        assertPermanentCount(playerC, "Silvercoat Lion", 0);  // 1 by original and 1 by token
        assertPermanentCount(playerC, "Pillarfield Ox", 0);   // by token

        // Still on the battlefield since only 3 Man-o'-War triggers
        assertPermanentCount(playerC, "Grizzly Bears", 1);

        assertHandCount(playerC, "Silvercoat Lion", 2);
        assertHandCount(playerC, "Pillarfield Ox", 1);

        assertLife(playerA, 38);
        assertLife(playerB, 38);
        assertLife(playerC, 38);
        assertLife(playerD, 40);

    }

}
