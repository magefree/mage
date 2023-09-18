package org.mage.test.cards.single.eld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SyrGwynHeroOfAshvaleTest extends CardTestPlayerBase {

    @Test
    public void equipKnightTest() {
        // Equipped creature gets +2/+2 and has trample and lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Behemoth Sledge"); // Artifact - Equipment {1}{G}{W}
        
        // Vigilance, menace
        // Whenever an equipped creature you control attacks, you draw a card and you lose 1 life.
        // Equipment you control have equip Knight {0}.        
        addCard(Zone.BATTLEFIELD, playerA, "Syr Gwyn, Hero of Ashvale"); // Legendary Creature {3}{R}{W}{B}  5/5 Human Knight

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip Knight", "Syr Gwyn, Hero of Ashvale");


        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Syr Gwyn, Hero of Ashvale", 7, 7);
        
    }

   @Test
    public void equipKnightTestInstantSpeed() {
        // Equipped creature gets +2/+2 and has trample and lifelink.
        addCard(Zone.BATTLEFIELD, playerA, "Behemoth Sledge"); // Artifact - Equipment {1}{G}{W}
        
        // You may activate equip abilities any time you could cast an instant.        
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Shikari", 2); // Creature 2/2

        // Vigilance, menace
        // Whenever an equipped creature you control attacks, you draw a card and you lose 1 life.
        // Equipment you control have equip Knight {0}.        
        addCard(Zone.BATTLEFIELD, playerA, "Syr Gwyn, Hero of Ashvale"); // Legendary Creature {3}{R}{W}{B}  5/5 Human Knight

        activateAbility(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Equip Knight", "Syr Gwyn, Hero of Ashvale");


        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Syr Gwyn, Hero of Ashvale", 7, 7);
        
    }    
}
