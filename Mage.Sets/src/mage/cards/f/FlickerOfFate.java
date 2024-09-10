package mage.cards.f;

import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlickerOfFate extends CardImpl {

    public FlickerOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature or enchantment, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private FlickerOfFate(final FlickerOfFate card) {
        super(card);
    }

    @Override
    public FlickerOfFate copy() {
        return new FlickerOfFate(this);
    }
}
