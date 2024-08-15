package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class EssenceFluxTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EssenceFlux Essence Flux} {U}
     * Sorcery
     * Exile target creature you control, then return that card to the battlefield under its owner's control.
     * If it's a Spirit, put a +1/+1 counter on it.
     */
    private static final String essenceFlux = "Essence Flux";

    @Test
    public void test_ExileSpiritNonSpirit() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Abuelo, Ancestral Echo");
        addCard(Zone.HAND, playerA, essenceFlux, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, essenceFlux, true);
        addTarget(playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, essenceFlux, true);
        addTarget(playerA, "Abuelo, Ancestral Echo");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Memnite", CounterType.P1P1, 0);
        assertCounterCount("Abuelo, Ancestral Echo", CounterType.P1P1, 1);
    }
}
