package org.mage.test.cards.single.woe;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThePrincessTakesFlightTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThePrincessTakesFlight The Princess Takes Flight} {2}{W}
     * Enchantment — Saga
     * I — Exile up to one target creature.
     * II — Target creature you control gets +2/+2 and gains flying until end of turn.
     * III — Return the exiled card to the battlefield under its owner’s control.
     */
    private static final String flight = "The Princess Takes Flight";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, flight, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, flight);
        addTarget(playerA, "Memnite");

        checkExileCount("after I, exiled Memnite", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);

        // turn 3
        addTarget(playerA, "Grizzly Bears");

        checkPT("after II, 4/4 Bears", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 2, 2 + 2);
        checkAbility("after II, flying Bears", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", FlyingAbility.class, true);
        checkExileCount("after II, still exiled Memnite", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);

        // boosts are temporary
        checkPT("4: back to 2/2 Bears", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 2, 2);
        checkAbility("4: back to non-flying Bears", 4, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", FlyingAbility.class, false);

        // turn 5

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerB, "Memnite", 0);
        assertPermanentCount(playerB, "Memnite", 1);
    }
}
