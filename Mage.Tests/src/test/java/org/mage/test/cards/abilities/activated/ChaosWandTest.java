package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChaosWandTest  extends CardTestPlayerBase {

    @Test
    public void testChaosWant() {
        addCard(Zone.BATTLEFIELD, playerA, "Chaos Wand");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.LIBRARY, playerB, "Blood Tithe");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, {T}: ");
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
    }
}
