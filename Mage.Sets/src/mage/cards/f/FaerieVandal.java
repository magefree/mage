package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author jmharmon
 */


public final class FaerieVandal extends CardImpl {

    public FaerieVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw your second card each turn, put a +1/+1 counter on Faerie Vandal.
        this.addAbility(new FaerieVandalTriggeredAbility());
    }

    public FaerieVandal(final FaerieVandal card) {
        super(card);
    }

    @Override
    public FaerieVandal copy() {
        return new FaerieVandal(this);
    }
}

class FaerieVandalTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggeredOnce = false;
    private boolean triggeredTwice = false;

    FaerieVandalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private FaerieVandalTriggeredAbility(final FaerieVandalTriggeredAbility ability) {
        super(ability);
        this.triggeredOnce = ability.triggeredOnce;
        this.triggeredTwice = ability.triggeredTwice;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_POST) {
            triggeredOnce = triggeredTwice = false;
            return false;
        }
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && event.getPlayerId().equals(controllerId)) {
            if (triggeredOnce) {
                if (triggeredTwice) {
                    return false;
                } else {
                    triggeredTwice = true;
                    return true;
                }
            } else {
                triggeredOnce = true;
                return false;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you draw your second card each turn, put a +1/+1 counter on {this}.";
    }

    @Override
    public FaerieVandalTriggeredAbility copy() {
        return new FaerieVandalTriggeredAbility(this);
    }
}
