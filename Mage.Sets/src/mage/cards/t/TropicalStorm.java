
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class TropicalStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("blue creature");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public TropicalStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Tropical Storm deals X damage to each creature with flying and 1 additional damage to each blue creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));
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
