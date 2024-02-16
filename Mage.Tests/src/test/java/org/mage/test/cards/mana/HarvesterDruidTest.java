package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * @author LevelX2, JayDi85
 */
public class HarvesterDruidTest extends CardTestPlayerBase {

    @Test
    public void testOneInstance() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // {T}: Add one mana of any color that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Harvester Druid", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals(2, options.size());
        assertManaOptions("{U}{R}{R}", options);
        assertManaOptions("{U}{U}{R}", options);
    }

    @Test
    public void testTwoInstances() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // {T}: Add one mana of any color that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Harvester Druid", 2);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals(3, options.size());
        assertManaOptions("{U}{R}{R}{R}", options);
        assertManaOptions("{U}{U}{R}{R}", options);
        assertManaOptions("{U}{U}{U}{R}", options);
    }
}
