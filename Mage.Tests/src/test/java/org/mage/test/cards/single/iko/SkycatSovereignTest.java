package org.mage.test.cards.single.iko;

import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SkycatSovereignTest extends CardTestPlayerBase {

    @Test
    public void test_BoostFromFlyers() {
        setStrictChooseMode(true);
        
        // Flying
        // Skycat Sovereign gets +1/+1 for each other creature you control with flying.
        // {2}{W}{U}: Create a 1/1 white Cat Bird creature token with flying.        
        addCard(Zone.BATTLEFIELD, playerA, "Skycat Sovereign"); // Creature {W}{U} (1/1)

        // Flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 2); // Creature — Griffin (2/2)

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Skycat Sovereign", 3, 3);
    }

    /**
     * Skycat Sovereign still gets +1/+1 for each creature that is supposed to have flying when there's an opposing Archetype of Imagination.
     */
    @Test
    public void test_NoBoostIfFlyingLost() {
        setStrictChooseMode(true);
        
        // Flying
        // Skycat Sovereign gets +1/+1 for each other creature you control with flying.
        // {2}{W}{U}: Create a 1/1 white Cat Bird creature token with flying.        
        addCard(Zone.BATTLEFIELD, playerA, "Skycat Sovereign"); // Creature {W}{U} (1/1)

        // Flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 2); // Creature — Griffin (2/2)

        // Creatures you control have flying.
        // Creatures your opponents control lose flying and can't have or gain flying.        
        addCard(Zone.BATTLEFIELD, playerB, "Archetype of Imagination"); //

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Skycat Sovereign", 1, 1);
    }
    
    @Test
    public void test_BoostFromToken() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Flying
        // Skycat Sovereign gets +1/+1 for each other creature you control with flying.
        // {2}{W}{U}: Create a 1/1 white Cat Bird creature token with flying.        
        addCard(Zone.BATTLEFIELD, playerA, "Skycat Sovereign"); // Creature {W}{U} (1/1)

        // Flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin", 2); // Creature — Griffin (2/2)
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W}{U}: Create");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Cat Bird Token", 1);
        assertColor(playerA, "Cat Bird Token", ObjectColor.WHITE, true);
        assertColor(playerA, "Cat Bird Token", ObjectColor.BLUE, false);
        assertAbility(playerA, "Cat Bird Token", FlyingAbility.getInstance(), true);
        
        assertPowerToughness(playerA, "Skycat Sovereign", 4, 4);
    }    
}