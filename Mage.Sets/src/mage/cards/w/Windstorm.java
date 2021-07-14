
package mage.cards.w;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author North
 */
public final class Windstorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Windstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{G}");


        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));
    }

    private Windstorm(final Windstorm card) {
        super(card);
    }

    @Override
    public Windstorm copy() {
        return new Windstorm(this);
    }
}
