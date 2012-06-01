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

    /**
     * Tests copying card with transform
     */
    @Test
    public void testCopyCardWithTransform() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Mayor of Avabruck");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Constants.Zone.HAND, playerB, "Clone");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mayor of Avabruck");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);

        assertPermanentCount(playerA, "Mayor of Avabruck", 1);
        assertPermanentCount(playerB, "Mayor of Avabruck", 1);
    }

    /**
     * Tests copied card should NOT be possible to transform
     */
    @Test
    public void testCopyCantTransform() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Mayor of Avabruck");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Constants.Zone.HAND, playerB, "Clone");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Mayor of Avabruck");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(5, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 1);

        // should transform - original
        assertPermanentCount(playerA, "Howlpack Alpha", 1);
        // should not transform - copy
        assertPermanentCount(playerB, "Mayor of Avabruck", 1);
    }

    /**
     * Tests copying already transformed card
     */
    @Test
    public void testCopyAlreadyTransformedCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mayor of Avabruck");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Constants.Zone.HAND, playerB, "Clone");

        // copy already transformed
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertHandCount(playerB, 1);

        // should transform - original
        assertPermanentCount(playerA, "Howlpack Alpha", 1);
        // check copying card is also transformed
        assertPermanentCount(playerB, "Howlpack Alpha", 1);
    }

}
