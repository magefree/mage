package org.mage.test.cards.single.dsk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TheJollyBalloonManTest extends CardTestPlayerBase {

    @Test
    public void test_CopyMainSideAbilitiesOnly() {
        // bug: https://github.com/magefree/mage/issues/13002

        // {1}, {T}: Create a token that's a copy of another target creature you control,
        // except it's a 1/1 red Balloon creature in addition to its other colors and types and it
        // has flying and haste. Sacrifice it at the beginning of the next end step. Activate only
        // as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "The Jolly Balloon Man");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        // When Witch Enchanter enters the battlefield, destroy target artifact or enchantment an opponent controls.
        // As Witch-Blessed Meadow enters the battlefield, you may pay 3 life. If you don't, it enters tapped.
        addCard(Zone.HAND, playerA, "Witch Enchanter"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // prepare mdfc (main side)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Witch Enchanter");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // copy main side without second side abilities, so no pay life dialog
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Create");
        addTarget(playerA, "Witch Enchanter"); // to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN); // stop before copy token will be destroyed
        execute();

        assertPermanentCount(playerA, "Witch Enchanter", 2);
    }
}
