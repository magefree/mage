

package mage.cards.v;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class ViridianRevel extends CardImpl {

    public ViridianRevel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");


        // Whenever an artifact is put into an opponent's graveyard from the battlefield, you may draw a card.
        this.addAbility(new ViridianRevelTriggeredAbility());
    }

    private ViridianRevel(final ViridianRevel card) {
        super(card);
    }

    @Override
    public ViridianRevel copy() {
        return new ViridianRevel(this);
    }
}

class ViridianRevelTriggeredAbility extends TriggeredAbilityImpl {
    ViridianRevelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    private ViridianRevelTriggeredAbility(final ViridianRevelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ViridianRevelTriggeredAbility copy() {
        return new ViridianRevelTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent)event).isDiesEvent()) {
            Card card = game.getPermanentOrLKIBattlefield(event.getTargetId());
            Player controller = game.getPlayer(getControllerId());
            if (controller != null && card != null && card.isArtifact(game)
                    && controller.hasOpponent(card.getOwnerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into an opponent's graveyard from the battlefield, you may draw a card.";
    }
}