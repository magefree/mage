package org.mage.test.cards.single.lci;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class EatenByPiranhasTest extends CardTestPlayerBase {

    /*
    Eaten by Piranhas
    {1}{U}
    Enchantment — Aura

    Flash (You may cast this spell any time you could cast an instant.)

    Enchant creature

    Enchanted creature loses all abilities and is a black Skeleton creature with base power and toughness 1/1. (It loses all other colors, card types, and creature types.)
     */
    private static final String eatenByPiranhas = "Eaten by Piranhas";

    @Test
    public void testEatenByPiranhas() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerB, eatenByPiranhas);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, eatenByPiranhas);
        addTarget(playerB, "Balduvian Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType("Balduvian Bears", CardType.CREATURE, SubType.SKELETON);
        assertPowerToughness(playerA, "Balduvian Bears", 1, 1);
        assertColor(playerA, "Balduvian Bears", ObjectColor.BLACK, true);
    }
}
