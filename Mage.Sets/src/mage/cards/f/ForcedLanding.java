package mage.cards.f;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForcedLanding extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ForcedLanding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put target creature with flying on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ForcedLanding(final ForcedLanding card) {
        super(card);
    }

    @Override
    public ForcedLanding copy() {
        return new ForcedLanding(this);
    }
}
