package org.mage.test.cards.single.ogw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ThoughtKnotSeerTest extends CardTestPlayerBase {

    /**
     * Reported bug I bounced a Thought-Knot Seer my opponent controlled with
     * enter the battlefield ability of a Reflector Mage. I should have drawn a
     * card since the Thought-Knot Seer left the battlefield but I didn't.
     */
    @Test
    public void testThoughtKnotSeerBouncedReflectorMage() {

        // {1}{W}{U} When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand.
        // That creature's owner can't cast spells with the same name as that creature until your next turn.
        addCard(Zone.HAND, playerA, "Reflector Mage"); // 2/3
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // {3}{<>} 4/4
        // When Thought-Knot Seer enters the battlefield, target opponent reveals their hand. You choose a nonland card from it and exile that card.
        // When Thought-Knot Seer leaves the battlefield, target opponent draws a card.
        addCard(Zone.BATTLEFIELD, playerB, "Thought-Knot Seer");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reflector Mage");
        // Thought-Knot Seer is auto-chosen since only target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Reflector Mage", 1);
        assertHandCount(playerB, "Thought-Knot Seer", 1);
        assertHandCount(playerA, 1); // should have drawn a card from Thought-Knot Seer leaving
    }

    /**
     * Simple bounce test on Thought-Knot Seer to differentiate between this and
     * Reflector Mage issue
     */
    @Test
    public void testThoughtKnotSeerBouncedUnsummon() {

        // {U} Return target creature to its owner's hand.
        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // {3}{<>} 4/4
        // When Thought-Knot Seer enters the battlefield, target opponent reveals their hand. You choose a nonland card from it and exile that card.
        // When Thought-Knot Seer leaves the battlefield, target opponent draws a card.
        addCard(Zone.BATTLEFIELD, playerB, "Thought-Knot Seer");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unsummon");
        // Thought-Knot Seer is auto-chosen since only target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Unsummon", 1);
        assertHandCount(playerB, "Thought-Knot Seer", 1);
        assertHandCount(playerA, 1); // should have drawn a card from Thought-Knot Seer leaving
    }

    /**
     *
     */
    @Test
    public void testThoughtKnotSeerDestroyed() {

        // {1}{B} Destroy target nonblack creature.
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // {3}{<>} 4/4
        // When Thought-Knot Seer enters the battlefield, target opponent reveals their hand. You choose a nonland card from it and exile that card.
        // When Thought-Knot Seer leaves the battlefield, target opponent draws a card.
        addCard(Zone.BATTLEFIELD, playerB, "Thought-Knot Seer");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade");
        // Thought-Knot Seer is auto-chosen since only target

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Thought-Knot Seer", 1);
        assertHandCount(playerA, 1); // should have drawn a card from Thought-Knot Seer leaving
    }

    /**
     *
     */
    @Test
    public void testThoughtKnotSeerExiled() {

        // {W} Exile target creature. Its controller may search their library for a basic land card, put that card onto the battlefield tapped, then shuffle their library.
        addCard(Zone.HAND, playerA, "Path to Exile");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // {3}{<>} 4/4
        // When Thought-Knot Seer enters the battlefield, target opponent reveals their hand. You choose a nonland card from it and exile that card.
        // When Thought-Knot Seer leaves the battlefield, target opponent draws a card.
        addCard(Zone.BATTLEFIELD, playerB, "Thought-Knot Seer");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Path to Exile");
        addTarget(playerA, "Thought-Knot Seer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, 1);
        assertExileCount("Thought-Knot Seer", 1);
        assertHandCount(playerA, 1); // should have drawn a card from Thought-Knot Seer leaving
    }
}
