package org.mage.test.cards.single.usg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class SporogenesisTest extends CardTestPlayerBase {

    @Test
    public void sporogenesisTest() {
        String sporogenesis = "Sporogenesis";
        // At the beginning of your upkeep, you may put a fungus counter on target nontoken creature.
        // Whenever a creature with a fungus counter on it dies, create a 1/1 green Saproling creature token for each fungus counter on that creature.
        // When Sporogenesis leaves the battlefield, remove all fungus counters from all creatures.

        String warleader = "Skophos Warleader";
        // {R}, Sacrifice another creature or an enchantment: Skophos Warleader gets +1/+0 and gains menace until end of turn.

        String vampire = "Barony Vampire";
        String ghoul = "Warpath Ghoul";
        String douser = "Douser of Lights";

        addCard(Zone.BATTLEFIELD, playerA, sporogenesis);
        addCard(Zone.BATTLEFIELD, playerA, warleader);
        addCard(Zone.BATTLEFIELD, playerA, vampire);
        addCard(Zone.BATTLEFIELD, playerA, ghoul);
        addCard(Zone.BATTLEFIELD, playerA, douser);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // turn 1 - upkeep does happen with all on battlefield
        setChoice(playerA, true);
        addTarget(playerA, warleader); // fungus counter
        checkPermanentCounters("no fungus", 1, PhaseStep.END_TURN, playerA, warleader, CounterType.FUNGUS, 1);

        // turn 3
        setChoice(playerA, true);
        addTarget(playerA, warleader); // second fungus counter
        checkPermanentCounters("fungus", 3, PhaseStep.END_TURN, playerA, warleader, CounterType.FUNGUS, 2);

        // turn 5
        setChoice(playerA, true);
        addTarget(playerA, vampire); // fungus counter
        checkPermanentCounters("fungus", 5, PhaseStep.END_TURN, playerA, vampire, CounterType.FUNGUS, 1);

        // turn 7
        setChoice(playerA, true);
        addTarget(playerA, ghoul); // fungus counter
        checkPermanentCounters("fungus", 7, PhaseStep.END_TURN, playerA, ghoul, CounterType.FUNGUS, 1);

        // turn 9
        setChoice(playerA, true);
        addTarget(playerA, ghoul); // second fungus counter
        checkPermanentCounters("fungus", 9, PhaseStep.PRECOMBAT_MAIN, playerA, ghoul, CounterType.FUNGUS, 2);
        activateAbility(9, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, Sacrifice");
        setChoice(playerA, ghoul);
        checkPT("boost", 9, PhaseStep.END_TURN, playerA, warleader, 5, 5);

        // turn 11
        setChoice(playerA, true);
        addTarget(playerA, douser); // fungus counter
        checkPermanentCounters("fungus", 11, PhaseStep.END_TURN, playerA, douser, CounterType.FUNGUS, 1);

        // turn 12
        activateAbility(12, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R}, Sacrifice");
        setChoice(playerA, sporogenesis);
        checkPermanentCounters("fungus", 12, PhaseStep.END_TURN, playerA, warleader, CounterType.FUNGUS, 0);
        checkPermanentCounters("fungus", 12, PhaseStep.END_TURN, playerA, vampire, CounterType.FUNGUS, 0);
        checkPermanentCounters("fungus", 12, PhaseStep.END_TURN, playerA, douser, CounterType.FUNGUS, 0);
        checkPT("boost", 12, PhaseStep.END_TURN, playerA, warleader, 5, 5);

        setStrictChooseMode(true);
        setStopAt(13, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, ghoul, 1);
        assertGraveyardCount(playerA, sporogenesis, 1);
    }

}
