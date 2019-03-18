package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GlarecasterTest extends CardTestPlayerBase {

    /**
     * I can activate Glarecaster's redirection ability, immediately cast two
     * Lightning Bolts on it and both get redirected towards the chosen target.
     * If I pass until the next step and cast another Bolt on it, it's not being
     * redirected anymore.
     *
     */
    @Test
    public void testTwoTimesInstantSpellDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Flying
        // {5}{W}: The next time damage would be dealt to Glarecaster and/or you this turn, that damage is dealt to any target instead.
        addCard(Zone.BATTLEFIELD, playerB, "Glarecaster"); // Creature 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{5}{W}: The next time damage would be dealt to", playerA);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Glarecaster");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 2);

        assertPermanentCount(playerB, "Glarecaster", 1);

        assertLife(playerA, 17);
        assertLife(playerB, 17);

    }

}
