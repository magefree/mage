package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class CommanderAsIndomitableCreativityCastTest extends CardTestCommander4Players {

    // target adjusters test in command zone

    // Player order: A -> D -> C -> B

    // https://github.com/magefree/mage/issues/5852
    // Indomitable Creativity
    // Destroy X target artifacts and/or creatures. For each permanent destroyed this way,
    // its controller reveals cards from the top of their library until an artifact or creature card is revealed
    // and exiles that card. Those players put the exiled cards onto the battlefield, then shuffle their libraries.

    @Test
    public void test_CastFromHand() {
        addCard(Zone.HAND, playerA, "Indomitable Creativity", 1); // {X}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // cast with X=1 and exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indomitable Creativity");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Balduvian Bears");

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_CastFromCommand() {
        addCard(Zone.COMMAND, playerA, "Indomitable Creativity", 1); // {X}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // cast with X=1 and exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Indomitable Creativity");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Balduvian Bears");
        setChoice(playerA, true); // return spell as commander

        checkPermanentCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 0);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

}
