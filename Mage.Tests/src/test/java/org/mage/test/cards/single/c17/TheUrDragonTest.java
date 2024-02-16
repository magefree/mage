package org.mage.test.cards.single.c17;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TheUrDragonTest extends CardTestPlayerBase {


    @Test
    public void test_basic() {
        setStrictChooseMode(true);
        
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        skipInitShuffling();
        // Eminence — As long as The Ur-Dragon is in the command zone or on the battlefield, other Dragon spells you cast cost 1 less to cast.
        // Flying
        // Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield.        
        addCard(Zone.BATTLEFIELD, playerA, "The Ur-Dragon", 1); // Creature (10/10)
        // Flying
        // {R}: Dragon Hatchling gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Dragon Hatchling", 2); // Creature Dragon {1}{R}  (0/1)
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Hatchling", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dragon Hatchling");
        
        attack(3, playerA, "The Ur-Dragon");
        attack(3, playerA, "Dragon Hatchling");
        attack(3, playerA, "Dragon Hatchling");
        setChoice(playerA, true); // Put a permanent card from your hand onto the battlefield?
        setChoice(playerA, "Silvercoat Lion");
        
        setStopAt(3, PhaseStep.END_COMBAT);
        
        execute();

        assertPermanentCount(playerA, "Dragon Hatchling", 2);
        assertPermanentCount(playerA, "Silvercoat Lion", 1  );
        assertHandCount(playerA, 3);
        
    }
}