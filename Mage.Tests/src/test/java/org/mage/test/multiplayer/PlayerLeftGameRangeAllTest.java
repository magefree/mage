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
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PlayerLeftGameRangeAllTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
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

    /**
     * Xmage throws an error involving an emblem unable to find the initial
     * source if it has a proc. To reproduce, a Planeswalker was taken from an
     * original player's control, such as using Scrambleverse to shuffle Jace,
     * Unraveler of Secrets, to a second player and then the second player uses
     * Jace's ability to create an emblem ("Whenever an opponent casts his or
     * her first spell each turn, counter that spell."). Then the original
     * player concedes the game and removes the Planeswalker. Once it becomes an
     * opponent of the original player's turn and that opponent plays a spell,
     * Xmage throws an error and rollsback the turn.
     *
     * I don't have the actual error report on my due to negligence, but what I
     * can recollect is that the error message was along the lines of "The
     * emblem cannot find the original source. This turn will be rolled back".
     * This error message will always appear when an opponent tries to play a
     * spell. Player order: A -> D -> C -> B
     */
    @Test
    public void TestOtherPlayerPlaneswalkerCreatedEmblem() {
        // +1: Scry 1, then draw a card.
        // -2: Return target creature to its owner's hand.
        // -8: You get an emblem with "Whenever an opponent casts his or her first spell each turn, counter that spell."
        addCard(Zone.BATTLEFIELD, playerC, "Jace, Unraveler of Secrets");
        addCounters(1, PhaseStep.DRAW, playerC, "Jace, Unraveler of Secrets", CounterType.LOYALTY, 8);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Enchant permanent (Target a permanent as you cast this. This card enters the battlefield attached to that permanent.)
        // You control enchanted permanent.
        addCard(Zone.HAND, playerA, "Confiscate"); // Enchantment Aura

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Confiscate", "Jace, Unraveler of Secrets");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-8: You get an emblem with");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Blind with Anger", "Rootwater Commando");
        attack(2, playerD, "Silvercoat Lion", playerC);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerC, 0);
        assertPermanentCount(playerC, 0);
        assertGraveyardCount(playerA, "Confiscate", 1);
        assertPermanentCount(playerA, "Jace, Unraveler of Secrets", 0); // Removed from game because player C left the game
        assertEmblemCount(playerA, 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Situation: I attacked an opponent with some creatures with True
     * Conviction in play. There were multiple "deals combat damage to a
     * player"-triggers (Edric, Spymaster of Trest, Daxos of Meletis et al),
     * then the opponent lost the game during the first strike combat
     * damage-step . In the second combat damage step the triggers went on the
     * stack again, although there was no player being dealt damage (multiplayer
     * game, so the game wasn't over yet). I don't think these abilities should
     * trigger again here.
     */
    @Test
    public void TestPlayerDiesDuringFirstStrikeDamageStep() {
        // Creatures you control have double strike and lifelink.
        addCard(Zone.BATTLEFIELD, playerD, "True Conviction");
        // Whenever a creature deals combat damage to one of your opponents, its controller may draw a card.
        addCard(Zone.BATTLEFIELD, playerD, "Edric, Spymaster of Trest");
        addCard(Zone.BATTLEFIELD, playerD, "Dross Crocodile", 8); // Creature 5/1

        attack(2, playerD, "Dross Crocodile", playerC);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerC, -3);
        assertLife(playerD, 7);

        assertHandCount(playerD, 2); // 1 (normal draw) + 1 from True Convition
        assertPermanentCount(playerC, 0);

    }

    /**
     * I've encountered a case today where someone conceded on their turn. The
     * remaining phases were went through as normal, but my Luminarch Ascension
     * did not trigger during the end step.
     */
    // Player order: A -> D -> C -> B
    @Test
    public void TestTurnEndTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        // {1}{W}: Create a 4/4 white Angel creature token with flying. Activate this ability only if Luminarch Ascension has four or more quest counters on it..
        addCard(Zone.HAND, playerA, "Luminarch Ascension"); // Enchantment {1}{W}

        addCard(Zone.HAND, playerC, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 1);

        addCard(Zone.HAND, playerD, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Luminarch Ascension");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerD);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Luminarch Ascension", 1);
        assertGraveyardCount(playerC, "Lightning Bolt", 1);

        assertLife(playerD, -1);
        Assert.assertFalse("Player D is no longer in the game", playerD.isInGame());

        assertCounterCount(playerA, "Luminarch Ascension", CounterType.QUEST, 1); // 1 from turn 2

    }
}
