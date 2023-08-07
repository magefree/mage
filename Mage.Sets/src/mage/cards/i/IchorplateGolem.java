package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IchorplateGolem extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with oil counters on them");

    static {
        filter.add(CounterType.OIL.getPredicate());
    }

    public IchorplateGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.PHYREXIAN, SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature enters the battlefield under your control, if
        // it has one or more oil counters on it, put an oil counter on it.
        this.addAbility(new IchorplateGolemTriggeredAbility());

        // Creatures you control with oil counters on them get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)
                .setText("Creatures you control with oil counters on them get +1/+1")));
    }

    private IchorplateGolem(final IchorplateGolem card) {
        super(card);
    }

    @Override
    public IchorplateGolem copy() {
        return new IchorplateGolem(this);
    }
}

class IchorplateGolemTriggeredAbility extends TriggeredAbilityImpl {

    public IchorplateGolemTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.OIL.createInstance()));
    }

    public IchorplateGolemTriggeredAbility(final IchorplateGolemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        if (permanent.isControlledBy(controllerId) && permanent.getCounters(game).getCount(CounterType.OIL) > 0) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public IchorplateGolemTriggeredAbility copy() {
        return new IchorplateGolemTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control, if it has one or more oil counters on it, put an oil counter on it.";
    }
}
