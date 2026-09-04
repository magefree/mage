package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CommanderPlaysCountWatcher;

/**
 * Duel Commander format rule: a player may cast only one of their commanders from the command zone
 * each game (the first one cast, the choice isn't announced before). Only casting from the command
 * zone is restricted, so the other commander can still be cast from another zone (Command Beacon,
 * Road of Return, Netherborn Altar, ...).
 * <p>
 * One effect is created per commander, see GameCommanderImpl.initCommanderEffects
 */
public class CastOnlyOneCommanderPerGameEffect extends ContinuousRuleModifyingEffectImpl {

    private final Card commander;

    public CastOnlyOneCommanderPerGameEffect(Card commander) {
        super(Duration.EndOfGame, Outcome.Detriment, true, true);
        this.commander = commander;
    }

    protected CastOnlyOneCommanderPerGameEffect(final CastOnlyOneCommanderPerGameEffect effect) {
        super(effect);
        this.commander = effect.commander;
    }

    @Override
    public CastOnlyOneCommanderPerGameEffect copy() {
        return new CastOnlyOneCommanderPerGameEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "You can cast only one of your commanders from the command zone each game ("
                + commander.getName() + ").";
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getZone() != Zone.COMMAND || !commander.isOwnedBy(event.getPlayerId())) {
            return false;
        }
        Card cardToCheck = game.getCard(event.getSourceId()); // split/mdf cards support
        if (cardToCheck == null || !commander.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (watcher == null) {
            return false;
        }
        // another commander of that player was already played from the command zone
        // (recasting the same commander is fine)
        return watcher.getPlayerCount(event.getPlayerId()) > watcher.getPlaysCount(commander.getId());
    }
}
