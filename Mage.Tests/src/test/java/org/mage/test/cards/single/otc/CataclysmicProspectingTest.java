package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CataclysmicProspectingTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CataclysmicProspecting Cataclysmic Prospecting} {X}{R}{R}
     * Sorcery
     * Cataclysmic Prospecting deals X damage to each creature. For each mana from a Desert spent to cast this spell, create a tapped Treasure token.
     */
    private static final String prospecting = "Cataclysmic Prospecting";

    @Test
    public void test_Two_Desert() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients"); // 2/10
        addCard(Zone.BATTLEFIELD, playerA, "Hostile Desert", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, prospecting);

        setChoice(playerA, "X=5");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prospecting);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, prospecting, 1);
        assertDamageReceived(playerB, "Indomitable Ancients", 5);
        assertPermanentCount(playerA, "Treasure Token", 2);
    }

    @Test
    public void test_Zero_Desert() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients"); // 2/10
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, prospecting);

        setChoice(playerA, "X=5");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, prospecting);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, prospecting, 1);
        assertDamageReceived(playerB, "Indomitable Ancients", 5);
        assertPermanentCount(playerA, "Treasure Token", 0);
    }
}
