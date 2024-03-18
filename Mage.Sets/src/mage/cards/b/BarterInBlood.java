package mage.cards.b;

import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class BarterInBlood extends CardImpl {

    public BarterInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Each player sacrifices two creatures.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private BarterInBlood(final BarterInBlood card) {
        super(card);
    }

    @Override
    public BarterInBlood copy() {
        return new BarterInBlood(this);
    }
}
