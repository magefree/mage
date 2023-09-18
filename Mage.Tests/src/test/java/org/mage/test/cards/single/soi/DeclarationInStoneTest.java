package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class DeclarationInStoneTest extends CardTestPlayerBase {

    @Test
    public void testExileNonToken_NameShared_ExilesMultiple() {

        /*
        Declaration in Stone {1}{W} Sorcery
        Exile target creature and all other creatures its controller controls with the same name as that creature. That player investigates for each nontoken creature exiled this way.
        */
        String dStone = "Declaration in Stone";
        String memnite = "Memnite"; // {0} 1/1
        String hGiant = "Hill Giant"; // {3}{R} 3/3
        
        addCard(Zone.BATTLEFIELD, playerB, memnite, 3);
        addCard(Zone.BATTLEFIELD, playerB, hGiant);
        addCard(Zone.HAND, playerA, dStone);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dStone, memnite);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, dStone, 1);
        assertPermanentCount(playerB, hGiant, 1);
        assertPermanentCount(playerB, memnite, 0);
        assertExileCount(playerB, memnite, 3);
        assertPermanentCount(playerB, "Clue Token", 3); // 3 creatures exiled = 3 clues for them
    }
    
    @Test
    public void testExileToken_NameShared_ExilesMultipleNoCluesFromTokensExiled() {

        /*
        Declaration in Stone {1}{W} Sorcery
        Exile target creature and all other creatures its controller controls with the same name as that creature. That player investigates for each nontoken creature exiled this way.
        */
        String dStone = "Declaration in Stone";
        
        /*
        Grave Titan {4}{B}{B}
         Creature — Giant
        Deathtouch
        Whenever Grave Titan enters the battlefield or attacks, create two 2/2 black Zombie creature tokens.
        */
        String gTitan = "Grave Titan";
        
        addCard(Zone.HAND, playerA, gTitan, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerB, dStone);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, gTitan);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, dStone, "Zombie Token");
        
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, dStone, 1);
        assertPermanentCount(playerA, gTitan, 1);
        assertPermanentCount(playerA, "Zombie Token", 0);
        assertPermanentCount(playerA, "Clue Token", 0); // tokens exiled do not generate clues
    }
}
