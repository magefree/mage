package org.mage.test.cards.single._5ed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnHavvaConstableTest extends CardTestPlayerBase {

    /*
    An-Havva Constable
    {1}{G}{G}
    Creature — Human
    An-Havva Constable’s toughness is equal to 1 plus the number of green creatures on the battlefield.
    2/1+*
     */
    private static final String constable = "An-Havva Constable";
    /*
    Bear Cub
    {1}{G}
    Creature — Bear
    2/2
     */
    private static final String cub = "Bear Cub";
    @Test
    public void testAnHavva() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, constable);
        addCard(Zone.HAND, playerA, constable);
        addCard(Zone.BATTLEFIELD, playerA, cub, 2);
        addCard(Zone.BATTLEFIELD, playerB, cub, 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        checkPT("An-Havva Constable has toughness 7", 1, PhaseStep.PRECOMBAT_MAIN, playerA, constable, 2, 1 + 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", cub);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("An-Havva Constable has toughness 6", 1, PhaseStep.PRECOMBAT_MAIN, playerA, constable, 2, 1 + 5);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        playerA.getHand().getCards(currentGame).stream()
                .filter(card -> card.getName().equals(constable))
                .findFirst().
                ifPresent(card -> Assert.assertEquals(6, card.getToughness().getValue()));

    }
}
