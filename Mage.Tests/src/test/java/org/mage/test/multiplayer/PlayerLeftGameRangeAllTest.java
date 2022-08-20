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
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author LevelX2
 */
public class PlayerLeftGameRangeAllTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 2);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
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

    /**
     * "When playing in a multiplayer match against humans, the aura curse
     * "Curse of Vengeance" is supposed to award cards and life to its caster
     * when the victim of the spell loses the game. It seems to erroneously
     * award those cards to the victim of the curse, who, by that point, is
     * already dead, making the spell almost totally useless."
     */
    @Test
    public void TestCurseOfVengeance() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Whenever enchanted player casts a spell, put a spite counter on Curse of Vengeance.
        // When enchanted player loses the game, you gain X life and draw X cards, where X is the number of spite counters on Curse of Vengeance.
        addCard(Zone.HAND, playerA, "Curse of Vengeance"); // Enchantment {B}

        addCard(Zone.HAND, playerC, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 1);

        addCard(Zone.HAND, playerD, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Vengeance", playerD);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerD);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerC, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Curse of Vengeance", 1);

        assertLife(playerD, -1);
        Assert.assertFalse("Player D is no longer in the game", playerD.isInGame());

        assertHandCount(playerA, 3);
        assertLife(playerA, 4);

    }

    /**
     * * 11/4/2015: In a multiplayer game, if Grasp of Fate's owner leaves the
     * game, the exiled cards will return to the battlefield. Because the
     * one-shot effect that returns the cards isn't an ability that goes on the
     * stack, it won't cease to exist along with the leaving player's spells and
     * abilities on the stack.
     */
    @Test
    public void TestGraspOfFateReturn() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent that player
        // controls until Grasp of Fate leaves the battlefield.
        addCard(Zone.HAND, playerA, "Grasp of Fate"); // Enchantment {1}{W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        addCard(Zone.HAND, playerC, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Juggernaut", 1);

        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grasp of Fate");
        addTarget(playerA, "Pillarfield Ox");
        addTarget(playerA, "Juggernaut");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerC, "Lightning Bolt", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerC, "Lightning Bolt", 1);

        assertLife(playerA, -1);
        Assert.assertFalse("Player D is no longer in the game", playerA.isInGame());

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPermanentCount(playerC, "Juggernaut", 1);
        assertPermanentCount(playerD, "Silvercoat Lion", 1);

    }

    /**
     * In an EDH game, a player controlling Thalia left the game and the ability
     * still lasted for the rest of the game. Gamelog if it helps.
     */
    @Test
    public void TestThaliaHereticCatharContinuousEffectEndsIfPlayerDies() {
        // Player order: A -> D -> C -> B

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // First strike
        // Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        addCard(Zone.HAND, playerA, "Thalia, Heretic Cathar"); // Creature {2}{W}

        addCard(Zone.HAND, playerD, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerD, "Mountain", 1);

        addCard(Zone.HAND, playerC, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Plains", 4);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thalia, Heretic Cathar");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Lightning Bolt", playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Pillarfield Ox");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerD, "Lightning Bolt", 1);

        assertLife(playerA, -1);
        Assert.assertFalse("Player D is no longer in the game", playerA.isInGame());

        assertPermanentCount(playerC, "Pillarfield Ox", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertTapped("Pillarfield Ox", false);
        assertTapped("Silvercoat Lion", false);

    }

    /**
     * In an EDH game, a player controlling Thalia left the game and the ability
     * still lasted for the rest of the game. Gamelog if it helps.
     */
    @Test
    public void TestThaliaHereticCatharContinuousEffectEndsIfPlayerConcedes() {
        // Player order: A -> D -> C -> B

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // First strike
        // Creatures and nonbasic lands your opponents control enter the battlefield tapped.
        addCard(Zone.HAND, playerA, "Thalia, Heretic Cathar"); // Creature {2}{W}

        addCard(Zone.HAND, playerD, "Juggernaut");
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 4);

        addCard(Zone.HAND, playerC, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Plains", 4);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thalia, Heretic Cathar");

        concede(2, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Juggernaut");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Pillarfield Ox");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        Assert.assertFalse("Player A is no longer in the game", playerA.isInGame());
        assertLife(playerA, 2);

        assertPermanentCount(playerD, "Juggernaut", 1);
        assertPermanentCount(playerC, "Pillarfield Ox", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertTapped("Pillarfield Ox", false);
        assertTapped("Silvercoat Lion", false);
        assertTapped("Juggernaut", false);

    }

    /**
     * https://github.com/magefree/mage/issues/6997
       Some continuous effects should stay in play even after the player that set them leaves the game. 
       Example: 
       * Player A: Casts Vorinclex, Voice of Hunger 
       * Player D: Taps all lands and do stuff (lands shouldn't untap during his next untap step)     
       * Player C: Kills Player A Player D: Lands untapped normally, though they shouldn't
       * 
       * This happened playing commander against 3 AIs. One of the AIs played Vorinclex, I tapped all my lands during my turn to do stuff. 
       * Next AI killed the one that had Vorinclex. When the game got to my turn, my lands untapped normally.
     */
    @Test
    public void TestContinuousEffectStaysAfterCreatingPlayerLeft() {
        // Player order: A -> D -> C -> B
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        // Trample
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerA, "Vorinclex, Voice of Hunger"); // Creature 7/6  {6}{G}{G}

        addCard(Zone.HAND, playerD, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);

        addCard(Zone.HAND, playerC, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Mountain", 1);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vorinclex, Voice of Hunger");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        setChoice(playerA, "Whenever an opponent taps a land for mana");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Lightning Bolt", playerA);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        
        execute();

        assertAllCommandsUsed();
        
        assertPermanentCount(playerD, "Silvercoat Lion", 1);
        
        assertGraveyardCount(playerC, "Lightning Bolt", 1);
        
        Assert.assertFalse("Player A is no longer in the game", playerA.isInGame());

        Assert.assertTrue("Player D is the active player",currentGame.getActivePlayerId().equals(playerD.getId()));
        
        assertTappedCount("Plains", true, 2); // Do not untap because of Vorinclex do not untap effect

    }

    
}
