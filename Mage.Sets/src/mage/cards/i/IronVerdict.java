package mage.cards.i;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronVerdict extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public IronVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Iron Verdict deals 5 damage to target tapped creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Foretell {W}
        this.addAbility(new ForetellAbility(this, "{W}"));
    }

    private IronVerdict(final IronVerdict card) {
        super(card);
    }

    @Override
    public IronVerdict copy() {
        return new IronVerdict(this);
    }
}
