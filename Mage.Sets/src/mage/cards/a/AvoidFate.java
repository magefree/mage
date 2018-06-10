
package mage.cards.a;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.TargetsPermanentPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class AvoidFate extends CardImpl {

    private final static FilterSpell filter = new FilterSpell("instant or Aura spell that targets a permanent you control");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new SubtypePredicate(SubType.AURA)));
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_PERMANENT));
    }

    public AvoidFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Counter target instant or Aura spell that targets a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public AvoidFate(final AvoidFate card) {
        super(card);
    }

    @Override
    public AvoidFate copy() {
        return new AvoidFate(this);
    }
}
