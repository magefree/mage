package org.mage.test.cards.single.hou;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheScarabGodTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheScarabGod The Scarab God} {3}{U}{B}
     * Legendary Creature — God
     * At the beginning of your upkeep, each opponent loses X life and you scry X, where X is the number of Zombies you control.
     * {2}{U}{B}: Exile target creature card from a graveyard. Create a token that’s a copy of it, except it’s a 4/4 black Zombie.
     * When The Scarab God dies, return it to its owner’s hand at the beginning of the next end step.
     * 5/5
     */
    private static final String god = "The Scarab God";

    @Test
    public void test_scry2() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancient Amphitheater");
        addCard(Zone.LIBRARY, playerA, "Baleful Strix");

        addCard(Zone.BATTLEFIELD, playerA, god, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Legions of Lim-Dul", 2);

        // The Scarab God's upkeep trigger triggers
        addTarget(playerA, "Ancient Amphitheater"); // put on bottom with scry 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
    }
}
