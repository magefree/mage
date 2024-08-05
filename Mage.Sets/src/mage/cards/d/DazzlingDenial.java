package mage.cards.d;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DazzlingDenial extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.BIRD));
    private static final Hint hint = new ConditionHint(condition, "You control a Bird");

    public DazzlingDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}. If you control a Bird, counter that spell unless its controller pays {4} instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(4)),
                new CounterUnlessPaysEffect(new GenericManaCost(2)),
                condition, "counter target spell unless its controller pays {2}. " +
                "If you control a Bird, counter that spell unless its controller pays {4} instead"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private DazzlingDenial(final DazzlingDenial card) {
        super(card);
    }

    @Override
    public DazzlingDenial copy() {
        return new DazzlingDenial(this);
    }
}
