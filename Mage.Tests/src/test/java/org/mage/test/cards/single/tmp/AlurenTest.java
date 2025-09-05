package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AlurenTest extends CardTestPlayerBase {

    /*
    Aluren
    {2}{G}{G}
    Enchantment
    Any player may cast creature spells with mana value 3 or less without paying their mana costs and as though they had flash.
     */
    private static final String aluren = "Aluren";

    /*
    Bear Cub
    {1}{G}
    Creature — Bear
    2/2
     */
    private static final String cub = "Bear Cub";

    @Test
    public void testAluren() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, aluren);
        addCard(Zone.HAND, playerA, cub);
        addCard(Zone.HAND, playerB, cub);

        castSpell(1, PhaseStep.UPKEEP, playerA, cub);
        setChoice(playerA, "Cast without paying its mana cost");

        castSpell(1, PhaseStep.END_TURN, playerB, cub);
        setChoice(playerB, "Cast without paying its mana cost");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, cub, 1);
        assertPermanentCount(playerB, cub, 1);
    }
}
