package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests cost reduction effects
 * 
 * @author BetaSteward
 */
public class ThaliaGuardianOfThrabenTest extends CardTestPlayerBase {

    @Test
    public void testShouldNotHaveEnoughMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        checkPlayableAbility("Not enough mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    @Test
    public void testShouldHaveEnoughMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertGraveyardCount(playerA, 1);
    }
}
