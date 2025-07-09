package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class AureliasFuryTest extends CardTestPlayerBase {

    // see issue #13774

    private static final String fury = "Aurelia's Fury"; // XRW
    // Aurelia’s Fury deals X damage divided as you choose among any number of targets.
    // Tap each creature dealt damage this way. Players dealt damage this way can’t cast noncreature spells this turn.

    private static final String elementalist = "Ardent Elementalist"; // 3R 2/1
    // When this creature enters, return target instant or sorcery card from your graveyard to your hand.
    private static final String hatchling = "Kraken Hatchling"; // 0/4
    private static final String glimmerbell = "Glimmerbell"; // 1/3 flying; 1U: untap ~
    private static final String crab = "Fortress Crab"; // 1/6


    @Test
    public void testAureliasFury() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5 + 4 + 4);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerB, glimmerbell);
        addCard(Zone.BATTLEFIELD, playerB, crab);
        addCard(Zone.HAND, playerA, fury);
        addCard(Zone.HAND, playerA, elementalist);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fury);
        setChoice(playerA, "X=3");
        addTargetAmount(playerA, hatchling, 2);
        addTargetAmount(playerA, glimmerbell, 1);

        checkDamage("first cast", 1, PhaseStep.BEGIN_COMBAT, playerA, hatchling, 2);
        checkDamage("first cast", 1, PhaseStep.BEGIN_COMBAT, playerB, glimmerbell, 1);
        checkPermanentTapped("first cast", 1, PhaseStep.BEGIN_COMBAT, playerA, hatchling, true, 1);
        checkPermanentTapped("first cast", 1, PhaseStep.BEGIN_COMBAT, playerB, glimmerbell, true, 1);
        checkPermanentTapped("first cast", 1, PhaseStep.BEGIN_COMBAT, playerB, crab, false, 1);

        activateAbility(1, PhaseStep.END_COMBAT, playerB, "{1}{U}: Untap"); // untap glimmerbell

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, elementalist);
        addTarget(playerA, fury); // return to hand
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, fury);
        setChoice(playerA, "X=2");
        addTargetAmount(playerA, crab, 1);
        addTargetAmount(playerA, playerB, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertDamageReceived(playerA, hatchling, 2);
        assertDamageReceived(playerB, glimmerbell, 1);
        assertDamageReceived(playerB, crab, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertTapped(hatchling, true);
        assertTapped(glimmerbell, false);
        assertTapped(crab, true);
    }

}
