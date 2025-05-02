package mage.cards.d;

import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author ayratn
 */
public final class DissipationField extends CardImpl {

    public DissipationField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Whenever a permanent deals damage to you, return it to its owner's hand.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT,
                new ReturnToHandTargetEffect().setText("return it to its owner's hand"), false));
    }

    private DissipationField(final DissipationField card) {
        super(card);
    }

    @Override
    public DissipationField copy() {
        return new DissipationField(this);
    }
}
