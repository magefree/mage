package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SmirkingSpelljackerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SmirkingSpelljacker Smirking Spelljacker} {4}{U}
     * Creature — Djinn Wizard Rogue
     * Flash
     * Flying
     * When Smirking Spelljacker enters the battlefield, exile target spell an opponent controls.
     * Whenever Smirking Spelljacker attacks, if a card is exiled with it, you may cast the exiled card without paying its mana cost.
     * 3/3
     */
    private static final String spelljacker = "Smirking Spelljacker";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerB, spelljacker);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, spelljacker);
        addTarget(playerB, "Lightning Bolt");

        checkExileCount("Bolt in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", 1);

        attack(2, playerB, spelljacker, playerA);
        setChoice(playerB, "Lightning Bolt"); // choosing bolt
        setChoice(playerB, true); // yes to cast bolt
        addTarget(playerB, playerA); // target for bolt

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20 - 3 - 3); // 3 from bolt, 3 from spelljacker
        assertPermanentCount(playerB, spelljacker, 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
