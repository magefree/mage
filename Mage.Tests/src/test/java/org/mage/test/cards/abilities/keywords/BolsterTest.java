package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BolsterTest extends CardTestPlayerBase {

    /**
     * Tests keyword bolster
     *
     * 701.32. Bolster <br>
     * 701.32a "Bolster N" means “Choose a creature you control with the least
     * toughness or tied for least toughness among creatures you control. Put N
     * +1/+1 counters on that creature.”
     */
    @Test
    public void Basic1Test() {
        // When Elite Scaleguard enters the battlefield, bolster 2.
        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Elite Scaleguard"); // Creature 2/3 {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Scaleguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        setStrictChooseMode(true);
        execute();

        assertCounterCount("Elite Scaleguard", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Elite Scaleguard", 4, 5);
    }

    @Test
    public void Basic2Test() {
        setStrictChooseMode(true);
        // When Elite Scaleguard enters the battlefield, bolster 2.
        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Elite Scaleguard"); // Creature 2/3 {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Scaleguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPowerToughness(playerA, "Elite Scaleguard", 2, 3);
        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
    }

    @Test
    public void EliteScaleguardTriggerTest() {
        // When Elite Scaleguard enters the battlefield, bolster 2.
        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Elite Scaleguard"); // Creature 2/3 {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Scaleguard");

        attack(3, playerA, "Silvercoat Lion");
        addTarget(playerA, "Pillarfield Ox"); // Tap from triggered ability of Elite Scaleguard

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elite Scaleguard", 2, 3);
        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertTapped("Silvercoat Lion", true);

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertTapped("Pillarfield Ox", true);

        assertLife(playerB, 16);
    }
    

    @Test
    public void EliteScaleguardTriggerTwiceTest() {
        // When Elite Scaleguard enters the battlefield, bolster 2.
        // Whenever a creature you control with a +1/+1 counter on it attacks, tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Elite Scaleguard"); // Creature 2/3 {4}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Scaleguard");

        attack(3, playerA, "Silvercoat Lion");
        addTarget(playerA, "Pillarfield Ox"); // Tap from triggered ability of Elite Scaleguard

        attack(5, playerA, "Silvercoat Lion");
        addTarget(playerA, "Pillarfield Ox"); // Tap from triggered ability of Elite Scaleguard

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elite Scaleguard", 2, 3);
        assertCounterCount("Silvercoat Lion", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertTapped("Silvercoat Lion", true);

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertTapped("Pillarfield Ox", true);

        assertLife(playerB, 12);
    }
    
}
