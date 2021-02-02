
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author Loki
 */
public final class NeedleStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public NeedleStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        this.getSpellAbility().addEffect(new DamageAllEffect(4, filter));
    }

    private NeedleStorm(final NeedleStorm card) {
        super(card);
    }

    @Override
    public NeedleStorm copy() {
        return new NeedleStorm(this);
    }
}
