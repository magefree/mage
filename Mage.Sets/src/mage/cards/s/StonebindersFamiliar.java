package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StonebindersFamiliar extends CardImpl {

    public StonebindersFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever one or more cards are put into exile during your turn, put a +1/+1 counter on Stonebinder's Familiar. This ability triggers only once each turn.
        this.addAbility(new StonebindersFamiliarTriggeredAbility());
    }

    private StonebindersFamiliar(final StonebindersFamiliar card) {
        super(card);
    }

    @Override
    public StonebindersFamiliar copy() {
        return new StonebindersFamiliar(this);
    }
}

class StonebindersFamiliarTriggeredAbility extends TriggeredAbilityImpl {

    StonebindersFamiliarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.setTriggersOnceEachTurn(true);
    }

    private StonebindersFamiliarTriggeredAbility(final StonebindersFamiliarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = ((ZoneChangeEvent) event);
        return zEvent.getToZone() == Zone.EXILED
                && isControlledBy(game.getActivePlayerId())
                && (zEvent.getFromZone() != Zone.BATTLEFIELD
                || !(zEvent.getTarget() instanceof PermanentToken));
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile during your turn, " +
                "put a +1/+1 counter on {this}. This ability triggers only once each turn.";
    }

    @Override
    public StonebindersFamiliarTriggeredAbility copy() {
        return new StonebindersFamiliarTriggeredAbility(this);
    }
}
