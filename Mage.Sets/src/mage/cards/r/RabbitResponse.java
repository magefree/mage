package mage.cards.r;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RabbitResponse extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.RABBIT));
    private static final Hint hint = new ConditionHint(condition, "You control a Rabbit");

    public RabbitResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Creatures you control get +2/+1 until end of turn. If you control a Rabbit, scry 2.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(2), condition,
                "if you control a Rabbit, scry 2"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private RabbitResponse(final RabbitResponse card) {
        super(card);
    }

    @Override
    public RabbitResponse copy() {
        return new RabbitResponse(this);
    }
}
