package org.mage.test.cards.single.pcy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MunghaWurmTest extends CardTestPlayerBase {

    /**
     * Mungha Wurm
     * {2}{G}{G}
     * Creature — Wurm
     * <p>
     * You can’t untap more than one land during your untap step.
     * 6/5
     */
    private static final String wurm = "Mungha Wurm";

    // etb tapped lands
    private static final String meadow = "Alpine Meadow";
    private static final String treeline = "Arctic Treeline";
    private static final String guildgate = "Azorius Guildgate";

    // destroy the wurm
    private static final String doomBlade = "Doom Blade";

    @Test
    public void wurmEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, meadow, 10);
        //addCard(Zone.BATTLEFIELD, playerA, guildgate, 1);
        addCard(Zone.BATTLEFIELD, playerB, treeline, 10);

        //setChoice(playerA, guildgate);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertTappedCount(meadow, true, 10 - 1);
        assertTappedCount(meadow, false, 1);
        //assertTappedCount(guildgate, false, 1);
        assertTappedCount(treeline, false, 10);
    }

    @Test
    public void wurmDying() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, doomBlade);
        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, meadow, 10);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //addCard(Zone.BATTLEFIELD, playerA, guildgate, 1);
        addCard(Zone.BATTLEFIELD, playerB, treeline, 10);

        // Killing the wurm, get rid of the untap replacement effect.
        castSpell(2, PhaseStep.END_TURN, playerA, doomBlade, wurm);

        //setChoice(playerA, guildgate);
        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertTappedCount(meadow, false, 10);
        //assertTappedCount(guildgate, false, 1);
        assertTappedCount(treeline, false, 10);
    }
}