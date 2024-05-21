package org.mage.test.cards.single.who;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class WeepingAngelTests extends CardTestPlayerBase {

    @Test
    public void testCreatureTypeLoss() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Weeping Angel");
        addCard(Zone.HAND, playerB, "Memnite");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite", true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertNotType("Weeping Angel", CardType.CREATURE);
    }

    @Test
    public void testCreatureTypeRegain() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Weeping Angel");
        addCard(Zone.HAND, playerB, "Memnite");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite", true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertType("Weeping Angel", CardType.CREATURE, true);
    }

    @Test
    public void testDamageEffect() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Weeping Angel");
        addCard(Zone.BATTLEFIELD, playerB, "Impervious Greatwurm");

        attack(1, playerA, "Weeping Angel", playerB);
        block(1, playerB, "Impervious Greatwurm", "Weeping Angel");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerB, "Impervious Greatwurm", 1);
        assertPermanentCount(playerB, "Impervious Greatwurm", 0);
        assertPermanentCount(playerA, "Weeping Angel", 1);
    }
}
