package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class BasriDevotedPaladin extends CardImpl {

    public BasriDevotedPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BASRI);
        this.setStartingLoyalty(4);

        // +1: Put a +1/+1 counter on up to one target creature. It gains vigilance until end of turn.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on up to one target creature"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −1: Whenever a creature attacks this turn, put a +1/+1 counter on it.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new BasriDevotedPaladinTriggeredAbility()), -1));

        // −6: Creatures you control get +2/+2 and gain flying until end of turn.
        Effect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("Creatures you control get +2/+2");
        LoyaltyAbility ultimateAbility = new LoyaltyAbility(effect, -6);
        effect = new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("and gain flying until end of turn");
        ultimateAbility.addEffect(effect);
        this.addAbility(ultimateAbility);
    }

    private BasriDevotedPaladin(final BasriDevotedPaladin card) {
        super(card);
    }

    @Override
    public BasriDevotedPaladin copy() {
        return new BasriDevotedPaladin(this);
    }
}

class BasriDevotedPaladinTriggeredAbility extends DelayedTriggeredAbility {

    public BasriDevotedPaladinTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false);
    }

    public BasriDevotedPaladinTriggeredAbility(BasriDevotedPaladinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BasriDevotedPaladinTriggeredAbility copy() {
        return new BasriDevotedPaladinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks this turn, put a +1/+1 counter on it.";
    }
}