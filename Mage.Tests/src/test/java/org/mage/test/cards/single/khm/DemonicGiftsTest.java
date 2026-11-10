package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DemonicGiftsTest extends CardTestPlayerBase {

    @Test
    public void test_Return() {
        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the
        // battlefield under its owner's control."
        addCard(Zone.HAND, playerA, "Demonic Gifts"); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Disfigure"); // {B}, -2/-2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Gifts", "Grizzly Bears");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2); // new object without the boost
    }

    @Test
    public void test_Return_NewObjectInGraveyard() {
        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the
        // battlefield under its owner's control."
        addCard(Zone.HAND, playerA, "Demonic Gifts"); // {1}{B}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Disfigure", 2); // {B}, -2/-2
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B}, instant, return creature card from graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2 + 1 + 4 + 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Gifts", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // dies with the gained ability, then returns and dies again as a new object before the first trigger resolves
        // The reanimating effect of Demonic Gifts works only once. Once the creature dies and returns to the
        // battlefield, it's a new object with no relation to the creature it was. If that new creature dies, it won't
        // come back.
        // (2021-02-05)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Grizzly Bears", "dies, return", StackClause.WHILE_ON_STACK);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // returned by Mannequin, the first trigger still on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disfigure", "Grizzly Bears", "dies, return", StackClause.WHILE_ON_STACK);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
