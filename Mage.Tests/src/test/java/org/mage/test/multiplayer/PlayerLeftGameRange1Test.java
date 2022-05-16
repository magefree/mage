package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author LevelX2
 */
public class PlayerLeftGameRange1Test extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 2);
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
        addCard(Zone.BATTLEFIELD, playerB, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Enchant creature
        // You control enchanted creature.
        addCard(Zone.HAND, playerA, "Control Magic");

        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Control Magic", "Rootwater Commando");

        attack(3, playerC, "Silvercoat Lion", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 0);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0);
        assertGraveyardCount(playerA, "Control Magic", 1);

    }

    /**
     * Tests Sorcery to control other players permanent
     */
    @Test
    public void TestControlledBySorcery() {
        addCard(Zone.BATTLEFIELD, playerB, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        // (This effect lasts indefinitely.)
        addCard(Zone.HAND, playerA, "Legerdemain"); // Sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Air");

        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Legerdemain", "Rootwater Commando^Wall of Air");

        attack(3, playerC, "Silvercoat Lion", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 0);
        assertGraveyardCount(playerA, "Legerdemain", 1);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0); // removed from game because player B left
        assertPermanentCount(playerB, "Wall of Air", 0);
        assertGraveyardCount(playerA, "Wall of Air", 0);
        assertPermanentCount(playerA, "Wall of Air", 1); // Returned back to player A

    }

    /**
     * Tests Instant to control other permanent
     */
    @Test
    public void TestOtherPlayerControllsCreature() {
        addCard(Zone.BATTLEFIELD, playerB, "Rootwater Commando");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Untap target nonlegendary creature and gain control of it until end of turn. That creature gains haste until end of turn.
        addCard(Zone.HAND, playerA, "Blind with Anger"); // Instant

        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Blind with Anger", "Rootwater Commando");

        attack(3, playerC, "Silvercoat Lion", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 0);
        assertGraveyardCount(playerA, "Blind with Anger", 1);
        assertPermanentCount(playerB, 0);
        assertPermanentCount(playerA, "Rootwater Commando", 0); // Removed from game because player C left
        assertPermanentCount(playerA, "Rootwater Commando", 0); // Returned back to player A
    }

    /**
     * Xmage throws an error involving an emblem unable to find the initial
     * source if it has a proc. To reproduce, a Planeswalker was taken from an
     * original player's control, such as using Scrambleverse to shuffle Jace,
     * Unraveler of Secrets, to a second player and then the second player uses
     * Jace's ability to create an emblem ("Whenever an opponent casts their
     * first spell each turn, counter that spell."). Then the original player
     * concedes the game and removes the Planeswalker. Once it becomes an
     * opponent of the original player's turn and that opponent plays a spell,
     * Xmage throws an error and rollsback the turn.
     * <p>
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
        // -8: You get an emblem with "Whenever an opponent casts their first spell each turn, counter that spell."
        addCard(Zone.BATTLEFIELD, playerB, "Jace, Unraveler of Secrets");
        addCounters(1, PhaseStep.DRAW, playerB, "Jace, Unraveler of Secrets", CounterType.LOYALTY, 8);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Enchant permanent (Target a permanent as you cast this. This card enters the battlefield attached to that permanent.)
        // You control enchanted permanent.
        addCard(Zone.HAND, playerA, "Confiscate"); // Enchantment Aura

        addCard(Zone.BATTLEFIELD, playerC, "Plains", 2);
        addCard(Zone.HAND, playerC, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);
        addCard(Zone.HAND, playerD, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Confiscate", "Jace, Unraveler of Secrets");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-8: You get an emblem with");

        attack(3, playerC, "Silvercoat Lion", playerB);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerC, "Silvercoat Lion");

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 0);
        assertPermanentCount(playerB, 0);
        assertGraveyardCount(playerA, "Confiscate", 1);
        assertPermanentCount(playerA, "Jace, Unraveler of Secrets", 0); // Removed from game because player C left the game
        assertEmblemCount(playerA, 1);
        assertPermanentCount(playerC, "Silvercoat Lion", 2); // Emblem does not work yet on player C, because range 1
        assertGraveyardCount(playerD, "Silvercoat Lion", 1); // Emblem should counter the spell
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

    @Test
    public void TestTurnEndTriggerAfterConcede() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        // {1}{W}: Create a 4/4 white Angel creature token with flying. Activate this ability only if Luminarch Ascension has four or more quest counters on it..
        addCard(Zone.HAND, playerA, "Luminarch Ascension"); // Enchantment {1}{W}

        addCard(Zone.HAND, playerD, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Luminarch Ascension");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        concede(2, PhaseStep.BEGIN_COMBAT, playerD);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Luminarch Ascension", 1);

        assertLife(playerD, 2);
        Assert.assertFalse("Player D is no longer in the game", playerD.isInGame());

        assertCounterCount(playerA, "Luminarch Ascension", CounterType.QUEST, 1); // 1 from turn 2
    }

    /**
     * Pithing Needle keeps the named card's abilities disabled even after the
     * player controlling the Needle loses the game.
     * <p>
     * I saw it happen during a Commander game. A player cast Pithing Needle
     * targeting my Proteus Staff. After I killed him, I still couldn't activate
     * the Staff.
     */
    @Test
    public void TestPithingNeedle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // As Pithing Needle enters the battlefield, name a card.
        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        addCard(Zone.HAND, playerA, "Pithing Needle"); // Artifact {1}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Proteus Staff", 1);

        addCard(Zone.BATTLEFIELD, playerD, "Island", 3);
        // {2}{U}, {T}: Put target creature on the bottom of its owner's library. That creature's controller reveals cards from the
        // top of their library until they reveal a creature card. The player puts that card onto the battlefield and the
        // rest on the bottom of their library in any order. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerD, "Proteus Staff", 1);

        addCard(Zone.BATTLEFIELD, playerD, "Eager Cadet", 1);
        addCard(Zone.LIBRARY, playerD, "Storm Crow", 2);

        addCard(Zone.BATTLEFIELD, playerC, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerC, "Proteus Staff", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Wall of Air", 1);
        addCard(Zone.LIBRARY, playerC, "Wind Drake", 2);

        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pithing Needle");
        setChoice(playerA, "Proteus Staff");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerD, "{2}{U}", "Silvercoat Lion"); // not allowed

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerC, "{2}{U}", "Eager Cadet"); // allowed because Needle out of range

        // Concede the game
        concede(3, PhaseStep.POSTCOMBAT_MAIN, playerA);

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}{U}", "Wall of Air"); // allowed because Needle lost game

        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            assertAllCommandsUsed();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerD must have 0 actions but found 1")) {
                Assert.fail("must throw error PlayerD canot acting, but got:\n" + e.getMessage());
            }
        }

        assertLife(playerA, 2);
        Assert.assertFalse("Player A is no longer in the game", playerA.isInGame());

        assertPermanentCount(playerA, 0);

        Permanent staffPlayerD = getPermanent("Proteus Staff", playerD);
        Assert.assertFalse("Staff of player D could not be used", staffPlayerD.isTapped());

        assertPermanentCount(playerD, "Eager Cadet", 0);
        assertPermanentCount(playerD, "Storm Crow", 1);

        Permanent staffPlayerC = getPermanent("Proteus Staff", playerC);
        Assert.assertTrue("Staff of player C could be used", staffPlayerC.isTapped());

        assertPermanentCount(playerC, "Wall of Air", 0);
        assertPermanentCount(playerC, "Wind Drake", 1);

        Permanent staffPlayerB = getPermanent("Proteus Staff", playerB);
        Assert.assertTrue("Staff of player B could be used", staffPlayerB.isTapped());

    }

    /**
     * Captive Audience doesn't work correctly in multiplayer #5593
     * <p>
     * Currently, if the controller of Captive Audience leaves the game, Captive
     * Audience returns to its owner instead of being exiled.
     */
    @Test
    public void TestCaptiveAudienceGoesToExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Captive Audience enters the battlefield under the control of an opponent of your choice.
        // At the beginning of your upkeep, choose one that hasn't been chosen —
        // • Your life total becomes 4.
        // • Discard your hand.
        // • Each opponent creates five 2/2 black Zombie creature tokens.
        addCard(Zone.HAND, playerA, "Captive Audience"); // Enchantment {5}{B}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Captive Audience");
        setChoice(playerA, "PlayerD");

        setModeChoice(playerD, "1");

        attack(5, playerA, "Silvercoat Lion", playerD);
        attack(5, playerA, "Pillarfield Ox", playerD);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertAllCommandsUsed();

        assertLife(playerA, 2);

        Assert.assertFalse("Player D is no longer in the game", playerD.isInGame());

        assertPermanentCount(playerD, 0);

        assertPermanentCount(playerA, "Captive Audience", 0);
        assertGraveyardCount(playerA, "Captive Audience", 0);
        assertExileCount(playerA, "Captive Audience", 1);

    }

}
