package org.mage.test.cards.single.mbc;

import mage.abilities.keyword.LivingWeaponAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Meowcelina
 */
public class EkthiContaminatorPriestTest extends CardTestPlayerBase {

    /*
    Ekthi, Contaminator Priest
    {3}{W}
    Legendary Creature - Phyrexian Cleric
    Other Phyrexians you control get +1/+1.
    Each Equipment you control has living weapon.
    3/3
    */
    private static final String ekthiContaminatorPriest = "Ekthi, Contaminator Priest";
    private static final String swiftfootBoots = "Swiftfoot Boots";
    private static final String phyrexianGem = "Phyrexian Germ Token";
    private static final String wastes = "Wastes";

    @Test
    public void testEkthiContaminatorPriest() {
        addCard(Zone.BATTLEFIELD, playerA, ekthiContaminatorPriest);
        addCard(Zone.BATTLEFIELD, playerA, wastes, 2);
        addCard(Zone.HAND, playerA, swiftfootBoots);
        addCard(Zone.BATTLEFIELD, playerB, swiftfootBoots);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swiftfootBoots, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, phyrexianGem, 1);
        assertAbility(playerA, swiftfootBoots, new LivingWeaponAbility(), true);
        assertAttachedTo(playerA, swiftfootBoots, phyrexianGem, true);
        assertPowerToughness(playerA, phyrexianGem, 1, 1);
        assertAbility(playerB, swiftfootBoots, new LivingWeaponAbility(), false);
    }
}