
package org.mage.test.cards.abilities.keywords;

import mage.abilities.Ability;
import mage.abilities.keyword.BloodthirstAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodthirstTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Isao, Enlightened Bushi");

        attack(2, playerB, "Isao, Enlightened Bushi");
        block(2, playerA, "Elite Vanguard", "Isao, Enlightened Bushi");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerB, "Isao, Enlightened Bushi", 4, 3);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    /**
     * Tests boosting on block
     */
    @Test
    public void testBloodlord() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Bloodthirst 3 (If an opponent was dealt damage this turn, this creature enters the battlefield with 3 +1/+1 counters)
        // Whenever you cast a Vampire creature spell, it gains bloodthirst 3
        addCard(Zone.HAND, playerA, "Bloodlord of Vaasgoth"); // {3}{B}{B}
        addCard(Zone.HAND, playerA, "Barony Vampire"); // 3/2  {2}{B}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bloodlord of Vaasgoth", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Barony Vampire");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Barony Vampire", 1);
        Permanent baron = getPermanent("Barony Vampire", playerA);
        boolean bloodthirstFound = false;
        for (Ability ability : baron.getAbilities()) {
            if (ability instanceof BloodthirstAbility) {
                bloodthirstFound = true;
                break;
            }
        }
        Assert.assertTrue("Baron Vampire is missing the bloodthirst ability", bloodthirstFound);
        assertPermanentCount(playerA, "Bloodlord of Vaasgoth", 1);
        assertPowerToughness(playerA, "Bloodlord of Vaasgoth", 6, 6);
        assertPermanentCount(playerA, "Barony Vampire", 1);
        assertPowerToughness(playerA, "Barony Vampire", 6, 5);
    }

}
