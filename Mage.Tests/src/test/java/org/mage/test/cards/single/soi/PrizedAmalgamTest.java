package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 1UB Creature - Zombie Whenever a creature enters the battlefield, if it
 * entered from your graveyard or you cast it from your graveyard, return Prized
 * Amalgam from your graveyard to the battlefield tapped at the beginning of the
 * next end step.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PrizedAmalgamTest extends CardTestPlayerBase {

    /**
     * Reanimated creature recurs Prized Amalgam
     */
    @Test
    public void testReanimation() {

        addCard(Zone.HAND, playerA, "Reanimate", 1); // {B} Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        addCard(Zone.GRAVEYARD, playerA, "Bronze Sable", 1); // (2) 2/1
        addCard(Zone.GRAVEYARD, playerA, "Prized Amalgam", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Bronze Sable");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18); // loss of 2 from reanimate
        assertGraveyardCount(playerA, "Reanimate", 1);
        assertPermanentCount(playerA, "Bronze Sable", 1);
        assertPermanentCount(playerA, "Prized Amalgam", 1);
        assertTapped("Prized Amalgam", true);
    }

    /**
     * Reported bug - Gravecrawler cast from grave does not trigger Prized
     * Amalgam.
     */
    @Test
    public void testGravecrawlerCastFromGrave() {
        // You may cast Gravecrawler from your graveyard as long as you control a Zombie.
        addCard(Zone.GRAVEYARD, playerA, "Gravecrawler", 1);
        //
        // Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your
        // graveyard, return Prized Amalgam from your graveyard to the battlefield tapped at the beginning of the
        // next end step.
        addCard(Zone.GRAVEYARD, playerA, "Prized Amalgam", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Gnawing Zombie", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gravecrawler");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gravecrawler", 1);
        assertPermanentCount(playerA, "Prized Amalgam", 1);
        assertTapped("Prized Amalgam", true);
    }

    /**
     * Reported bug - creature returned from opponent's graveyard to battlefield
     * by Ojutai's Command incorrectly triggers Prized Amalgam
     */
    @Test
    public void testOpponentReturnsCreatureFromGrave() {

        addCard(Zone.HAND, playerA, "Reanimate", 1);
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant", 1); // {3}{R} 3/3, 4 CMC
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.GRAVEYARD, playerB, "Prized Amalgam", 1); // {1}{U}{B}, 3 CMC

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate");
        addTarget(playerA, "Hill Giant");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 16); // lose 4 life from reanimate 4 CMC by Hill Giant
        assertPermanentCount(playerA, "Hill Giant", 1);
        assertPermanentCount(playerB, "Prized Amalgam", 0); // should not recur
        assertGraveyardCount(playerB, "Prized Amalgam", 1); // stays in grave
    }

    /*
     * Test opponent returning a card from your graveyard to battlefield.
     */
    @Test
    public void testOpponentReturnsCreatureFromYourGrave() {

        addCard(Zone.HAND, playerA, "Necromantic Summons", 1); // Put target creature card from a graveyard onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerB, "Merfolk Looter", 1); // {U} 1/1
        // Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, return Prized Amalgam from your graveyard to the battlefield tapped at the beginning of the next end step.
        addCard(Zone.GRAVEYARD, playerB, "Prized Amalgam", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Necromantic Summons", "Merfolk Looter");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Merfolk Looter", 1);
        assertPermanentCount(playerB, "Prized Amalgam", 1);
        assertGraveyardCount(playerB, "Prized Amalgam", 0);
        assertTapped("Prized Amalgam", true);
    }
}
