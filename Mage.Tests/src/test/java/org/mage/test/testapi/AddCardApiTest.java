package org.mage.test.testapi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.utils.SystemUtil;
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
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "", info.get(0));
        Assert.assertEquals(info.toString(), "", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("single name");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "", info.get(0));
        Assert.assertEquals(info.toString(), "single name", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("SET-name");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "SET", info.get(0));
        Assert.assertEquals(info.toString(), "name", info.get(1));

        // only upper case set codes can be used
        info = SystemUtil.parseSetAndCardNameCommand("non-set-code-name");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "", info.get(0));
        Assert.assertEquals(info.toString(), "non-set-code-name", info.get(1));

        info = SystemUtil.parseSetAndCardNameCommand("SET-card-name");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "SET", info.get(0));
        Assert.assertEquals(info.toString(), "card-name", info.get(1));

        // must find first symbols before delimeter, e.g. TOO
        info = SystemUtil.parseSetAndCardNameCommand("TOO-LONG-SET-card-name");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "TOO", info.get(0));
        Assert.assertEquals(info.toString(), "LONG-SET-card-name", info.get(1));

        // short cards names like ED-E, Lonesome Eyebot (set code must be x3 length)
        info = SystemUtil.parseSetAndCardNameCommand("ED-E, Lonesome Eyebot");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "", info.get(0));
        Assert.assertEquals(info.toString(), "ED-E, Lonesome Eyebot", info.get(1));
        //
        info = SystemUtil.parseSetAndCardNameCommand("XLN-ED-E, Lonesome Eyebot");
        Assert.assertEquals(info.toString(), 2, info.size());
        Assert.assertEquals(info.toString(), "XLN", info.get(0));
        Assert.assertEquals(info.toString(), "ED-E, Lonesome Eyebot", info.get(1));
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
        getBattlefieldCards(playerA)
                .stream()
                .filter(info -> info.getCard().getName().equals("Memorial to Glory"))
                .forEach(info -> Assert.assertEquals("40K", info.getCard().getExpansionSetCode()));

        assertPermanentCount(playerA, "Plains", 2);
        getBattlefieldCards(playerA)
                .stream()
                .filter(info -> info.getCard().getName().equals("Plains"))
                .forEach(info -> Assert.assertEquals("PANA", info.getCard().getExpansionSetCode()));
    }

    @Test(expected = org.junit.ComparisonFailure.class)
    public void test_CardNameWithSetCode_RaiseErrorOnUnknownSet() {
        addCard(Zone.BATTLEFIELD, playerA, "SS4-Plains", 1);
    }

    // Add card to exile added for #11738
    @Test
    public void test_AddCardExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Mind Raker");

        addCard(Zone.EXILED, playerB, "Llanowar Elves");

        checkExileCount("llanowar elves in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Llanowar Elves", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Raker");
        setChoice(playerA, true);
        addTarget(playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Llanowar Elves", 0);
        assertGraveyardCount(playerB, "Llanowar Elves", 1);
    }
}
