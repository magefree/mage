
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DisruptingShoalTest extends CardTestPlayerBase {

    @Test
    public void testWithManaPaymentEqual() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);  // {X}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Silvercoat Lion");
        setChoice(playerB, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void testWithManaPaymentDifferent() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);  // {X}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Silvercoat Lion");
        setChoice(playerB, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs And
     * the X Value is equal to the CMC of the exiled blue card
     *
     */
    @Test
    public void testWithBlueCardsInHand() {
        addCard(Zone.HAND, playerA, "Pillarfield Ox");
        addCard(Zone.HAND, playerA, "Spell Snare");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.HAND, playerB, "Mistfire Adept", 2); // blue cards with 4 CMC to pay Disrupting Shoal
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Pillarfield Ox", "Pillarfield Ox");
        playerB.addChoice("Yes"); // use alternate costs = Mistfire Adept = CMC = 4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spell Snare", "Disrupting Shoal", "Disrupting Shoal");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertExileCount(playerB, 1);     // Mistfire Adept
        assertHandCount(playerB, "Mistfire Adept", 1);     // One Left

        assertHandCount(playerA, "Spell Snare", 1);     // Can't be cast -> no valid target

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
    }

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs And
     * the X Value can be equal to either half of a fuse card.
     *
     * Reported bug: "Casting Disrupting Shoal pitching Far // Away does not
     * counter spells with converted mana cost 2 or 3, which it should. Instead
     * it does counter spells with converted mana cost 5, which it shouldn't".
     */
    @Test
    public void testWithFuseCardCounterCMCTwo() {

        // CMC 2 and CMC 3
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // 2/2 {1}{G}

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal", 1);
        /**
         * Far {1}{U} Instant Return target creature to its owner's hand. Away
         * {2}{B} Instant Target player sacrifices a creature. Fuse (You may
         * cast one or both halves of this card from your hand.)
         */
        addCard(Zone.HAND, playerB, "Far // Away", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Grizzly Bears");
        playerB.addChoice("Yes"); // use alternate costs = 2 CMC = Far

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0); // should have been countered by Shoal
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs And
     * the X Value can be equal to either half of a fuse card.
     *
     * Reported bug: "Casting Disrupting Shoal pitching Far // Away does not
     * counter spells with converted mana cost 2 or 3, which it should. Instead
     * it does counter spells with converted mana cost 5, which it shouldn't".
     */
    @Test
    public void testWithFuseCardCounterCMCThree() {

        addCard(Zone.HAND, playerA, "Centaur Courser"); // 3/3 {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        /**
         * Far {1}{U} Instant Return target creature to its owner's hand. Away
         * {2}{B} Instant Target player sacrifices a creature. Fuse (You may
         * cast one or both halves of this card from your hand.)
         */
        addCard(Zone.HAND, playerB, "Disrupting Shoal", 1);
        addCard(Zone.HAND, playerB, "Far // Away", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Centaur Courser");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Centaur Courser", "Centaur Courser");
        playerB.addChoice("Yes"); // use alternate costs = 3 CMC = Away

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Centaur Courser", 0); // should have been countered by Shoal
        assertGraveyardCount(playerA, "Centaur Courser", 1);
    }

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs And
     * the X Value can be equal to either half of a fuse card. Not the combined
     * cost of both.
     *
     * Reported bug: "Casting Disrupting Shoal pitching Far // Away does not
     * counter spells with converted mana cost 2 or 3, which it should. Instead
     * it does counter spells with converted mana cost 5, which it shouldn't".
     */
    @Test
    public void testWithFuseCardShouldNotCounterCMCFive() {

        addCard(Zone.HAND, playerA, "Air Elemental"); // 4/4 Flying {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        /**
         * Far {1}{U} Instant Return target creature to its owner's hand. Away
         * {2}{B} Instant Target player sacrifices a creature. Fuse (You may
         * cast one or both halves of this card from your hand.)
         */
        addCard(Zone.HAND, playerB, "Disrupting Shoal", 1);
        addCard(Zone.HAND, playerB, "Far // Away", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Air Elemental");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Air Elemental", "Air Elemental");
        playerB.addChoice("Yes"); // use alternate costs = 2 or 3 CMC = Far // Away, not the combined cost!

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Air Elemental", 1); // should NOT have been countered by Shoal
        assertGraveyardCount(playerA, "Air Elemental", 0);
    }
}
