package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author ciaccona007
 */
public class CinderCloudTest extends CardTestPlayerBase {

    //Destroy target creature. If a white creature dies this way, Cinder Cloud deals damage to that creature's controller equal to the creature's power.

    @Test
    public void testDamageWhenWhiteCreatureDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Cinder Cloud");

        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions"); //Creature - Cat 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Cloud", "Savannah Lions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
        assertPermanentCount(playerB, 0);
    }

    @Test
    public void testNoDamageWhenWhiteCreatureDoesntDie() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Cinder Cloud");
        // When Rest in Peace enters the battlefield, exile all cards from all graveyards.
        // If a card or token would be put into a graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Rest in Peace"); //Enchantment

        addCard(Zone.BATTLEFIELD, playerB, "Savannah Lions"); //Creature - Cat 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Cloud", "Savannah Lions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertExileCount(playerB, 1);
        assertPermanentCount(playerB, 0);
    }
    @Test
    public void testNoDamageIfNonwhiteCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Cinder Cloud");
        addCard(Zone.BATTLEFIELD, playerB, "Gray Ogre"); //Creature - Ogre 2/2
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cinder Cloud", "Gray Ogre");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, 0);
    }
}
