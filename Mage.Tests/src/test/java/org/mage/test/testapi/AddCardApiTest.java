package org.mage.test.testapi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.server.util.SystemUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;

/**
 * @author JayDi85
 */
public class AddCardApiTest extends CardTestPlayerBase {

    @Test
    public void test_CardParsing() {
        List<String> info;

        info = SystemUtil.parseSetAndCardNameCommand("");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("", info.get(0));
        Assert.assertEquals("", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("single name");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("", info.get(0));
        Assert.assertEquals("single name", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("SET-name");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("SET", info.get(0));
        Assert.assertEquals("name", info.get(1));

        // only upper case set codes can be used
        info = SystemUtil.parseSetAndCardNameCommand("non-set-code-name");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("", info.get(0));
        Assert.assertEquals("non-set-code-name", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("SET-card-name");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("SET", info.get(0));
        Assert.assertEquals("card-name", info.get(1));

        // must find first symbols before delimeter, e.g. TOO
        info = SystemUtil.parseSetAndCardNameCommand("TOO-LONG-SET-card-name");
        Assert.assertEquals(2, info.size());
        Assert.assertEquals("TOO", info.get(0));
        Assert.assertEquals("LONG-SET-card-name", info.get(1));
    }

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
        addCard(Zone.BATTLEFIELD, playerA, "40K-Memorial to Glory", 2);
        addCard(Zone.BATTLEFIELD, playerA, "PANA-Plains", 2);

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
        addCard(Zone.BATTLEFIELD, playerA, "SS4-Plains", 1);
    }
}
