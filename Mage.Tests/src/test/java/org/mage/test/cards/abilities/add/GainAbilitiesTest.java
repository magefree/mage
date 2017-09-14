package org.mage.test.cards.abilities.add;

import mage.abilities.keyword.SkulkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class GainAbilitiesTest extends CardTestPlayerBase {

    /*
    Reported bug: Behind the Scenes grants skulk to all creatures instead of just ones under owner's control
    */
    @Test
    public void behindTheScenesShouldOnlyGrantSkulkToCreaturesYouControl() {
        
        /*
    Behind the Scenes {2}{B}
    Enchantment
    Creatures you control have skulk. (They can't be blocked by creatures with greater power.)
        {4}{W}: Creatures you control get +1/+1 until end of turn
        */
        String bScenes = "Behind the Scenes";        
        String hGiant = "Hill Giant"; // {3}{R} 3/3
        String bSable = "Bronze Sable"; // {2} 2/1        
        String memnite = "Memnite"; // {0} 1/1
        String gBears = "Grizzly Bears"; // {1}{G} 2/2
        
        addCard(Zone.BATTLEFIELD, playerA, bScenes);
        addCard(Zone.BATTLEFIELD, playerA, hGiant);
        addCard(Zone.BATTLEFIELD, playerA, bSable);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerB, gBears);
        
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        assertAbility(playerA, hGiant, new SkulkAbility(), true);
        assertAbility(playerA, bSable, new SkulkAbility(), true);
        assertAbility(playerB, memnite, new SkulkAbility(), false);
        assertAbility(playerB, gBears, new SkulkAbility(), false);
    }
}
