package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.b.BessSoulNourisher Bess, Soul Nourisher}
 * <p>
 * Whenever one or more other creatures with base power and toughness 1/1 enter the battlefield under your control,
 * put a +1/+1 counter on Bess, Soul Nourisher.
 * <p>
 * Whenever Bess attacks, each other creature you control with base power and toughness 1/1 gets +X/+X until end of turn,
 * where X is the number of +1/+1 counters on Bess.
 *
 * @author Alex-Vasile
 */
public class BessSoulNourisherTest extends CardTestPlayerBase {

    // {1}{G}{W}
    private static final String bessSoulNourisher = "Bess, Soul Nourisher";
    // {3}{W}
    // Create three 1/1 white Soldier creature tokens.
    private static final String captainsCall = "Captain's Call";

    /**
     * Test that it only triggers once for a group entering
     */
    @Test
    public void testEntersGroup() {
        addCard(Zone.BATTLEFIELD, playerA, bessSoulNourisher);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, captainsCall);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, captainsCall);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Soldier Token", 3);
        assertCounterCount(playerA, bessSoulNourisher, CounterType.P1P1, 1); // Should only get one +1/+1 since all soldiers tokens enter at once
    }

    /**
     * Test that the boosting corectly affects only creatures with BASE power and toughness of 1/1.
     * Vodalian is a 1/1 so it should be buffed
     * Artic merfolk is a 1/1 buffed to a 2/2, but it still has BASE PT of 1/1, so it should be buffed.
     * Banewhip Punisher base 2/2 with a -1/-1 counter on it, so a 1/1, but its BASE is still 2/2, so it should not be buffed.
     */
    @Test
    public void testBoost() {
        // 1/1
        // Other Merfolk you control get +1/+1.
        String vodalianHexcatcher = "Vodalian Hexcatcher";
        // 1/1
        String arcticMerfolk = "Arctic Merfolk";
        // 2/2
        // When Banewhip Punisher enters the battlefield, you may put a -1/-1 counter on target creature.
        String banewhipPunisher = "Banewhip Punisher";

        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 5);
        addCard(Zone.BATTLEFIELD, playerA, bessSoulNourisher);
        addCard(Zone.BATTLEFIELD, playerA, arcticMerfolk);
        addCard(Zone.HAND, playerA, banewhipPunisher);
        addCard(Zone.HAND, playerA, vodalianHexcatcher);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, banewhipPunisher, true);
        setChoice(playerA, "Yes");
        addTarget(playerA, banewhipPunisher);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vodalianHexcatcher);

        attack(1, playerA, bessSoulNourisher);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount(bessSoulNourisher, CounterType.P1P1, 1);
        assertLife(playerB, 20 - 2);
        assertPowerToughness(playerA, vodalianHexcatcher, 2, 2); // 1/1 + 1/1 from Bess
        assertPowerToughness(playerA, arcticMerfolk, 3, 3); // 1/1 + (1/1 from Bess) + (1/1 Vodalian)
    }
}
