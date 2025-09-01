package org.mage.test.cards.single.ody;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PilgrimOfJusticeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.PilgrimOfJustice Pilgrim of Justice} {2}{W}
     * Creature — Human Cleric
     * Protection from red
     * {W}, Sacrifice this creature: The next time a red source of your choice would deal damage this turn, prevent that damage.
     * 1/3
     */
    private static final String pilgrim = "Pilgrim of Justice";

    @Test
    public void test_DamageOnCreature_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, pilgrim, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 0);
        assertTapped("Goblin Piker", true);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, pilgrim, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped("Goblin Piker", true);
    }
}
