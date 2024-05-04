package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FblthpLostOnTheRangeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FblthpLostOnTheRange Fblthp, Lost on the Range} {1}{U}{U}
     * Legendary Creature — Homunculus
     * Ward {2}
     * You may look at the top card of your library any time.
     * The top card of your library has plot. The plot cost is equal to its mana cost.
     * You may plot nonland cards from the top of your library.
     * 1/1
     */
    private static final String fblthp = "Fblthp, Lost on the Range";

    @Test
    public void Test_Plot_FromTop_LightningBolt() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fblthp);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        assertHandCount(playerA, 0); // no card in hand, Bolt is on top.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertTappedCount("Mountain", true, 1); // cost {R} to plot
    }

    @Test
    public void Test_Plot_FromTop_RegularPlot() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fblthp);
        addCard(Zone.LIBRARY, playerA, "Beastbond Outcaster"); // {2}{G}, plot {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        assertHandCount(playerA, 0); // no card in hand, Outcaster is on top.
        checkPlayableAbility("regular Plot {1}{G}", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {1}{G}", true);
        checkPlayableAbility("no mana for added Plot {2}{G}", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {2}{G}", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {1}{G}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Beastbond Outcaster", 1);
        assertTappedCount("Forest", true, 2);
    }

    @Test
    public void Test_Plot_FromTop_AddedPlot() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fblthp);
        addCard(Zone.LIBRARY, playerA, "Beastbond Outcaster"); // {2}{G}, plot {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        assertHandCount(playerA, 0); // no card in hand, Outcaster is on top.
        checkPlayableAbility("regular Plot {1}{G}", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {1}{G}", true);
        checkPlayableAbility("added Plot {2}{G}", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {2}{G}", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {2}{G}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Beastbond Outcaster", 1);
        assertTappedCount("Forest", true, 3);
    }

    @Test
    public void Test_Plot_FromTop_Adventure() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fblthp);
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot {2}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Bonecrusher Giant", 1);
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void Test_Plot_FromTop_Split() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, fblthp);
        addCard(Zone.LIBRARY, playerA, "Life // Death"); // split {G} / {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Life // Death", 1);
        assertTappedCount("Bayou", true, 3);
    }
}
