package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TokenImagesTest extends CardTestPlayerBase {

    @Test
    public void test_TokenMustGetSameSetCodeAsSourceCard() {
        //{3}{W}, {T}, Sacrifice Memorial to Glory: Create two 1/1 white Soldier creature tokens.
        addCard(Zone.BATTLEFIELD, playerA, "40K-Memorial to Glory");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}, {T}, Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 2);
        currentGame.getBattlefield().getAllPermanents().stream()
                .filter(card -> card.getName().equals("Soldier Token"))
                .forEach(card -> {
                    Assert.assertEquals("40K", card.getExpansionSetCode());
                    Assert.assertEquals("40K", ((PermanentToken) card).getToken().getOriginalExpansionSetCode());
                });
    }
}
