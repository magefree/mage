package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen, JayDi85
 */
public class TheCloneSagaTest extends CardTestPlayerBase {

    /**
     * The Clone Saga
     * {3}{U}
     * Enchantment - Saga
     * (As this Saga enters step, add a lore counter. Sacrifice after III.)
     * I -- Surveil 3.
     * II -- When you next cast a creature spell this turn, copy it, except the copy isn't legendary.
     * III -- Choose a card name. Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.
     */
    private static final String theCloneSaga = "The Clone Saga";

    /**
     * Ragavan, Nimble Pilferer
     * {R}
     * Legendary Creature - Monkey Pirate
     * Whenever Ragavan, Nimble Pilferer deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
     * Dash {1}{R}
     * 2/1 
     */
    private static final String ragavanNimblePilferer = "Ragavan, Nimble Pilferer";

    @Test
    public void test_Normal() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, theCloneSaga);
        addCard(Zone.HAND, playerA, ragavanNimblePilferer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // skip surveil 3 on first chapter
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "Mountain", 2); // put order of 3 cards

        // resolve trigger on second chapter
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // prepare attackers, original and copied
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ragavanNimblePilferer);
        setChoice(playerA, "Cast with no alternative cost");

        // third chaper on turn 3
        setChoice(playerA, ragavanNimblePilferer); // choose name
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);

        // attack and trigger the saga effect
        attack(3, playerA, ragavanNimblePilferer);
        attack(3, playerA, ragavanNimblePilferer);
        //Triggered list (total 4):
        //Ability: The Clone Saga [5d7] - TheCloneSagaDelayedTrigger: Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.
        //Ability: Ragavan, Nimble Pilferer [794] - DealsCombatDamageToAPlayerTriggeredAbility: Whenever {this} deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
        //Ability: The Clone Saga [5d7] - TheCloneSagaDelayedTrigger: Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.
        //Ability: Ragavan, Nimble Pilferer [b8f] - DealsCombatDamageToAPlayerTriggeredAbility: Whenever {this} deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
        setChoice(playerA, "Whenever ", 3); // any order of 4 triggers

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertHandCount(playerA, 1 + 1 + 1); // 1 draw + 2 triggers
        assertPermanentCount(playerA, "Treasure Token", 2);
    }
}