package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ZellixSanityFlayerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.z.ZellixSanityFlayer Zellix, Sanity Flayer} {2}{U}
     * Legendary Creature — Horror
     * Hive Mind — Whenever a player mills one or more creature cards, you create a 1/1 black Horror creature token.
     * {1}, {T}: Target player mills three cards.
     * Choose a Background (You can have a Background as a second commander.)
     * 2/3
     */
    private static final String zellix = "Zellix, Sanity Flayer";

    @Test
    public void test_Trigger_2Player_Milling() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, zellix);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);
        addCard(Zone.LIBRARY, playerB, "Memnite", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");
        setChoice(playerA, "<i>Hive Mind</i>"); // stacking triggers

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, "Horror Token", 2);
    }

    @Test
    public void test_Trigger_1Player_MillingCreature() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, zellix);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);
        addCard(Zone.LIBRARY, playerB, "Taiga", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, "Horror Token", 1);
    }

    @Test
    public void test_NoTrigger_0CreatureMilled() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, zellix);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Whetstone"); // {3}: Each player mills two cards.
        addCard(Zone.LIBRARY, playerA, "Taiga", 2);
        addCard(Zone.LIBRARY, playerB, "Taiga", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, 2);
        assertPermanentCount(playerA, "Horror Token", 0);
    }
}
