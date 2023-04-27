package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class TappedForManaFromMultipleEffects extends CardTestPlayerBase {

    @Test
    public void test_NyxbloomAncient_One() {
        // If you tap a permanent for mana, it produces three times as much of that mana instead.
        addCard(Zone.HAND, playerA, "Nyxbloom Ancient"); // {4}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        //
        addCard(Zone.HAND, playerA, "Chlorophant"); // {G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // 1x3 by nyx

        // cast nyx
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nyxbloom Ancient");

        // cast chloro
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Chlorophant");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Nyxbloom Ancient", 1);
        assertPermanentCount(playerA, "Chlorophant", 1);
    }

    @Test
    public void test_NyxbloomAncient_Two() {
        // If you tap a permanent for mana, it produces three times as much of that mana instead.
        addCard(Zone.HAND, playerA, "Nyxbloom Ancient", 1); // {4}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        //
        addCard(Zone.HAND, playerA, "Nyxbloom Ancient", 1); // {4}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3); // 3 x 3 from another nyx
        //
        addCard(Zone.HAND, playerA, "Chlorophant"); // {G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // 1x3 by nyx

        // cast nyx 1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nyxbloom Ancient");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast nyx 2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nyxbloom Ancient");
        // TODO: TAPPED_FOR_MANA replace event called from checkTappedForManaReplacement and start to choose replace events (is that problem?)
        // use case (that test): comment one 1-2 choices to fail in 1-2 calls
        setChoice(playerA, "Nyxbloom Ancient"); // getPlayable... checkTappedForManaReplacement... chooseReplacementEffect
        setChoice(playerA, "Nyxbloom Ancient"); // playManaAbility... resolve... checkToFirePossibleEvents... chooseReplacementEffect

        // cast chloro
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Chlorophant");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Nyxbloom Ancient", 2);
        assertPermanentCount(playerA, "Chlorophant", 1);
    }

    @Test
    public void test_NyxbloomAncient_IntegerOverflow() {
        // If you tap a permanent for mana, it produces three times as much of that mana instead.
        int permanentsCount = 30;

        addCard(Zone.BATTLEFIELD, playerA, "Nyxbloom Ancient", permanentsCount);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // total mana = 3^count, so must be Integer overflow protection (no zero or negative values)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkManaPool("max mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", Integer.MAX_VALUE);
        setChoice(playerA, "Nyxbloom Ancient", permanentsCount - 1); // choose replacement effects order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ChromeMox_Direct() {
        // Imprint — When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.
        // {T}: Add one mana of any of the exiled card’s colors.
        addCard(Zone.HAND, playerA, "Chrome Mox", 1); // {0}
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chrome Mox");
        setChoice(playerA, true); // use imprint
        setChoice(playerA, "Balduvian Bears"); // discard

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add one");
        checkManaPool("must produce green", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "G", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Chrome Mox", 1);
        assertExileCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_ManaReflect_Direct() {
        // If you tap a permanent for mana, it produces twice as much of that mana instead.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkManaPool("must produce green", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 2); // double by reflection

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_ChromeMox_WithManaReflect() {
        // Imprint — When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.
        // {T}: Add one mana of any of the exiled card’s colors.
        addCard(Zone.HAND, playerA, "Chrome Mox", 1); // {0}
        addCard(Zone.HAND, playerA, "Balduvian Bears", 1);
        //
        // If you tap a permanent for mana, it produces twice as much of that mana instead.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chrome Mox");
        setChoice(playerA, true); // use imprint
        setChoice(playerA, "Balduvian Bears"); // discard

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add one");
        checkManaPool("must produce green", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "G", 2); // double by reflection

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Chrome Mox", 1);
        assertExileCount(playerA, "Balduvian Bears", 1);
    }

    @Test
    public void test_GoblinClearcutter_Direct() {
        // {T}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Clearcutter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice a Fo");
        setChoice(playerA, "Forest"); // sacrifice
        setChoice(playerA, "Green");
        setChoice(playerA, "Green");
        setChoice(playerA, "Green");
        checkManaPool("must produce green", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_GoblinClearcutter_WithManaReflect() {
        // {T}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Clearcutter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        // If you tap a permanent for mana, it produces twice as much of that mana instead.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Reflection", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice a Fo");
        setChoice(playerA, "Forest"); // sacrifice
        setChoice(playerA, "Green");
        setChoice(playerA, "Green");
        setChoice(playerA, "Green");
        checkManaPool("must produce green", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "G", 3 * 2); // double by mana reflect

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}