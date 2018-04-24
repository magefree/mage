package org.mage.test.cards.single.bfz;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class HalimarTidecallerTest extends CardTestPlayerBase {

    @Test
    public void testMe() {

        /*
         Halimar Tidecaller {2}{U}
        Creature — Human Wizard Ally 2/3
        When Halimar Tidecaller enters the battlefield, you may return target card with awaken from your graveyard to your hand.
        Land creatures you control have flying.
        */
        String hTidecaller = "Halimar Tidecaller";
        
        /*
         Treetop Village
        Land
        Treetop Village enters the battlefield tapped.
        {T}: Add Green.
        {1}{G}: Treetop Village becomes a 3/3 green Ape creature with trample until end of turn. It's still a land. 
        */
        String tVillage = "Treetop Village";
        
        addCard(Zone.BATTLEFIELD, playerA, hTidecaller);
        addCard(Zone.BATTLEFIELD, playerA, tVillage);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G}:"); // activate tree-top
        attack(1, playerA, tVillage);
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertLife(playerB, 17); // 3 damage from tree-top
        assertAbility(playerA, tVillage, FlyingAbility.getInstance(), true);
        assertAbility(playerA, tVillage, TrampleAbility.getInstance(), true);
    }
}
