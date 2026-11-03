package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class BolgsCompanyTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BolgsCompany Bolg's Company} {B}{R}
     * Creature - Goblin Soldier
     * This creature has haste as long as you control another Goblin.
     * {T}, Sacrifice another Goblin: Add {B}{R}.
     */
    private static final String company = "Bolg's Company";
    private static final String piker = "Goblin Piker";

    @Test
    public void test_ManaAbilityDoesNotUseTheStack() {
        addCard(Zone.BATTLEFIELD, playerA, company);
        addCard(Zone.BATTLEFIELD, playerA, piker);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice another Goblin: Add {B}{R}.");
        setChoice(playerA, piker);
        checkStackSize("no stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkManaPool("black", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "B", 1);
        checkManaPool("red", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "R", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, piker, 1);
        assertTapped(company, true);
    }

    @Test
    public void test_PaysWhileCastingASpell() {
        addCard(Zone.BATTLEFIELD, playerA, company);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Terminate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Terminate", "Grizzly Bears");
        setChoice(playerA, piker);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, piker, 1);
        assertGraveyardCount(playerA, "Terminate", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
