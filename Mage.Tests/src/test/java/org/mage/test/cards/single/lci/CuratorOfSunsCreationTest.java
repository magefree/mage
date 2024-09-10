package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CuratorOfSunsCreationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CuratorOfSunsCreation} <br>
     * Curator of Sun's Creation {3}{R} <br>
     * Creature — Human Artificer <br>
     * Whenever you discover, discover again for the same value. This ability triggers only once each turn. <br>
     * 3/3
     */
    private static final String curator = "Curator of Sun's Creation";

    @Test
    public void test_trigger() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, curator);
        addCard(Zone.HAND, playerA, "Trumpeting Carnosaur"); // {4}{R}{R}, etb discover 5
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 3); // 2/2 for 2
        addCard(Zone.LIBRARY, playerA, "Grave Titan"); // cost too much for discover 5
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // 2/2 for 2
        addCard(Zone.LIBRARY, playerA, "Grave Titan"); // cost too much for discover 5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Trumpeting Carnosaur");
        setChoice(playerA, true); // cast for free from first discover
        setChoice(playerA, true); // cast for free from second discover

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
    }
}
