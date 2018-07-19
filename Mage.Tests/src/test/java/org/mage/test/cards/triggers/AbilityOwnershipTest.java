package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AbilityOwnershipTest extends CardTestPlayerBase {

    @Test
    public void testOwned() {
        addCard(Zone.GRAVEYARD, playerB, "Soul Snuffers");
        addCard(Zone.GRAVEYARD, playerB, "Minister of Pain");

        addCard(Zone.HAND, playerA, "Rise of the Dark Realms");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Obelisk Spider");

        setLife(playerA, 20);
        setLife(playerB, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise of the Dark Realms");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Soul Snuffers"); // sacrifice to Exploit

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // Obelisk Spider Triggers twice once for the counter on Obelisk Spider. Once for the counter on Minister of Pain.
        assertLife(playerA, 22);
        assertLife(playerB, 18);
    }

    @Test
    public void testToGraveyard() {
        addCard(Zone.GRAVEYARD, playerB, "Soul Snuffers");
        addCard(Zone.GRAVEYARD, playerB, "Minister of Pain");
        addCard(Zone.BATTLEFIELD, playerB, "Obelisk Spider");

        addCard(Zone.HAND, playerA, "Rise of the Dark Realms");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);

        setLife(playerA, 20);
        setLife(playerB, 20);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise of the Dark Realms");
        setChoice(playerA, "Yes");
        addTarget(playerA, "Soul Snuffers"); // sacrifice to Exploit

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
