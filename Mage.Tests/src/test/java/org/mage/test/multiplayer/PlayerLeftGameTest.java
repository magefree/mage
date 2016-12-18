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
public class PlayerLeftGameTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, 0, 2);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    /**
     * Tests Enchantment to control other permanent
     */
    @Test
    public void TestControlledByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerC, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Enchant creature
        // You control enchanted creature.
        addCard(Zone.HAND, playerA, "Control Magic");

        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Control Magic", "Rootwater Commando");

        attack(2, playerD, "Silvercoat Lion", playerC);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerC, 0);
        assertPermanentCount(playerC, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0);
        assertGraveyardCount(playerA, "Control Magic", 1);

    }

    /**
     * Tests Enchantment to control other permanent
     */
    @Test
    public void TestControlledBySorcery() {
        addCard(Zone.BATTLEFIELD, playerC, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        // (This effect lasts indefinitely.)
        addCard(Zone.HAND, playerA, "Legerdemain"); // Sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Air");

        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Legerdemain", "Rootwater Commando^Wall of Air");

        attack(2, playerD, "Silvercoat Lion", playerC);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerC, 0);
        assertGraveyardCount(playerA, "Legerdemain", 1);
        assertPermanentCount(playerC, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0); // Goes to graveyard becuase player C left
        assertPermanentCount(playerC, "Wall of Air", 0);
        assertGraveyardCount(playerA, "Wall of Air", 0);
        assertPermanentCount(playerA, "Wall of Air", 1); // Returned back to player A

    }

    /**
     * Tests Enchantment to control other permanent
     */
    @Test
    public void TestOtherPlayerControllsCreature() {
        addCard(Zone.BATTLEFIELD, playerC, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Untap target nonlegendary creature and gain control of it until end of turn. That creature gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Blind with Anger"); // Instant

        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Blind with Anger", "Rootwater Commando");

        attack(2, playerD, "Silvercoat Lion", playerC);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerC, 0);
        assertGraveyardCount(playerA, "Blind with Anger", 1);
        assertPermanentCount(playerC, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0); // Goes to graveyard becuase player C left
        assertPermanentCount(playerA, "Rootwater Commando", 0); // Returned back to player A
    }
}
