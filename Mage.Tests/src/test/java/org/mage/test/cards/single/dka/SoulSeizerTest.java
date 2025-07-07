package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SoulSeizerTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(1, playerA, "Soul Seizer");
        addTarget(playerA, "Craw Wurm");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Ghastly Haunting", 1);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertPermanentCount(playerA, "Craw Wurm", 1);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.HAND, playerB, "Clear");


        attack(1, playerA, "Soul Seizer");
        addTarget(playerA, "Craw Wurm");
        setChoice(playerA, true);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clear", "Ghastly Haunting");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Ghastly Haunting", 0);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Soul Seizer");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Battlegrowth");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Soul Seizer");
        attack(1, playerA, "Soul Seizer");
        addTarget(playerA, "Craw Wurm");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Ghastly Haunting", 1);
        assertCounterCount("Ghastly Haunting", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Soul Seizer", 0);
        assertGraveyardCount(playerA, 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPowerToughness(playerA, "Craw Wurm", 6, 4, Filter.ComparisonScope.All);
    }

}
