package mage.cards.k;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KishlaSkimmer extends CardImpl {

    public KishlaSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a card leaves your graveyard during your turn, draw a card. This ability triggers only once each turn.
        this.addAbility(new KishlaSkimmerTriggeredAbility().setTriggersLimitEachTurn(1));
    }

    private KishlaSkimmer(final KishlaSkimmer card) {
        super(card);
    }

    @Override
    public KishlaSkimmer copy() {
        return new KishlaSkimmer(this);
    }
}

class KishlaSkimmerTriggeredAbility extends TriggeredAbilityImpl {

    public KishlaSkimmerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggerPhrase("Whenever a card leaves your graveyard during your turn, ");
    }

    private KishlaSkimmerTriggeredAbility(final KishlaSkimmerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KishlaSkimmerTriggeredAbility copy() {
        return new KishlaSkimmerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.isActivePlayer(getControllerId())) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(zEvent.getTargetId());
        return card != null && card.getOwnerId().equals(getControllerId());
    }
}
