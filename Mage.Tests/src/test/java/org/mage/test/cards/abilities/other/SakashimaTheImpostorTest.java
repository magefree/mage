package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by alexsandro on 06/03/17.
 */
public class SakashimaTheImpostorTest extends CardTestPlayerBase {

    @Test
    public void copySpellStutterTest() {
        // Flash, Flying
        // When Spellstutter Sprite enters the battlefield, counter target spell with converted mana cost X or less, 
        // where X is the number of Faeries you control.        
        addCard(Zone.BATTLEFIELD, playerA, "Spellstutter Sprite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        // You may have Sakashima the Impostor enter the battlefield as a copy of any creature on the battlefield, 
        // except its name is Sakashima the Impostor, it's legendary in addition to its other types,
        // and it has "{2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step."        
        addCard(Zone.HAND, playerB, "Sakashima the Impostor", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sakashima the Impostor");
        setChoice(playerB, "Spellstutter Sprite");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Spellstutter Sprite", 1);

        assertPowerToughness(playerB, "Sakashima the Impostor", 1, 1);
    }

    /**
     * I played Sakashima the Imposter copying an opponents Pawn of Ulamaog.
     * Sakashima gained the following ability: "Whenever Pawn of Ulamog or
     * another nontoken creature you control dies, you may create a 0/1
     * colorless Eldrazi Spawn creature token. It has "Sacrifice this creature:
     * Add {C}." Then Sakashima died due to combat damage and the ability did
     * not trigger.
     *
     */
    @Test
    public void copyDiesTriggeredTest() {
        // Whenever Pawn of Ulamog or another nontoken creature you control dies, you may create a 0/1 colorless 
        // Eldrazi Spawn creature token. It has "Sacrifice this creature: Add {C}."        
        addCard(Zone.BATTLEFIELD, playerA, "Pawn of Ulamog", 1); // Creature 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // Creature 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        // You may have Sakashima the Impostor enter the battlefield as a copy of any creature on the battlefield, 
        // except its name is Sakashima the Impostor, it's legendary in addition to its other types,
        // and it has "{2}{U}{U}: Return Sakashima the Impostor to its owner's hand at the beginning of the next end step."        
        addCard(Zone.HAND, playerB, "Sakashima the Impostor", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sakashima the Impostor");
        setChoice(playerB, "Pawn of Ulamog");

        attack(4, playerB, "Sakashima the Impostor");
        block(4, playerA, "Silvercoat Lion", "Sakashima the Impostor");
                
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Sakashima the Impostor", 1);        
        
        assertPermanentCount(playerA, "Eldrazi Spawn Token", 1);
        assertPermanentCount(playerB, "Eldrazi Spawn Token", 1);
    }
}
