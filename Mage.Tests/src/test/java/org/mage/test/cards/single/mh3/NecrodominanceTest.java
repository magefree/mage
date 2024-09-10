package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class NecrodominanceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.n.Necrodominance Necrodominance} {B}{B}{B}
     * Legendary Enchantment
     * Skip your draw step.
     * At the beginning of your end step, you may pay any amount of life. If you do, draw that many cards.
     * Your maximum hand size is five.
     * If a card or token would be put into your graveyard from anywhere, exile it instead.
     */
    private static final String necro = "Necrodominance";

    @Test
    public void test_ReplacementEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, necro);
        // Whenever a creature you control dies, each opponent loses 1 life.
        // Whenever a creature an opponent controls dies, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "The Meathook Massacre");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.HAND, playerA, "Gisa's Bidding"); // Create 2 2/2 black zombie creature tokens
        addCard(Zone.HAND, playerA, "Damnation");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gisa's Bidding", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Damnation");
        // A single trigger from playerB's Bears

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 1);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, 10); // 8 lands, 2 enchantments
        assertGraveyardCount(playerA, 0);
        assertGraveyardCount(playerB, 1);
        assertExileCount(playerA, 4);
    }
}
