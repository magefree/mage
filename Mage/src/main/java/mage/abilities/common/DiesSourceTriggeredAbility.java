package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DiesSourceTriggeredAbility extends ZoneChangeTriggeredAbility {

    public DiesSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, "When {this} dies, ", optional);
    }

    public DiesSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesSourceTriggeredAbility(DiesSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesSourceTriggeredAbility copy() {
        return new DiesSourceTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && event.getTargetId().equals(getSourceId())) {            
            for (Effect effect : getEffects()) {
                effect.setValue("permanentLeftBattlefield", zEvent.getTarget());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }



}
