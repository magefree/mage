package org.mage.test.cards.abilities.other;

import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.mana.ManaOptions;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

public class GiveManaAbilityAndCastSourceAbilityTest extends CardTestPlayerBase {

    @Test
    public void testDistinctSymbolsAreAlternatives() {
        setStrictChooseMode(true);
        addCustomCardWithAbility("Mana helper", playerA, new GiveManaAbilityAndCastSourceAbility("UBR"), null, null, "", Zone.HAND);
        addCustomCardWithAbility("Target land", playerA, null, null, CardType.LAND, "", Zone.BATTLEFIELD);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Exile {this} from your hand");
        addTarget(playerA, "Target land");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals(3, manaOptions.size());
        assertManaOptions("{U}", manaOptions);
        assertManaOptions("{B}", manaOptions);
        assertManaOptions("{R}", manaOptions);
    }

    @Test
    public void testRepeatedSymbolsProduceMultipleMana() {
        setStrictChooseMode(true);
        addCustomCardWithAbility("Mana helper", playerA, new GiveManaAbilityAndCastSourceAbility("CC", 3), null, null, "", Zone.HAND);
        addCustomCardWithAbility("Target land", playerA, null, null, CardType.LAND, "", Zone.BATTLEFIELD);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}, Exile {this} from your hand");
        addTarget(playerA, "Target land");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals(1, manaOptions.size());
        assertManaOptions("{C}{C}", manaOptions);
    }
}
