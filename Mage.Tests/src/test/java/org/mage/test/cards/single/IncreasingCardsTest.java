package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests flashback
 * 
 * @author BetaSteward
 */
public class IncreasingCardsTest extends CardTestPlayerBase {

    /**
     *  Increasing Ambition
     *  Sorcery, 4B
     *  Search your library for a card and put that card into your hand. If Increasing
     *  Ambition was cast from a graveyard, instead search your library for two cards
     *  and put those cards into your hand. Then shuffle your library.
     *  Flashback {7}{B} (You may cast this card from your graveyard for its flashback cost. Then exile it.)
     */
    @Test
    public void testIncreasingAmbition() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.HAND, playerA, "Increasing Ambition");
        addCard(Zone.LIBRARY, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Ambition");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {7}{B}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertHandCount(playerA, 4);
        assertExileCount("Increasing Ambition", 1);

    }

    @Test
    public void testIncreasingConfusion() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Increasing Confusion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Confusion");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {X}{U}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertExileCount("Increasing Confusion", 1);
        assertGraveyardCount(playerB, 9);

    }

    @Test
    public void testIncreasingDevotion() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 9);
        addCard(Zone.HAND, playerA, "Increasing Devotion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Devotion");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {7}{W}{W}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, "Human", 15);
        assertExileCount("Increasing Devotion", 1);

    }

    @Test
    public void testIncreasingSavagery() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, "Increasing Savagery");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Savagery");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {5}{G}{G}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 0);
        assertPowerToughness(playerA, "Ornithopter", 15, 17, Filter.ComparisonScope.Any);
        assertExileCount("Increasing Savagery", 1);

    }

    @Test
    public void testIncreasingVengeance() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.HAND, playerA, "Increasing Vengeance");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Vengeance", "Lightning Bolt");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {3}{R}{R}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 5);
        assertGraveyardCount(playerA, 2);
        assertExileCount("Increasing Vengeance", 1);

    }

}
