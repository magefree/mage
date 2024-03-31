package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DocAurlockGrizzledGeniusTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DocAurlockGrizzledGenius Doc Aurlock, Grizzled Genius} {G}{U}
     * Legendary Creature — Bear Druid
     * Spells you cast from your graveyard or from exile cost {2} less to cast.
     * Plotting cards from your hand costs {2} less.
     * 2/3
     */
    private static final String doc = "Doc Aurlock, Grizzled Genius";

    /**
     * {@link mage.cards.d.DjinnOfFoolsFall Djinn of Fool's Fall} {4}{U}
     * Creature — Djinn
     * Flying
     * Plot {3}{U}
     * 4/3
     */
    private static final String djinn = "Djinn of Fool's Fall";

    @Test
    public void TestPlottingCostReduction() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, doc);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, djinn);

        checkPlayableAbility("plot can't be used during upkeep", 1, PhaseStep.UPKEEP, playerA, "Plot", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        checkExileCount("plot is in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, djinn, 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, djinn + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, djinn, 1);
        assertTappedCount("Island", true, 0);
    }
}
