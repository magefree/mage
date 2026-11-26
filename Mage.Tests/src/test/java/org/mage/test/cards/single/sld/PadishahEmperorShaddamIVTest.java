package org.mage.test.cards.single.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PadishahEmperorShaddamIVTest extends CardTestPlayerBase {

    private static final String SHADDAM = "Padishah Emperor Shaddam IV";

    @Test
    public void testCreatureDeathsAndOptionalPayment() {
        // Whenever another nontoken creature dies while you're the monarch, you may pay 1 life. If you do, create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker");
        addCustomEffect_TargetDestroy(playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose", true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkMonarch("controller is monarch", 1, PhaseStep.PRECOMBAT_MAIN, playerA, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Silvercoat Lion");
        setChoice(playerA, true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Goblin Piker");
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertPermanentCount(playerA, "Treasure Token", 2);
    }

    @Test
    public void testOnlyOtherNontokenCreaturesAndOnlyWhileMonarch() {
        // Whenever another nontoken creature dies while you're the monarch, you may pay 1 life. If you do, create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose");
        addCard(Zone.HAND, playerA, "Raise the Alarm");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCustomEffect_TargetDestroy(playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose", true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Soldier Token");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", SHADDAM);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerA, "Treasure Token", 0);
        assertGraveyardCount(playerA, SHADDAM, 1);
        assertPermanentCount(playerA, "Soldier Token", 1);
    }

    @Test
    public void testMonarchOnlyCheckedWhenCreatureDies() {
        // Whenever another nontoken creature dies while you're the monarch, you may pay 1 life. If you do, create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Throne of the High City");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 4);
        addCustomEffect_TargetDestroy(playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose", true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{4}, {T}, Sacrifice",
                TestPlayer.NO_TARGET, "you may pay 1 life", StackClause.WHILE_ON_STACK);
        setChoice(playerA, true);
        checkMonarch("opponent became monarch", 1, PhaseStep.BEGIN_COMBAT, playerA, playerB);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "target destroy", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertLife(playerA, 19);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void testAnotherCreatureDiesWithShaddam() {
        // Whenever another nontoken creature dies while you're the monarch, you may pay 1 life. If you do, create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Thorn of the Black Rose");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCustomEffect_TargetDestroy(playerA, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thorn of the Black Rose", true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", SHADDAM + "^Grizzly Bears");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertGraveyardCount(playerA, SHADDAM, 1);
    }

    @Test
    public void testEndStepChoosesAmongTiedGreatestOpposingCreatures() {
        // At the beginning of your end step, destroy target creature with the greatest power among creatures your opponents control.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.BATTLEFIELD, playerA, "Impervious Greatwurm");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Colossal Dreadmaw");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        addTarget(playerA, "Colossal Dreadmaw");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertGraveyardCount(playerB, "Colossal Dreadmaw", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Impervious Greatwurm", 1);
    }

    @Test
    public void testEndStepRechecksGreatestPowerOnResolution() {
        // At the beginning of your end step, destroy target creature with the greatest power among creatures your opponents control.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Titanic Growth");

        addTarget(playerA, "Craw Wurm");
        castSpell(1, PhaseStep.END_TURN, playerB, "Titanic Growth", "Hill Giant",
            "destroy target creature with the greatest power", StackClause.WHILE_ON_STACK);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertPermanentCount(playerB, "Hill Giant", 1);
    }

    @Test
    public void testUntargetableGreatestCreatureStillCounts() {
        // At the beginning of your end step, destroy target creature with the greatest power among creatures your opponents control.
        addCard(Zone.BATTLEFIELD, playerA, SHADDAM);
        addCard(Zone.BATTLEFIELD, playerB, "Carnage Tyrant");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerB, "Carnage Tyrant", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }
}
