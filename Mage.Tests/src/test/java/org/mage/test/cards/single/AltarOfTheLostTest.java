package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class AltarOfTheLostTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        // Altar of the Lost enters the battlefield tapped.
        // {tap}: Add two mana in any combination of colors to your mana pool. Spend this mana only to cast spells with flashback from a graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        // Put two 1/1 white Spirit creature tokens with flying onto the battlefield.
        // Flashback {1}{B}
        addCard(Zone.GRAVEYARD, playerA, "Lingering Souls");

        setChoice(playerA, "Black");
        setChoice(playerA, "Black");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback {1}{B}");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 2);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Altar of the Lost");
        addCard(Zone.HAND, playerA, "Lingering Souls");

        setChoice(playerA, "Black");
        setChoice(playerA, "Black");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lingering Souls");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Spirit", 0);
    }

}
