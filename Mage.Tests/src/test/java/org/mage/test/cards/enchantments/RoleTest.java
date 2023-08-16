package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RoleTest extends CardTestPlayerBase {

    private static final String courtier = "Cursed Courtier";
    private static final String veteran = "Embereth Veteran";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, courtier);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertPermanentCount(playerA, "Cursed", 1);
        assertPowerToughness(playerA, courtier, 1, 1);
    }

    @Test
    public void testReplace() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, veteran);
        addCard(Zone.HAND, playerA, courtier);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, courtier);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}, Sacrifice", courtier);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, courtier, 1);
        assertPermanentCount(playerA, veteran, 0);
        assertGraveyardCount(playerA, veteran, 1);
        assertPermanentCount(playerA, "Cursed", 0);
        assertPermanentCount(playerA, "Young Hero", 1);
        assertPowerToughness(playerA, courtier, 3, 3);
    }
}
