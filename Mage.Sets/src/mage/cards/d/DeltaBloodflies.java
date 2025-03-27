package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeltaBloodflies extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you control a creature with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a creture with a counter on it");

    public DeltaBloodflies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, if you control a creature with a counter on it, each opponent loses 1 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(1)).withInterveningIf(condition).addHint(hint));
    }

    private DeltaBloodflies(final DeltaBloodflies card) {
        super(card);
    }

    @Override
    public DeltaBloodflies copy() {
        return new DeltaBloodflies(this);
    }
}
