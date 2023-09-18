
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HelmOfTheHostTest extends CardTestPlayerBase {

    /**
     * If you animate Gideon of the Trials and equip it with Helm of the Host
     * the nonlegendary copies can't become creatures with the 0 ability. You
     * can activate it just fine (and it gets put on the stack) but nothing
     * happens and you can't use another ability.
     */
    @Test
    public void testCopyPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        //Starting Loyalty: 3
        // +1: Until your next turn, prevent all damage target permanent would deal.
        // 0: Until end of turn, Gideon of the Trials becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        // 0: You get an emblem with "As long as you control a Gideon planeswalker, you can't lose the game and your opponent can't win the game."
        addCard(Zone.BATTLEFIELD, playerA, "Gideon of the Trials", 1);
        // At the beginning of combat on your turn, create a token that's a copy of equipped creature, except the token isn't legendary if equipped creature is legendary. That token gains haste.
        // Equip {5}
        addCard(Zone.BATTLEFIELD, playerA, "Helm of the Host", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Until end of turn");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Until end of turn");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Until end of turn");

        attack(3, playerA, "Gideon of the Trials");
        attack(3, playerA, "Gideon of the Trials");

        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Gideon of the Trials", 2);

        assertCounterCount("Gideon of the Trials", CounterType.LOYALTY, 3);

        assertLife(playerB, 12);
        assertLife(playerA, 20);
    }
}
