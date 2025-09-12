package mage.cards.c;

import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ClipWings extends CardImpl {

    public ClipWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Each opponent sacrifices a creature with flying.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_CREATURE_FLYING)
                .setText("each opponent sacrifices a creature of their choice with flying"));
    }

    private ClipWings(final ClipWings card) {
        super(card);
    }

    @Override
    public ClipWings copy() {
        return new ClipWings(this);
    }
}
