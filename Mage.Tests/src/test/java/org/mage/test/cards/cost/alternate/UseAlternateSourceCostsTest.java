
package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UseAlternateSourceCostsTest extends CardTestPlayerBase {

    @Test
    public void DreamHallsCastColoredSpell() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.HAND, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertTapped("Mountain", false);
    }

    @Test
    public void DreamHallsCantCastColorlessSpell() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.HAND, playerA, "Juggernaut", 1); // Creature 5/3 - {4}
        addCard(Zone.HAND, playerA, "Haunted Plate Mail", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Juggernaut"); // Cast Juggernaut by discarding Haunted Plate Mail may not work

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Haunted Plate Mail", 0);
        assertTapped("Mountain", true);
        //Juggernaut is not cast by alternate casting costs
        assertPermanentCount(playerA, "Juggernaut", 1);
    }

    @Test
    public void DreamHallsCastWithFutureSight() {
        // Rather than pay the mana cost for a spell, its controller may discard a card that shares a color with that spell.
        addCard(Zone.BATTLEFIELD, playerA, "Dream Halls", 1);
        // Play with the top card of your library revealed.
        // You may play the top card of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Future Sight", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // Add the mountains so the spell is included in teh available spells

        addCard(Zone.LIBRARY, playerA, "Gray Ogre", 1); // Creature 3/1
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre"); // Cast Orgre by discarding the Lightning Bolt

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Mountain", false);
        //Gray Ogre is cast with the discard
        assertPermanentCount(playerA, "Gray Ogre", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
