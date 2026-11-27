package org.mage.test.cards.single.ori;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.l.LilianaHereticalHealer Liliana, Heretical Healer} {1}{B}{B}, 2/3
 * Lifelink
 * Whenever another nontoken creature you control dies, exile Liliana, Heretical Healer, then return her to the
 * battlefield transformed under her owner's control. If you do, create a 2/2 black Zombie creature token.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class LilianaHereticalHealerTest extends CardTestPlayerBase {

    private static final String liliana = "Liliana, Heretical Healer";

    /**
     * 400.7: a Liliana flickered in response is a new object, so the trigger neither returns her nor makes a
     * Zombie. The next death transforms her and makes one.
     */
    @Test
    public void testTransformAndZombie() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3);
        addCard(Zone.BATTLEFIELD, playerA, liliana);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.HAND, playerA, "Cloudshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", liliana, "Whenever another nontoken creature");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("flickered in response", 1, PhaseStep.PRECOMBAT_MAIN, playerA, liliana, 1);
        checkPermanentCount("no Zombie", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Zombie Token", 0);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, liliana, 0);
        assertPermanentCount(playerA, "Liliana, Defiant Necromancer", 1);
        assertPermanentCount(playerA, "Zombie Token", 1);
    }
}
