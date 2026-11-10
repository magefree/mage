package org.mage.test.cards.single.tmc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IrmaPartTimeMutantTest extends CardTestPlayerBase {

    // At the beginning of combat on your turn, Irma becomes a copy of up to one other target creature you control,
    // except her name is Irma, Part-Time Mutant and she has this ability. Then put a +1/+1 counter on her.
    private static final String irma = "Irma, Part-Time Mutant";

    @Test
    public void testCopyCreatureKeepsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, irma);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm");

        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Craw Wurm");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, irma, 1);
        assertPowerToughness(playerA, irma, 6 + 2, 4 + 2);
        assertSubtype(irma, SubType.WURM);
        assertNotSubtype(irma, SubType.BEAR);
    }

    @Test
    public void testCopyCloneCopyingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, irma);
        // You may have this creature enter as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Craw Wurm");

        addTarget(playerA, "Craw Wurm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, irma, 1);
        assertPowerToughness(playerA, irma, 6 + 1, 4 + 1);
        assertSubtype(irma, SubType.WURM);
        assertNotSubtype(irma, SubType.SHAPESHIFTER);
    }

    @Test
    public void testCopySynthInfiltratorCopyingCreature() {
        addCard(Zone.BATTLEFIELD, playerA, irma);
        // You may have this creature enter as a copy of any creature on the battlefield,
        // except it's a Synth artifact creature in addition to its other types.
        addCard(Zone.HAND, playerA, "Synth Infiltrator");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Synth Infiltrator");
        setChoice(playerA, true);
        setChoice(playerA, "Craw Wurm");

        addTarget(playerA, "Craw Wurm");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, irma, 1);
        assertPowerToughness(playerA, irma, 6 + 1, 4 + 1);
        assertType(irma, CardType.ARTIFACT, SubType.SYNTH);
        assertSubtype(irma, SubType.WURM);
    }
}
