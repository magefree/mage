package org.mage.test.cards.rules;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class CantCastTest extends CardTestPlayerBase {

    /**
     * I control Void Winnower.
     * But my opponent can cast Jayemdae Tome (that's converted mana cost is even).
     * They can cast other even spell.
     * Test casting cost 4.
     */
    @Test
    public void testVoidWinnowerEvenSpell() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.HAND, playerA, "Jayemdae Tome", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jayemdae Tome"); // {4}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            assertAllCommandsUsed();

            assertHandCount(playerA, "Jayemdae Tome", 1);
            assertPermanentCount(playerA, "Jayemdae Tome", 0);

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Test Blaze ({X}{R}) with X=3 so that it's total cost is even.
     */
    @Test
    public void testVoidWinnowerEvenSpellWithX() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Blaze deals X damage to any target.
        addCard(Zone.HAND, playerA, "Blaze", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerA);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        // TODO: Replace these with checkPlayableAbility when the effect has been implemented so that the card is no
        //       longer shown as castable.
        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertHandCount(playerA, "Blaze", 1);
        assertLife(playerB, 20);
    }

    /**
     * Test Blaze ({X}{R}) with X=4 so that it's total cost is odd.
     */
    @Test
    public void testVoidWinnowerUnevenSpellWithX() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Blaze deals X damage to any target.
        addCard(Zone.HAND, playerA, "Blaze", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Blaze", 0);
        assertGraveyardCount(playerA, "Blaze", 1);

        assertLife(playerB, 16);

    }

    /**
     * Test mmorphing a creature.
     */
    @Test
    public void testVoidWinnowerWithMorph() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");
        /*
         Pine Walker
         Creature - Elemental
         5/5
         Morph {4}{G} (You may cast this card face down as a 2/2 creature for . Turn it face up any time for its morph cost.)
         Whenever Pine Walker or another creature you control is turned face up, untap that creature.
         */
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        assertHandCount(playerA, "Pine Walker", 1);
    }

    /**
     * Test with casting cost = {0}
     */
    @Test
    public void testVoidWinnowerZero() {
        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        // Your opponents can't block with creatures with even converted mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Void Winnower");

        // <i>Metalcraft</i> &mdash; {T}: Add one mana of any color. Activate this ability only if you control three or more artifacts.
        addCard(Zone.HAND, playerA, "Mox Opal", 1); // {0}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Opal");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertHandCount(playerA, "Mox Opal", 1);
    }

    /**
     * Test that panic can only be cast during the correct pahse/steü
     */
    @Test
    public void testPanic() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Cast Panic only during combat before blockers are declared.
        // Target creature can't block this turn.
        // Draw a card at the beginning of the next turn's upkeep.
        addCard(Zone.HAND, playerA, "Panic", 4); // Instant - {R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Panic", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Panic", "Silvercoat Lion");

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Panic", 3);
        assertHandCount(playerA, 4);
        assertGraveyardCount(playerA, "Panic", 1);
    }

    /**
     * I "Aether Vialed" (Aether Vial) an Ethersworn Canonist into the
     * battlefield in response to a colored spell(an Elf). The Canonist entered
     * the battlefield. Then, my oponente used Abrupt Decay to destroy it and
     * continue to play spells normaly. Ethersworn Canonist effect should
     * imediately effect the game as it entered the battlefield, and still count
     * spells cast earlier in the turn. In other words, my oponent shouldn't be
     * able to cast anymore colored spells.
     */
    @Test
    public void testEtherswornCanonist() {
        // Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells.
        addCard(Zone.HAND, playerA, "Ethersworn Canonist", 4); // Creaturre - {1}{W}

        // At the beginning of your upkeep, you may put a charge counter on Aether Vial.
        // {T}: You may put a creature card with converted mana cost equal to the number of charge counters on Aether Vial from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Aether Vial", 1);
        // addCounters(1, PhaseStep.UPKEEP, playerA, "Aether Vial", CounterType.CHARGE, 1);

        addCard(Zone.HAND, playerB, "Llanowar Elves", 1); // Creature {G}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        // Abrupt Decay can't be countered.
        // Destroy target nonland permanent with converted mana cost 3 or less.
        addCard(Zone.HAND, playerB, "Abrupt Decay", 1); // {B}{G}

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Llanowar Elves");

        activateAbility(4, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: You");
        setChoice(playerB, "Ethersworn Canonist");

//        checkPlayableAbility("2nd spell cast", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Abrupt", false);
        castSpell(4, PhaseStep.POSTCOMBAT_MAIN, playerB, "Abrupt Decay", "Ethersworn Canonist");
        setStopAt(4, PhaseStep.END_TURN);

        try {
            execute();
            assertAllCommandsUsed();

            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("must not have throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertCounterCount(playerA, "Aether Vial", CounterType.CHARGE, 2);
        assertPermanentCount(playerB, "Llanowar Elves", 1);

        assertPermanentCount(playerA, "Ethersworn Canonist", 1);
        assertHandCount(playerB, "Abrupt Decay", 1);

    }

    /**
     * Alhammarret, High Arbiter's ability doesn't work Despite naming the
     * Damnation in my hand, I was able to cast it next turn without issue.
     */
    @Test
    public void testAlhammarret() {
        // Flying
        // As Alhammarret, High Arbiter enters the battlefield, each opponent reveals their hand. You choose the name of a nonland card revealed this way.
        // Your opponents can't cast spells with the chosen name.
        addCard(Zone.HAND, playerA, "Alhammarret, High Arbiter", 4); // Creature - {5}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // Destroy all creatures. They can't be regenerated.
        addCard(Zone.HAND, playerB, "Damnation", 1); // SORCERY {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Alhammarret, High Arbiter");
        addTarget(playerA, "Damnation");

        checkPlayableAbility("damnation check", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Damnation", false);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Alhammarret, High Arbiter", 1);
        assertHandCount(playerB, "Damnation", 1);

    }

}
