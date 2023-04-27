package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class ReplicateTest extends CardTestPlayerBase {

    /**
     * 702.55. Replicate
     * 702.55a Replicate is a keyword that represents two abilities. The first is a static ability that
     * functions while the spell with replicate is on the stack. The second is a triggered ability that
     * functions while the spell with replicate is on the stack. “Replicate [cost]” means “As an
     * additional cost to cast this spell, you may pay [cost] any number of times” and “When you cast
     * this spell, if a replicate cost was paid for it, copy it for each time its replicate cost was paid. If
     * the spell has any targets, you may choose new targets for any of the copies.” Paying a spell's
     * replicate cost follows the rules for paying additional costs in rules 601.2b and 601.2e–g.
     * 702.55b If a spell has multiple instances of replicate, each is paid separately and triggers based on
     * the payments made for it, not any other instance of replicate.
     *
     */

    /**
     * Train of Thought
     * Sorcery, 1U (2)
     * Replicate {1}{U} (When you cast this spell, copy it for each time you paid its replicate cost.)
     * Draw a card.
     */

    @Test
    public void testReplicate1Time() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void testReplicate2Times() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 3);
    }

    @Test
    public void testNotReplicate() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Train of Thought");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Train of Thought");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Train of Thought", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testReplicate_User() {
        // Replicate {1}{R} (When you cast this spell, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)
        // Pyromatics deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Pyromatics", 1); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyromatics");
        addTarget(playerA, playerB);
        setChoice(playerA, true); // replicate 1
        setChoice(playerA, true); // replicate 2
        setChoice(playerA, false); // stop
        //
        setChoice(playerA, false); // don't change target 1
        setChoice(playerA, false); // don't change target 2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3); // 1 + 2 replicates
    }

    @Test
    @Ignore // TODO: enable test after replicate ability will be supported by AI
    public void testReplicate_AI() {
        // Replicate {1}{R} (When you cast this spell, copy it for each time you paid its replicate cost. You may choose new targets for the copies.)
        // Pyromatics deals 1 damage to any target.
        addCard(Zone.HAND, playerA, "Pyromatics", 1); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyromatics");
        addTarget(playerA, playerB);
        //setChoice(playerA, true); // replicate 1 - AI must choice max possible
        //setChoice(playerA, true); // replicate 2 - AI must choice max possible
        //setChoice(playerA, false); // stop - AI must choice max possible
        //
        //setChoice(playerA, false); // don't change target 1
        //setChoice(playerA, false); // don't change target 2

        //setStrictChooseMode(true); - AI must choice
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3); // 1 + 2 replicates
    }

}
