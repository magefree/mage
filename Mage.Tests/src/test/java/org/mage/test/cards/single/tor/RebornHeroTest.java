package org.mage.test.cards.single.tor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class RebornHeroTest extends CardTestPlayerBase {

    @Test
    public void test_ReturnUnderYourControl() {
        // Threshold - As long as there are seven or more cards in your graveyard, this creature has "When this
        // creature dies, you may pay {W}{W}. If you do, return this card to the battlefield under your control."
        addCard(Zone.BATTLEFIELD, playerB, "Reborn Hero"); // 2/2
        addCard(Zone.GRAVEYARD, playerA, "Forest", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerA, "Act of Treason"); // {2}{R}, gain control
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Disfigure"); // {B}, -2/-2
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // dies under your control - returns under your control
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Reborn Hero");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disfigure", "Reborn Hero");
        setChoice(playerA, true); // pay {W}{W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Reborn Hero", 1);
        assertPermanentCount(playerB, "Reborn Hero", 0);
    }

    @Test
    public void test_Return_NewObjectInGraveyard() {
        // Threshold - As long as there are seven or more cards in your graveyard, this creature has "When this
        // creature dies, you may pay {W}{W}. If you do, return this card to the battlefield under your control."
        addCard(Zone.BATTLEFIELD, playerA, "Reborn Hero"); // 2/2
        addCard(Zone.GRAVEYARD, playerA, "Forest", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.HAND, playerA, "Disfigure", 2); // {B}, -2/-2
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B}, instant, return creature card from graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 4 + 1);

        // dies, then returns and dies again as a new object before the first trigger resolves
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 1 + 4 + 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disfigure", "Reborn Hero");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Reborn Hero", "you may pay", StackClause.WHILE_ON_STACK);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // returned by Mannequin, the first trigger still on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Disfigure", "Reborn Hero", "you may pay", StackClause.WHILE_ON_STACK);
        setChoice(playerA, false); // the new object's own trigger - don't pay
        setChoice(playerA, true); // the first trigger - pay {W}{W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Reborn Hero", 0);
        assertGraveyardCount(playerA, "Reborn Hero", 1);
    }
}
