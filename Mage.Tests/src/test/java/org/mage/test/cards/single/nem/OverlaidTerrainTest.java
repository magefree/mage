package org.mage.test.cards.single.nem;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OverlaidTerrainTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OverlaidTerrain Overlaid Terrain} {2}{G}{G}
     * Enchantment
     * As Overlaid Terrain enters the battlefield, sacrifice all lands you control.
     * Lands you control have “{T}: Add two mana of any one color.”
     */
    private static final String terrain = "Overlaid Terrain";

    @Test
    public void test_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, terrain);

        // Will be sacrificed:
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        // Will not be sacrificed:
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Taiga", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, terrain);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 1 + 2);
        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 4);
        assertPermanentCount(playerB, 4);
    }

    @Test
    public void test_Mana() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, terrain);
        addCard(Zone.HAND, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Ajani's Sunstriker"); // {W}{W}

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani's Sunstriker");
        setChoice(playerA, "White"); // choice for mana color

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ajani's Sunstriker", 1);
    }
}
