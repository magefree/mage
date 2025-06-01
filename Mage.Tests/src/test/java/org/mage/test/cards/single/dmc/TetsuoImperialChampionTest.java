package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TetsuoImperialChampionTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TetsuoImperialChampion Tetsuo, Imperial Champion} {U}{B}{R}
     * <p>
     * Legendary Creature — Human Samurai
     * Whenever Tetsuo attacks, if it's equipped, choose one —
     * • Tetsuo deals damage equal to the greatest mana value among Equipment attached to it to any target.
     * • You may cast an instant or sorcery spell from your hand with mana value less than or equal to the greatest mana value among Equipment attached to Tetsuo without paying its mana cost.
     * 3/3
     */
    private static final String tetsuo = "Tetsuo, Imperial Champion";

    @Test
    public void test_attack_notrigger() {
        addCard(Zone.BATTLEFIELD, playerA, tetsuo);

        attack(1, playerA, tetsuo, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_attack_equipped_damage_mv3() {
        addCard(Zone.BATTLEFIELD, playerA, tetsuo);
        addCard(Zone.BATTLEFIELD, playerA, "Civic Saber"); // {1} +1/+0 for each color equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Barbed Battlegear"); // {3} +4/-1 equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", tetsuo);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", tetsuo);

        attack(1, playerA, tetsuo, playerB);
        setModeChoice(playerA, "1"); // choose damage mode
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 3 - 4 - 3);
    }


    @Test
    public void test_attack_equipped_damage_mv1() {
        addCard(Zone.BATTLEFIELD, playerA, tetsuo);
        addCard(Zone.BATTLEFIELD, playerA, "Brokers Initiate"); // 0/4
        addCard(Zone.BATTLEFIELD, playerA, "Civic Saber"); // {1} +1/+0 for each color equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Barbed Battlegear"); // {3} +4/-1 equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Brokers Initiate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", tetsuo);

        attack(1, playerA, tetsuo, playerB);
        attack(1, playerA, "Brokers Initiate", playerB);
        setModeChoice(playerA, "1"); // choose damage mode
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 3 - 4 - 1);
    }
}
