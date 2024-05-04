package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ProjektorInspectorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.ProjektorInspector} {U}
     * Creature — Human Detective
     * Whenever Projektor Inspector or another Detective enters the battlefield under your control and
     * whenever a Detective you control is turned face up, you may draw a card. If you do, discard a card.
     * 3/2
     */
    private static final String inspector = "Projektor Inspector";

    @Test
    public void test_Trigger_FaceUp() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Healing Salve"); // for discard
        addCard(Zone.BATTLEFIELD, playerA, inspector);
        addCard(Zone.HAND, playerA, "Basilica Stalker"); // Disguise {4}{B}, detective
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basilica Stalker using Disguise", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{B}:");
        setChoice(playerA, true); // yes to loot

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Healing Salve", 1);
    }
}
