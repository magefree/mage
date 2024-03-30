package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SaddleTest extends CardTestPlayerBase {

    private static final String charger = "Quilled Charger";
    private static final String bear = "Grizzly Bears";

    private void assertSaddled(String name, boolean saddled) {
        Permanent permanent = getPermanent(name);
        Assert.assertEquals(
                name + " should " + (saddled ? "" : "not ") + "be saddled",
                saddled, permanent.isSaddled()
        );
    }

    @Test
    public void testNoSaddle() {
        addCard(Zone.BATTLEFIELD, playerA, charger);

        attack(1, playerA, charger, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(charger, true);
        assertSaddled(charger, false);
        assertAbility(playerA, charger, new MenaceAbility(false), false);
        assertLife(playerB, 20 - 4);
    }

    @Test
    public void testSaddle() {
        addCard(Zone.BATTLEFIELD, playerA, charger);
        addCard(Zone.BATTLEFIELD, playerA, bear);

        setChoice(playerA, bear);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, charger, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(bear, true);
        assertTapped(charger, true);
        assertSaddled(charger, true);
        assertAbility(playerA, charger, new MenaceAbility(false), true);
        assertLife(playerB, 20 - 4 - 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertSaddled(charger, false);
    }
}
