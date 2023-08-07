package mage.cards.e;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.*;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public class EvolvingAdaptive extends CardImpl {

    public EvolvingAdaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.PHYREXIAN, SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Evolving Adaptive enters the battlefield with an oil counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                "with an oil counter on it"
        ));

        // Evolving Adaptive gets +1/+1 for each oil counter on it.
        DynamicValue oilCounters = new CountersSourceCount(CounterType.OIL);
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(oilCounters, oilCounters, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each oil counter on it")));

        // Whenever another creature enters the battlefield under your control, if that creature has greater power or
        // toughness than Evolving Adaptive, put an oil counter on Evolving Adaptive.
        this.addAbility(new EvolvingAdaptiveTriggeredAbility());
    }

    private EvolvingAdaptive(final EvolvingAdaptive card) {
        super(card);
    }

    @Override
    public EvolvingAdaptive copy() {
        return new EvolvingAdaptive(this);
    }
}

class EvolvingAdaptiveTriggeredAbility extends TriggeredAbilityImpl {

    public EvolvingAdaptiveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.OIL.createInstance()));
        this.setTriggerPhrase("Whenever another creature enters the battlefield under your " +
                "control, if that creature has greater power or toughness than {this}, ");
    }

    public EvolvingAdaptiveTriggeredAbility(final EvolvingAdaptiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EvolvingAdaptiveTriggeredAbility copy() {
        return new EvolvingAdaptiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enteringCreature = game.getPermanent(event.getTargetId());
        if (enteringCreature == null || !StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL.match(enteringCreature, getControllerId(), this, game)) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return !(enteringCreature.getPower().getValue() <= permanent.getPower().getValue() &&
                enteringCreature.getToughness().getValue() <= permanent.getToughness().getValue());
    }
}
