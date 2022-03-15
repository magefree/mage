package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DecayedTest extends CardTestPlayerBase {

    @Test
    public void decayedToken() {
        addCard(Zone.HAND, playerA, "Falcon Abomination", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Falcon Abomination");
        attack(3, playerA, "Zombie Token");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Falcon Abomination", 1);
        assertPermanentCount(playerA, "Zombie Token", 0);
    }

    @Test
    public void decayedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Gisa, Glorious Resurrector", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Doom Blade", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade");
        addTarget(playerA, "Grizzly Bears");
        // Gisa - "If a creature an opponent controls would die, exile it instead."
        checkExileCount("Gisa Exile Ability", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        attack(5, playerA, "Grizzly Bears");
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Gisa, Glorious Resurrector", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertExileCount("Grizzly Bears", 0);
        // Grizzly Bears should sacrifice after combat and go to playerB's graveyard
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
