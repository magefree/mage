package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author LevelX2
 */
public final class CrawlingSensation extends CardImpl {

    public CrawlingSensation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, you may put the top two cards of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new MillCardsControllerEffect(2), true));

        // Whenever one or more land cards are put into your graveyard from anywhere for the first time each turn, create a 1/1 green Insect creature token.
        this.addAbility(new CrawlingSensationTriggeredAbility());
    }

    private CrawlingSensation(final CrawlingSensation card) {
        super(card);
    }

    @Override
    public CrawlingSensation copy() {
        return new CrawlingSensation(this);
    }
}

class CrawlingSensationTriggeredAbility extends TriggeredAbilityImpl {

    public CrawlingSensationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new InsectToken()), false);
    }

    private CrawlingSensationTriggeredAbility(final CrawlingSensationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null && Zone.GRAVEYARD == zEvent.getToZone() && zEvent.getCards() != null) {
            // TODO: Switch to handle this with a game watcher because this does not work correctly if control changes
            Integer usedOnTurn = (Integer) game.getState().getValue("usedOnTurn" + getControllerId() + getOriginalId());
            if (usedOnTurn == null || usedOnTurn < game.getTurnNum()) {
                for (Card card : zEvent.getCards()) {
                    if (card != null) {
                        UUID cardOwnerId = card.getOwnerId();
                        if (cardOwnerId != null
                                && card.isOwnedBy(getControllerId())
                                && card.isLand(game)) {
                            game.getState().setValue("usedOnTurn" + getControllerId() + getOriginalId(), game.getTurnNum());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CrawlingSensationTriggeredAbility copy() {
        return new CrawlingSensationTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere for the first time each turn, create a 1/1 green Insect creature token.";
    }
}
