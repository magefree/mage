package org.mage.test.cards.single.msc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 105.3
 * Effects may change an object’s color or give a color to a colorless object. If an effect gives an object a new
 * color, the new color replaces all previous colors the object had (unless the effect said the object became that
 * color “in addition” to its other colors). Effects may also make a colored object become colorless.
 * <p>
 * 205.1a
 * ... Similarly, when an effect sets one or more of an object’s subtypes, the new subtype(s) replaces any existing
 * subtypes from the appropriate set (creature types, land types, artifact types, enchantment types, planeswalker
 * types, or spell types). ...
 * <p>
 * 613.4b
 * Layer 7b: Effects that set power and/or toughness to a specific number or value are applied. Effects that refer to
 * the base power and/or toughness of a creature apply in this layer.
 * <p>
 * 613.4c
 * Layer 7c: Effects and counters that modify power and/or toughness (but don’t set power and/or toughness to a
 * specific number or value) are applied.
 *
 * @author DarioBarrabinoDev
 */
public class SauronDinoDevoteeTest extends CardTestPlayerBase {

    @Test
    public void test_EntersGainLife() {
        // Whenever Sauron enters or attacks, choose one --
        // * Cure Cancer -- You gain 3 life.
        // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur
        // with base power and toughness 5/5 for as long as it has a saurian counter on it.
        addCard(Zone.HAND, playerA, "Sauron, Dino Devotee"); // {3}{G}{G}, 4/4, flying
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sauron, Dino Devotee");
        setModeChoice(playerA, "1"); // Cure Cancer

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sauron, Dino Devotee", 1);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_AttacksSaurianCounter() {
        // Whenever Sauron enters or attacks, choose one --
        // * Cure Cancer -- You gain 3 life.
        // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur
        // with base power and toughness 5/5 for as long as it has a saurian counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Sauron, Dino Devotee"); // 4/4, flying
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // colorless Thopter artifact creature 0/2, flying
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter", CounterType.P1P1, 1);

        attack(1, playerA, "Sauron, Dino Devotee");
        setModeChoice(playerA, "2"); // Turn People into Dinosaurs
        addTarget(playerA, "Ornithopter"); // saurian counter target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 4);
        assertCounterCount("Ornithopter", CounterType.SAURIAN, 1);
        assertColor(playerA, "Ornithopter", "G", true);
        assertSubtype("Ornithopter", SubType.DINOSAUR);
        assertNotSubtype("Ornithopter", SubType.THOPTER); // creature types are replaced
        assertType("Ornithopter", CardType.ARTIFACT, true); // card types stay
        assertAbility(playerA, "Ornithopter", FlyingAbility.getInstance(), true); // abilities stay
        assertPowerToughness(playerA, "Ornithopter", 5 + 1, 5 + 1); // base 5/5 plus the +1/+1 counter
    }

    @Test
    public void test_CounterRemoved() {
        // Whenever Sauron enters or attacks, choose one --
        // * Cure Cancer -- You gain 3 life.
        // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur
        // with base power and toughness 5/5 for as long as it has a saurian counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Sauron, Dino Devotee"); // 4/4, flying
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // colorless Thopter artifact creature 0/2, flying
        //
        // Sacrifice Vampire Hexmage: Remove all counters from target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Vampire Hexmage"); // 2/1

        attack(1, playerA, "Sauron, Dino Devotee");
        setModeChoice(playerA, "2"); // Turn People into Dinosaurs
        addTarget(playerA, "Ornithopter"); // saurian counter target

        checkPT("dinosaur", 1, PhaseStep.END_COMBAT, playerA, "Ornithopter", 5, 5);
        checkColor("dinosaur", 1, PhaseStep.END_COMBAT, playerA, "Ornithopter", "G", true);
        checkSubType("dinosaur", 1, PhaseStep.END_COMBAT, playerA, "Ornithopter", SubType.DINOSAUR, true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", "Ornithopter"); // remove the saurian counter
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        // the effect has ended, a new saurian counter doesn't bring it back
        addCounters(1, PhaseStep.END_TURN, playerA, "Ornithopter", CounterType.SAURIAN, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertCounterCount("Ornithopter", CounterType.SAURIAN, 1);
        assertColor(playerA, "Ornithopter", "G", false);
        assertSubtype("Ornithopter", SubType.THOPTER);
        assertNotSubtype("Ornithopter", SubType.DINOSAUR);
        assertPowerToughness(playerA, "Ornithopter", 0, 2);
    }

    @Test
    public void test_CantTargetItself() {
        // Whenever Sauron enters or attacks, choose one --
        // * Cure Cancer -- You gain 3 life.
        // * Turn People into Dinosaurs -- Put a saurian counter on another target creature. It's a green Dinosaur
        // with base power and toughness 5/5 for as long as it has a saurian counter on it.
        addCard(Zone.BATTLEFIELD, playerA, "Sauron, Dino Devotee"); // 4/4, flying

        attack(1, playerA, "Sauron, Dino Devotee");
        setModeChoice(playerA, "2"); // Turn People into Dinosaurs, Sauron is the only creature, so no legal target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4);
        assertCounterCount("Sauron, Dino Devotee", CounterType.SAURIAN, 0);
        assertSubtype("Sauron, Dino Devotee", SubType.VAMPIRE);
        assertPowerToughness(playerA, "Sauron, Dino Devotee", 4, 4);
    }
}
