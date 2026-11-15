package org.mage.test.cards.single.voc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class BreathkeeperSeraphTest extends CardTestPlayerBase {

    @Test
    public void test_ReturnUnderOwnerControlAtYourNextUpkeep() {
        // As long as Breathkeeper Seraph is paired with another creature, each of those creatures has "When this
        // creature dies, you may return it to the battlefield under its owner's control at the beginning of your next
        // upkeep."
        addCard(Zone.HAND, playerA, "Breathkeeper Seraph"); // {4}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Act of Treason"); // {2}{R}, gain control
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Disfigure"); // {B}, -2/-2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // steal and pair
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Breathkeeper Seraph");
        setChoice(playerA, true); // use soulbond
        setChoice(playerA, "Grizzly Bears"); // pair with

        // dies under your control - returns under its owner's control
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears");
        setChoice(playerA, true); // use return

        // not on the opponent's upkeep
        checkPermanentCount("opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears", 0);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_Return_NewObjectInGraveyard() {
        // As long as Breathkeeper Seraph is paired with another creature, each of those creatures has "When this
        // creature dies, you may return it to the battlefield under its owner's control at the beginning of your next
        // upkeep."
        addCard(Zone.HAND, playerA, "Breathkeeper Seraph"); // {4}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Disfigure", 2); // {B}, -2/-2
        addCard(Zone.HAND, playerA, "Zombify"); // {3}{B}, return creature card from graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 4 + 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Breathkeeper Seraph");
        setChoice(playerA, true); // use soulbond
        setChoice(playerA, "Grizzly Bears"); // pair with

        // dies paired, then returns and dies again as a new object before your next upkeep
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears");
        setChoice(playerA, true); // use return
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Zombify", "Grizzly Bears");
        setChoice(playerA, false); // don't pair again
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
