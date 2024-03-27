package org.mage.test.cards.abilities.keywords;

import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CommittedCrimeTest extends CardTestPlayerBase {

    private static final String bear = "Grizzly Bears";
    private static final String murder = "Murder";

    private void makeTester() {
        addCustomCardWithAbility(
                "tester", playerA, new CommittedCrimeTriggeredAbility(new GainLifeEffect(1), false)
        );
    }

    @Test
    public void testMurderOpponent() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, bear);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, bear);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, bear, 0);
        assertGraveyardCount(playerB, bear, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testMurderSelf() {
        makeTester();
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, bear);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertGraveyardCount(playerA, murder, 1);
        assertLife(playerA, 20);
    }
}
