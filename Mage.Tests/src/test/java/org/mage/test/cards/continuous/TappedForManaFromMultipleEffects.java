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
    public void test_One() {
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
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Nyxbloom Ancient", 1);
        assertPermanentCount(playerA, "Chlorophant", 1);
    }

    @Test
    public void test_Two() {
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
        // real choice must be called one time only (from playManaAbility) after fix, see above to-do
        // TODO: TAPPED_FOR_MANA replace event called from checkTappedForManaReplacement and start to choose replace events, if logs enabled then it cause the error
        // use case 1 (human game only): do not ignore check playable logs in chooseReplacementEffect for HumanPlayer - you will get rollback error on second Nyxbloom Ancient cast
        // use case 2 (that test): comment one 1-2 choices to fail in 1-2 calls
        setChoice(playerA, "Nyxbloom Ancient: If you tap a permanent"); // getPlayable... checkTappedForManaReplacement... chooseReplacementEffect
        setChoice(playerA, "Nyxbloom Ancient: If you tap a permanent"); // playManaAbility... resolve... checkToFirePossibleEvents... chooseReplacementEffect

        // cast chloro
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Chlorophant");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Nyxbloom Ancient", 2);
        assertPermanentCount(playerA, "Chlorophant", 1);
    }
}