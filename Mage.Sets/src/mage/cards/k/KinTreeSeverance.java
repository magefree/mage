package mage.cards.k;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinTreeSeverance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public KinTreeSeverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2/W}{2/B}{2/G}");

        // Exile target permanent with mana value 3 or greater.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private KinTreeSeverance(final KinTreeSeverance card) {
        super(card);
    }

    @Override
    public KinTreeSeverance copy() {
        return new KinTreeSeverance(this);
    }
}
