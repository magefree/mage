package mage.cards.c;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author weirddan455
 */
public final class CrawlingInfestation extends CardImpl {

    public CrawlingInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, you may mill two cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(2), TargetController.YOU, true));

        // Whenever one or more creature cards are put into your graveyard from anywhere during your turn, create a 1/1 green Insect creature token. This ability triggers only once each turn.
        this.addAbility(new CrawlingInfestationTriggeredAbility());
    }

    private CrawlingInfestation(final CrawlingInfestation card) {
        super(card);
    }

    @Override
    public CrawlingInfestation copy() {
        return new CrawlingInfestation(this);
    }
}

class CrawlingInfestationTriggeredAbility extends TriggeredAbilityImpl {

    public CrawlingInfestationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new InsectToken()));
        this.setTriggersOnceEachTurn(true);
        setTriggerPhrase("Whenever one or more creature cards are put into your graveyard from anywhere during your turn, ");
    }

    private CrawlingInfestationTriggeredAbility(final CrawlingInfestationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CrawlingInfestationTriggeredAbility copy() {
        return new CrawlingInfestationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD && game.isActivePlayer(controllerId)) {
            Card card = game.getCard(zEvent.getTargetId());
            return card != null && !card.isCopy() && card.isCreature(game) && card.isOwnedBy(controllerId);
        }
        return false;
    }
}
