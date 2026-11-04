package org.mage.test.cards.single.mic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * 118.9d
 * If an alternative cost is being paid to cast a spell, any additional costs, cost increases, and cost reductions that
 * affect that spell are applied to that alternative cost. (See rule 601.2f.)
 *
 * @author DarioBarrabinoDev
 */
public class VisionsOfRuinTest extends CardTestCommanderDuelBase {

    @Test
    public void test_FlashbackReducedByCommanderInCommandZone() {
        // Each opponent sacrifices an artifact. For each artifact sacrificed this way, you create a Treasure token.
        // Flashback {8}{R}{R}. This spell costs {X} less to cast this way, where X is the greatest mana value
        // of a commander you own on the battlefield or in the command zone.
        addCard(Zone.GRAVEYARD, playerA, "Visions of Ruin"); // {3}{R}
        addCard(Zone.COMMAND, playerA, "Kylox, Visionary Inventor"); // commander, {5}{U}{R}, mana value 7
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3); // {8}{R}{R} - 7
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // artifact to sacrifice

        checkPlayableAbility("reduced", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerB, "Ornithopter"); // sacrificed artifact

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertPermanentCount(playerA, "Treasure Token", 1); // for the sacrificed artifact
        assertExileCount(playerA, "Visions of Ruin", 1); // flashback exiles it
        assertTappedCount("Mountain", true, 3);
    }

    @Test
    public void test_FlashbackReducedByCommanderOnBattlefield() {
        // Each opponent sacrifices an artifact. For each artifact sacrificed this way, you create a Treasure token.
        // Flashback {8}{R}{R}. This spell costs {X} less to cast this way, where X is the greatest mana value
        // of a commander you own on the battlefield or in the command zone.
        addCard(Zone.GRAVEYARD, playerA, "Visions of Ruin"); // {3}{R}
        addCard(Zone.COMMAND, playerA, "Kylox, Visionary Inventor"); // commander, {5}{U}{R}, mana value 7
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 7 + 3); // 7 for Kylox, {8}{R}{R} - 7 for flashback
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // artifact to sacrifice

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kylox, Visionary Inventor");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("commander on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kylox, Visionary Inventor", 1);

        checkPlayableAbility("reduced", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback", true);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");
        setChoice(playerB, "Ornithopter"); // sacrificed artifact

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ornithopter", 1);
        assertExileCount(playerA, "Visions of Ruin", 1); // flashback exiles it
        assertTappedCount("Volcanic Island", true, 7 + 3);
    }
}
