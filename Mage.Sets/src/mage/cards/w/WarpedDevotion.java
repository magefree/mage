
package mage.cards.w;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public final class WarpedDevotion extends CardImpl {

    public WarpedDevotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // Whenever a permanent is returned to a player's hand, that player discards a card.
        this.addAbility(new WarpedDevotionTriggeredAbility());
    }

    private WarpedDevotion(final WarpedDevotion card) {
        super(card);
    }

    @Override
    public WarpedDevotion copy() {
        return new WarpedDevotion(this);
    }
}

class WarpedDevotionTriggeredAbility extends TriggeredAbilityImpl {

    public WarpedDevotionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1), false);
    }

    private WarpedDevotionTriggeredAbility(final WarpedDevotionTriggeredAbility ability) {
        super(ability);
    }

    public WarpedDevotionTriggeredAbility copy() {
        return new WarpedDevotionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent)event;
        if(zce.getFromZone() == Zone.BATTLEFIELD && zce.getToZone() == Zone.HAND) {
            for(Effect effect: getEffects()) {
                effect.setTargetPointer(new FixedTarget(zce.getTarget().getOwnerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent is returned to a player's hand, that player discards a card.";
    }

}
