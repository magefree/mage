

package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetSpell;

/**
 * @author Loki
 */
public final class HisokasDefiance extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Spirit or Arcane spell");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.SPIRIT), new SubtypePredicate(SubType.ARCANE)));
    }

    public HisokasDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public HisokasDefiance(final HisokasDefiance card) {
        super(card);
    }

    @Override
    public HisokasDefiance copy() {
        return new HisokasDefiance(this);
    }

}
