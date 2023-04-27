package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PalisadeGiantTest extends CardTestPlayerBase {

    /**
     * when you have two Palisade Giants in play and your opponent deals you
     * damage, the damage is applied to both of them rather than allowing you to
     * choose which replacement effect applies (as it should). I experienced
     * this multiple times in games vs the A.I.
     */
    @Test
    public void testRedirectDamage() {
                
        // All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead.
        addCard(Zone.BATTLEFIELD, playerA, "Palisade Giant", 2); // Creature 2/7

        addCard(Zone.HAND, playerB, "Lightning Bolt"); // Instant {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setChoice(playerA, "Palisade Giant");
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        int damage = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Palisade Giant")) {
                damage += permanent.getDamage();
            }
        }
        Assert.assertEquals("Only 3 damage in sum should be dealt to the Palisade Giants", 3, damage);

    }

}
