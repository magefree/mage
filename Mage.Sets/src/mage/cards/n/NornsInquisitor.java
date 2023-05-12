package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
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
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NornsInquisitor extends CardImpl {

    public NornsInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Norn's Inquisitor enters the battlefield, incubate 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(2)));

        // Whenever a permanent you control transforms into a Phyrexian, put a +1/+1 counter on it.
        this.addAbility(new NornsInquisitorTriggeredAbility());
    }

    private NornsInquisitor(final NornsInquisitor card) {
        super(card);
    }

    @Override
    public NornsInquisitor copy() {
        return new NornsInquisitor(this);
    }
}

class NornsInquisitorTriggeredAbility extends TriggeredAbilityImpl {

    public NornsInquisitorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
    }

    public NornsInquisitorTriggeredAbility(final NornsInquisitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NornsInquisitorTriggeredAbility copy() {
        return new NornsInquisitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !StaticFilters.FILTER_CONTROLLED_PERMANENT.match(permanent, getControllerId(), this, game) || !permanent.hasSubtype(SubType.PHYREXIAN, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control transforms into a Phyrexian, put a +1/+1 counter on it.";
    }
}
