package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AtTheBeginOfCombatDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public enum PhaseSelection {

        NEXT_COMBAT("next combat"),
        NEXT_COMBAT_PHASE_THIS_TURN("next combat phase this turn", Duration.EndOfTurn);

        private final String text;
        private final Duration duration;

        PhaseSelection(String text) {
            this(text, Duration.EndOfGame);
        }

        PhaseSelection(String text, Duration duration) {
            this.text = text;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public AtTheBeginOfCombatDelayedTriggeredAbility(Effect effect, PhaseSelection phaseSelection) {
        super(effect, phaseSelection.duration, true, false);
        setTriggerPhrase("at the beginning of the " + phaseSelection + ", ");
    }

    protected AtTheBeginOfCombatDelayedTriggeredAbility(final AtTheBeginOfCombatDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfCombatDelayedTriggeredAbility copy() {
        return new AtTheBeginOfCombatDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
