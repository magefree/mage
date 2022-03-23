
package mage.abilities.effects.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DontUntapInControllersNextUntapStepTargetEffect extends ContinuousRuleModifyingEffectImpl {

    private UUID onlyIfControlledByPlayer;
    private String targetName;
    // used for Telekinesis - skips next two untap steps if true
    private boolean twoSteps;
    // holds the info what target was already handled in Untap of its controller
    private final Map<UUID, Boolean> handledTargetsDuringTurn = new HashMap<>();

    /**
     * Attention: This effect won't work with targets controlled by different
     * controllers If this is needed, the validForTurnNum has to be saved per
     * controller.
     */
    public DontUntapInControllersNextUntapStepTargetEffect() {
        this("");
    }

    public DontUntapInControllersNextUntapStepTargetEffect(String targetName) {
        this(targetName, false, null);
    }

    /**
     * @param targetName               used as target text for the generated rule text
     * @param onlyIfControlledByPlayer the effect only works if the permanent is
     *                                 controlled by that controller, null = it works for all players
     */
    public DontUntapInControllersNextUntapStepTargetEffect(String targetName, UUID onlyIfControlledByPlayer) {
        this(targetName, false, onlyIfControlledByPlayer);
    }

    public DontUntapInControllersNextUntapStepTargetEffect(String targetName, boolean twoSteps, UUID onlyIfControlledByPlayer) {
        super(Duration.Custom, Outcome.Detriment, false, true);
        this.targetName = targetName;
        this.twoSteps = twoSteps;
        this.onlyIfControlledByPlayer = onlyIfControlledByPlayer;
    }

    public DontUntapInControllersNextUntapStepTargetEffect(final DontUntapInControllersNextUntapStepTargetEffect effect) {
        super(effect);
        this.targetName = effect.targetName;
        this.twoSteps = effect.twoSteps;
        this.handledTargetsDuringTurn.putAll(effect.handledTargetsDuringTurn);
        this.onlyIfControlledByPlayer = effect.onlyIfControlledByPlayer;
    }

    @Override
    public DontUntapInControllersNextUntapStepTargetEffect copy() {
        return new DontUntapInControllersNextUntapStepTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        Permanent permanentToUntap = game.getPermanent((event.getTargetId()));
        if (permanentToUntap != null && mageObject != null) {
            return permanentToUntap.getLogName() + " doesn't untap (" + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP || event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // the check if a permanent untap phase is already handled is needed if multiple effects are added to prevent untap in next untap step of controller
        // if we don't check it for every untap step of a turn only one effect would be consumed instead of all be valid for the next untap step
        if (event.getType() == GameEvent.EventType.UNTAP_STEP) {
            boolean allHandled = true;
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    if (game.isActivePlayer(permanent.getControllerId())
                            && ((onlyIfControlledByPlayer == null) || (game.isActivePlayer(onlyIfControlledByPlayer)))) { // if effect works only for specific player, all permanents have to be set to handled in that players untap step
                        if (!handledTargetsDuringTurn.containsKey(targetId)) {
                            // it's the untep step of the current controller and the effect was not handled for this target yet, so do it now
                            handledTargetsDuringTurn.put(targetId, false);
                            allHandled = false;
                        } else if (!handledTargetsDuringTurn.get(targetId)) {
                            // if it was already ready to be handled on an previous Untap step set it to done if not already so
                            handledTargetsDuringTurn.put(targetId, !twoSteps);
                        }
                    } else {
                        allHandled = false;
                    }
                }
            }

            if (allHandled) {
                discard();
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            if (handledTargetsDuringTurn.containsKey(event.getTargetId())
                    && !handledTargetsDuringTurn.get(event.getTargetId())
                    && getTargetPointer().getTargets(game, source).contains(event.getTargetId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && game.isActivePlayer(permanent.getControllerId())) {
                    if ((onlyIfControlledByPlayer == null) || game.isActivePlayer(onlyIfControlledByPlayer)) { // If onlyIfControlledByPlayer is set, then don't apply unless we're currently controlled by the specified player.
                        handledTargetsDuringTurn.put(event.getTargetId(), !twoSteps);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (targetName != null && !targetName.isEmpty()) {
            if (targetName.equals("Those creatures") || targetName.equals("They")) {
                return targetName + " don't untap during their controller's next " + (twoSteps ? "two " : "") + "untap step" + (twoSteps ? "s" : "");
            } else
                return targetName + " doesn't untap during its controller's next " + (twoSteps ? "two " : "") + "untap step" + (twoSteps ? "s" : "");
        } else {
            return "target " + (mode == null || mode.getTargets().isEmpty() ? "creature" : mode.getTargets().get(0).getTargetName()) + " doesn't untap during its controller's next " + (twoSteps ? "two " : "") + "untap step" + (twoSteps ? "s" : "");
        }
    }
}