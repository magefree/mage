package org.mage.test.cards.single.all;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.i.Illumination}
 * Illumination {W}{W}, Instant: Counter target artifact or enchantment spell.
 * Its controller gains life equal to its mana value.
 * <p>
 * CR 608.2h: once the countered spell has left the stack, the effect uses its
 * last known information, so the controller still gains life equal to its mana value.
 */
public class IlluminationTest extends CardTestPlayerBase {

    private static final String illumination = "Illumination";

    @Test
    public void testControllerGainsManaValueOfCounteredArtifact() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Sol Ring"); // {1}, mana value 1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerB, illumination);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, illumination, "Sol Ring", "Sol Ring");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerB, illumination, 1);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }

    @Test
    public void testControllerGainsManaValueOfCounteredEnchantment() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Pacifism"); // {1}{W}, mana value 2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerB, illumination);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pacifism", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, illumination, "Pacifism", "Pacifism");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Pacifism", 1);
        assertLife(playerA, 22);
    }
}
