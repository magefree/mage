package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class FuryOfTheHordeTest extends CardTestPlayerBase {

    /*
     * Fury of the Horde
     * Sorcery, 5RR (7)
     * You may exile two red cards from your hand rather than pay Fury of the
     * Horde's mana cost.
     * Untap all creatures that attacked this turn. After this main phase, there
     * is an additional combat phase followed by an additional main phase.
     *
     */
    // test that creatures attack twice
    @Test
    public void testCreaturesAttackTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Fury of the Horde");

        attack(3, playerA, "Craw Wurm");
        attack(3, playerA, "Goblin Roughrider");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fury of the Horde");
        attack(3, playerA, "Craw Wurm");
        attack(3, playerA, "Goblin Roughrider");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        this.assertLife(playerB, 2);

    }

    // test that creatures attack once
    @Test
    public void testCreaturesAttackOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Fury of the Horde");

        attack(3, playerA, "Craw Wurm");
        attack(3, playerA, "Goblin Roughrider");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        this.assertLife(playerB, 11);

    }

    // test that only creatures that attacked attack twice
    @Test
    public void testCreaturesThatAttacked() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Fury of the Horde");

        attack(3, playerA, "Craw Wurm");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fury of the Horde");
        attack(3, playerA, "Craw Wurm");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        this.assertLife(playerB, 8);

    }

}
