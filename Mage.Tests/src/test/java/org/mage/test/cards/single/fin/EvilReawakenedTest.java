package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.e.EvilReawakened Evil Reawakened} {4}{B}
 * Sorcery
 * Return target creature card from your graveyard to the battlefield with two additional +1/+1 counters on it.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class EvilReawakenedTest extends CardTestPlayerBase {

    /**
     * A return the Cage stops leaves no counters behind for the card's next, unrelated return.
     */
    @Test
    public void testStoppedReturnLeavesNoCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 8);
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage"); // Creature cards in graveyards can't enter the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Evil Reawakened");
        addCard(Zone.HAND, playerA, "Disenchant");
        addCard(Zone.HAND, playerA, "Reanimate"); // {B} sorcery: put target creature card from a graveyard onto the battlefield, lose life equal to its mana value

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Evil Reawakened", "Grizzly Bears", true);
        checkGraveyardCount("the Cage keeps it in the graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disenchant", "Grafdigger's Cage", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }
}
