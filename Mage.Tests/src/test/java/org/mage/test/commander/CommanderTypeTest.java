package org.mage.test.commander;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class CommanderTypeTest extends CardTestCommander4Players {

    /*
    Sephiroth, Fabled SOLDIER
    {2}{B}
    Legendary Creature - Human Avatar Soldier
    Whenever Sephiroth enters or attacks, you may sacrifice another creature. If you do, draw a card.
    Whenever another creature dies, target opponent loses 1 life and you gain 1 life. If this is the fourth time this ability has resolved this turn, transform Sephiroth.
    3/3
    */
    private static final String sephirothFabledSOLDIER = "Sephiroth, Fabled SOLDIER";

    /*
    Sephiroth, One-Winged Angel
    Legendary Creature - Angel Nightmare Avatar
    Flying
    Super Nova -- As this creature transforms into Sephiroth, One-Winged Angel, you get an emblem with "Whenever a creature dies, target opponent loses 1 life and you gain 1 life."
    Whenever Sephiroth attacks, you may sacrifice any number of other creatures. If you do, draw that many cards.
    5/5
    */
    private static final String sephirothOneWingedAngel = "Sephiroth, One-Winged Angel";

    /*
    Grizzly Bears
    {1}{G}
    Creature - Bear

    2/2
    */
    private static final String grizzlyBears = "Grizzly Bears";

    @Test
    public void testCommanderTypeTransform() {
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.COMMAND, playerA, sephirothFabledSOLDIER);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, grizzlyBears, 5);

        // Cast commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sephirothFabledSOLDIER);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, "Yes"); // Sacrifice another creature to draw a card
        setChoice(playerA, grizzlyBears);

        // Trigger death of another creature 5 times to transform
        addTarget(playerA, playerB, 6);
        for (int i = 0; i < 4; i++) {
            activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", grizzlyBears);
            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        }

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", sephirothOneWingedAngel);
        setChoice(playerA, "Yes"); // move to command zone
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // re-cast commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sephirothFabledSOLDIER);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 6);
        assertLife(playerA, 20 + 6);
        assertPermanentCount(playerA, sephirothFabledSOLDIER, 1);
        assertGraveyardCount(playerA, grizzlyBears, 5);
        assertEmblemCount(playerA, 1); // Emblem from second side
    }
}
