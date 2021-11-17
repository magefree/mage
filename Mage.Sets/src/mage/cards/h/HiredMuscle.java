
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class HiredMuscle extends CardImpl {


    public HiredMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Scarmaker";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Hired Muscle.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));

        // At the beginning of the end step, if there are two or more ki counters on Hired Muscle, you may flip it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect(new Scarmaker()), true),
                new SourceHasCounterCondition(CounterType.KI, 2, Integer.MAX_VALUE),
                "At the beginning of the end step, if there are two or more ki counters on {this}, you may flip it."));
    }

    private HiredMuscle(final HiredMuscle card) {
        super(card);
    }

    @Override
    public HiredMuscle copy() {
        return new HiredMuscle(this);
    }
}

class Scarmaker extends TokenImpl {

    Scarmaker() {
        super("Scarmaker", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(4);
        toughness = new MageInt(4);

        // Remove a ki counter from Scarmaker: Target creature gains fear until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
    public Scarmaker(final Scarmaker token) {
        super(token);
    }

    public Scarmaker copy() {
        return new Scarmaker(this);
    }
}
