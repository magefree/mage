package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.ControlCombatRedundancyWatcher;

/**
 * @author L_J, TheElk801
 */
public class ChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public ChooseBlockersEffect(Duration duration) {
        super(duration, Outcome.Benefit, false, false);
    }

    private ChooseBlockersEffect(final ChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public ChooseBlockersEffect copy() {
        return new ChooseBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        ControlCombatRedundancyWatcher.addBlockingController(source.getControllerId(), this.duration, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!ControlCombatRedundancyWatcher.checkBlockingController(source.getControllerId(), game)) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            return false;
        }
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, source, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("you choose which creatures block this ");
        switch (duration) {
            case EndOfTurn:
                sb.append("turn");
                break;
            case EndOfCombat:
                sb.append("combat");
                break;
            default:
                throw new IllegalArgumentException("duration type not supported");
        }
        sb.append(" and how those creatures block");
        return sb.toString();
    }
}
