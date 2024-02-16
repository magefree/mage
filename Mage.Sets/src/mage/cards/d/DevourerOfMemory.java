package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevourerOfMemory extends CardImpl {

    public DevourerOfMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever one or more cards are put into your graveyard from your library, Devourer of Memory gets +1/+1 until end of turn and can't be blocked this turn.
        this.addAbility(new DevourerOfMemoryTriggeredAbility());

        // {1}{U}{B}: Put the top card of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(1), new ManaCostsImpl<>("{1}{U}{B}")
        ));
    }

    private DevourerOfMemory(final DevourerOfMemory card) {
        super(card);
    }

    @Override
    public DevourerOfMemory copy() {
        return new DevourerOfMemory(this);
    }
}

class DevourerOfMemoryTriggeredAbility extends TriggeredAbilityImpl {

    DevourerOfMemoryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
        this.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
    }

    private DevourerOfMemoryTriggeredAbility(final DevourerOfMemoryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent != null
                && zEvent.getFromZone() == Zone.LIBRARY
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getCards() != null
                && zEvent
                .getCards()
                .stream()
                .map(Card::getOwnerId)
                .filter(this.getControllerId()::equals)
                .count() > 0;
    }

    @Override
    public DevourerOfMemoryTriggeredAbility copy() {
        return new DevourerOfMemoryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more cards are put into your graveyard from your library, " +
                "{this} gets +1/+1 until end of turn and can't be blocked this turn.";
    }
}
