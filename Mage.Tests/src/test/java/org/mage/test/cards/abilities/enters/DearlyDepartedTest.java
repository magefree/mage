package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DearlyDepartedTest extends CardTestPlayerBase {

    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // Dearly Departed
        // Creature — Spirit 5/5, 4WW (6)
        // Flying
        // As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.GRAVEYARD, playerA, "Dearly Departed");
        /**
         * Thraben Doomsayer Creature — Human Cleric 2/2, 1WW (3)
         *
         * {T}: Put a 1/1 white Human creature token onto the battlefield.
         * Fateful hour — As long as you have 5 or less life, other creatures
         * you control get +2/+2.
         */
        addCard(Zone.HAND, playerA, "Thraben Doomsayer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Doomsayer");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Create a 1/1 white Human creature token.");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Thraben Doomsayer", 3, 3);

        assertPermanentCount(playerA, "Human Token", 1);
        // check that the +1/+1 counter was added to the token
        assertPowerToughness(playerA, "Human Token", 2, 2);
    }
}
