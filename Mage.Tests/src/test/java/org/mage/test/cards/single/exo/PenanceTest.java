package org.mage.test.cards.single.exo;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PenanceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.Penance Penance} {2}{W}
     * Enchantment
     * Put a card from your hand on top of your library: The next time a black or red source of your choice would deal damage this turn, prevent that damage.
     */
    private static final String penance = "Penance";

    @Test
    public void test_DamageOnCreature_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, penance, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.HAND, playerA, "Plains", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a card");
        setChoice(playerA, "Plains"); // card to put on top
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
        addCard(Zone.BATTLEFIELD, playerA, penance, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.HAND, playerA, "Plains", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Put a card");
        setChoice(playerA, "Plains"); // card to put on top
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped("Goblin Piker", true);
    }
}
