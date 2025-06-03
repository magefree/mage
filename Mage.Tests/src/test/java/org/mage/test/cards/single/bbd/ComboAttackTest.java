package org.mage.test.cards.single.bbd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class ComboAttackTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.ComboAttack Combo Attack} {2}{G}
     * Sorcery
     * Two target creatures your team controls each deal damage equal to their power to target creature
     */
    private static final String combo = "Combo Attack";

    @Test
    public void test_Normal() {
        addCard(Zone.HAND, playerA, combo, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Fortress Crab", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, combo, "Memnite^Runeclaw Bear^Fortress Crab");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, combo, 1);
        assertDamageReceived(playerB, "Fortress Crab", 3);

    }

    @Test
    public void test_IllegalFirst() {
        addCard(Zone.HAND, playerA, combo, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Fortress Crab", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerB, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, combo, "Memnite^Runeclaw Bear^Fortress Crab");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", "Memnite", combo);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, combo, 1);
        assertGraveyardCount(playerB, "Unsummon", 1);
        assertHandCount(playerA, "Memnite", 1);
        assertDamageReceived(playerB, "Fortress Crab", 2);

    }

    @Test
    public void test_IllegalSecond() {
        addCard(Zone.HAND, playerA, combo, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Fortress Crab", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerB, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerB, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, combo, "Memnite^Runeclaw Bear^Fortress Crab");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Unsummon", "Runeclaw Bear", combo);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, combo, 1);
        assertGraveyardCount(playerB, "Unsummon", 1);
        assertHandCount(playerA, "Runeclaw Bear", 1);
        assertDamageReceived(playerB, "Fortress Crab", 1);

    }
}
