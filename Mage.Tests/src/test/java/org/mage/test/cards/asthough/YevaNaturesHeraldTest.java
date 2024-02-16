package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Yeva, Nature's Herald:
 * You may cast green creature cards as though they had flash.
 *
 * @author noxx
 */
public class YevaNaturesHeraldTest extends CardTestPlayerBase {

    /**
     * Yeva's ability should work when its on the battlefield.
     */
    @Test
    public void testOnBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Llanowar Elves");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Llanowar Elves");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    /**
     * Yeva's ability should not work on Non-Green creatures.
     */
    @Test
    public void testNonGreen() {
        addCard(Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Elite Vanguard");

        checkPlayableAbility("Can't flash", 1, PhaseStep.BEGIN_COMBAT, playerA, "Elite Vanguard", false);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    /**
     * Yeva's ability should not work when it's not on the battlefield.
     */
    @Test
    public void testOtherZones() {
        addCard(Zone.GRAVEYARD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.LIBRARY, playerA, "Yeva, Nature's Herald");
        addCard(Zone.HAND, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Llanowar Elves");

        checkPlayableAbility("Can't flash", 1, PhaseStep.BEGIN_COMBAT, playerA, "Cast Llanowar", false);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

    /**
     * After Yeva is gone its ability should be gone.
     */
    @Test
    public void testEffectGetRemovedOnExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Yeva, Nature's Herald");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Path to Exile");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Path to Exile", "Yeva, Nature's Herald");
        checkPlayableAbility("Can't flash", 1, PhaseStep.BEGIN_COMBAT, playerA, "Cast Llanowar", false);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 0);
    }

}
