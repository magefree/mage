package org.mage.test.cards.abilities.curses;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CurseOfShallowGravesTest extends CardTestPlayerBase {

    /*
        Curse of Shallow Graves
        {2}{B}
        Enchant player
        Whenever a player attacks enchanted player with one or more creatures, that attacking player may create a tapped 2/2 black Zombie creature token.

        bug: https://www.slightlymagic.net/forum/viewtopic.php?f=70&t=23164&p=229567#p229567
    */

    @Test
    public void test_AttackPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Curse of Shallow Graves");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Shallow Graves", playerB);
        checkPermanentCount("curse on battle", 1, PhaseStep.BEGIN_COMBAT, playerA, "Curse of Shallow Graves", 1);

        // turn 1 - attack without token
        attack(1, playerA, "Balduvian Bears", playerB);
        setChoice(playerA, false); // don't create token
        checkPermanentCount("zombie 0", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombie Token", 0);

        // turn 3 - attack with token
        attack(3, playerA, "Balduvian Bears", playerB);
        setChoice(playerA, true); // create token
        checkPermanentCount("zombie 1", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombie Token", 1);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_AttackPlaneswalker() {
        // planeswalker only attack must be ignored by token's effect
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Curse of Shallow Graves");
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Chandra Ablaze", 1); // 5 loyalty

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Shallow Graves", playerB);
        checkPermanentCount("curse on battle", 1, PhaseStep.BEGIN_COMBAT, playerA, "Curse of Shallow Graves", 1);

        // turn 1 - attack player without token
        attack(1, playerA, "Balduvian Bears", playerB);
        setChoice(playerA, false); // don't create token
        checkPermanentCount("zombie 0", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombie Token", 0);

        // turn 3 - attack planeswalker (no choices at all)
        attack(3, playerA, "Balduvian Bears", "Chandra Ablaze");
        //setChoice(playerA, true);
        checkPermanentCount("zombie 0", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombie Token", 0);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertCounterCount(playerB, "Chandra Ablaze", CounterType.LOYALTY, 5 - 2);
    }
}
