
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author okuRaku
 */
public class DontUntapInPlayersNextUntapStepAllEffect extends ContinuousRuleModifyingEffectImpl {

    private int validForTurnNum;
    //private String targetName;
    FilterPermanent filter;

    /**
     * Attention: This effect won't work with targets controlled by different
     * controllers If this is needed, the validForTurnNum has to be saved per
     * controller.
     *
     * @param filter
     */
    public DontUntapInPlayersNextUntapStepAllEffect(FilterPermanent filter) {
        super(Duration.Custom, Outcome.Detriment, false, true);
        this.filter = filter;
    }

    public DontUntapInPlayersNextUntapStepAllEffect(final DontUntapInPlayersNextUntapStepAllEffect effect) {
        super(effect);
        this.validForTurnNum = effect.validForTurnNum;
        this.filter = effect.filter;

    }

    @Override
    public DontUntapInPlayersNextUntapStepAllEffect copy() {
        return new DontUntapInPlayersNextUntapStepAllEffect(this);
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
        // the check for turn number is needed if multiple effects are added to prevent untap in next untap step of controller
        // if we don't check for turn number, every untap step of a turn only one effect would be used instead of correctly only one time
        // to skip the untap effect.

        // Discard effect if it's related to a previous turn
        if (validForTurnNum > 0 && validForTurnNum < game.getTurnNum()) {
            discard();
            return false;
        }
        // remember the turn of the untap step the effect has to be applied
        if (event.getType() == GameEvent.EventType.UNTAP_STEP) {
            if (game.isActivePlayer(getTargetPointer().getFirst(game, source))) {
                if (validForTurnNum == game.getTurnNum()) { // the turn has a second untap step but the effect is already related to the first untap step
                    discard();
                    return false;
                }
                validForTurnNum = game.getTurnNum();
            }
        }

        if (game.getTurnStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                Player controller = game.getPlayer(source.getControllerId());
                if (!permanent.isControlledBy(getTargetPointer().getFirst(game, source))) {
                    return false;
                }
                if (controller != null && !game.isOpponent(controller, permanent.getControllerId())) {
                    return false;
                }
                if (game.isActivePlayer(permanent.getControllerId())
                        && // controller's untap step
                        filter.match(permanent, source.getControllerId(), source, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return filter.getMessage() + " target opponent controls don't untap during their next untap step.";
    }
}
