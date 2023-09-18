package org.mage.test.cards.cost.splitcards;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author fireshoes
 */
public class SplitCardCmcTest extends CardTestPlayerBase {

    /**
    * The core of the change is that we're no longer assuming split cards have two sets of characteristics
    * when they're not on the stack. Some characteristics have multiple pieces of information very naturally
    * —Destined to Lead is an instant sorcery, the same as Ornithopter is an artifact creature. It's black and green
    * just like Winding Constrictor because its mana cost has B and G in it. Continuing that, the mana cost combines
    * the components, and a card asking for Destined to Lead's mana cost sees 4BG.
    *
    * So now, the converted mana cost question is simple: if Destined to Lead isn't on the stack, it has a converted mana cost
    * of 6. Destined on the stack is still a black instant with a converted mana cost of 2, and Lead on the stack is still a
    * green sorcery with a converted mana cost of 4, but Destined to Lead, any time it's not one or the other, is a black and green
    * instant sorcery with a converted mana cost of 6.
    */

    @Test
    public void testSplitCardCmcInHand() {
        // Total CMC of Failure // Comply is 3, so should be exiled by Transgress the Mind.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Devoid
        // Target player reveals their hand. You may choose a card from it with converted mana cost 3 or greater and exile that card.
        addCard(Zone.HAND, playerA, "Transgress the Mind");
        addCard(Zone.HAND, playerB, "Failure // Comply");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transgress the Mind", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Failure // Comply", 1);
    }

    @Test
    public void testSplitCardCmcOnStack() {
        // Counterbalance revealing Wear // Tear counters a spell with converted mana cost 3, but not 1 or 2.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Typhoid Rats"); // Creature 1/1 {B}

        // Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell
        // if it has the same converted mana cost as the revealed card.
        addCard(Zone.BATTLEFIELD, playerB, "Counterbalance");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        addCard(Zone.LIBRARY, playerB, "Wear // Tear"); // CMC now 3
        skipInitShuffling(); // so the set to top card stays at top

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Typhoid Rats");
        setChoice(playerB, true); // Reveal to Counterbalance to attempt to counter Typhoid Rats

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Typhoid Rats", 1);
        assertGraveyardCount(playerA, "Typhoid Rats", 0);
        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 0);
    }

}
