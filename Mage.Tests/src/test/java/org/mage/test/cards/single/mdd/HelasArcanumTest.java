package org.mage.test.cards.single.mdd;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HelasArcanumTest extends CardTestPlayerBase {

    @Test
    public void test_BoostAndDeathtouch() {
        // {P} - Target creature gets +1/+0 and gains deathtouch until end of turn.
        addCard(Zone.HAND, playerA, "Hela's Arcanum"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hela's Arcanum");
        setModeChoice(playerA, "1"); // +1/+0 and deathtouch
        setModeChoice(playerA, TestPlayer.MODE_SKIP); // up to five - no more modes
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2);
        assertAbility(playerA, "Grizzly Bears", DeathtouchAbility.getInstance(), true);
    }

    @Test
    public void test_ReturnAndDestroyEach() {
        // {P}{P} - Until end of turn, target creature gains "When this creature dies, return it to the battlefield
        // tapped under its owner's control."
        // {P}{P}{P} - Destroy each non-Zombie creature with power 3 or less.
        addCard(Zone.HAND, playerA, "Hela's Arcanum"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse"); // zombie 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm"); // 6/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hela's Arcanum");
        setModeChoice(playerA, "2"); // return on dies
        addTarget(playerA, "Grizzly Bears");
        setModeChoice(playerA, "3"); // destroy each

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // returned
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTapped("Grizzly Bears", true);
        // destroyed
        assertGraveyardCount(playerB, "Hill Giant", 1);
        // keep
        assertPermanentCount(playerB, "Walking Corpse", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    @Test
    public void test_Return_NewObjectInGraveyard() {
        // {P}{P} - Until end of turn, target creature gains "When this creature dies, return it to the battlefield
        // tapped under its owner's control."
        addCard(Zone.HAND, playerA, "Hela's Arcanum"); // {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.HAND, playerA, "Disfigure", 2); // {B}, -2/-2
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B}, instant, return creature card from graveyard
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 4 + 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hela's Arcanum");
        setModeChoice(playerA, "2"); // return on dies
        addTarget(playerA, "Grizzly Bears");
        setModeChoice(playerA, TestPlayer.MODE_SKIP); // up to five - no more modes
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // dies with the gained ability, then returns and dies again as a new object before the first trigger resolves
        // If the targeted creature dies and is then returned to the battlefield, it's considered to be a new
        // creature. If that new creature dies, it won't come back a second time.
        // (Supernatural Stamina, 2020-11-10)
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
