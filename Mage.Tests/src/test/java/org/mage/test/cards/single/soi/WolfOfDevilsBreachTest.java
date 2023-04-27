package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 3RR Creature - Elemental Wolf Whenever Wolf of Devil's Breach attacks, you
 * may pay 1R and discard a card. If you do, Wolf of Devil's Breach deals damage
 * to target creature or planeswalker equal to the discarded card's converted
 * mana cost.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class WolfOfDevilsBreachTest extends CardTestPlayerBase {

    @Test
    public void attackChooseToPay() {
        // Whenever Wolf of Devil's Breach attacks, you may pay {1}{R} and discard a card. If you do, Wolf of Devil's Breach deals
        // damage to target creature or planeswalker equal to the discarded card's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Wolf of Devil's Breach", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Bronze Sable", 1); // (2) 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2

        attack(1, playerA, "Wolf of Devil's Breach");
        setChoice(playerA, true);
        setChoice(playerA, "Bronze Sable");
        addTarget(playerA, "Grizzly Bears");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Bronze Sable", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    /**
     *
     */
    @Test
    public void attackDoNotPay() {
        // Whenever Wolf of Devil's Breach attacks, you may pay {1}{R} and discard a card. If you do, Wolf of Devil's Breach deals
        // damage to target creature or planeswalker equal to the discarded card's converted mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Wolf of Devil's Breach", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Bronze Sable", 1); // (2) 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // 2/2

        setStrictChooseMode(true);
        attack(1, playerA, "Wolf of Devil's Breach");
        addTarget(playerA, "Grizzly Bears");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Bronze Sable", 1); // never discarded
        assertGraveyardCount(playerA, "Bronze Sable", 0);
        assertGraveyardCount(playerB, "Grizzly Bears", 0);
    }
}
