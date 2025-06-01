package org.mage.test.cards.single.ltr;

import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TaleOfTinuvielTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TaleOfTinuviel Tale of Tinuviel} {3}{W}{W}
     * Enchantment — Saga
     * I — Target creature you control gains indestructible for as long as you control this Saga.
     * II — Return target creature card from your graveyard to the battlefield.
     * III — Up to two target creatures you control each gain lifelink until end of turn.
     */
    private static final String tale = "Tale of Tinuviel";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, tale, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, tale);
        addTarget(playerA, "Grizzly Bears");

        checkAbility("after I, Grizzly Bears indestructible", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", IndestructibleAbility.class, true);

        // turn 3
        addTarget(playerA, "Memnite");

        // turn 5
        checkAbility("before III, Grizzly Bears indestructible", 5, PhaseStep.UPKEEP, playerA, "Grizzly Bears", IndestructibleAbility.class, true);

        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Memnite");

        checkAbility("after III, Grizzly Bears not indestructible", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", IndestructibleAbility.class, false);
        checkAbility("after III, Grizzly Bears lifelink", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", LifelinkAbility.class, true);
        checkAbility("after III, Memnite lifelink", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", LifelinkAbility.class, true);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Grizzly Bears", LifelinkAbility.getInstance(), false);
        assertAbility(playerA, "Memnite", LifelinkAbility.getInstance(), false);
    }
}
