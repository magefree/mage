package mage.cards.c;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.util.functions.CardTypeCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CopyLand extends CardImpl {

    public CopyLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // You may have Copy Land enter the battlefield as a copy of any land on the battlefield, except it's an enchantment in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_LAND, new CardTypeCopyApplier(CardType.ENCHANTMENT)
        ).setText("as a copy of any land on the battlefield, except it's an enchantment in addition to its other types"), true));
    }

    private CopyLand(final CopyLand card) {
        super(card);
    }

    @Override
    public CopyLand copy() {
        return new CopyLand(this);
    }
}
