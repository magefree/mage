package mage.cards.y;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class YourFateEndsHere extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker with mana value 3 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 3));
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public YourFateEndsHere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature or planeswalker with mana value 3 or greater. Surveil 1.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private YourFateEndsHere(final YourFateEndsHere card) {
        super(card);
    }

    @Override
    public YourFateEndsHere copy() {
        return new YourFateEndsHere(this);
    }
}
