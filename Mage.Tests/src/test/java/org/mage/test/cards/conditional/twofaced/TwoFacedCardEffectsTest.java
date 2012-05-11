package org.mage.test.cards.conditional.twofaced;

import junit.framework.Assert;
import mage.Constants;
import mage.filter.Filter;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class TwoFacedCardEffectsTest extends CardTestPlayerBase {

    /**
     * Tests that effects disappears when card gets transformed
     */
    @Test
    public void testEffectTurnedOffOnTransform() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mayor of Avabruck");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Wolfir Avenger");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Inquisitor");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // check was transformed
        assertPermanentCount(playerA, "Howlpack Alpha", 1);

        // check new effect works
        assertPowerToughness(playerA, "Wolfir Avenger", 4, 4, Filter.ComparisonScope.Any);

        // check old effect doesn't work
        Permanent eliteInquisitor = getPermanent("Elite Inquisitor", playerA.getId());
        Assert.assertEquals(2, eliteInquisitor.getPower().getValue());
        Assert.assertEquals(2, eliteInquisitor.getToughness().getValue());
    }

}
