package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class FaithsShieldTest extends CardTestPlayerBase {

    /*
    Target permanent you control gains protection from the color of your choice until end of turn.
    Fateful hour - If you have 5 or less life, instead you and each permanent you control
                    gain protection from the color of your choice until end of turn.
    */
    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Faith's Shield");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        setChoice(playerA, "Red");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "White Knight");

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("setup good targets")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "White Knight", 1);
    }

    @Test
    public void testCardExile1() {
        setLife(playerA, 5);
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Faith's Shield");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 5);
        assertLife(playerB, 20);
    }

}
