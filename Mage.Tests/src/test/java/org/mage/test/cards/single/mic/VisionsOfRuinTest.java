package org.mage.test.cards.single.mic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

public class VisionsOfRuinTest extends CardTestCommanderDuelBase {

    private static final String visions = "Visions of Ruin";

    @Test
    public void test_FlashbackReducedByCommanderInCommandZone() {
        // Each opponent sacrifices an artifact. For each artifact sacrificed this way, you create a Treasure token.
        // Flashback {8}{R}{R}. This spell costs {X} less to cast this way, where X is the greatest mana value
        // of a commander you own on the battlefield or in the command zone.
        addCard(Zone.GRAVEYARD, playerA, visions);
        addCard(Zone.COMMAND, playerA, "Kylox, Visionary Inventor"); // {5}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // {8}{R}{R} - 7
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        checkPlayableAbility("reduced", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerB, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
        assertExileCount(playerA, visions, 1);
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void test_FlashbackReducedByCommanderOnBattlefield() {
        addCard(Zone.GRAVEYARD, playerA, visions);
        addCard(Zone.COMMAND, playerA, "Kylox, Visionary Inventor"); // {5}{U}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 7 + 3);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kylox, Visionary Inventor");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("commander on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kylox, Visionary Inventor", 1);

        checkPlayableAbility("reduced", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerB, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertExileCount(playerA, visions, 1);
        assertTappedCount("Volcanic Island", true, 7 + 3);
    }
}
