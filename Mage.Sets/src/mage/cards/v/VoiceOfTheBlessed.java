package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoiceOfTheBlessed extends CardImpl {

    public VoiceOfTheBlessed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put a +1/+1 counter on Voice of the Blessed.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // As long as Voice of the Blessed has four or more +1/+1 counters on it, it has flying and vigilance.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.WhileOnBattlefield
                ), VoiceOfTheBlessedCondition.FOUR, "as long as {this} has " +
                "four or more +1/+1 counters on it, it has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield
        ), VoiceOfTheBlessedCondition.FOUR, "and vigilance"));
        this.addAbility(ability);

        // As long as Voice of the Blessed has ten or more +1/+1 counters on it, it has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield
                ), VoiceOfTheBlessedCondition.TEN, "as long as {this} has " +
                "ten or more +1/+1 counters on it, it has indestructible"
        )));
    }

    private VoiceOfTheBlessed(final VoiceOfTheBlessed card) {
        super(card);
    }

    @Override
    public VoiceOfTheBlessed copy() {
        return new VoiceOfTheBlessed(this);
    }
}

enum VoiceOfTheBlessedCondition implements Condition {
    FOUR(4),
    TEN(10);
    private final int counters;

    VoiceOfTheBlessedCondition(int counters) {
        this.counters = counters;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) >= counters;
    }
}