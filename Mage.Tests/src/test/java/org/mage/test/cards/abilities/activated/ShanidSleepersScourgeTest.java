package org.mage.test.cards.abilities.activated;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ShanidSleepersScourgeTest extends CardTestPlayerBase {

    @Test
    public void testShanidSleepersScourge() {
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addCard(Zone.HAND, playerA, "Academy Ruins", 1);
        addCard(Zone.HAND, playerA, "Mox Amber", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Ruins");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Amber");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 2);
        assertLife(playerB, 20);
    }

    @Test
    public void testShanidSleepersScourgeNoTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        addCard(Zone.HAND, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
        assertLife(playerB, 20);
    }

    @Test
    public void testShanidSleepersScourgeMenace() {
        addCard(Zone.BATTLEFIELD, playerA, "Shanid, Sleepers' Scourge");
        addCard(Zone.BATTLEFIELD, playerA, "Gaddock Teeg");
        addCard(Zone.BATTLEFIELD, playerB, "Gaddock Teeg");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerA, 20);
        assertHandCount(playerA, 0);
        assertLife(playerB, 20);
        assertAbility(playerA, "Gaddock Teeg", new MenaceAbility(), true);
        assertAbility(playerB, "Gaddock Teeg", new MenaceAbility(), false);
    }

}
