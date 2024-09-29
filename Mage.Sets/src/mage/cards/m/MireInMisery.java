package mage.cards.m;

import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MireInMisery extends CardImpl {

    public MireInMisery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Each opponent sacrifices a creature or enchantment.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private MireInMisery(final MireInMisery card) {
        super(card);
    }

    @Override
    public MireInMisery copy() {
        return new MireInMisery(this);
    }
}
