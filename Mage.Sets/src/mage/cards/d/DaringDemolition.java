
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class DaringDemolition extends CardImpl {

    private final static FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new SubtypePredicate(SubType.VEHICLE)));
    }

    public DaringDemolition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy target creature or Vehicle.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public DaringDemolition(final DaringDemolition card) {
        super(card);
    }

    @Override
    public DaringDemolition copy() {
        return new DaringDemolition(this);
    }
}
