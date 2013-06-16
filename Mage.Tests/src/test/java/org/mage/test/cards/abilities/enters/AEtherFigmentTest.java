package org.mage.test.cards.abilities.enters;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class AEtherFigmentTest extends CardTestPlayerBase {

    @Test
    public void testEnteringWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "AEther Figment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "AEther Figment");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "AEther Figment", 1);

        Permanent aetherFigment = getPermanent("AEther Figment", playerA.getId());
        Assert.assertEquals(3, aetherFigment.getPower().getValue());
        Assert.assertEquals(3, aetherFigment.getToughness().getValue());
    }
}
