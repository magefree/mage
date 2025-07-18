
package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TropicalStorm extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("blue creature");

    static {
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public TropicalStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Tropical Storm deals X damage to each creature with flying and 1 additional damage to each blue creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter2).setText("and 1 additional damage to each blue creature"));

    }

    private TropicalStorm(final TropicalStorm card) {
        super(card);
    }

    @Override
    public TropicalStorm copy() {
        return new TropicalStorm(this);
    }
}
