package org.mage.test.cards.single.nph;

import mage.abilities.keyword.InfectAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TriumphOfTheHordesTest extends CardTestPlayerBase {

    //issue 3292, Triumph of the Hordes gives a Chromatic Lantern Infect + Trample
    @Test
    public void triumphOfTheHordesNonCreatureTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Lantern", 1);
        addCard(Zone.HAND, playerA, "Triumph of the Hordes", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Triumph of the Hordes");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertAbility(playerA, "Chromatic Lantern", InfectAbility.getInstance(), false);
        assertAbility(playerA, "Grizzly Bears", InfectAbility.getInstance(), true);
    }
}
