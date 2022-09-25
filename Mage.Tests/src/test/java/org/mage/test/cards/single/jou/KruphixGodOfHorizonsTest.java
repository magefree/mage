package org.mage.test.cards.single.jou;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class KruphixGodOfHorizonsTest extends CardTestPlayerBase {

    private static final String kruphix = "Kruphix, God of Horizons";
    private static final String sliver = "Metallic Sliver";
    private static final String repeal = "Mystic Repeal";

    @Test
    public void testKruphixNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, kruphix);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertManaPool(playerA, ManaType.COLORLESS, 3);
        assertManaPool(playerA, ManaType.GREEN, 0);
        assertPermanentCount(playerA, kruphix, 1);
    }

    @Test
    public void testKruphixRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, kruphix);
        addCard(Zone.HAND, playerA, sliver);
        addCard(Zone.HAND, playerA, repeal);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, sliver);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, repeal, kruphix);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertManaPool(playerA, ManaType.COLORLESS, 0);
        assertManaPool(playerA, ManaType.GREEN, 0);
        assertPermanentCount(playerA, sliver, 1);
        assertPermanentCount(playerA, kruphix, 0);
        assertTappedCount("Forest", true, 1);
    }
}
