/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.oneshot.library;

import java.util.ArrayList;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PutToLibraryTest extends CardTestPlayerBase {

    @Test
    public void testPutSecondFromTop() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Put target creature into its owner's library second from the top. Its controller gains 3 life.
        addCard(Zone.HAND, playerA, "Oust"); // Sorcery {W}

        addCard(Zone.BATTLEFIELD, playerB, "Dread Wanderer");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oust", "Dread Wanderer");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Oust", 1);
        assertLibraryCount(playerB, "Dread Wanderer", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 23);

        ArrayList<Card> cardArray = new ArrayList<>(playerB.getLibrary().getCards(currentGame));
        Assert.assertTrue("Library has no cards but should have", cardArray.size() > 1);
        Card secondCard = cardArray.get(1);
        Assert.assertTrue("Second card from top should be Dread Wanderer, bnut it isn't",
                secondCard != null && secondCard.getName().equals("Dread Wanderer"));

    }

}
