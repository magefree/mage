package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WyllsReversalTest extends CardTestPlayerBase {

    @Test
    public void testCannotTargetAbilityWithoutTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Etali, Primal Conqueror");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Wyll's Reversal");
        addCard(Zone.LIBRARY, playerB, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Etali, Primal Conqueror");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerB, true);
        checkStackObject("etb trigger on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "When {this} enters", 1);
        checkPlayableAbility("ability has no targets", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast " + "Wyll's Reversal", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Etali, Primal Conqueror", 1);
        assertHandCount(playerB, "Wyll's Reversal", 1);
    }

    @Test
    public void testCanTargetSpellWithTargets() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Wyll's Reversal");
        addCard(Zone.LIBRARY, playerB, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell has targets", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Wyll's Reversal", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerB, "Wyll's Reversal", 1);
    }

    @Test
    public void testRoll14DoesNotCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Wyll's Reversal");
        addCard(Zone.LIBRARY, playerB, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Wyll's Reversal", "Lightning Bolt");
        setDieRollResult(playerB, 14);
        setChoice(playerB, false); // keep the original target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testRoll15Copies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Wyll's Reversal");
        addCard(Zone.LIBRARY, playerB, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Wyll's Reversal", "Lightning Bolt");
        setDieRollResult(playerB, 15);
        setChoice(playerB, false); // keep the original target
        setChoice(playerB, false); // keep the copy's target

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - (2 * 3));
    }
}
