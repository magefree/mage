package org.mage.test.cards.single.bbd;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class BullRushBruiserTest extends CardTestPlayerBase {

    @Test
    public void test_YourWarrior() {
        // Whenever this creature attacks, if your team controls another Warrior, this creature gains first strike
        // until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Bull-Rush Bruiser"); // 4/3
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker"); // warrior 2/1

        attack(1, playerA, "Bull-Rush Bruiser");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Bull-Rush Bruiser", FirstStrikeAbility.getInstance(), true);
    }

    @Test
    public void test_OpponentWarrior() {
        // Whenever this creature attacks, if your team controls another Warrior, this creature gains first strike
        // until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Bull-Rush Bruiser"); // 4/3
        //
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker"); // warrior 2/1

        attack(1, playerA, "Bull-Rush Bruiser");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Bull-Rush Bruiser", FirstStrikeAbility.getInstance(), false);
    }
}
