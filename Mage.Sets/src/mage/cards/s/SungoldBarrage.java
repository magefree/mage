package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SungoldBarrage extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public SungoldBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature with toughness 4 or greater.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SungoldBarrage(final SungoldBarrage card) {
        super(card);
    }

    @Override
    public SungoldBarrage copy() {
        return new SungoldBarrage(this);
    }
}
