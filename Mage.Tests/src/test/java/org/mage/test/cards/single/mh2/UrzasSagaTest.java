package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class UrzasSagaTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.u.UrzasSaga Urza's Saga}
     * Enchantment Land — Urza's Saga
     * I — This Saga gains “{T}: Add {C}.”
     * II — This Saga gains “{2}, {T}: Create a 0/0 colorless Construct artifact creature token with ‘This token gets +1/+1 for each artifact you control.’”
     * III — Search your library for an artifact card with mana cost {0} or {1}, put it onto the battlefield, then shuffle.
     */
    private static final String saga = "Urza's Saga";

    @Test
    public void test_SimplePlay() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Black Lotus");
        addCard(Zone.LIBRARY, playerA, "Plateau", 2);

        addCard(Zone.HAND, playerA, saga, 1);
        addCard(Zone.HAND, playerA, "Sol Ring", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, saga);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA); // let trigger I resolve
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sol Ring");

        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA); // let trigger II
        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        checkPermanentCount("after first token creation", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Construct Token", 1);

        activateManaAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        activateAbility(5, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");
        addTarget(playerA, "Black Lotus");

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, saga, 1);
        assertPermanentCount(playerA, "Black Lotus", 1);
        assertPermanentCount(playerA, "Construct Token", 2);
        assertPowerToughness(playerA, "Construct Token", 4, 4);
    }
}
