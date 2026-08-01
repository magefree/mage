package mage.cards.h;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

/**
 *
 * @author muz
 */
public final class HighlyIllogical extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 2 or less");


    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    public HighlyIllogical(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Counter target spell with mana value 2 or less. If this spell was kicked, instead counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect()
            .setText("Counter target spell with mana value 2 or less. If this spell was kicked, instead counter target spell.")
        );
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
            KickedCondition.ONCE,
            new TargetSpell(filter),
            new TargetSpell()
        ));
    }

    private HighlyIllogical(final HighlyIllogical card) {
        super(card);
    }

    @Override
    public HighlyIllogical copy() {
        return new HighlyIllogical(this);
    }
}
