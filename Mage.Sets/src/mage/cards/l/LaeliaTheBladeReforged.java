package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * Rules update: 6/18/2021
 * Laelia, the Blade Reforged has received an update to its Oracle text.
 * Specifically, its last triggered ability doesn't care which player is exiling cards from the library or graveyard.
 * Cards put into exile from your library or graveyard for any reason, such as the delve ability, cause the ability to trigger.
 *
 * @author jmharmon
 */
public final class LaeliaTheBladeReforged extends CardImpl {

    public LaeliaTheBladeReforged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Laelia, the Blade Reforged attacks, exile the top card of your library. You may play that card this turn.
        this.addAbility(new AttacksTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn), false));

        // Whenever one or more cards are put into exile from your library and/or your graveyard, put a +1/+1 counter on Laelia.
        this.addAbility(new LaeliaTheBladeReforgedAddCountersTriggeredAbility());
    }

    private LaeliaTheBladeReforged(final LaeliaTheBladeReforged card) {
        super(card);
    }

    @Override
    public LaeliaTheBladeReforged copy() {
        return new LaeliaTheBladeReforged(this);
    }
}

class LaeliaTheBladeReforgedAddCountersTriggeredAbility extends TriggeredAbilityImpl {

    LaeliaTheBladeReforgedAddCountersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private LaeliaTheBladeReforgedAddCountersTriggeredAbility(final LaeliaTheBladeReforgedAddCountersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaeliaTheBladeReforgedAddCountersTriggeredAbility copy() {
        return new LaeliaTheBladeReforgedAddCountersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeBatchEvent zEvent = (ZoneChangeBatchEvent) event;
        return zEvent.getEvents()
                .stream()
                .filter(e -> e.getFromZone() == Zone.LIBRARY || e.getFromZone() == Zone.GRAVEYARD)
                .filter(e -> e.getToZone() == Zone.EXILED)
                .map(ZoneChangeEvent::getTargetId)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(Card::getOwnerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from your library "
                + "and/or your graveyard, put a +1/+1 counter on {this}.";
    }
}
