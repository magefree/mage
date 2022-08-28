package org.mage.test.oathbreaker.FFA3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestOathbreaker3PlayersFFA;

/**
 *
 * @author LevelX2
 */

public class BasicSaheeliSublimeArtificerTest extends CardTestOathbreaker3PlayersFFA {

    /**
     * Check that if a player left the game, it's commander is also removed
     */
    @Test
    public void commanderRemovedTest() {

        concede(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertCommandZoneCount(playerB, "Saheeli, Sublime Artificer", 1);
        assertCommandZoneCount(playerB, "Thoughtcast", 1);
        assertCommandZoneCount(playerC, "Saheeli, Sublime Artificer", 1);
        assertCommandZoneCount(playerC, "Thoughtcast", 1);
        assertCommandZoneCount(playerA, "Saheeli, Sublime Artificer", 0);
        assertCommandZoneCount(playerA, "Thoughtcast", 0);

    }

    @Test
    public void castCommanderTest() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 4);

        // Planeswalker 5 Loyality Counter
        // Whenever you cast a noncreature spell, create a 1/1 colorless Servo artifact creature token.
        // -2: Target artifact you control becomes a copy of another target artifact or creature you control until end of turn,
        // except it's an artifact in addition to its other types.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saheeli, Sublime Artificer", true); // Planeswalker 5  {1}{U/R}{U/R}

        // Affinity for artifacts
        // Draw two cards.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtcast"); // Sorcery {4}{U}

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();


        assertCommandZoneCount(playerB, "Saheeli, Sublime Artificer", 1);
        assertCommandZoneCount(playerB, "Thoughtcast", 1);
        assertCommandZoneCount(playerC, "Saheeli, Sublime Artificer", 1);
        assertCommandZoneCount(playerC, "Thoughtcast", 1);

        assertHandCount(playerA, 3);
        assertPermanentCount(playerA, "Saheeli, Sublime Artificer", 1);
        assertCommandZoneCount(playerA, "Thoughtcast", 1);
    }
}
