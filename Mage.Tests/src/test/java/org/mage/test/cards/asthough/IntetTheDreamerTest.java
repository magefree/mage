package org.mage.test.cards.asthough;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class IntetTheDreamerTest extends CardTestPlayerBase {

    @Test
    public void test_SplitCard() {
        skipInitShuffling();

        // Whenever Intet, the Dreamer deals combat damage to a player, you may pay {2}{U}. If you do, exile
        // the top card of your library face down.
        // You may play that card without paying its mana cost for as long as Intet remains on the battlefield.
        // You may look at that card for as long as it remains exiled.
        addCard(Zone.BATTLEFIELD, playerA, "Intet, the Dreamer"); // 6/6
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Wax {G}
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.LIBRARY, playerA, "Wax // Wane", 1); // instant, split card

        // attack and trigger an exile effect
        attack(1, playerA, "Intet, the Dreamer");
        setChoice(playerA, true); // pay and exile face down
        waitStackResolved(1, PhaseStep.COMBAT_DAMAGE);
        checkExileCount("after exile", 1, PhaseStep.COMBAT_DAMAGE, playerA, "Wax // Wane", 1); // face down for owner looks like a normal card
        runCode("after exile", 1, PhaseStep.COMBAT_DAMAGE, playerA, (info, player, game) -> {
            Assert.assertEquals("must have 1 card in exile", 1, game.getExile().getAllCards(game).size());
            Card card = game.getExile().getAllCards(game).get(0);
            Assert.assertTrue("must be face down in exile", card.isFaceDown(game));
        });

        // free cast and boost intet
        castSpell(1, PhaseStep.COMBAT_DAMAGE, playerA, "Wax", "Intet, the Dreamer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Intet, the Dreamer", 6 + 2, 6 + 2); // boost from Wax
    }
}
