package org.mage.test.cards.abilities.enters;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
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
         *  Thraben Doomsayer
         *  Creature — Human Cleric 2/2, 1WW (3)
         *  {T}: Put a 1/1 white Human creature token onto the battlefield.
         *  Fateful hour — As long as you have 5 or less life, other creatures you control get +2/+2.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Thraben Doomsayer");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Put a 1/1 white Human creature token onto the battlefield.");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Human", 1);

        // check that the +1/+1 counter was added to the token
        Permanent humanToken = getPermanent("Human", playerA.getId());
        Assert.assertEquals(2, humanToken.getPower().getValue());
        Assert.assertEquals(2, humanToken.getToughness().getValue());
    }
}
