
package org.mage.test.cards.abilities.keywords;

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
public class MegamorphTest extends CardTestPlayerBase {

    @Test
    public void testManifestMegamorph() {
        // Reach (This creature can block creatures with flying.)
        // Megamorph {5}{G}
        addCard(Zone.HAND, playerA, "Aerie Bowmasters", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aerie Bowmasters using Morph");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{G}: Turn");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Aerie Bowmasters", 1);
        assertPowerToughness(playerA, "Aerie Bowmasters", 4, 5); // 3/4  and the +1/+1 counter from Megamorph

        Permanent aerie = getPermanent("Aerie Bowmasters", playerA);
        Assert.assertTrue("Aerie Bowmasters has to be green", aerie != null && aerie.getColor(currentGame).isGreen());

    }

}
