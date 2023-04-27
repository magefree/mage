package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class EchoTest extends CardTestPlayerBase {

    /*
     *    I flickered an Avalanche Riders with its Echo trigger on the stack with Restoration Angel.
     *    When the trigger resolved, my Riders was sacrificed, even though it should have been
     *    considered a new permanent.
     */

    @Test
    public void testEchoTriggerChecksIdentity() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Avalanche Riders  Creature - Human Nomad 2/2 {3}{R}
        // Haste
        // Echo (At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)
        // When Avalanche Riders enters the battlefield, destroy target land.
        addCard(Zone.HAND, playerA, "Avalanche Riders");

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Restoration Angel  {3}{W}
        // Flash
        // Flying
        // When Restoration Angel enters the battlefield, you may exile target non-Angel creature you control,
        // then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Restoration Angel");


        // cast Avalanche Riders and destroy forest
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Avalanche Riders");
        addTarget(playerA, "Forest");

        // Avalanche Riders go to echo, cast Restoration Angel to restore rider (do not apply echo with 4 mana)
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        activateManaAbility(3, PhaseStep.UPKEEP, playerA, "{T}: Add {W}");
        castSpell(3, PhaseStep.UPKEEP, playerA, "Restoration Angel");
        addTarget(playerA, "Avalanche Riders");
        setChoice(playerA, true); // raider do restore

        // Avalanche Riders triggered again
        addTarget(playerA, "Forest");

        // but no echo for blinked rider

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Avalanche Riders", 1);
        assertPermanentCount(playerA, "Restoration Angel", 1);

        assertPermanentCount(playerA, "Forest", 0);
        assertGraveyardCount(playerA, "Forest", 2);
        assertTappedCount("Plains", true, 4);
        assertTappedCount("Mountain", true, 0);
    }


}