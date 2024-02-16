
package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BecomesCreatureTest extends CardTestPlayerBase {

    @Test
    public void testChimericMass() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Chimeric Mass enters the battlefield with X charge counters on it.
        // {1}: Until end of turn, Chimeric Mass becomes a Construct artifact creature with "This creature's power and toughness are each equal to the number of charge counters on it."
        addCard(Zone.HAND, playerA, "Chimeric Mass", 1); // Artifiact - {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chimeric Mass");
        setChoice(playerA, "X=3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chimeric Mass", 1);
        assertPowerToughness(playerA, "Chimeric Mass", 3, 3);
        assertType("Chimeric Mass", CardType.CREATURE, SubType.CONSTRUCT);

    }

    @Test
    public void testChimericMassAbilityRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Chimeric Mass enters the battlefield with X charge counters on it.
        // {1}: Until end of turn, Chimeric Mass becomes a Construct artifact creature with "This creature's power and toughness are each equal to the number of charge counters on it."
        addCard(Zone.HAND, playerA, "Chimeric Mass", 1); // Artifiact - {X}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chimeric Mass");
        setChoice(playerA, "X=3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}:");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chimeric Mass", 1);
        assertPowerToughness(playerA, "Chimeric Mass", 0, 0);
        assertType("Chimeric Mass", CardType.CREATURE, false);

        Assert.assertTrue("All layered effect have to be removed", currentGame.getContinuousEffects().getLayeredEffects(currentGame).isEmpty());

    }
}
