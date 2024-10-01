package org.mage.test.cards.single.cmm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class NarciFableSingerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.NarciFableSinger Narci, Fable Singer} {1}{W}{B}{G}
     * Legendary Creature — Human Bard
     * Lifelink
     * Whenever you sacrifice an enchantment, draw a card.
     * Whenever the final chapter ability of a Saga you control resolves, each opponent loses X life and you gain X life, where X is that Saga’s mana value.
     * 3/3
     */
    private static final String narci = "Narci, Fable Singer";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, narci);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "History of Benalia");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "History of Benalia");

        setChoice(playerA, "Whenever you sacrifice an enchantment, draw a card."); // stack both triggers

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "History of Benalia", 1);
        assertHandCount(playerA, 3); // 2 from draw step, 1 from Narci
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
    }
}
