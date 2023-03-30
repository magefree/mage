package org.mage.test.testapi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AddCardApiTest extends CardTestPlayerBase {

    @Test
    public void test_CardName_Normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Memorial to Glory", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 3);
        assertPermanentCount(playerA, "Memorial to Glory", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CardName_RaiseErrorOnUnknownCard() {
        addCard(Zone.BATTLEFIELD, playerA, "xxx", 1);
    }

    @Test
    public void test_CardNameWithSetCode_Normal() {
        addCard(Zone.BATTLEFIELD, playerA, "40K:Memorial to Glory", 2);
        addCard(Zone.BATTLEFIELD, playerA, "PANA:Plains", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memorial to Glory", 2);
        getBattlefieldCards(playerA).stream()
                .filter(card -> card.getName().equals("Memorial to Glory"))
                .forEach(card -> Assert.assertEquals("40K", card.getExpansionSetCode()));

        assertPermanentCount(playerA, "Plains", 2);
        getBattlefieldCards(playerA).stream()
                .filter(card -> card.getName().equals("Plains"))
                .forEach(card -> Assert.assertEquals("PANA", card.getExpansionSetCode()));
    }

    @Test(expected = org.junit.ComparisonFailure.class)
    public void test_CardNameWithSetCode_RaiseErrorOnUnknownSet() {
        addCard(Zone.BATTLEFIELD, playerA, "SS4:Plains", 1);
    }
}
