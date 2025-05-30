package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class AncientAdamantoiseTest extends CardTestPlayerBase {
    private static final String adamantoise = "Ancient Adamantoise";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testDamageStays() {
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, adamantoise);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, adamantoise);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Permanent permanent = getPermanent(adamantoise);
        Assert.assertEquals(adamantoise + " should have 3 damage on it on the next turn", 3, permanent.getDamage());
    }
}
