package org.mage.test.cards.abilities.optional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IdentityThiefTests extends CardTestPlayerBase {

    @Test
    public void shouldntExileIfAbilityDeclined() {
        addCard(Zone.BATTLEFIELD, playerA, "Identity Thief");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Identity Thief");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertExileCount("Silvercoat Lion", 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void shouldExileIfAbilityChosen() {
        addCard(Zone.BATTLEFIELD, playerA, "Identity Thief");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(1, playerA, "Identity Thief");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 0);
    }
}
