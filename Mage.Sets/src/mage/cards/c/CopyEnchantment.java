
package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class CopyEnchantment extends CardImpl {

    public CopyEnchantment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT), true));
    }

    private CopyEnchantment(final CopyEnchantment card) {
        super(card);
    }

    @Override
    public CopyEnchantment copy() {
        return new CopyEnchantment(this);
    }
}
