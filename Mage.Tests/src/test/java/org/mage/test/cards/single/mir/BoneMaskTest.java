package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BoneMaskTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BoneMask Bone Mask}  {4}
     * Artifact
     * {2}, {T}: The next time a source of your choice would deal damage to you this turn, prevent that damage. Exile cards from the top of your library equal to the damage prevented this way.
     */
    private static final String mask = "Bone Mask";

    @Test
    public void test_DamageOnCreature_NoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, mask, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 2); // no prevent
        assertExileCount(playerA, 0);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, mask, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertExileCount(playerA, 2);
    }

    @Test
    public void test_DoubleStrike_Prevent_ThenConsumedAndNoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, mask, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blade Historian", 1); // 2/3 "Attacking creatures you control have double strike."
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        setChoice(playerA, "Blade Historian"); // source to prevent from

        attack(2, playerB, "Blade Historian", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertExileCount(playerA, 2);
    }
}
