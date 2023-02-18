package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.*;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class EvolvingAdaptive extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue oilCounters = new CountersSourceCount(CounterType.OIL);

    public EvolvingAdaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        //Evolving Adaptive enters the battlefield with an oil counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                "with an oil counter on it"
        ));

        //Evolving Adaptive gets +1/+1 for each oil counter on it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(oilCounters, oilCounters, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each oil counter on it")));

        //Whenever another creature enters the battlefield under your control, if that creature has greater power or
        //toughness than Evolving Adaptive, put an oil counter on Evolving Adaptive.
        this.addAbility(new EvolvingAdaptiveTriggeredAbility(new AddCountersSourceEffect(CounterType.OIL.createInstance())
                .setText("if that creature has greater power or toughness than Evolving Adaptive, put an oil counter " +
                        "on Evolving Adaptive.")
                , filter, this.objectId));
    }

    private EvolvingAdaptive(final EvolvingAdaptive card) {
        super(card);
    }

    @Override
    public EvolvingAdaptive copy() {
        return new EvolvingAdaptive(this);
    }
}

class EvolvingAdaptiveTriggeredAbility extends EntersBattlefieldControlledTriggeredAbility {

    UUID permanentID;

    public EvolvingAdaptiveTriggeredAbility(Effect effect, FilterPermanent filter, UUID permanentID) {
        super(effect, filter);
        this.permanentID = permanentID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enteringCreature = game.getPermanent(event.getSourceId());
        Permanent permanent = game.getPermanent(permanentID);
        if (permanent != null){
            if (!(enteringCreature.getPower().getValue() > permanent.getPower().getValue()) &&
                    !(enteringCreature.getToughness().getValue() > permanent.getToughness().getValue())) {
                return false;
            }
        }
        return super.checkTrigger(event, game);
    }

    public EvolvingAdaptiveTriggeredAbility(final EntersBattlefieldControlledTriggeredAbility ability) {
        super(ability);
    }
}
