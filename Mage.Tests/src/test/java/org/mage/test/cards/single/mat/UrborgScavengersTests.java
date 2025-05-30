package org.mage.test.cards.single.mat;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class UrborgScavengersTests extends CardTestPlayerBase {

    @Test
    public void getsHexproofHasteTest() {

        addCard(Zone.HAND, playerA, "Urborg Scavengers");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Cragplate Baloth");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urborg Scavengers", true);
        addTarget(playerA, "Cragplate Baloth");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        Abilities<Ability> abilities = new AbilitiesImpl<>();
        abilities.add(HasteAbility.getInstance());
        abilities.add(HexproofAbility.getInstance());
        assertAbilities(playerA, "Urborg Scavengers", abilities);

        assertCounterCount(playerA, "Urborg Scavengers", CounterType.P1P1, 1);
    }

}
