package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class SkyriderPatrol extends CardImpl {

    public SkyriderPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, you may pay {G}{U}. When you do, put a +1/+1 counter on another target creature you control, and that creature gains flying until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(
                        new SkyriderPatrolCreateReflexiveTriggerEffect(),
                        new ManaCostsImpl("{G}{U}"),
                        "Pay {G}{U} to put a +1/+1 counter on another"
                        + " creature you control and give it flying?"
                ).setText("you may pay {G}{U}. When you do, "
                        + "put a +1/+1 counter on another target creature you control, "
                        + "and that creature gains flying until end of turn."),
                TargetController.YOU, false
        ));
    }

    public SkyriderPatrol(final SkyriderPatrol card) {
        super(card);
    }

    @Override
    public SkyriderPatrol copy() {
        return new SkyriderPatrol(this);
    }
}

class SkyriderPatrolCreateReflexiveTriggerEffect extends OneShotEffect {

    public SkyriderPatrolCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    public SkyriderPatrolCreateReflexiveTriggerEffect(final SkyriderPatrolCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SkyriderPatrolCreateReflexiveTriggerEffect copy() {
        return new SkyriderPatrolCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new SkyriderPatrolReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class SkyriderPatrolReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SkyriderPatrolReflexiveTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.OneUse, true);
        this.addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(),
                Duration.EndOfTurn
        ));
        this.addTarget(new TargetPermanent(filter));
    }

    public SkyriderPatrolReflexiveTriggeredAbility(final SkyriderPatrolReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkyriderPatrolReflexiveTriggeredAbility copy() {
        return new SkyriderPatrolReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you pay {G}{U}, put a +1/+1 counter "
                + "on another target creature you control, "
                + "and that creature gains flying until end of turn.";
    }
}
