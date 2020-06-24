package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CostReduceWithConditionTest extends CardTestPlayerBase {

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
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Balduvian Bears", 1);
    }

    @Test
    @Ignore
    // TODO: implement workaround like putToStackAsNonPlayable for abilities, see https://github.com/magefree/mage/issues/6685
    public void test_PriceOfFame_Reduce() {
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
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Anje Falkenrath", 1);
    }
}
