package org.mage.test.cards.single.spm;

import mage.abilities.keyword.MenaceAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class AlienSymbiosisTest extends CardTestPlayerBase {

    /*
    Alien Symbiosis
    {1}{B}
    Enchantment - Aura
    Enchant creature
    Enchanted creature gets +1/+1, has menace, and is a Symbiote in addition to its other types.
    You may cast this card from your graveyard by discarding a card in addition to paying its other costs.
    */
    private static final String alienSymbiosis = "Alien Symbiosis";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear

    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testAlienSymbiosisCastFromGrave() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, alienSymbiosis);
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alienSymbiosis, bearCub);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bearCub, 3, 3);
        assertAbilityCount(playerA, bearCub, MenaceAbility.class, 1);
        assertSubtype(bearCub, SubType.SYMBIOTE);
    }
}