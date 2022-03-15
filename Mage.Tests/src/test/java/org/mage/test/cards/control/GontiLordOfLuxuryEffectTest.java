
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GontiLordOfLuxuryEffectTest extends CardTestPlayerBase {

    /**
     * Returning to your hand a creature you own but is controlled by an
     * opponent doesn't let you replay it. Happened after I Aether Tradewinded
     * my Rashmi that an opponent cast with Gonti, Lord of Luxury (the exile
     * part could have something to do with this?). Then on my turn I couldn't
     * replay it.
     */
    @Test
    public void testCanBeCastAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury", 1); // Creature {2}{B}{B}

        addCard(Zone.LIBRARY, playerB, "Rashmi, Eternities Crafter"); // Creature {2}{G}{U}
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // Return target permanent you control and target permanent you don't control to their owners' hands.
        addCard(Zone.HAND, playerB, "Aether Tradewinds", 1); // Intant {2}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Rashmi, Eternities Crafter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Rashmi, Eternities Crafter");

        castSpell(1, PhaseStep.END_TURN, playerB, "Aether Tradewinds", "Silvercoat Lion^Rashmi, Eternities Crafter");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Rashmi, Eternities Crafter");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Aether Tradewinds", 1);
        assertHandCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerB, "Rashmi, Eternities Crafter", 0);
        assertPermanentCount(playerB, "Rashmi, Eternities Crafter", 1);

    }

    /**
     * Opponent using Gonti, Lord of Luxury took Mirari's Wake out of my library
     * and cast it. I cast Cyclonic Rift on Mirari's Wake to put it back in my
     * hand and was unable to recast Mirari's Wake.
     */
    @Test
    public void testCanBeCastAgainCyclonicRift() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury", 1); // Creature 2/3 {2}{B}{B}

        // Creatures you control get +1/+1.
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        addCard(Zone.LIBRARY, playerB, "Mirari's Wake"); // Enchantment {3}{G}{W}
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        // Return target nonland permanent you don't control to its owner's hand.
        // Overload {6}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerB, "Cyclonic Rift", 1); // Intant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Mirari's Wake");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mirari's Wake");
        castSpell(1, PhaseStep.END_TURN, playerB, "Cyclonic Rift", "Mirari's Wake");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mirari's Wake");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gonti, Lord of Luxury", 1);
        assertPowerToughness(playerA, "Gonti, Lord of Luxury", 2, 3);
        assertGraveyardCount(playerB, "Cyclonic Rift", 1);

        assertPermanentCount(playerB, "Mirari's Wake", 1);
        assertPowerToughness(playerB, "Silvercoat Lion", 3, 3);

    }

    /**
     * I noticed in a game that when you cast Lingering Souls off of Gonti, Lord
     * of Luxury and then the lingering souls goes to the graveyard it cannot be
     * flashed back. The gonti was my opponent's and the lingering souls was
     * mine for reference.
     */
    @Test
    public void testCanBeCastLaterWithFlashBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury", 1); // Creature 2/3 {2}{B}{B}

        // Create two 1/1 white Spirit creature tokens with flying.
        // Flashback {1}{B}
        addCard(Zone.LIBRARY, playerB, "Lingering Souls"); // Sorcery {2}{W}
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Lingering Souls");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lingering Souls");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Flashback");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gonti, Lord of Luxury", 1);
        assertPowerToughness(playerA, "Gonti, Lord of Luxury", 2, 3);
        assertPermanentCount(playerA, "Spirit Token", 2);

        assertPermanentCount(playerB, "Spirit Token", 2);

        assertExileCount("Lingering Souls", 1);

    }

    /**
     * An opponent used a Gonti against me, and took my Ob Nixilis Reignited. He
     * later played it, and I killed my Ob Nixilis. Then later, using Seasons
     * Past, I got Ob Nixilis back in my hand. However, the Ob Nixilis was now
     * uncastable. Has anyone else encountered this?
     */
    @Test
    public void testPlaneswalkerCanBeCastLaterFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        // Deathtouch
        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down,
        // then put the rest on the bottom of that library in a random order. For as long as that card remains exiled,
        // you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        addCard(Zone.HAND, playerA, "Gonti, Lord of Luxury", 1); // Creature 2/3 {2}{B}{B}

        // +1: You draw a card and you lose 1 life.
        // -3: Destroy target creature.
        // -8: Target opponent gets an emblem with "Whenever a player draws a card, you lose 2 life."
        addCard(Zone.LIBRARY, playerB, "Ob Nixilis Reignited"); // Planeswalker [5]  {3}{B}{B}
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        // Return any number of cards with different converted mana costs from your graveyard to your hand. Put Seasons Past on the bottom of its owner's library.
        addCard(Zone.HAND, playerB, "Seasons Past", 1); // Sorcery {4}{G}{G}

        addCard(Zone.BATTLEFIELD, playerB, "Dross Crocodile", 2); // Creature 5/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gonti, Lord of Luxury");
        addTarget(playerA, playerB);
        setChoice(playerA, "Ob Nixilis Reignited");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ob Nixilis Reignited");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-3:", "Dross Crocodile");

        attack(2, playerB, "Dross Crocodile", "Ob Nixilis Reignited");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Seasons Past");
        setChoice(playerB, "Ob Nixilis Reignited");

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Ob Nixilis Reignited");

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Gonti, Lord of Luxury", 1);
        assertPermanentCount(playerB, "Dross Crocodile", 1);
        assertGraveyardCount(playerB, "Dross Crocodile", 1);

        assertGraveyardCount(playerB, "Seasons Past", 0);

        assertHandCount(playerB, "Ob Nixilis Reignited", 0);
        assertPermanentCount(playerB, "Ob Nixilis Reignited", 1);

    }
}
