package org.mage.test.cards.single.yone;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GhalmaTheShaperTest extends CardTestPlayerBase {

    @Test
    public void testAttackConjuresCastableSteelAndCreatesArtifactMyr() {
        addCard(Zone.BATTLEFIELD, playerA, "Ghalma the Shaper");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        attack(1, playerA, "Ghalma the Shaper");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tempered Steel");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 17);
        assertTapped("Ghalma the Shaper", false);
        assertPermanentCount(playerA, "Tempered Steel", 1);
        assertPermanentCount(playerA, "Myr Token", 1);
        assertPowerToughness(playerA, "Myr Token", 3, 3);
        assertPowerToughness(playerA, "Ghalma the Shaper", 3, 3);
    }
}
