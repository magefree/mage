package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.Objects;
import java.util.UUID;

/**
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
        this.addAbility(new AttacksTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), false));

        // Whenever a spell or ability you control exiles one or more cards from your library and/or your graveyard, put a +1/+1 counter on Laelia.
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

    LaeliaTheBladeReforgedAddCountersTriggeredAbility(final LaeliaTheBladeReforgedAddCountersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaeliaTheBladeReforgedAddCountersTriggeredAbility copy() {
        return new LaeliaTheBladeReforgedAddCountersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        final int numberExiled = zEvent.getCards().size();
        if (zEvent.getToZone() != Zone.EXILED
                || numberExiled == 0) {
            return false;
        }
        switch (zEvent.getFromZone()) {
            case LIBRARY:
                if (zEvent
                        .getCards()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(Card::getOwnerId)
                        .anyMatch(this::isControlledBy)
                        && numberExiled > 0) {
                    this.getEffects().clear();
                    this.getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
                    return true;
                }
            case GRAVEYARD:
                if (zEvent
                        .getCards()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(Card::getOwnerId)
                        .anyMatch(this::isControlledBy)
                        && numberExiled > 0) {
                    this.getEffects().clear();
                    this.getEffects().add(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
                    return true;
                }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into exile from your library "
                + "and/or your graveyard, put a +1/+1 counter on {this}.";
    }
}
