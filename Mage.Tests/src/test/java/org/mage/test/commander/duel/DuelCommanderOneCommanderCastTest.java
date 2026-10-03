package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameCommanderImpl;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * Duel Commander: a player can cast only one of their commanders from the command zone each game
 * (the first one cast). Casting the other one from any other zone is still allowed.
 */
public class DuelCommanderOneCommanderCastTest extends CardTestCommanderDuelBase {

    private void enableDuelCommanderRule() {
        ((GameCommanderImpl) currentGame).setCastOnlyOneCommanderPerGame(true);
    }

    private void prepareCommandersAndLands() {
        // Partner (You can have two commanders if both have partner.)
        addCard(Zone.COMMAND, playerA, "Thrasios, Triton Hero"); // Creature {G}{U} 1/3
        addCard(Zone.COMMAND, playerA, "Ishai, Ojutai Dragonspeaker"); // Creature {2}{W}{U} 1/1
        addCard(Zone.COMMAND, playerB, "Daxos of Meletis");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
    }

    @Test
    public void testCanCastAnyCommanderAtFirst() {
        prepareCommandersAndLands();
        enableDuelCommanderRule();

        checkPlayableAbility("first is playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Thrasios", true);
        checkPlayableAbility("second is playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ishai", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testCantCastSecondCommander() {
        prepareCommandersAndLands();
        enableDuelCommanderRule();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");
        checkPlayableAbility("second is locked", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ishai", false);
        checkPlayableAbility("still locked next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Ishai", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thrasios, Triton Hero", 1);
        assertCommandZoneCount(playerA, "Ishai, Ojutai Dragonspeaker", 1);
    }

    @Test
    public void testCanRecastFirstCommander() {
        prepareCommandersAndLands();
        enableDuelCommanderRule();

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Thrasios, Triton Hero");
        setChoice(playerA, true); // move commander to command zone

        // commander tax makes it {2}{G}{U} now
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");
        checkPlayableAbility("second is still locked", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Ishai", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thrasios, Triton Hero", 1);
        assertCommandZoneCount(playerA, "Ishai, Ojutai Dragonspeaker", 1);
    }

    @Test
    public void testNormalCommanderCanCastBothPartners() {
        prepareCommandersAndLands();
        // no Duel Commander rule here, both commanders can be cast

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thrasios, Triton Hero");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ishai, Ojutai Dragonspeaker");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thrasios, Triton Hero", 1);
        assertPermanentCount(playerA, "Ishai, Ojutai Dragonspeaker", 1);
    }
}
