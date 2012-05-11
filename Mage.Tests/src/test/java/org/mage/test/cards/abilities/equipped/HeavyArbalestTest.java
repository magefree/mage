package org.mage.test.cards.abilities.equipped;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class HeavyArbalestTest extends CardTestPlayerBase {

    /**
     * Tests that creature with Heavy Arbalest will use it and won't untap
     */
    @Test
    public void testNotUntapping() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Heavy Arbalest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {4}", "Elite Vanguard");
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals 2 damage", playerB);
        
        setStopAt(3, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 18);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertTrue(eliteVanguard.getAttachments().size() > 0);
        Assert.assertTrue(eliteVanguard.isTapped());
    }

    /**
     * Tests that creature with Heavy Arbalest will use it and untap later
     */
    @Test
    public void testUntapsLater() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Heavy Arbalest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {4}", "Elite Vanguard");
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals 2 damage", playerB);

        setStopAt(5, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        Permanent eliteVanguard = getPermanent("Elite Vanguard", playerA.getId());
        Assert.assertTrue(eliteVanguard.getAttachments().size() > 0);
        Assert.assertFalse(eliteVanguard.isTapped());
    }

}
