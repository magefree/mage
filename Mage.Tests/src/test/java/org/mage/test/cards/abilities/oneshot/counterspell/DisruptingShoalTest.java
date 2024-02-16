package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class DisruptingShoalTest extends CardTestPlayerBase {

    // https://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/762504-disrupting-shoal-and-fuse-split-cards
    // For the purpose of Disrupting Shoal, a split card in your hand has a converted mana cost equal to the sum of
    // the converted mana costs of its two halves, because "[t]he mana cost of a split card is the combined mana
    // costs of its two halves" while it's in your hand (C.R. 708.4, 708.4b, 202.3). For example, Beck & Call
    // has converted mana cost 8 while it's in your hand. You can exile a split card with Disrupting Shoal
    // only if that card has converted mana cost X. Whether the card has fuse or not doesn't matter.
    // Nevertheless, Disrupting Shoal can still target a spell even if X doesn't match that spell's converted
    // mana cost, because Disrupting Shoal requires only a "spell" as a target (C.R. 114.1a, 601.2c); it's just
    // that it will do nothing if X doesn't match the targeted spell's converted mana cost when it resolves.

    @Test
    public void testWithManaPaymentEqual() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);  // {X}{U}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Silvercoat Lion", "Silvercoat Lion");
        setChoice(playerB, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Silvercoat Lion", "Silvercoat Lion");
        setChoice(playerB, "X=1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Test that Disrupting Shoal can be played with alternate casting costs And
     * the X Value is equal to the CMC of the exiled blue card
     */
    @Test
    public void testWithBlueCardsInHand() {
        addCard(Zone.HAND, playerA, "Pillarfield Ox");

        // Counter target spell with converted mana cost 2.
        addCard(Zone.HAND, playerA, "Spell Snare");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        // Counter target spell if its converted mana cost is X.
        addCard(Zone.HAND, playerB, "Disrupting Shoal");
        addCard(Zone.HAND, playerB, "Mistfire Adept", 2); // blue cards with 4 CMC to pay Disrupting Shoal
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // cast spell with cmc = 4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Pillarfield Ox", "Pillarfield Ox");
        setChoice(playerB, true); // use alternate costs
        setChoice(playerB, "Mistfire Adept"); // pay to cast Mistfire Adept (CMC = 4)

        // rules: 202.3e When calculating the converted mana cost of an object with an {X} in its mana cost,
        // X is treated as 0 while the object is not on the stack, and X is treated as the number chosen for
        // it while the object is on the stack.
        //
        // SO Spell Snare can't be played here (it need cmc 2, but Disrupting Shoal got cmc = 4 + 2)
        checkPlayableAbility("can't cast Spell Snare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Spell Snare", false);
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spell Snare", "Disrupting Shoal", "Disrupting Shoal");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertExileCount(playerB, 1); // Mistfire Adept
        assertHandCount(playerB, "Mistfire Adept", 1); // One Left

        assertHandCount(playerA, "Spell Snare", 1); // Can't be cast -> no valid target

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
    }

    @Test
    public void testWithFuseCardCounterCMC_LeftIgnore() {

        // cmc 2
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
        addCard(Zone.HAND, playerB, "Far // Away", 1); // cmc 2 + 3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // try to pay by split card, but can't counter -- X <> bear's cmc
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Grizzly Bears", "Grizzly Bears");
        setChoice(playerB, true); // use alternative cost
        setChoice(playerB, "Far // Away"); // pay by card (cmc = 5, so X = 5 too)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1); // can't counter (cmc 2 <> x = 5)
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testWithFuseCardCounterCMC_RightIgnore() {
        // cmc 3
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

        // try to pay by split card, but can't counter -- X <> centaur's cmc
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Centaur Courser", "Centaur Courser");
        setChoice(playerB, true); // use alternative cost
        setChoice(playerB, "Far // Away"); // pay by card (cmc = 5, so X = 5 too)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Centaur Courser", 1); // can't counter (cmc 3 <> x = 5)
        assertGraveyardCount(playerA, "Centaur Courser", 0);
    }

    @Test
    public void testWithFuseCardShouldNotCounterCMC_BothUses() {
        // cmc 5
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

        // try to pay by split card and it works
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Disrupting Shoal", "Air Elemental", "Air Elemental");
        setChoice(playerB, true); // use alternative cost
        setChoice(playerB, "Far // Away"); // pay by card (cmc = 5, so X = 5 too)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 1); // Far // Away should be exiled as part of Disrupting alternative cost
        assertGraveyardCount(playerB, "Disrupting Shoal", 1);
        assertPermanentCount(playerA, "Air Elemental", 0); // will counter cause (cmc 5 == x = 5)
        assertGraveyardCount(playerA, "Air Elemental", 1);
    }
}
