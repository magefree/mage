package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FoeRazerRegent extends CardImpl {

    public FoeRazerRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Foe-Razer Regent enters the battlefield, you may have it fight target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect().setText("you may have it fight target creature you don't control"), true);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Whenever a creature you control fights, put two +1/+1 counters on it at the beginning of the next end step.
        this.addAbility(new FoeRazerRegentTriggeredAbility());
    }

    private FoeRazerRegent(final FoeRazerRegent card) {
        super(card);
    }

    @Override
    public FoeRazerRegent copy() {
        return new FoeRazerRegent(this);
    }
}

class FoeRazerRegentTriggeredAbility extends TriggeredAbilityImpl {

    FoeRazerRegentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new FoeRazerRegentDelayedTriggeredAbility(), true), false);
    }

    private FoeRazerRegentTriggeredAbility(final FoeRazerRegentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FoeRazerRegentTriggeredAbility copy() {
        return new FoeRazerRegentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FIGHTED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && permanent.isControlledBy(getControllerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control fights, put two +1/+1 counters on it at the beginning of the next end step.";
    }
}

class FoeRazerRegentDelayedTriggeredAbility extends DelayedTriggeredAbility {

    FoeRazerRegentDelayedTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
    }

    private FoeRazerRegentDelayedTriggeredAbility(final FoeRazerRegentDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FoeRazerRegentDelayedTriggeredAbility copy() {
        return new FoeRazerRegentDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "put two +1/+1 counters on it at the beginning of the next end step";
    }
}
