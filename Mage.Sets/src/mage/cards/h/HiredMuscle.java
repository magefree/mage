
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HiredMuscle extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.KI, 2);

    public HiredMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Scarmaker";

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Hired Muscle.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.KI.createInstance()),
                StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE, true
        ));

        // At the beginning of the end step, if there are two or more ki counters on Hired Muscle, you may flip it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new FlipSourceEffect(new Scarmaker()).setText("flip it"), true, condition
        ));
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
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(4);
        toughness = new MageInt(4);

        // Remove a ki counter from Scarmaker: Target creature gains fear until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.KI.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Scarmaker(final Scarmaker token) {
        super(token);
    }

    public Scarmaker copy() {
        return new Scarmaker(this);
    }
}
