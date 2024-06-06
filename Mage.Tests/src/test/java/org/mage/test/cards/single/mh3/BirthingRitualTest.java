package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BirthingRitualTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BirthingRitual Birthing Ritual} {1}{G}
     * Enchantment
     * At the beginning of your end step, if you control a creature, look at the top seven cards of your library.
     * Then you may sacrifice a creature. If you do, you may put a creature card with mana value X or less from
     * among those cards onto the battlefield, where X is 1 plus the sacrificed creature’s mana value.
     * Put the rest on the bottom of your library in a random order.
     */
    private static final String ritual = "Birthing Ritual";

    @Test
    public void test_NoCreature_NoTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ritual);

        // No trigger.

        setStopAt(2, PhaseStep.UPKEEP);
        execute();
    }

    @Test
    public void test_Trigger_NoSacrifice() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ritual);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Memnite", 4);
        addCard(Zone.LIBRARY, playerA, "Plains", 4);

        setChoice(playerA, TestPlayer.CHOICE_SKIP); // no sacrifice

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Memnite", 0);
    }

    @Test
    public void test_Trigger_Sacrifice_NoChoice() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ritual);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Memnite", 4);
        addCard(Zone.LIBRARY, playerA, "Plains", 4);

        setChoice(playerA, "Grizzly Bears"); // sacrifice the Bears
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // don't choose a creature to put in play

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Memnite", 0);
    }

    @Test
    public void test_Trigger_Sacrifice_Choice() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ritual);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Memnite", 8);
        addCard(Zone.LIBRARY, playerA, "Centaur Courser", 8);

        // Choice for the turn 1 turigger:
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerA, "Centaur Courser"); // 2 -> 3 mv

        checkPermanentCount("3: Courser in play", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Centaur Courser", 1);
        checkGraveyardCount("3: Bears in graveyard", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // Choice for the turn 3 trigger:
        setChoice(playerA, "Centaur Courser");
        setChoice(playerA, "Memnite"); // 3 -> 0 mv

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Centaur Courser", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Centaur Courser", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Centaur Courser", 1);
    }

    @Ignore // TODO: something weird with unit test, will be fixed separately.
    @Test
    public void test_Trigger_Sacrifice_MVRestriction() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, ritual);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.LIBRARY, playerA, "Memnite", 4);
        addCard(Zone.LIBRARY, playerA, "Baneslayer Angel", 4);

        setChoice(playerA, "Elite Vanguard"); // sacrifice the Vanguard
        setChoice(playerA, "Baneslayer Angel");

        setStopAt(2, PhaseStep.UPKEEP);
        try {
            execute();
            Assert.fail("should have failed to execute, as Baneslayer Angel is too high mv");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Select up to one creature card with mana value 2 or less")) {
                Assert.fail("must throw error about missing choice:\n" + e.getMessage());
            }
        }
    }
}
