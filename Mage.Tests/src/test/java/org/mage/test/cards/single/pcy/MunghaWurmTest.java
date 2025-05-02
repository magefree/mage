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
    private static final String guildgate2 = "Golgari Guildgate";

    // destroy the wurm
    private static final String doomBlade = "Doom Blade";

    @Test
    public void wurmEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, meadow, 10);
        addCard(Zone.BATTLEFIELD, playerA, guildgate, 1);
        addCard(Zone.BATTLEFIELD, playerA, guildgate2, 1);
        addCard(Zone.BATTLEFIELD, playerB, treeline, 10);

        addTarget(playerA, guildgate); // untapping the first guildgate t1

        // t2, no effect since wurm is only messing with its controller untap

        addTarget(playerA, guildgate2); // untapping the second guildgate t3

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertTappedCount(meadow, true, 10); // all meadows still tapped
        assertTappedCount(guildgate, false, 1);
        assertTappedCount(guildgate2, false, 1);
        assertTappedCount(treeline, false, 10);
    }

    @Test
    public void wurmDying() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, doomBlade);
        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, meadow, 10);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, guildgate, 1);
        addCard(Zone.BATTLEFIELD, playerB, treeline, 10);

        addTarget(playerA, guildgate); // untapping the guildgate t1

        // Killing the wurm, get rid of the untap replacement effect.
        castSpell(2, PhaseStep.END_TURN, playerA, doomBlade, wurm);

        setStopAt(3, PhaseStep.UPKEEP);
        execute();

        assertTappedCount(meadow, false, 10);
        assertTappedCount(guildgate, false, 1);
        assertTappedCount(treeline, false, 10);
    }
}