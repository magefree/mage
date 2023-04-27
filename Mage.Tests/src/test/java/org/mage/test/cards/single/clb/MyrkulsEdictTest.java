package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MyrkulsEdict Myrkul's Edict}
 * Sorcery
 * {1}{B}
 *
 * Roll a d20.
 * 1—9 | Choose an opponent. That player sacrifices a creature.
 * 10—19 | Each opponent sacrifices a creature.
 * 20 | Each opponent sacrifices a creature with the greatest power among creatures that player controls.
 *
 * @author Alex-Vasile
 */
public class MyrkulsEdictTest extends CardTestPlayerBase {
    private static final String myrkulsEdict = "Myrkul's Edict";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9381
     *      AI doesn't sacrifice a creature on a roll of 1-9
     */
    @Ignore
    @Test
    public void opponentSacrificesLevel1() {
        addCard(Zone.HAND, playerA, myrkulsEdict);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        setStrictChooseMode(true);

        setDieRollResult(playerA, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrkulsEdict);
        addTarget(playerA, playerB);
        addTarget(playerB, "Silvercoat Lion");

        execute();

        assertPermanentCount(playerB, 0);
    }

    @Test
    public void opponentSacrificesLevel2() {
        addCard(Zone.HAND, playerA, myrkulsEdict);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        setStrictChooseMode(true);

        setDieRollResult(playerA, 11);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrkulsEdict);
        addTarget(playerB, "Silvercoat Lion");

        execute();

        assertPermanentCount(playerB, 0);
    }


    @Test
    public void opponentSacrificesLevel3() {
        addCard(Zone.HAND, playerA, myrkulsEdict);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Aesi, Tyrant of Gyre Strait"); // 5/5

        setStrictChooseMode(true);

        setDieRollResult(playerA, 20);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, myrkulsEdict);
        addTarget(playerB, "Aesi, Tyrant of Gyre Strait");

        execute();

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Aesi, Tyrant of Gyre Strait", 0);
    }
}
