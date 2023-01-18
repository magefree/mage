package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

/**
 * @author TheElk801
 */
public final class MinorMisstep extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public MinorMisstep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target spell with mana value 1 or less.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private MinorMisstep(final MinorMisstep card) {
        super(card);
    }

    @Override
    public MinorMisstep copy() {
        return new MinorMisstep(this);
    }
}
