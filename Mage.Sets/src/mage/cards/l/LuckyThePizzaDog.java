package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FoodToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author muz
 */
public final class LuckyThePizzaDog extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Cat, Dog, or Hero spell");

    static {
        filter.add(Predicates.or(
            SubType.CAT.getPredicate(),
            SubType.DOG.getPredicate(),
            SubType.HERO.getPredicate()
        ));
    }

    private static final Condition condition = YouGainedLifeCondition.getZero();

    public LuckyThePizzaDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a Cat, Dog, or Hero spell, create a Food token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new FoodToken()), filter, false));

        // At the beginning of each end step, if you gained life this turn, put a +1/+1 counter on Lucky.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, condition
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private LuckyThePizzaDog(final LuckyThePizzaDog card) {
        super(card);
    }

    @Override
    public LuckyThePizzaDog copy() {
        return new LuckyThePizzaDog(this);
    }
}
