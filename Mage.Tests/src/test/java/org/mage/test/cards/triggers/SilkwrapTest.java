package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * When Silkwrap enters the battlefield, exile target creature with converted mana cost 3 or less an opponent controls until Silkwrap leaves the battlefield. 
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SilkwrapTest extends CardTestPlayerBase {

    /**
     * Reported bug - Silkwrap does not exile Hangarback.
     * Cards with X CMC are considered 0 CMC on the battlefield.
     */
    @Test
    public void testHangarback() {
        
        addCard(Zone.HAND, playerA, "Hangarback Walker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 8);        
        addCard(Zone.HAND, playerB, "Silkwrap", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hangarback Walker");
        setChoice(playerA, "X=4");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silkwrap");
        addTarget(playerB, "Hangarback Walker");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Silkwrap", 1);
        assertPermanentCount(playerA, "Hangarback Walker", 0);
        assertExileCount("Hangarback Walker", 1);
    }
}
