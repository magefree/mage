
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author emerald000
 */
public final class ProfaneMemento extends CardImpl {

    public ProfaneMemento(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a creature card is put into an opponent's graveyard from anywhere, you gain 1 life.
        this.addAbility(new ProfaneMementoTriggeredAbility());
    }

    private ProfaneMemento(final ProfaneMemento card) {
        super(card);
    }

    @Override
    public ProfaneMemento copy() {
        return new ProfaneMemento(this);
    }
}

class ProfaneMementoTriggeredAbility extends TriggeredAbilityImpl {
    
    public ProfaneMementoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
        setTriggerPhrase("Whenever a creature card is put into an opponent's graveyard from anywhere, ");
    }
    
    public ProfaneMementoTriggeredAbility(final ProfaneMementoTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public ProfaneMementoTriggeredAbility copy() {
        return new ProfaneMementoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature(game) && game.getOpponents(controllerId).contains(card.getOwnerId())) {
                return true;
            }
        }
        return false;
    }
}
