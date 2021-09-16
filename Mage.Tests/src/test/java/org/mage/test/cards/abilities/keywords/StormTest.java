
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class StormTest extends CardTestPlayerBase {

    /**
     * 702.39. Storm 702.39a Storm is a triggered ability that functions on the
     * stack. “Storm” means “When you cast this spell, put a copy of it onto the
     * stack for each other spell that was cast before it this turn. If the
     * spell has any targets, you may choose new targets for any of the copies.”
     * 702.39b If a spell has multiple instances of storm, each triggers
     * separately.
     *
     */
    /**
     * Grapeshot Sorcery, 1R (2) Grapeshot deals 1 damage to target creature or
     * player. Storm (When you cast this spell, copy it for each spell cast
     * before it this turn. You may choose new targets for the copies.)
     *
     */
    @Test
    public void testStorm1x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 15);
    }

    @Test
    public void testStorm2x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 11);
    }

    @Test
    public void testStorm3x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 7);
    }

    @Test
    public void testStorm4x() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 3);
    }

    @Test
    public void testNoStorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Grapeshot");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 19);
    }

    /**
     * If a spell with storm gets countered, the strom trigger is also stifled,
     * which isn't how its supposed to work. For example a Chalic of the Void
     * set to 1 counters Flusterstorm and also counters the storm trigger, which
     * shouldn't happen
     */
    @Test
    public void testStormSpellCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Grapeshot deals 1 damage to any target.
        // Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        addCard(Zone.HAND, playerA, "Grapeshot");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.HAND, playerB, "Counterspell");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Counterspell", "Grapeshot");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 16);  // 3 (Lightning Bolt) + 1 from Storm copied Grapeshot
    }

    /**
     * I provide a game log fo the issue with storm mentioned earlier. I guess
     * Pyromancer Ascension is a culprit.
     *
     *
     */
    @Test
    public void testStormAndPyromancerAscension() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on Pyromancer Ascension.
        // Whenever you cast an instant or sorcery spell while Pyromancer Ascension has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Pyromancer Ascension", 1);
        // Grapeshot deals 1 damage to any target. - Sorcery {1}{R}
        // Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        addCard(Zone.LIBRARY, playerA, "Grapeshot", 2);
        skipInitShuffling();
        // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        addCard(Zone.HAND, playerA, "Sleight of Hand");
        addCard(Zone.HAND, playerA, "Shock", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleight of Hand");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "targetPlayer=PlayerB", "Shock", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "targetPlayer=PlayerB", "Shock", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Shock", 3);
        assertGraveyardCount(playerA, "Grapeshot", 1);
        assertCounterCount("Pyromancer Ascension", CounterType.QUEST, 2);
        assertLife(playerB, 8); // 6 from the Shocks + 5 from Grapeshot + 1 from Pyromancer Ascencsion copy
    }

    /**
     * I provide a game log fo the issue with storm mentioned earlier. I guess
     * Pyromancer Ascension is a culprit.
     *
     *
     */
    @Test
    public void testStormAndFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Geistflame deals 1 damage to any target.
        // Flashback {3}{R} (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerA, "Geistflame", 2); // {R}
        addCard(Zone.LIBRARY, playerA, "Grapeshot", 2);
        skipInitShuffling();
        // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        addCard(Zone.HAND, playerA, "Sleight of Hand");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sleight of Hand");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geistflame", playerB);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "Flashback {3}{R}");
        addTarget(playerA, playerB);
        castSpell(1, PhaseStep.END_COMBAT, playerA, "Geistflame", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grapeshot", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount("Geistflame", 1);
        assertGraveyardCount(playerA, "Geistflame", 1);
        assertGraveyardCount(playerA, "Grapeshot", 1);
        assertLife(playerB, 12); // 3 from the Geistflame + 5 from Grapeshot
    }

    /*
     * I cast Wheel of Fortune. (1st)
     * I cast Mox Emerald. (2nd)
     * I cast Turnabout. (3rd)
     * I cast Yawgmoth's Will. (4th)
     * I cast Palinchron from graveyard. (5th)
     * I cast Mind's Desire from graveyard. Storm makes 2
     * copies (instead of 5). (6th) I cast Turnabout from graveyard. (7th) I
     * cast Golgari Signet from exile. (8th) I cast Empty the Warrens. Storm
     * makes 5 copies (instead of 8). (9th)
     *
     */
    @Test
    public void testStormYawgmothsWill() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Each player discards their hand,
        // then draws seven cards.
        addCard(Zone.HAND, playerA, "Wheel of Fortune", 1); // {2}{R}
        addCard(Zone.LIBRARY, playerA, "Mox Emerald", 1);
        // Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls.
        addCard(Zone.LIBRARY, playerA, "Turnabout", 1); // {2}{U}{U}

        // Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        addCard(Zone.LIBRARY, playerA, "Yawgmoth's Will", 1);  // {2}{B}
        skipInitShuffling();

        // Flying
        // When Palinchron enters the battlefield, untap up to seven lands.
        // {2}{U}{U}: Return Palinchron to its owner's hand.
        addCard(Zone.HAND, playerA, "Palinchron", 1);  // {5}{U}{U}
        // Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        // Storm
        addCard(Zone.HAND, playerA, "Mind's Desire", 1);  // {4}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wheel of Fortune");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Emerald");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Turnabout");
        setChoice(playerA, "Land");
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Palinchron");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind's Desire");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wheel of Fortune", 1);
        assertPermanentCount(playerA, "Mox Emerald", 1);
        assertGraveyardCount(playerA, "Turnabout", 1);
        assertPermanentCount(playerA, "Palinchron", 1);

        assertExileCount("Yawgmoth's Will", 1);
        assertExileCount("Mind's Desire", 1);

        assertExileCount(playerA, 8); // 6 from Mind's Desire and the Desire and the Yawgmoth's Will

    }

}
