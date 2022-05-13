package org.mage.test.cards.abilities.other;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GainMenaceAbilityAsSingletonTest extends CardTestPlayerBase {

    @Test
    @Ignore // enable it after MenaceAbility will be singleton, see https://github.com/magefree/mage/issues/6720
    public void test_SingletonNonMultiInstances() {
        // bug: permanent shows multiple instances of one ability in card's text
        // https://github.com/magefree/mage/issues/6720

        // {3}{R}{R}
        // Whenever Sethron, Hurloon General or another nontoken Minotaur enters the battlefield under your control, create a 2/3 red Minotaur creature token.
        // {2}{B/R}: Minotaurs you control get +1/+0 and gain menace and haste until end of turn. ({B/R} can be paid with either {B} or {R}.)
        addCard(Zone.HAND, playerA, "Sethron, Hurloon General", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 + 3 * 3);

        // prepare minotaur token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sethron, Hurloon General");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Minotaur", 1);

        // boost 1
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B/R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boost 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Minotaur", 2 + 1, 3);

        // boost 2
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B/R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boost 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Minotaur", 2 + 1 * 2, 3);

        // boost 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B/R}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boost 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Minotaur", 2 + 1 * 3, 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        Permanent permanent = getPermanent("Minotaur", playerA);
        Assert.assertEquals("must have only 1 Menace instance", 1, permanent.getAbilities(currentGame).stream()
                .filter(MenaceAbility.class::isInstance).count());

    }
}
