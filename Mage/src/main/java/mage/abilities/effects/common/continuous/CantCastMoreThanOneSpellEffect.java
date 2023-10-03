package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author LevelX2
 */
public class CantCastMoreThanOneSpellEffect extends ContinuousRuleModifyingEffectImpl {

    private final TargetController targetController;

    public CantCastMoreThanOneSpellEffect(TargetController targetController) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.targetController = targetController;
    }

    protected CantCastMoreThanOneSpellEffect(final CantCastMoreThanOneSpellEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public CantCastMoreThanOneSpellEffect copy() {
        return new CantCastMoreThanOneSpellEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (targetController) {
            case YOU:
                if (!event.getPlayerId().equals(source.getControllerId())) {
                    return false;
                }
                break;
            case NOT_YOU:
                if (event.getPlayerId().equals(source.getControllerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                    return false;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment == null || !attachment.isAttachedTo(event.getPlayerId())) {
                    return false;
                }
        }
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("You");
                break;
            case NOT_YOU:
                sb.append("Each other player");
                break;
            case OPPONENT:
                sb.append("Each opponent");
                break;
            case ANY:
                sb.append("Each player");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("Enchanted player");
                break;
            default:
                throw new UnsupportedOperationException("TargetController = " + targetController.toString() + " not supported");
        }
        sb.append(" can't cast more than one spell each turn");
        return sb.toString();
    }
}
