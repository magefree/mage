
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class AtTheBeginOfMainPhaseDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public enum PhaseSelection {

        NEXT_PRECOMBAT_MAIN("next precombat main phase"),
        NEXT_POSTCOMAT_MAIN("next postcombat main phase"),
        NEXT_MAIN("next main phase"),
        NEXT_MAIN_THIS_TURN("next main phase this turn", Duration.EndOfTurn);

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

    private final TargetController targetController;
    private final PhaseSelection phaseSelection;

    public AtTheBeginOfMainPhaseDelayedTriggeredAbility(Effect effect, boolean optional, TargetController targetController, PhaseSelection phaseSelection) {
        super(effect, phaseSelection.duration, true, optional);
        this.targetController = targetController;
        this.phaseSelection = phaseSelection;

    }

    public AtTheBeginOfMainPhaseDelayedTriggeredAbility(final AtTheBeginOfMainPhaseDelayedTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.phaseSelection = ability.phaseSelection;
    }

    @Override
    public AtTheBeginOfMainPhaseDelayedTriggeredAbility copy() {
        return new AtTheBeginOfMainPhaseDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return checkPhase(event.getType());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case ANY:
                return true;
            case YOU:
                return event.getPlayerId().equals(this.controllerId);

            case OPPONENT:
                if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                    return true;
                }
                break;

            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.isControlledBy(event.getPlayerId())) {
                        return true;
                    }
                }
        }
        return false;
    }

    private boolean checkPhase(EventType eventType) {
        switch (phaseSelection) {
            case NEXT_MAIN:
            case NEXT_MAIN_THIS_TURN:
                return EventType.PRECOMBAT_MAIN_PHASE_PRE == eventType || EventType.POSTCOMBAT_MAIN_PHASE_PRE == eventType;
            case NEXT_POSTCOMAT_MAIN:
                return EventType.POSTCOMBAT_MAIN_PHASE_PRE == eventType;
            case NEXT_PRECOMBAT_MAIN:
                return EventType.PRECOMBAT_MAIN_PHASE_PRE == eventType;
            default:
                return false;
        }
    }

    @Override
    public String getTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your " + phaseSelection + ", ";
            case OPPONENT:
                return "At the beginning of an opponent's " + phaseSelection + ", ";
            case ANY:
                return "At the beginning of the " + phaseSelection + ", ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the " + phaseSelection + " of enchanted creature's controller, ";
        }
        return "";
    }
}
