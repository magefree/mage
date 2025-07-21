package mage.cards.g;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GaleForce extends CardImpl {

    public GaleForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        this.getSpellAbility().addEffect(new DamageAllEffect(5, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private GaleForce(final GaleForce card) {
        super(card);
    }

    @Override
    public GaleForce copy() {
        return new GaleForce(this);
    }

}
