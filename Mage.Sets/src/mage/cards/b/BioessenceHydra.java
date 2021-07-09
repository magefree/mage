package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BioessenceHydra extends CardImpl {

    public BioessenceHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Bioessence Hydra enters the battlefield with a +1/+1 counter on it for each loyalty counter on planeswalkers you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), BioessenceHydraDynamicValue.instance, true
        ), "with a +1/+1 counter on it for each loyalty counter on planeswalkers you control."
        ));

        // Whenever one or more loyalty counters are put on planeswalkers you control, put that many +1/+1 counters on Bioessence Hydra.
        this.addAbility(new BioessenceHydraTriggeredAbility());
    }

    private BioessenceHydra(final BioessenceHydra card) {
        super(card);
    }

    @Override
    public BioessenceHydra copy() {
        return new BioessenceHydra(this);
    }
}

enum BioessenceHydraDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int counter = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_PLANESWALKER, sourceAbility.getControllerId(), game
        )) {
            if (permanent != null) {
                counter += permanent.getCounters(game).getCount(CounterType.LOYALTY);
            }
        }
        return counter;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class BioessenceHydraTriggeredAbility extends TriggeredAbilityImpl {

    BioessenceHydraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private BioessenceHydraTriggeredAbility(final BioessenceHydraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BioessenceHydraTriggeredAbility copy() {
        return new BioessenceHydraTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.LOYALTY.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null
                    && !event.getTargetId().equals(this.getSourceId())
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(this.getControllerId())) {
                this.getEffects().clear();
                if (event.getAmount() > 0) {
                    this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(event.getAmount())));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more loyalty counters are put on a planeswalker you control, put that many +1/+1 counters on {this}.";
    }
}
