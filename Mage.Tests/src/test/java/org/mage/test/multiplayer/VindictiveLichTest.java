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
public class VindictiveLichTest extends CardTestMultiPlayerBase {

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
     * Tests multiplayer effects Player order: A -> D -> C -> B
     */
    @Test
    public void CallerOfThePackTest() {

        // When Vindictive Lich dies, choose one or more. Each mode must target a different player.
        // *Target opponent sacrifices a creature.
        // *Target opponent discards two cards.
        // *Target opponent loses 5 life.
        addCard(Zone.BATTLEFIELD, playerA, "Vindictive Lich"); // Creature {3}{B} 4/1

        // Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodflow Connoisseur"); // Creature {2}{B} 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerC, "Lightning Bolt", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Vindictive Lich");

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);
        setModeChoice(playerA, "2");
        addTarget(playerA, playerC);
        setModeChoice(playerA, "3");
        addTarget(playerA, playerD);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Vindictive Lich", 1);
        assertPowerToughness(playerA, "Bloodflow Connoisseur", 2, 2);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerC, 0);
        assertLife(playerD, 35);

    }

}
