package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AettirAndPriwenTest extends CardTestPlayerBase {

    /*
    Aettir and Priwen
    {6}
    Legendary Artifact — Equipment
    Equipped creature has base power and toughness X/X, where X is your life total.
    Equip {5}
     */
    private static final String aettir = "Aettir and Priwen";
    /*
    Bear Cub
    {1}{G}
    Creature — Bear
    2/2
     */
    private static final String cub = "Bear Cub";
    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
     */
    public static final String bolt = "Lightning Bolt";

    @Test
    public void testAettirAndPriwen() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, aettir);
        addCard(Zone.BATTLEFIELD, playerA, cub);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, bolt, 2);

        checkPowerToughness(2, cub, 1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}: Equip");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPowerToughness(20, cub, 1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerA);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPowerToughness(20 - 3, cub, 1, PhaseStep.POSTCOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerA);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkPowerToughness(20 - 3 * 2, cub,  1, PhaseStep.POSTCOMBAT_MAIN);

        checkPowerToughness(20 - 3 * 2, cub, 2,  PhaseStep.PRECOMBAT_MAIN);
    }

    void checkPowerToughness(int xValue, String name, int turnNum, PhaseStep phaseStep) {
        runCode("Checking P/T is " + xValue, turnNum, phaseStep, playerA, (info, player, game) -> {
            Permanent permanent = getPermanent(name, player);
            Assert.assertEquals(xValue, permanent.getPower().getModifiedBaseValue());
            Assert.assertEquals(xValue, permanent.getToughness().getModifiedBaseValue());
        });
    }
}
