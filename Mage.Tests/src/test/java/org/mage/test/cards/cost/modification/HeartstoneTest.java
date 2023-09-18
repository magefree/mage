
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HeartstoneTest extends CardTestPlayerBase {

    /**
     * Heartstone is not reducing the cost of Sigil Tracer 's activated ability
     */
    @Test
    public void testSigilTracer() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // {1}{U}, Tap two untapped Wizards you control: Copy target instant or sorcery spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Sigil Tracer");
        // Activated abilities of creatures cost {1} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana.
        addCard(Zone.BATTLEFIELD, playerA, "Heartstone");
        addCard(Zone.BATTLEFIELD, playerA, "Fugitive Wizard");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}", "Lightning Bolt", "Lightning Bolt");
        setChoice(playerA, true);
        addTarget(playerA, playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertTapped("Fugitive Wizard", true);
        assertLife(playerA, 17);
        assertLife(playerB, 17);

        assertTappedCount("Island", true, 1);

    }

}
