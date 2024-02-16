package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class EndTurnEffectTest extends CardTestPlayerBase {

    /**
     * Additional bug: Days Undoing and Sphinx's Tutelage are broken. You
     * shouldn't get triggers off of Tutelage, since the turn ends, but it has
     * you resolve them in your cleanup step.
     * <p>
     * http://tabakrules.tumblr.com/post/122350751009/days-undoing-has-been-officially-spoiled-on
     */
    @Test
    public void testSpellsAffinity() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever you draw a card, target opponent puts the top two cards of their library into their graveyard. If they're both nonland cards that share a color, repeat this process.
        // {5}{U}: Draw a card, then discard a card.
        addCard(Zone.BATTLEFIELD, playerA, "Sphinx's Tutelage");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards. If it's your turn, end the turn.
        //  (Exile all spells and abilities on the stack, including this card.
        //   Discard down to your maximum hand size. Damage wears off, and
        //   "this turn" and "until end of turn" effects end.)
        addCard(Zone.HAND, playerA, "Day's Undoing"); //Sorcery {2}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day's Undoing");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertExileCount("Day's Undoing", 1);

        assertHandCount(playerA, 7);
        assertHandCount(playerB, 7);

        assertGraveyardCount(playerB, 0); // because the triggers of Sphinx's Tutelage cease to exist

    }

    @Test
    public void testSpellSplitCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // End the turn.
        //  (Exile all spells and abilities on the stack, including this card.
        //   Discard down to your maximum hand size. Damage wears off, and
        //   "this turn" and "until end of turn" effects end.)
        addCard(Zone.HAND, playerA, "Time Stop"); //Instant {4}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Fire
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice
        // Tap target permanent. Draw a card.
        addCard(Zone.HAND, playerB, "Fire // Ice"); // Instant {1}{R} // {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Ice", "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Time Stop", TestPlayer.NO_TARGET, "Ice");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerB, "Fire // Ice", 0);
        assertExileCount(playerA, "Time Stop", 1);
        assertExileCount(playerB, "Fire // Ice", 1);
        assertTapped("Silvercoat Lion", false);
        assertHandCount(playerA, 0);
        assertHandCount(playerB, 0);

    }

    /**
     * Test to remove a Aftermath card from spell
     */
    @Test
    public void testSpellAftermath() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Insult Sorcery {2}{R}
        // Damage can't be prevented this turn. If a source you control would deal damage this turn, it deals double that damage instead.
        // Injury Sorcery {2}{R}
        // Aftermath (Cast this spell only from your graveyard. Then exile it.)
        // Injury deals 2 damage to target creature and 2 damage to target player.
        addCard(Zone.GRAVEYARD, playerA, "Insult // Injury");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        // End the turn.(Exile all spells and abilities on the stack. Discard down to your maximum hand size. Damage wears off, and \"this turn\" and \"until end of turn\" effects end.)
        // At the beginning of your next end step, you lose the game.
        addCard(Zone.HAND, playerB, "Glorious End"); //Instant {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Injury", "Silvercoat Lion");
        addTarget(playerA, playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Glorious End", TestPlayer.NO_TARGET, "Injury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Glorious End", 1);
        assertGraveyardCount(playerA, "Insult // Injury", 0);
        assertExileCount(playerA, "Insult // Injury", 1);
        assertGraveyardCount(playerB, "Glorious End", 0);
        assertHandCount(playerA, 0);
        // TODO Check
        assertHandCount(playerB, 1); // No idea why playerB has a mountain into hand

    }

    /**
     * Test to end the turn by an ability
     */
    @Test
    public void testSundialOfTheInfinite() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Mountain", 10);
        // {1}, {T}: End the turn. Activate this ability only during your turn.
        addCard(Zone.HAND, playerA, "Sundial of the Infinite", 1); // Artifact {2}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerB, "Disenchant"); //Instant {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sundial of the Infinite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Sundial of the Infinite");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1},", TestPlayer.NO_TARGET, "Disenchant");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Disenchant", 1);

        assertPermanentCount(playerA, "Sundial of the Infinite", 1);

        assertHandCount(playerA, 7); // Discard to maximum hand size
        assertHandCount(playerB, 1); // 1 card drawn at start of 2nd turn
    }
}
