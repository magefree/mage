package mage.cards.n;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.ExploredEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NicanzilCurrentConductor extends CardImpl {

    public NicanzilCurrentConductor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature you control explores a land card, you may put a land card from your hand onto the battlefield tapped.
        this.addAbility(new NicanzilCurrentConductorTriggeredAbility(
                new PutCardFromHandOntoBattlefieldEffect(
                        StaticFilters.FILTER_CARD_LAND_A, false, true
                ), true
        ));

        // Whenever a creature you control explores a nonland card, put a +1/+1 counter on Nicanzil, Current Conductor.
        this.addAbility(new NicanzilCurrentConductorTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private NicanzilCurrentConductor(final NicanzilCurrentConductor card) {
        super(card);
    }

    @Override
    public NicanzilCurrentConductor copy() {
        return new NicanzilCurrentConductor(this);
    }
}

class NicanzilCurrentConductorTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean isLand;

    NicanzilCurrentConductorTriggeredAbility(Effect effect, boolean isLand) {
        super(Zone.BATTLEFIELD, effect);
        this.isLand = isLand;
        this.setTriggerPhrase("Whenever a creature you control explores a " + (isLand ? "" : "non") + "land card, ");
    }

    private NicanzilCurrentConductorTriggeredAbility(final NicanzilCurrentConductorTriggeredAbility ability) {
        super(ability);
        this.isLand = ability.isLand;
    }

    @Override
    public NicanzilCurrentConductorTriggeredAbility copy() {
        return new NicanzilCurrentConductorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLORED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ExploredEvent eEvent = (ExploredEvent) event;
        Permanent permanent = game.getPermanent(eEvent.getTargetId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(getControllerId())
                && eEvent.getCard() != null
                && eEvent.getCard().isLand(game) == isLand;
    }
}
