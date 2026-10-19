package org.mage.test.cards.single.ytdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LamStormCraneElderTest extends CardTestPlayerBase {

    @Test
    public void testNoncreatureSpellsConjureMentorsAndTriggerProwess() {
        addCard(Zone.BATTLEFIELD, playerA, "Lam, Storm Crane Elder");
        addCard(Zone.HAND, playerA, "Darksteel Relic");
        addCard(Zone.HAND, playerA, "Welding Jar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Darksteel Relic");
        setChoice(playerA, "Prowess");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Welding Jar");
        setChoice(playerA, "Prowess");
        setChoice(playerA, "Prowess");
        setChoice(playerA, "Whenever you cast a noncreature spell, conjure");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Monastery Mentor", 2);
        assertPermanentCount(playerA, "Monk Token", 1);
        assertPowerToughness(playerA, "Lam, Storm Crane Elder", 5, 5);
    }

    @Test
    public void testCreatureSpellDoesNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Lam, Storm Crane Elder");
        addCard(Zone.HAND, playerA, "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Monastery Mentor", 0);
        assertPowerToughness(playerA, "Lam, Storm Crane Elder", 3, 3);
    }
}
