package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class BushidoTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testBeingBlocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Isao, Enlightened Bushi");

        attack(2, playerB, "Isao, Enlightened Bushi");
        block(2, playerA, "Elite Vanguard", "Isao, Enlightened Bushi");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerB, "Isao, Enlightened Bushi", 4, 3);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    /**
     * Tests boosting on block
     */
    @Test
    public void testBlocking() {
        addCard(Zone.BATTLEFIELD, playerA, "Isao, Enlightened Bushi");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Isao, Enlightened Bushi", "Elite Vanguard");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Isao, Enlightened Bushi", 4, 3);
        assertPermanentCount(playerB, "Elite Vanguard", 0);
    }

    /**
     * Tests boosting on double block, it may only trigger once
     */
    @Test
    public void testMultipleBlocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Quirion Elves", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Isao, Enlightened Bushi"); // 2/1  Bushido 2
        attack(2, playerB, "Isao, Enlightened Bushi");
        block(2, playerA, "Llanowar Elves", "Isao, Enlightened Bushi");
        block(2, playerA, "Quirion Elves", "Isao, Enlightened Bushi");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerB, "Isao, Enlightened Bushi", 4, 3);
        assertPermanentCount(playerA, "Llanowar Elves", 0);
        assertPermanentCount(playerA, "Quirion Elves", 0);
    }
}
