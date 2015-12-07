package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OmniscienceTest extends CardTestPlayerBase {

    @Test
    public void testSpellNoCost() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        addCard(Zone.HAND, playerA, "Gray Ogre", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gray Ogre");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        //Gray Ogre is cast because it is free
        assertPermanentCount(playerA, "Gray Ogre", 1);
    }

    @Test
    public void testSpellHasCostIfCastFromGraveyard() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Haakon, Stromgald Scourge", 1);

        addCard(Zone.GRAVEYARD, playerA, "Knight of the White Orchid", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knight of the White Orchid");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        //Knight of the White Orchid was not cast due to lack of mana
        assertPermanentCount(playerA, "Knight of the White Orchid", 0);
    }

    /**
     * If you cast a card with monocolored hybrid mana with Omniscience's
     * alternate casting cost, you will be asked to pay 1 colorless mana per
     * monocolored hybrid mana in its cost. For example, while casting Beseech
     * the Queen, you are asked to pay {1}{1}{1}.
     */
    @Test
    public void testMonocoloredHybridMana() {
        // You may cast nonland cards from your hand without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience", 1);

        // ({2B} can be paid with any two mana or with {B}. This card's converted mana cost is 6.)
        // Search your library for a card with converted mana cost less than or equal to the number of lands you control, reveal it, and put it into your hand. Then shuffle your library.
        addCard(Zone.HAND, playerA, "Beseech the Queen", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Beseech the Queen");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Beseech the Queen is cast because it is free
        assertGraveyardCount(playerA, "Beseech the Queen", 1);
    }
}
