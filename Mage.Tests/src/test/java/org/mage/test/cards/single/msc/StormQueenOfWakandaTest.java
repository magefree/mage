package org.mage.test.cards.single.msc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class StormQueenOfWakandaTest extends CardTestPlayerBase {

    /**
     * Storm, Queen of Wakanda {3}{G}{W}
     * Legendary Creature - Mutant Noble Hero 4/5
     * Flying
     * Whenever Storm attacks, until end of turn, another target attacking creature gains flying and gets +X/+0,
     * where X is Storm's power.
     * Whenever a creature with flying attacks you, Storm deals damage equal to her power to that creature.
     */
    private static final String storm = "Storm, Queen of Wakanda";

    private static final String bear = "Grizzly Bears"; // 2/2
    private static final String drake = "Wind Drake"; // 2/2 flying

    @Test
    public void test_AttackBoostsAnotherAttacker() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, storm);
        addCard(Zone.BATTLEFIELD, playerA, bear);

        attack(3, playerA, storm, playerB);
        attack(3, playerA, bear, playerB);
        addTarget(playerA, bear);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, bear, FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, bear, 2 + 4, 2);
        assertLife(playerB, 20 - 4 - 6);
    }

    @Test
    public void test_FlyingAttackerTakesDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, storm);
        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        attack(2, playerB, drake, playerA);
        attack(2, playerB, bear, playerA);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // only the flying attacker is damaged, the bear connects
        assertGraveyardCount(playerB, drake, 1);
        assertPermanentCount(playerB, bear, 1);
        assertLife(playerA, 20 - 2);
    }
}
