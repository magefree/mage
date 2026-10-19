package org.mage.test.cards.single.yecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GiltLeafAlchemistTest extends CardTestPlayerBase {

    @Test
    public void testKindredElfCardCountsAndForestEntersUntapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Gilt-Leaf Alchemist");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Elvish Promenade");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Conjure");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Forest", 1);
        assertPermanentCount(playerB, "Forest", 0);
        assertTapped("Forest", false);
        assertTapped("Gilt-Leaf Alchemist", true);
    }

    @Test
    public void testRequiresTwoElfCardsInOwnGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Gilt-Leaf Alchemist");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerB, "Elvish Mystic");
        checkPlayableAbility("only one own Elf", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Conjure", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Forest", 0);
    }

    @Test
    public void testConditionIsNotRecheckedOnResolution() {
        addCard(Zone.BATTLEFIELD, playerA, "Gilt-Leaf Alchemist");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Tormod's Crypt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Conjure");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 0);
        assertExileCount(playerA, "Llanowar Elves", 2);
        assertPermanentCount(playerA, "Forest", 1);
    }
}
