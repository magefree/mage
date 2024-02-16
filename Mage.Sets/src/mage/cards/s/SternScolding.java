package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SternScolding extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("creature spell with power or toughness 2 or less");

    static {
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.FEWER_THAN, 3),
                new ToughnessPredicate(ComparisonType.FEWER_THAN, 3)
        ));
    }

    public SternScolding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target creature spell with power or toughness 2 or less.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private SternScolding(final SternScolding card) {
        super(card);
    }

    @Override
    public SternScolding copy() {
        return new SternScolding(this);
    }
}
