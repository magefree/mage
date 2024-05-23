package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SmolderingStagecoachTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SmolderingStagecoach Smoldering Stagecoach} {3}{R}
     * Artifact — Vehicle
     * Smoldering Stagecoach’s power is equal to the number of instant and sorcery cards in your graveyard.
     * Whenever Smoldering Stagecoach attacks, the next instant spell and the next sorcery spell you cast this turn each have cascade.
     * Crew 2
     * 3/3
     */
    private static final String stagecoach = "Smoldering Stagecoach";

    @Test
    public void test_Cascade_Into_Cascade() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, stagecoach);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // 2 power for crew
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.HAND, playerA, "Absorb Vis"); // {6}{B} Sorcery - Target player loses 4 life and you gain 4 life.

        addCard(Zone.LIBRARY, playerA, "Raging Goblin"); // {R}, won't be cascaded into
        addCard(Zone.LIBRARY, playerA, "Goblin Piker"); // {1}{R}, will be be cascaded into by Mercy
        addCard(Zone.LIBRARY, playerA, "Angel's Mercy"); // {2}{W}{W} Instant - You gain 7 life, will be cascaded into by Absorb Vis

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew");
        setChoice(playerA, "Elite Vanguard"); // for crew
        attack(1, playerA, stagecoach, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Absorb Vis", playerB);
        setChoice(playerA, true, 2); // yes to both cascade trigger

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Piker", 1);
        assertPermanentCount(playerA, "Raging Goblin", 0);
        assertLife(playerB, 20 - 4);
        assertLife(playerA, 20 + 4 + 7);
    }
}
