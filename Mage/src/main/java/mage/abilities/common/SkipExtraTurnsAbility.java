package mage.abilities.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author PurpleCrowbar
 */
public class SkipExtraTurnsAbility extends SimpleStaticAbility {

    private final boolean onlyOpponents;

    public SkipExtraTurnsAbility() {
        this(false);
    }

    public SkipExtraTurnsAbility(boolean onlyOpponents) {
        super(Zone.BATTLEFIELD, new SkipExtraTurnsEffect(onlyOpponents));
        this.onlyOpponents = onlyOpponents;
    }

    private SkipExtraTurnsAbility(final SkipExtraTurnsAbility ability) {
        super(ability);
        this.onlyOpponents = ability.onlyOpponents;
    }

    @Override
    public SkipExtraTurnsAbility copy() {
        return new SkipExtraTurnsAbility(this);
    }

    @Override
    public String getRule() {
        return "If a" + (onlyOpponents ? "n opponent" : " player") + " would begin an extra turn, that player skips that turn instead.";
    }
}

class SkipExtraTurnsEffect extends ReplacementEffectImpl {

    private final boolean onlyOpponents;

    SkipExtraTurnsEffect(boolean onlyOpponents) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.onlyOpponents = onlyOpponents;
    }

    private SkipExtraTurnsEffect(final SkipExtraTurnsEffect effect) {
        super(effect);
        this.onlyOpponents = effect.onlyOpponents;
    }

    @Override
    public SkipExtraTurnsEffect copy() {
        return new SkipExtraTurnsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXTRA_TURN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !onlyOpponents || game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game);
    }
}
