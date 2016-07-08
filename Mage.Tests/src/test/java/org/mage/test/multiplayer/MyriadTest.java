/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.multiplayer;

import java.io.FileNotFoundException;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MyriadTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, 0, 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    /**
     * Tests Myriad multiplayer effects Player order: A -> D -> C -> B
     */
    @Test
    public void CallerOfThePackTest() {
        // Trample
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker he or she controls. Exile those tokens at the end of combat.)
        addCard(Zone.BATTLEFIELD, playerD, "Caller of the Pack"); // 8/6

        attack(2, playerD, "Caller of the Pack", playerA);

        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertPermanentCount(playerD, "Caller of the Pack", 3);
    }

    @Test
    public void CallerOfThePackTestExile() {
        // Trample
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker he or she controls. Exile those tokens at the end of combat.)
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
        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker he or she controls. Exile those tokens at the end of combat.)
        addCard(Zone.BATTLEFIELD, playerD, "Caller of the Pack"); // 8/6

        // +1: You gain 2 life.
        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        // -6: Put a white Avatar creature token onto the battlefield. It has "This creature's power and toughness are each equal to your life total."
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");

        attack(2, playerD, "Caller of the Pack", playerC);
        addTarget(playerD, "Ajani Goldmane");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerD, "Caller of the Pack", 1);
        assertGraveyardCount(playerA, "Ajani Goldmane", 1);

        assertLife(playerA, 42);
        assertLife(playerB, 32);
        assertLife(playerC, 32);
        assertLife(playerD, 40);

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

        // Equipped creature has myriad.(Whenever this creature attacks, for each opponent other than the defending player,
        // put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker
        // he or she controls. Exile those tokens at the end of combat.)
        // Equip {4}
        addCard(Zone.BATTLEFIELD, playerD, "Blade of Selves");
        addCard(Zone.BATTLEFIELD, playerD, "Island", 4);

        // When Man-o'-War enters the battlefield, return target creature to its owner's hand.
        addCard(Zone.HAND, playerD, "Man-o'-War"); // {2}{U}  2/2

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Man-o'-War");
        addTarget(playerD, "Silvercoat Lion");
        activateAbility(6, PhaseStep.PRECOMBAT_MAIN, playerD, "Equip", "Man-o'-War");

        attack(6, playerD, "Man-o'-War", playerC);
        addTarget(playerD, "Silvercoat Lion");
        addTarget(playerD, "Pillarfield Ox");
        addTarget(playerD, "Grizzly Bears");

        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerD, "Man-o'-War", 1);
        assertPermanentCount(playerC, "Silvercoat Lion", 0);
        assertPermanentCount(playerC, "Pillarfield Ox", 0);
        assertPermanentCount(playerC, "Grizzly Bears", 1); // not in range

        assertHandCount(playerC, "Silvercoat Lion", 2);
        assertHandCount(playerC, "Pillarfield Ox", 1);

        assertLife(playerA, 38);
        assertLife(playerB, 38);
        assertLife(playerC, 38);
        assertLife(playerD, 40);

    }

}
