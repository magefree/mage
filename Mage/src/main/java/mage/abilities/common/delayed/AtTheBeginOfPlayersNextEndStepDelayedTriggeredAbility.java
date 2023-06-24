package mage.abilities.common.delayed;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

      protected UUID playerId;


    public AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(Effect effect, UUID playerId) {
        super(effect, Duration.Custom, true, false);
        this.playerId = playerId;
    }

    public AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(final AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }
  
      @Override
      public AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility copy() {
          return new AtTheBeginOfPlayersNextEndStepDelayedTriggeredAbility(this);
      }
  
      @Override
      public boolean checkEventType(GameEvent event, Game game) {
          return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
      }
  
      @Override
      public boolean checkTrigger(GameEvent event, Game game) {
          return game.getActivePlayerId().equals(playerId);
      }
  
      @Override
      public String getRule() {
          return "At the beginning of its owners next end step, " + super.getRule();
      }
  
}
