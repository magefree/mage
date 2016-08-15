package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class KusariGamaTest extends CardTestPlayerBase {

    // reported bug: trigger occurs but no damage is dealt
    @Test
    public void testTriggeredAbilityDealsDamage() {

        // Kusari-Gama - Artifact Equipment - Equip {3}
        // Equipped creature has "2: This creature gets +1/+0 until end of turn."
        // Whenever equipped creature deals damage to a blocking creature, Kusari-Gama deals that much damage to each other creature defending player controls.
        addCard(Zone.BATTLEFIELD, playerA, "Kusari-Gama");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate"); // 2/3 vigilance {1}{G}

        addCard(Zone.BATTLEFIELD, playerB, "Wall of Omens"); // 0/4 {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2); // 2/2 {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 {3}{R}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Sylvan Advocate");
        attack(1, playerA, "Sylvan Advocate");
        block(1, playerB, "Wall of Omens", "Sylvan Advocate");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kusari-Gama", 1);
        assertPermanentCount(playerB, "Wall of Omens", 1);
        assertPermanentCount(playerB, "Hill Giant", 1);

        Permanent wallPerm = getPermanent("Wall of Omens", playerB);
        Permanent giantPerm = getPermanent("Hill Giant", playerB);
        Assert.assertEquals("Wall of Omens should have 2 damage dealt to it", 2, wallPerm.getDamage());
        Assert.assertEquals("Hill Giant should have 2 damage dealt to it", 2, giantPerm.getDamage());

        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
    }
}
