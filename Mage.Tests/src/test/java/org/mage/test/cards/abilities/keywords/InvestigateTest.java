package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InvestigateTest extends CardTestPlayerBase {

    @Test
    public void testInvestigatingBothPlayers() {
        String wernog = "Wernog, Rider's Chaplain"; // {W}{B} 1/2
        // When Wernog, Rider’s Chaplain enters or leaves the battlefield, each opponent may investigate.
        // Each opponent who doesn’t loses 1 life.
        // You investigate X times, where X is one plus the number of opponents who investigated this way.
        String absence = "Fateful Absence"; // {1}{W} Instant
        // Destroy target creature or planeswalker. Its controller investigates.
        String clue = "Clue Token";
        String erdwal = "Erdwal Illuminator"; // Whenever you investigate for the first time each turn, investigate an additional time.

        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.HAND, playerA, wernog);
        addCard(Zone.HAND, playerA, absence);
        addCard(Zone.BATTLEFIELD, playerA, erdwal, 2);
        addCard(Zone.BATTLEFIELD, playerB, erdwal);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, wernog);
        setChoice(playerB, false); // opponent chooses not to investigate
        setChoice(playerA, "Whenever you investigate for the "); // order triggers

        checkLife("lost 1 life", 1, PhaseStep.BEGIN_COMBAT, playerB, 19);
        checkPermanentCount("3 clues", 1, PhaseStep.BEGIN_COMBAT, playerA, clue, 3); // one, plus two erdwal triggers
        checkPlayableAbility("clue ability", 1, PhaseStep.BEGIN_COMBAT, playerA, "{2}, Sacrifice ", true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, absence, wernog); // + 1 clue
        setChoice(playerB, true); // opponent chooses to investigate

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertGraveyardCount(playerA, absence, 1);
        assertGraveyardCount(playerA, wernog, 1);
        assertPermanentCount(playerA, clue, 3 + 1 + 2);
        assertPermanentCount(playerB, clue, 1 + 1);

    }

    @Test
    public void testBriarbridgePatrol() {
        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate.
        // At the beginning of each end step, if you sacrificed three or more Clues this turn,
        // you may put a creature card from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1); // 2/4

        attack(1, playerA, "Briarbridge Patrol");
        block(1, playerB, "Pillarfield Ox", "Briarbridge Patrol");

        attack(3, playerA, "Briarbridge Patrol");
        block(3, playerB, "Pillarfield Ox", "Briarbridge Patrol");

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2},");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Briarbridge Patrol", 1);
        assertHandCount(playerA, 2); // 1 from sacrificed Clue and 1 from draw of turn 3
        assertPermanentCount(playerA, "Clue Token", 1);

    }
}
