package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FirstStrikeAbility;
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

    private static final String arynx = "Trained Arynx";
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
        addCard(Zone.BATTLEFIELD, playerA, arynx);

        attack(1, playerA, arynx, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(arynx, true);
        assertSaddled(arynx, false);
        assertAbility(playerA, arynx, FirstStrikeAbility.getInstance(), false);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testSaddle() {
        addCard(Zone.BATTLEFIELD, playerA, arynx);
        addCard(Zone.BATTLEFIELD, playerA, bear);

        setChoice(playerA, bear);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        attack(1, playerA, arynx, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(bear, true);
        assertTapped(arynx, true);
        assertSaddled(arynx, true);
        assertAbility(playerA, arynx, FirstStrikeAbility.getInstance(), true);
        assertLife(playerB, 20 - 3);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertSaddled(arynx, false);
    }
}
