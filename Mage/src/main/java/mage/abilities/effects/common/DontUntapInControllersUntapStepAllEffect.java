

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * 
 * 
 * @author LevelX2
 */

public class DontUntapInControllersUntapStepAllEffect extends ContinuousRuleModifyingEffectImpl {

    TargetController targetController;
    FilterPermanent filter;
    
    public DontUntapInControllersUntapStepAllEffect(Duration duration, TargetController targetController, FilterPermanent filter) {
        super(duration, Outcome.Detriment, false, false);
        this.targetController = targetController;
        this.filter = filter;
    }

    public DontUntapInControllersUntapStepAllEffect(final DontUntapInControllersUntapStepAllEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
        this.filter = effect.filter;
    }

    @Override
    public DontUntapInControllersUntapStepAllEffect copy() {
        return new DontUntapInControllersUntapStepAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                switch(targetController) {
                    case YOU:
                        if (!permanent.isControlledBy(source.getControllerId())) {
                            return false;
                        }
                        break;
                    case OPPONENT:
                        Player controller = game.getPlayer(source.getControllerId());
                        if (controller != null && !game.isOpponent(controller, permanent.getControllerId())) {
                            return false;
                        }
                        break;
                    case ANY:
                        break;
                    default:
                        throw new RuntimeException("Type of TargetController not supported!");
                }
                if (game.isActivePlayer(permanent.getControllerId()) && // controller's untap step
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
        StringBuilder sb =  new StringBuilder(filter.getMessage()).append(" don't untap during ");
        switch(targetController) {
            case ANY:
                sb.append("their controllers' ");
                break;
            default:
                throw new RuntimeException("Type of TargetController not supported yet!");
        }        
        sb.append("untap steps");
        return sb.toString();
    }

}
