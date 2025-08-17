package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AeveProgenitorOozeTest extends CardTestPlayerBase {

    /*
    Aeve, Progenitor Ooze
    {2}{G}{G}{G}
    Legendary Creature — Ooze
    Storm (When you cast this spell, copy it for each spell cast before it this turn. Copies become tokens.)
    Aeve isn’t legendary if it’s a token.
    Aeve enters with a +1/+1 counter on it for each other Ooze you control.
    2/2
     */
    private static final String aeve = "Aeve, Progenitor Ooze";
    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
     */
    public static final String bolt = "Lightning Bolt";

    @Test
    public void testAeve() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, aeve);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aeve);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, aeve, 2);
        assertTokenCount(playerA, aeve, 1);
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerA.getId(), currentGame)) {
            if (permanent.getName().equals(aeve)) {
                if (permanent instanceof PermanentToken) {
                    Assert.assertEquals(0, permanent.getCounters(currentGame).getCount(CounterType.P1P1));
                } else {
                    Assert.assertEquals(1, permanent.getCounters(currentGame).getCount(CounterType.P1P1));
                }
            }
        }
    }
}
