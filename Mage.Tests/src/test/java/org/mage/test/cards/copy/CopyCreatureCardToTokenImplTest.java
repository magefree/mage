/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CopyCreatureCardToTokenImplTest extends CardTestPlayerBase {

    /**
     * Unesh, Criosphinx Sovereign did not have his ETB effect trigger when he
     * had a token copy of himself made through casting Hour of Eternity. I
     * think there was another creature too that didn't get the ETB effect
     * either.
     */
    @Test
    public void testTokenTriggeresETBEffect() {
        // Flying
        // Sphinx spells you cast cost {2} less to cast.
        // Whenever Unesh, Criosphinx Sovereign or another Sphinx enters the battlefield
        // under your control, reveal the top four cards of your library. An opponent seperates
        // those cards into two piles. Put one pile into your hand and the other into your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Unesh, Criosphinx Sovereign", 1); // Sphinx 4/4

        // Exile X target creature cards from your graveyard. For each card exiled this way,
        // create a token that's a copy of that card, except it's a 4/4 black Zombie.
        addCard(Zone.HAND, playerA, "Hour of Eternity"); // Sorcery {X}{X}{U}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hour of Eternity", "Unesh, Criosphinx Sovereign");
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Hour of Eternity", 1);
        assertPermanentCount(playerA, "Unesh, Criosphinx Sovereign", 1);
        assertGraveyardCount(playerA, "Unesh, Criosphinx Sovereign", 0);
        assertExileCount(playerA, "Unesh, Criosphinx Sovereign", 1);

        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, 2);
    }

}
