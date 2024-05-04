package org.mage.test.cards.single.thb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author Susucr
 */
public class AshiokSculptorOfFearsTest extends CardTestMultiPlayerBase {

    /**
     * {@link mage.cards.a.AshiokSculptorOfFears Ashiok, Sculptor of Fears} {4}{U}{B}
     * Legendary Planeswalker — Ashiok
     * +2: Draw a card. Each player mills two cards.
     * −5: Put target creature card from a graveyard onto the battlefield under your control.
     * −11: Gain control of all creatures target opponent controls.
     * Loyalty: 4
     */
    private static final String ashiok = "Ashiok, Sculptor of Fears";

    @Test
    public void test_Minus11() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, ashiok);
        addCard(Zone.BATTLEFIELD, playerA, "Doubling Season", 2); // To have 16 Loyalty at first
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 6);

        // Will take control of those:
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        // Will not take control of those:
        addCard(Zone.BATTLEFIELD, playerB, "Taiga", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grist, the Hunger Tide", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ashiok, true);
        setChoice(playerA, "Doubling Season"); // ordering replacement effects

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-11", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 9 + 2);
        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerB, 5 - 2);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerC, "Grizzly Bears", 2);
        assertPermanentCount(playerD, "Grizzly Bears", 2);
    }
}
