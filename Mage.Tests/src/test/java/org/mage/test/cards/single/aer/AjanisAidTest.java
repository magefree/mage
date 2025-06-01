package org.mage.test.cards.single.aer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AjanisAidTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AjanisAid Ajani's Aid} {2}{G}{W}
     * Enchantment
     * When this enchantment enters, you may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it, and put it into your hand. If you search your library this way, shuffle.
     * Sacrifice this enchantment: Prevent all combat damage a creature of your choice would deal this turn.
     */
    private static final String aid = "Ajani's Aid";

    @Test
    public void test_DamageOnCreature_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, aid, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Goblin Piker"); // creature to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 0);
        assertTapped("Goblin Piker", true);
        assertGraveyardCount(playerA, aid, 1);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, aid, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped("Goblin Piker", true);
        assertGraveyardCount(playerA, aid, 1);
    }

    @Test
    public void test_DamageNonCombat_NoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, aid, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer", 1); // {T}: This creature deals 1 damage to any target.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Prodigal Pyromancer"); // source to prevent from
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: ", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
        assertGraveyardCount(playerA, aid, 1);
    }
}
