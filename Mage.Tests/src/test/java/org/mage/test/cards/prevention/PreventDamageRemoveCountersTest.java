package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PreventDamageRemoveCountersTest extends CardTestPlayerBase {

    @Test
    public void testCounterRemoval() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Flame Slash", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Oathsworn Knight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Polukranos, Unchained", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flame Slash", "Oathsworn Knight");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Polukranos, Unchained");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Oathsworn Knight", 1);
        assertPermanentCount(playerA, "Polukranos, Unchained", 1);
        assertPowerToughness(playerA, "Oathsworn Knight", 3, 3); // 1 counter removed
        assertPowerToughness(playerA, "Polukranos, Unchained", 4, 4); // 2 counters removed

    }

    @Test
    public void testMagmaPummeler() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.HAND, playerA, "Magma Pummeler", 1);
        addCard(Zone.HAND, playerA, "Shock", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magma Pummeler");
        setChoice(playerA, "X=5");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Magma Pummeler");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Magma Pummeler", 1);
        assertPowerToughness(playerA, "Magma Pummeler", 3, 3); // 2 counters removed
        assertLife(playerB, 18); // 2 damage dealt

    }

    @Test
    public void testProteanHydraBoosted() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1);
        addCard(Zone.HAND, playerA, "Protean Hydra", 1);
        addCard(Zone.HAND, playerA, "Hornet Sting", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Protean Hydra");
        setChoice(playerA, "X=0");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hornet Sting", "Protean Hydra");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Protean Hydra", 1);
        assertPowerToughness(playerA, "Protean Hydra", 1, 1);

    }

}
