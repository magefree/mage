package org.mage.test.cards.single.tmp;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PhyrexianSplicerTest extends CardTestPlayerBase {

    @Test
    public void test_Normal() {
        // {2}, {T}, Choose flying, first strike, trample, or shadow: Until end of turn, target creature with the
        // chosen ability loses it and another target creature gains it.
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Splicer", 1); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        addCard(Zone.BATTLEFIELD, playerA, "Akroma, Angel of Wrath");
        // Shadow
        addCard(Zone.BATTLEFIELD, playerA, "Augur il-Vec");

        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", TrampleAbility.class, true);
        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augur il-Vec", TrampleAbility.class, false);

        // move trample from one to another
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Choose");
        addTarget(playerA, "Akroma, Angel of Wrath"); // loose
        addTarget(playerA, "Augur il-Vec"); // gain
        setChoice(playerA, "Trample");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akroma, Angel of Wrath", TrampleAbility.class, false);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Augur il-Vec", TrampleAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
