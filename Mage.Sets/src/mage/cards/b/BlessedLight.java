package mage.cards.b;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlessedLight extends CardImpl {

    public BlessedLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Exile target creature or enchantment.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private BlessedLight(final BlessedLight card) {
        super(card);
    }

    @Override
    public BlessedLight copy() {
        return new BlessedLight(this);
    }
}
