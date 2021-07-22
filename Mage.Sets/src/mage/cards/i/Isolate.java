package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class Isolate extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with mana value 1");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
    }

    public Isolate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target permanent with converted mana cost 1.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Isolate(final Isolate card) {
        super(card);
    }

    @Override
    public Isolate copy() {
        return new Isolate(this);
    }
}
