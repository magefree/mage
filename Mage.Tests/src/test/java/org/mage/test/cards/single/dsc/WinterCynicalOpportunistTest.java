package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WinterCynicalOpportunistTest extends CardTestPlayerBase {

    private static final String WINTER = "Winter, Cynical Opportunist";
    private static final String MEMNITE = "Memnite";
    private static final String WILDS = "Evolving Wilds";
    private static final String GROWTH = "Giant Growth";
    private static final String DIVINATION = "Divination";

    @Test
    public void testEndStepExilesBatchAndReturnsPermanentWithFinality() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, WINTER);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.GRAVEYARD, playerA, MEMNITE);
        addCard(Zone.GRAVEYARD, playerA, WILDS);
        addCard(Zone.GRAVEYARD, playerA, GROWTH);
        addCard(Zone.GRAVEYARD, playerA, DIVINATION);

        setChoice(playerA, true);
        setChoice(playerA, MEMNITE + "^" + WILDS + "^" + GROWTH + "^" + DIVINATION);
        setChoice(playerA, MEMNITE);
        checkPermanentCounters("Memnite returned with finality", 2, PhaseStep.UPKEEP, playerA, MEMNITE, CounterType.FINALITY, 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", MEMNITE);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, MEMNITE, 0);
        assertExileCount(playerA, MEMNITE, 1);
        assertGraveyardCount(playerA, MEMNITE, 0);
        assertGraveyardCount(playerA, "Murder", 1);
    }

    @Test
    public void testDoesNothingWhenGraveyardCannotSupplyFourCardTypes() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, WINTER);
        addCard(Zone.GRAVEYARD, playerA, MEMNITE);
        addCard(Zone.GRAVEYARD, playerA, WILDS);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, MEMNITE, 0);
        assertExileCount(playerA, MEMNITE, 0);
        assertGraveyardCount(playerA, MEMNITE, 1);
        assertGraveyardCount(playerA, WILDS, 1);
    }
}
