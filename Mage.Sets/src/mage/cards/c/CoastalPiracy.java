
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Xavierv3131
 */
public final class CoastalPiracy extends CardImpl {

    public CoastalPiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");

        // Whenever a creature you control deals combat damage to an opponent, you may draw a card.
        this.addAbility(new CoastalPiracyTriggeredAbility());
    }

    public CoastalPiracy(final CoastalPiracy card) {
        super(card);
    }

    @Override
    public CoastalPiracy copy() {
        return new CoastalPiracy(this);
    }
}

class CoastalPiracyTriggeredAbility extends TriggeredAbilityImpl {

    public CoastalPiracyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.optional = true;
    }

    public CoastalPiracyTriggeredAbility(final CoastalPiracyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CoastalPiracyTriggeredAbility copy() {
        return new CoastalPiracyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()
                && game.getOpponents(this.controllerId).contains(((DamagedPlayerEvent) event).getPlayerId())) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to an opponent, you may draw a card.";
    }

}
