package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.z.ZaxaraTheExemplary Zaxara, the Exemplar}
 * Deathtouch
 * {T}: Add two mana of any one color.
 * Whenever you cast a spell with {X} in its mana cost, create a 0/0 green Hydra creature token, then put X +1/+1 counters on it.
 *
 * @author Alex-Vasile
 */
public class ZaxaraTheExemplaryTest extends CardTestPlayerBase {

    private static final String zaxara = "Zaxara, the Exemplary";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9597
     *      Whenever you cast a spell with X in its cost and Zaxara on the board, Zaxara does nothing.
     */
    @Test
    public void triggersOfXSpells() {
        // {X}
        String stonecoilSerpent = "Stonecoil Serpent";
        addCard(Zone.HAND, playerA, stonecoilSerpent);
        addCard(Zone.BATTLEFIELD, playerA, zaxara);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stonecoilSerpent);
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hydra Token", 1);
        assertPowerToughness(playerA, "Hydra Token", 2, 2);
    }

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9597
     *      Whenever you cast a spell without X in its cost and Zaxara on the board, Zaxara will create a 0/0 Hydra and put no counters on it.
     */
    @Test
    public void DoesNotTriggerOfNonXSpells() {
        String lighningBolt = "Lightning Bolt";
        addCard(Zone.HAND, playerA, lighningBolt);
        addCard(Zone.BATTLEFIELD, playerA, zaxara);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lighningBolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkStackSize("Should not have triggered", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
