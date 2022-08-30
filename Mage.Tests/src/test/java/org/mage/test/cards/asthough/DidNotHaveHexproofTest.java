package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author drmDev
 */
public class DidNotHaveHexproofTest extends CardTestPlayerBase {

    /*
     Witchbane Orb (4)
    When Witchbane Orb enters the battlefield, destroy all Curses attached to you.
    You have hexproof. (You can't be the target of spells or abilities your opponents control, including Aura spells.)
    */
    public static final String wOrb = "Witchbane Orb";

    /*
    Detection Tower (Land)
    {T}: Add Colorless.
    1, {T}: Until end of turn, your opponents and creatures your opponents control with hexproof can be the targets of spells and abilities you control 
    as though they didn't have hexproof.
    */
    public static final String dTower = "Detection Tower";

    @Test
    public void detectionTowerAllowsTargettingPlayerWithWitchbaneOrb() {
        
        addCard(Zone.BATTLEFIELD, playerA, dTower);        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Shock"); // {R} 2 dmg to any target
        addCard(Zone.BATTLEFIELD, playerB, wOrb);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Shock", playerB);
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
                
        assertTappedCount("Mountain", true, 2);
        assertTapped(dTower, true);
        assertGraveyardCount(playerA, "Shock", 1);
        assertLife(playerB, 18);
    }
}
