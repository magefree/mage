package org.mage.test.cards.single.iko;

import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class UnbreakableBondTest extends CardTestPlayerBase {

    @Test
    public void testLifelinkCounter() {
        addCard(Zone.GRAVEYARD, playerA, "Barony Vampire");
        addCard(Zone.HAND, playerA, "Unbreakable Bond");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unbreakable Bond", "Barony Vampire");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Unbreakable Bond", 1);
        assertCounterCount(playerA, "Barony Vampire", CounterType.LIFELINK, 1);
        assertPowerToughness(playerA, "Barony Vampire", 3, 2);
        assertAbility(playerA, "Barony Vampire", LifelinkAbility.getInstance(), true);
    }
}
