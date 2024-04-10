package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class HeartlessConscriptionTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.h.HeartlessConscription Heartless Conscription} {6}{B}{B}
     * Sorcery
     * Exile all creatures. For each card exiled this way, you may play that card for as long as it remains exiled, and mana of any type can be spent to cast that spell. Exile Heartless Conscription.
     */
    private static final String conscription = "Heartless Conscription";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, conscription);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, conscription, true);
        checkExileCount("Elite Vanguard in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 1);
        checkExileCount("Goblin Piker in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Piker", 1);
        checkExileCount(conscription + " in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, conscription, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Goblin Piker");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Piker", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertTappedCount("Swamp", true, 1);
    }
}
