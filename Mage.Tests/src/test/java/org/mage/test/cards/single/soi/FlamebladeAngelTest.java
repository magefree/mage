package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 4RR
 * Creature - Angel
 * Flying
 * <p>
 * Whenever a source an opponent controls deals damage to you or a permanent you control,
 * you may have Flameblade Angel deal 1 damage to that source's controller.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class FlamebladeAngelTest extends CardTestPlayerBase {

    /**
     * Reported bug: Not triggering when damage is dealt to the creatures I control.
     */
    @Test
    public void testDamageToCreature() {

        addCard(Zone.BATTLEFIELD, playerA, "Flameblade Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots"); // 0/5
        addCard(Zone.HAND, playerB, "Shock"); // instant deals 2 dmg to creature/player
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock");
        addTarget(playerB, "Wall of Roots");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent roots = getPermanent("Wall of Roots", playerA);
        Assert.assertEquals("Wall of Roots should have 2 damage dealt to it", 2, roots.getDamage());
        assertGraveyardCount(playerB, "Shock", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 19); // Angel should deal 1 damage to Shock's controller
    }

    /**
     * Reported bug: Not triggering when damage is dealt to the creatures I control.
     */
    @Test
    public void testDamageToMultipleCreatures() {

        addCard(Zone.BATTLEFIELD, playerA, "Flameblade Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots"); // 0/5
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.HAND, playerB, "Shock", 2); // instant deals 2 dmg to creature/player
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock");
        addTarget(playerB, "Wall of Roots");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock");
        addTarget(playerB, "Grizzly Bears");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent roots = getPermanent("Wall of Roots", playerA);
        Assert.assertEquals("Wall of Roots should have 2 damage dealt to it", 2, roots.getDamage());
        assertGraveyardCount(playerB, "Shock", 2);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 18); // Angel should deal 1 damage twice to Shock's controller
    }

    /**
     * Reported bug: Not triggering when damage is dealt to the creatures I control.
     */
    @Test
    public void testCombatDamageToCreatures() {

        addCard(Zone.BATTLEFIELD, playerA, "Flameblade Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots"); // 0/5
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3

        attack(2, playerB, "Elite Vanguard");
        attack(2, playerB, "Hill Giant");
        block(2, playerA, "Wall of Roots", "Hill Giant");
        block(2, playerA, "Grizzly Bears", "Elite Vanguard");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Permanent roots = getPermanent("Wall of Roots", playerA);
        Assert.assertEquals("Wall of Roots should have 3 damage dealt to it", 3, roots.getDamage());
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 18); // Angel should deal 1 damage twice
    }
}
