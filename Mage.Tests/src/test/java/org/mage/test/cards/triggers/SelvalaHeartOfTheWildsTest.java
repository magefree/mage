package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Selvala, Heart of the Wilds {1}{G}{G} Whenever another creature enters the
 * battlefield, its controller may draw a card if its power is greater than each
 * other creature's power Add X mana in any combination of colors to your mana
 * pool, where X is the greatest power among creatures you control
 *
 * @author jeffwadsworth
 */
public class SelvalaHeartOfTheWildsTest extends CardTestPlayerBase {

    @Test
    public void test_NoTriggerOnLowerPower() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island", 1);

        // Whenever another creature enters the battlefield, its controller may draw a card if its
        // power is greater than each other creature's power.
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        //
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        // Nightmare's power and toughness are each equal to the number of Swamps you control.
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        // cast low power memnite - no draw trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0); // no draw
    }

    /**
     * After Memnite enters the battlefield, the trigger fires.  In response, 2 Giant Growths targeting the Memnite
     * pumps its power to the highest on the battlefield allowing the controller to draw a card.
     */
    @Test
    public void test_TriggerOnBigBoosted() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island", 1);

        // Whenever another creature enters the battlefield, its controller may draw a card if its
        // power is greater than each other creature's power.
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        //
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        // Nightmare's power and toughness are each equal to the number of Swamps you control.
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        //
        // Target creature gets +3/+3 until end of turn.
        addCard(Zone.HAND, playerA, "Giant Growth", 2); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // prepare etb trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackSize("must have etb", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkStackObject("must have etb", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever another creature", 1);

        // boost before trigger resolve (make memnite 7/7)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite", "Whenever another creature");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite", "Whenever another creature");
        checkStackSize("must have etb + boost spells", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);

        // trigger on grater power
        setChoice(playerA, true); // draw card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Memnite", 7, 7);
        assertGraveyardCount(playerA, "Giant Growth", 2);
        assertHandCount(playerA, "Island", 1); // after draw
    }
}
