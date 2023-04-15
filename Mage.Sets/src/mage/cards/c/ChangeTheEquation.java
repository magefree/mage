package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChangeTheEquation extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 2 or less");
    private static final FilterSpell filter2 = new FilterSpell("red or green spell with mana value 6 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 7));
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)
        ));
    }

    public ChangeTheEquation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one --
        // * Counter target spell with mana value 2 or less.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // * Counter target red or green spell with mana value 6 or less.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect()).addTarget(new TargetSpell(filter2)));
    }

    private ChangeTheEquation(final ChangeTheEquation card) {
        super(card);
    }

    @Override
    public ChangeTheEquation copy() {
        return new ChangeTheEquation(this);
    }
}
