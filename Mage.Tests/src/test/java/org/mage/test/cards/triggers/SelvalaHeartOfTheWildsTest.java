
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
/**
 * Selvala, Heart of the Wilds {1}{G}{G} Whenever another creature enters the
 * battlefield, its controller may draw a card if its power is greater than each
 * other creature's power Add X mana in any combination of colors to your mana
 * pool, where X is the greatest power among creatures you control
 */
public class SelvalaHeartOfTheWildsTest extends CardTestPlayerBase {

    @Test
    public void testTrigger() {
        // No card will be drawn due to the Memnite having a lower power than any other permanent on the battlefield
        addCard(Zone.LIBRARY, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 0); // no cards drawn

    }

    /**
     * After Memnite enters the battlefield, the trigger fires.  In response, 2 Giant Growths targeting the Memnite
     * pumps its power to the highest on the battlefield allowing the controller to draw a card.
     */
    @Test
    public void testTriggerWithGiantGrowth() {
        addCard(Zone.LIBRARY, playerA, "Island", 2);
        // Whenever another creature enters the battlefield, its controller may draw a card if its power is greater than each other creature's power.
        // {G}, {T}: Add X mana in any combination of colors, where X is the greatest power among creatures you control.
        addCard(Zone.BATTLEFIELD, playerA, "Selvala, Heart of the Wilds", 1); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Shivan Dragon", 1); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Memnite"); // 1/1
        addCard(Zone.HAND, playerA, "Giant Growth", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Blinking Spirit", 1); // 2/2
        // Flying
        // Nightmare's power and toughness are each equal to the number of Swamps you control.
        addCard(Zone.BATTLEFIELD, playerB, "Nightmare", 1); // 4/4
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=0");
        setChoice(playerA, "X=5");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN ,1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite"); // a whopping 7/7

        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPowerToughness(playerA, "Memnite", 7, 7);
        assertGraveyardCount(playerA, "Giant Growth", 2);
        assertHandCount(playerA, 1); // 2 cards drawn

    }
}
