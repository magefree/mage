package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OppositionAgentTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OppositionAgent Opposition Agent} {2}{B}
     * Creature — Human Rogue
     * Flash
     * You control your opponents while they’re searching their libraries.
     * While an opponent is searching their library, they exile each card they find. You may play those cards for as long as they remain exiled, and you may spend mana as though it were mana of any color to cast them.
     * 3/2
     */
    private static final String agent = "Opposition Agent";

    @Test
    public void test_ReplacementEffect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, agent);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Mystical Tutor"); // Search for instant/sorcery then put it on top
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.UPKEEP, playerB, "Mystical Tutor");
        setChoice(playerA, true); // yes to message "you control opponent, Continue - Wait"
        addTarget(playerA, "Lightning Bolt"); // playerA makes the choice for tutor

        checkExileCount("Bolt in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertTappedCount("Island", true, 1 + 1);
        assertGraveyardCount(playerB, 2);
    }

    // Bug: Opposition agent let the opponent's play the card
    @Test
    public void test_DonateAgentAfter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, agent);
        addCard(Zone.HAND, playerA, "Donate");
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Mystical Tutor"); // Search for instant/sorcery then put it on top
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);

        castSpell(1, PhaseStep.UPKEEP, playerB, "Mystical Tutor");
        setChoice(playerA, true); // yes to message "you control opponent, Continue - Wait"
        addTarget(playerA, "Lightning Bolt"); // playerA makes the choice for tutor

        checkExileCount("Bolt in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Donate", playerB);
        addTarget(playerA, agent); // second target for Donate

        checkPermanentCount("playerB controls Opposition Agent", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, agent, 1);
        checkPlayableAbility("playerA can still play Bolt", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Lightning Bolt", true);
        checkPlayableAbility("playerB can not play Bolt", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cast Lightning Bolt", false);
        castSpell(1, PhaseStep.END_TURN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerB, 20 - 3);
        assertTappedCount("Island", true, 1);
        assertTappedCount("Tropical Island", true, 3 + 1);
        assertGraveyardCount(playerB, 2);
    }
}
