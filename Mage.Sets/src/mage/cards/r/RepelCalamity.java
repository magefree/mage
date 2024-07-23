package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepelCalamity extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with power or toughness 4 or greater");

    static {
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.MORE_THAN, 3),
                new ToughnessPredicate(ComparisonType.MORE_THAN, 3)
        ));
    }

    public RepelCalamity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target creature with power or toughness 4 or greater.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RepelCalamity(final RepelCalamity card) {
        super(card);
    }

    @Override
    public RepelCalamity copy() {
        return new RepelCalamity(this);
    }
}
