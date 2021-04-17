package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DefiantGreatmaw extends CardImpl {

    public DefiantGreatmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HIPPO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Defiant Greatmaw enters the battlefield, put two -1/-1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more -1/-1 counters on Defiant Greatmaw, remove a -1/-1 counter from another target creature you control.
        this.addAbility(new DefiantGreatmawTriggeredAbility());
    }

    private DefiantGreatmaw(final DefiantGreatmaw card) {
        super(card);
    }

    @Override
    public DefiantGreatmaw copy() {
        return new DefiantGreatmaw(this);
    }
}

class DefiantGreatmawTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    DefiantGreatmawTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterTargetEffect(CounterType.M1M1.createInstance(1)), false);
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    private DefiantGreatmawTriggeredAbility(final DefiantGreatmawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.M1M1.getName())
                && isControlledBy(event.getPlayerId())
                && event.getTargetId().equals(getSourceId());
    }

    @Override
    public DefiantGreatmawTriggeredAbility copy() {
        return new DefiantGreatmawTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more -1/-1 counters on {this}, " +
                "remove a -1/-1 counter from another target creature you control.";
    }
}
