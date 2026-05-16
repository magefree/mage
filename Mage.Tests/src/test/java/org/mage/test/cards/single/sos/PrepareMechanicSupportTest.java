package org.mage.test.cards.single.sos;

import mage.abilities.SpellAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.cards.PrepareSpellCard;
import mage.cards.e.EiganjoDynastorian;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PrepareMechanicSupportTest extends CardTestPlayerBase {

    @Test
    public void testCopiedPrepareCardKeepsExileSpellAbility() {
        PrepareCard card = new EiganjoDynastorian(
                playerA.getId(), new CardSetInfo("Eiganjo Dynastorian", "SOC", "13", Rarity.RARE)
        );
        PrepareCard copy = (PrepareCard) card.copy();

        Assert.assertTrue(copy.getSpellCard() instanceof PrepareSpellCard);
        SpellAbility ability = copy.getSpellCard().getSpellAbility();
        Assert.assertNotNull(ability);
        Assert.assertEquals(SpellAbilityType.PREPARE_SPELL, ability.getSpellAbilityType());
        Assert.assertEquals(Zone.EXILED, ability.getZone());
        Assert.assertTrue(ability.getRule().contains("Return all enchantment cards"));
    }
}
