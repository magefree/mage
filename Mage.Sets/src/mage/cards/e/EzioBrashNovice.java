package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EzioBrashNovice extends CardImpl {

    public EzioBrashNovice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Ezio, Brash Novice attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it")));

        // As long as Ezio has two or more counters on it, it has first strike and is an Assassin in addition to its other types.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), EzioBrashNoviceCondition.instance,
                "as long as {this} has two or more counters on it, it has first strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new AddCardSubTypeSourceEffect(Duration.WhileOnBattlefield, SubType.ASSASSIN),
                EzioBrashNoviceCondition.instance, "and is an Assassin in addition to its other types"
        ));
        this.addAbility(ability);
    }

    private EzioBrashNovice(final EzioBrashNovice card) {
        super(card);
    }

    @Override
    public EzioBrashNovice copy() {
        return new EzioBrashNovice(this);
    }
}

enum EzioBrashNoviceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(permanent -> permanent.getCounters(game))
                .map(Counters::getTotalCount)
                .map(x -> x >= 2)
                .orElse(false);
    }
}
