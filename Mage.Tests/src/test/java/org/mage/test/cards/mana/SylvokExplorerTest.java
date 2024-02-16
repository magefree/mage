package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class SylvokExplorerTest extends CardTestPlayerBase {

    @Test
    public void testOneInstance() {
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 1 red and 1 white mana", "{R}{W}", options.getAtIndex(0).toString());
        Assert.assertEquals("Player should be able to create 1 blue and 1 white mana", "{W}{U}", options.getAtIndex(1).toString());
    }

    @Test
    public void testTwoInstances() {
        // {T}: Add one mana of any color that a land an opponent controls could produce.        
        addCard(Zone.BATTLEFIELD, playerB, "Exotic Orchard", 2);

        // {T}: Add one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Sylvok Explorer", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 3 white mana", "{W}{W}{W}", options.getAtIndex(0).toString());
    }
}
