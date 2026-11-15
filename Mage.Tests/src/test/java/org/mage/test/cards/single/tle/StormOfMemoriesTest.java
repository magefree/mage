package org.mage.test.cards.single.tle;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.StormOfMemories Storm of Memories} {2}{R}{R}{R}
 * Sorcery
 * Storm
 * Exile an instant or sorcery card with mana value 3 or less from your graveyard at random.
 * You may cast it without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
 */
public class StormOfMemoriesTest extends CardTestPlayerBase {

    private static final String storm = "Storm of Memories";
    private static final String bolt = "Lightning Bolt";
    private static final String judgment = "Day of Judgment"; // mana value 4, never chosen

    /**
     * Two Bolts in the graveyard, so the random pick doesn't matter: the storm copy casts one,
     * the original exiles the other and declines to cast it.
     */
    @Test
    public void test_StormCopyCastsAndOriginalDeclines() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.HAND, playerA, storm);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.GRAVEYARD, playerA, judgment);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, storm);
        setChoice(playerA, true); // storm copy: cast the exiled Bolt
        addTarget(playerA, playerB);
        setChoice(playerA, false); // original: decline

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3 - 3);
        assertExileCount(playerA, bolt, 2);
        assertGraveyardCount(playerA, bolt, 0);
        assertGraveyardCount(playerA, judgment, 1);
        assertGraveyardCount(playerA, storm, 1);
        assertTappedCount("Mountain", true, 6);
    }
}
