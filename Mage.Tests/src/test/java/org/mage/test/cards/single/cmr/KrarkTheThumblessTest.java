package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class KrarkTheThumblessTest extends CardTestPlayerBase {

    @Test
    public void test_MustAbleToCastAgainAfterRemoveToHand() {
        // https://github.com/magefree/mage/issues/7198
        // bug: Casting a lightning bolt and failing flip 1 and then winning flip 2. While the copy was on the stack
        // and the lightning bolt back in hand, i was not allowed to cast the lightning bolt until the copy resolved.

        // Whenever you cast an instant or sorcery spell, flip a coin. If you lose the flip, return that spell to
        // its owner's hand. If you win the flip, copy that spell, and you may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Krark, the Thumbless", 1);
        //
        // You may have Sakashima the Impostor enter the battlefield as a copy of any creature on the battlefield,
        // except its name is Sakashima the Impostor, it's legendary in addition to its other types...
        addCard(Zone.HAND, playerA, "Sakashima the Impostor", 1); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * 2);

        // prepare two legendary creatures (for two flipcoin events)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sakashima the Impostor");
        setChoice(playerA, true); // enter as copy
        setChoice(playerA, "Krark, the Thumbless"); // copy target
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // FIRST BOLT CAST
        // cast bolt and generate 2 triggers
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, "Whenever"); // 2x triggers raise
        checkStackSize("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3); // bolt + 2x triggers
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast L", 1);
        checkStackObject("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 2);
        checkHandCardCount("after cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);

        // resolve first trigger and lose (move spell to hand)
        setFlipCoinResult(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkStackSize("after lose trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1); // one trigger
        checkStackObject("after lose trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast L", 0);
        checkStackObject("after lose trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 1);
        checkHandCardCount("after lose trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1); // moved to hand

        // resolve second trigger and win (copy the spell)
        setFlipCoinResult(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        setChoice(playerA, false); // do not change a target of the copy
        checkStackSize("after win trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1); // copied bolt
        checkStackObject("after lose trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast L", 1);
        checkStackObject("after win trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast", 0);
        checkHandCardCount("after win trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1); // stil in hand

        // SECOND BOLT CAST
        // try to cast removed spell from hand
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, "Whenever"); // 2x triggers raise
        setFlipCoinResult(playerA, true); // win and copy second bolt
        setChoice(playerA, false); // keep target
        setFlipCoinResult(playerA, true); // win and copy second bolt
        setChoice(playerA, false); // keep target

        // TOTAL to resolve:
        // * 1 copied bolt from first bolt cast
        // * 1 bolt + 2 copied bolts from second bolt cast
        // total damage: 4 * 3

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4 * 3);
    }
}
