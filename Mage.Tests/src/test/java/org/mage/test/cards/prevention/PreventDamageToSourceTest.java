package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class PreventDamageToSourceTest extends CardTestPlayerBase {

    private static final String elder = "Revered Elder"; // 1/2
    private static final String preventAbility = "{1}: Prevent the next 1 damage that would be dealt to ";
    private static final String shock = "Shock"; // R 2 damage to any target
    private static final String ds = "Desert Sandstorm"; // 2R 1 damage to each creature
    private static final String cloudshift = "Cloudshift"; // W flicker

    @Test
    public void test1DamagePrevented() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, elder);
        addCard(Zone.HAND, playerA, shock);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, preventAbility);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, shock, elder);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, shock, 1);
        assertDamageReceived(playerA, elder, 1); // 1 damage prevented
    }

    @Test
    public void testFlickerDoesntPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);
        addCard(Zone.BATTLEFIELD, playerA, elder);
        addCard(Zone.HAND, playerA, ds);
        addCard(Zone.HAND, playerA, cloudshift);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ds);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, preventAbility);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // resolve prevent ability, desert sandstorm still on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cloudshift, elder);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, ds, 1);
        assertGraveyardCount(playerA, cloudshift, 1);
        assertDamageReceived(playerA, elder, 1); // damage not prevented
    }

}
