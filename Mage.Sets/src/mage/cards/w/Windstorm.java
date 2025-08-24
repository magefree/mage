
package mage.cards.w;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class Windstorm extends CardImpl {

    public Windstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        this.getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private Windstorm(final Windstorm card) {
        super(card);
    }

    @Override
    public Windstorm copy() {
        return new Windstorm(this);
    }
}
