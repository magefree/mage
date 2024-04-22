package org.mage.test.cards.single.ltr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SavvyTraderTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SavvyTrader Savvy Trader}
     * Savvy Trader {3}{G}
     * Creature — Human Citizen
     * When Savvy Trader enters the battlefield, exile target permanent card from your graveyard. You may play that card for as long as it remains exiled.
     * Spells you cast from anywhere other than your hand cost {1} less to cast.
     * 3/3
     */
    private static final String trader = "Savvy Trader";

    @Test
    public void test_Play_Baneslayer() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, trader, 1);
        addCard(Zone.GRAVEYARD, playerA, "Baneslayer Angel", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 4); // 4 is enough to pay for Angel with trader reduction.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trader);
        addTarget(playerA, "Baneslayer Angel");

        checkExileCount("Angel got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Baneslayer Angel", 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Baneslayer Angel");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Savannah", true, 4);
        assertPermanentCount(playerA, "Baneslayer Angel", 1);
    }

    @Test
    public void test_Play_Swamp() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, trader, 1);
        addCard(Zone.GRAVEYARD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trader);
        addTarget(playerA, "Swamp");

        checkExileCount("Swamp got exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Swamp", 1);

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
    }
}
