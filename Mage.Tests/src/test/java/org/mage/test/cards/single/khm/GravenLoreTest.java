package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GravenLoreTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GravenLore Graven Lore} {3}{U}{U}
     * Snow Instant
     * Scry X, where X is the amount of {S} spent to cast this spell, then draw three cards. ({S} is mana from a snow source.)
     */
    private static final String lore = "Graven Lore";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.HAND, playerA, lore, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lore);
        addTarget(playerA, "Baleful Strix^Ancient Amphitheater"); // put on bottom with Scry 2
        setChoice(playerA, "Ancient Amphitheater"); // order for bottom

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Mountain", 3);
    }
}
