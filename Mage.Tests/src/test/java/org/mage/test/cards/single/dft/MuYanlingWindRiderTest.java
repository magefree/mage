package org.mage.test.cards.single.dft;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class MuYanlingWindRiderTest extends CardTestPlayerBase {

    // When this creature enters, create a 3/2 colorless Vehicle artifact token with crew 1.
    // Vehicles you control have flying.
    // Whenever one or more creatures you control with flying deal combat damage to a player, draw a card.
    private final String muyanling = "Mu Yanling, Wind Rider";

    @Test
    public void testToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, muyanling);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, muyanling, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, muyanling, 1);
        assertPermanentCount(playerA, "Vehicle Token", 1);
        assertAbility(playerA, "Vehicle Token", FlyingAbility.getInstance(), true);
    }

    @Test
    public void testDraw() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, muyanling);
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Ankle Biter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, muyanling, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 1");
        setChoice(playerA, "Memnite");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        attack(3, playerA, "Vehicle Token", playerB);
        attack(3, playerA, muyanling, playerB);
        attack(3, playerA, "Ankle Biter", playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, muyanling, 1);
        assertPermanentCount(playerA, "Vehicle Token", 1);
        assertAbility(playerA, "Vehicle Token", FlyingAbility.getInstance(), true);
        assertHandCount(playerA,  2); // 1 for turn plus 1 for attack
    }

}
