package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class CostReduceWithConditionTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_PriceOfFame_Normal() {
        // {3}{B}
        // This spell costs {2} less to cast if it targets a legendary creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Price of Fame", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Price of Fame", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }

    @Test
    public void test_PriceOfFame_Reduce_Manual() {
        // https://github.com/magefree/mage/issues/6685

        // {3}{B}
        // This spell costs {2} less to cast if it targets a legendary creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Price of Fame", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Anje Falkenrath", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Price of Fame", "Anje Falkenrath");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Anje Falkenrath", 1);
    }

    @Test
    public void test_PriceOfFame_Reduce_AI() {
        // https://github.com/magefree/mage/issues/6685

        // {3}{B}
        // This spell costs {2} less to cast if it targets a legendary creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Price of Fame", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4 - 2);
        addCard(Zone.BATTLEFIELD, playerB, "Anje Falkenrath", 1);

        // AI must see and play that cards too
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Anje Falkenrath", 1);
    }
}
