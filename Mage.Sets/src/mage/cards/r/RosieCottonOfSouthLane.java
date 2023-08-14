package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RosieCottonOfSouthLane extends CardImpl {

    public RosieCottonOfSouthLane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Rosie Cotton of South Lane enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you create a token, put a +1/+1 counter on target creature you control other than Rosie.
        this.addAbility(new RosieCottonOfSouthLaneTriggeredAbility());
    }

    private RosieCottonOfSouthLane(final RosieCottonOfSouthLane card) {
        super(card);
    }

    @Override
    public RosieCottonOfSouthLane copy() {
        return new RosieCottonOfSouthLane(this);
    }
}

class RosieCottonOfSouthLaneTriggeredAbility extends TriggeredAbilityImpl {

    RosieCottonOfSouthLaneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
    }

    private RosieCottonOfSouthLaneTriggeredAbility(final RosieCottonOfSouthLaneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RosieCottonOfSouthLaneTriggeredAbility copy() {
        return new RosieCottonOfSouthLaneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKEN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "Whenever you create a token, put a +1/+1 counter on target creature you control other than {this}.";
    }
}
