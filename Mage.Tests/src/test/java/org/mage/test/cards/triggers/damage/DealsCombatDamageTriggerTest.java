package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DealsCombatDamageTriggerTest extends CardTestPlayerBase {

    private static final String drinker = "Drinker of Sorrow"; // 5/3
    // Whenever Drinker of Sorrow deals combat damage, sacrifice a permanent.
    private static final String memnite = "Memnite"; // 1/1

    @Test
    public void triggerSourceDealsDamage() {
        addCard(Zone.BATTLEFIELD, playerA, drinker);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(1, playerA, drinker, playerB);

        addTarget(playerA, memnite); // to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 5);
        assertPermanentCount(playerA, drinker, 1);
        assertGraveyardCount(playerA, memnite, 1);
    }

    @Test
    public void noTriggerOtherDealsDamage() {
        addCard(Zone.BATTLEFIELD, playerA, drinker);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(1, playerA, memnite, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
        assertPermanentCount(playerA, drinker, 1);
        assertPermanentCount(playerA, memnite, 1);
    }

    @Test
    public void triggerTwoSourcesDealDamage() {
        addCard(Zone.BATTLEFIELD, playerA, drinker, 2);

        attack(1, playerA, drinker, playerB);
        attack(1, playerA, drinker, playerB);

        setChoice(playerA, "Whenever"); // order identical triggers
        addTarget(playerA, drinker); // to sacrifice
        addTarget(playerA, drinker); // to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 10);
        assertGraveyardCount(playerA, drinker, 2);
    }

}
