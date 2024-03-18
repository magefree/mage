package org.mage.test.cards.single.mkm;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AssembleThePlayersTest extends CardTestPlayerBase {

    /*
     Assemble the Players {1}{W}     Enchantment
     You may look at the top card of your library any time.
     Once each turn, you may cast a creature spell with power 2 or less from the top of your library.
     */
    private static final String assemble = "Assemble the Players";
    private static final String merfolk = "Coral Merfolk";
    private static final String drake = "Seacoast Drake";
    private static final String glacial = "Glacial Stalker";

    @Test
    public void testNormalAndMorph() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, assemble);
        addCard(Zone.LIBRARY, playerA, glacial);
        addCard(Zone.LIBRARY, playerA, drake);
        addCard(Zone.LIBRARY, playerA, merfolk);

        checkHandCardCount("not in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, merfolk, 0);
        checkHandCardCount("not in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, drake, 0);
        checkHandCardCount("not in hand", 1, PhaseStep.PRECOMBAT_MAIN, playerA, glacial, 0);
        checkPlayableAbility("from library", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + merfolk, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, merfolk);

        checkPlayableAbility("once per turn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + drake, false);

        checkHandCardCount("not in hand", 3, PhaseStep.PRECOMBAT_MAIN, playerA, merfolk, 0);
        checkHandCardCount("in hand", 3, PhaseStep.PRECOMBAT_MAIN, playerA, drake, 1);
        checkHandCardCount("not in hand", 3, PhaseStep.PRECOMBAT_MAIN, playerA, glacial, 0);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, glacial + " using Morph");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);
        assertPowerToughness(playerA, merfolk, 2, 1);

    }
}
