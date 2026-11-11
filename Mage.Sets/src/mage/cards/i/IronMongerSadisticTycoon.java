package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

public final class IronMongerSadisticTycoon extends CardImpl {

    static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VILLAIN, "Villains you control");

    public IronMongerSadisticTycoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new IronMongerConniveTriggeredAbility());
    }

    private IronMongerSadisticTycoon(final IronMongerSadisticTycoon card) {
        super(card);
    }

    @Override
    public IronMongerSadisticTycoon copy() {
        return new IronMongerSadisticTycoon(this);
    }
}

class IronMongerConniveTriggeredAbility extends TriggeredAbilityImpl {

    IronMongerConniveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(),
                IronMongerSadisticTycoon.filter), false);
        setTriggerPhrase("Whenever a creature you control connives, put a +1/+1 counter on each Villain you control");
    }

    private IronMongerConniveTriggeredAbility(final IronMongerConniveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CONNIVE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public IronMongerConniveTriggeredAbility copy() {
        return new IronMongerConniveTriggeredAbility(this);
    }
}
