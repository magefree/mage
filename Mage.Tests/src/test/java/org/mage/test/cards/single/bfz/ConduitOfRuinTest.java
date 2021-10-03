package org.mage.test.cards.single.bfz;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ConduitOfRuinTest extends CardTestPlayerBase {

    @Test
    public void testCast() {
        setStrictChooseMode(true);
        
        // Emrakul, the Aeons Torn can't be countered.
        // When you cast Emrakul, take an extra turn after this one.
        // Flying, protection from colored spells, annihilator 6
        // When Emrakul is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.        
        addCard(Zone.LIBRARY, playerA, "Emrakul, the Aeons Torn"); // Creature {15} 15/15
        
        // When you cast Conduit of Ruin, you may search your library for a colorless creature card with converted mana cost 7 or greater, then shuffle your library and put that card on top of it.
        // The first creature spell you cast each turn costs {2} less to cast.
        addCard(Zone.HAND, playerA, "Conduit of Ruin"); // Creature {6} 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 13);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conduit of Ruin");
        setChoice(playerA, true); // When you cast this spell, you may search...
        addTarget(playerA, "Emrakul, the Aeons Torn");
        
        setStopAt(3, PhaseStep.DRAW);

        execute();
        
        assertLibraryCount(playerA, "Emrakul, the Aeons Torn", 0);
        assertHandCount(playerA, "Emrakul, the Aeons Torn", 1);
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Emrakul, the Aeons Torn");
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Conduit of Ruin", 1);
        assertPermanentCount(playerA, "Emrakul, the Aeons Torn", 1);
    }
}