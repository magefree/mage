/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * New SOI mechanics change requires the night-side CMC to be equal to the
 * day-side CMC
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class NightSideCMCTest extends CardTestPlayerBase {

    /**
     *
     *
     */
    @Test
    public void insectileAbberationRepealXis1Test() {
        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        addCard(Zone.BATTLEFIELD, playerA, "Delver of Secrets"); // night-side of Delver of Secrets {U}

        addCard(Zone.LIBRARY, playerA, "Lightning Bolt"); // to transform Delver of Secrets
        skipInitShuffling();
        // Instant - {X}{U}
        // Return target nonland permanent with converted mana cost X to its owner's hand. Draw a card.
        addCard(Zone.HAND, playerB, "Repeal");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Repeal");
        setChoice(playerB, "X=1");
        addTarget(playerB, "Insectile Aberration");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Repeal", 1);
        assertPermanentCount(playerA, "Insectile Aberration", 0);
        assertHandCount(playerA, "Delver of Secrets", 1); // day-side of Insectile Abberation returned to hand
    }
}
