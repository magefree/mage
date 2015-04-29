package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OmniscienceTest extends CardTestPlayerBase {
	
	@Test
	public void testSpellNoCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);
        
        addCard(Zone.HAND, playerA, "Gray Ogre", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        //Gray Ogre is cast because it is free
        assertPermanentCount(playerA, "Gray Ogre", 1);
	}
	
	@Test
	public void testSpellHasCostIfCastFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Haakon, Stromgald Scourge", 1);
        
        addCard(Zone.GRAVEYARD, playerA, "Knight of the White Orchid", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knight of the White Orchid");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        //Knight of the White Orchid was not cast due to lack of mana
        assertPermanentCount(playerA, "Knight of the White Orchid", 0);
	}
	
	

}
