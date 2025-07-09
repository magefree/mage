package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SamiWildcatCaptainTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SamiWildcatCaptain Sami, Wildcat Captain} {4}{R}{W}
     * Legendary Creature — Human Artificer Rogue
     * Double strike, vigilance
     * Spells you cast have affinity for artifacts. (They cost {1} less to cast for each artifact you control.)
     * 4/4
     */
    private static final String sami = "Sami, Wildcat Captain";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, sami);
        addCard(Zone.BATTLEFIELD, playerA, "Chrome Steed"); // Artifact
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Arcane Encyclopedia"); // {3} Artifact
        addCard(Zone.HAND, playerA, "Fearless Halberdier"); // {2}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Encyclopedia", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fearless Halberdier");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Mountain", true, 3);
        assertPermanentCount(playerA, "Arcane Encyclopedia", 1);
        assertPermanentCount(playerA, "Fearless Halberdier", 1);
    }
}
