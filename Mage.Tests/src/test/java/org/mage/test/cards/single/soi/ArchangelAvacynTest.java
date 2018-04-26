package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {3}{W}{W} Angel - day
 *
 * Flash Flying, vigilance When Archangel Avacyn enters the battlefield,
 * creatures you control gain indestructible until end of turn. When a non-Angel
 * creature you control dies, transform Archangel Avacyn at the beginning of the
 * next upkeep.
 *
 * (Night) - red card When this creature transforms into Avacyn, the Purifier,
 * it deals 3 damage to each other creature and each opponent.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ArchangelAvacynTest extends CardTestPlayerBase {

    /**
     * Reported bug: "Archangel Avacyn damages herself when she transforms"
     */
    @Test
    public void basicTransformTest() {
        // Flash
        // Flying
        // Vigilance
        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        // Transformed side: Avacyn, the Purifier - Creature 6/5
        // Flying
        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Archangel Avacyn");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Omens"); // 0/4
        addCard(Zone.HAND, playerA, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Roots"); // 0/5
        addCard(Zone.HAND, playerB, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", "Elite Vanguard");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerB, "Shock", 1);
        assertPermanentCount(playerA, "Avacyn, the Purifier", 1);
        assertPermanentCount(playerA, "Wall of Omens", 1);
        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerB, "Wall of Roots", 1);

        Permanent avacyn = getPermanent("Avacyn, the Purifier", playerA);
        Assert.assertEquals("Damage to Avacyn, the Purifier should be 0 not 3", 0, avacyn.getDamage());

        assertGraveyardCount(playerB, "Hill Giant", 1);

    }

    /**
     * Reported Bug: If more than 1(in my case 3) non-angel creature dies, she
     * flips various times dealing the 3 damage more than 1 time.
     */
    @Test
    public void TransformOnlyOnceTest() {
        // Flash
        // Flying
        // Vigilance
        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        // Transformed side: Avacyn, the Purifier - Creature 6/5
        // Flying
        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Archangel Avacyn");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots"); // 0/5
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Seismic Shudder deals 1 damage to each creature without flying.
        addCard(Zone.HAND, playerA, "Seismic Shudder"); // {1}{R}

        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 3); // 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seismic Shudder");
        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerA, "Seismic Shudder", 1);
        assertGraveyardCount(playerA, "Elite Vanguard", 3);

        assertPermanentCount(playerA, "Wall of Roots", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }
}
