package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class LightningStormTest extends CardTestPlayerBase {

    /**
     * So, this just happened to me. My opponent cast Lightning Storm and while
     * it was on the stack I couldn't use the ability despite having land in
     * hand which isn't something I've had an issue with before.
     * <p>
     * My opponent had a Leyline of Sanctity in play, so perhaps that was
     * causing the issue somehow? Does anyone want to try and replicate it?
     */
    @Test
    public void ActivateByBothPlayersTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Lightning Storm deals X damage to any target, where X is 3 plus the number of charge counters on it.
        // Discard a land card: Put two charge counters on Lightning Storm. You may choose a new target for it. Any player may activate this ability but only if Lightning Storm is on the stack.
        addCard(Zone.HAND, playerA, "Lightning Storm"); // {1}{R}{R}

        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.HAND, playerB, "Mountain");

        // A activate
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Storm", playerB);

        // B discard and re-target
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Discard");
        setChoice(playerB, true); // change target
        addTarget(playerB, playerA); // new target
        setChoice(playerB, "Mountain"); // discard cost

        // A discard and re-target
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, "Mountain");
        setChoice(playerA, true);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Storm", 1);
        assertGraveyardCount(playerB, "Mountain", 1);
        assertGraveyardCount(playerA, "Mountain", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3 - 2 - 2);
    }

}
