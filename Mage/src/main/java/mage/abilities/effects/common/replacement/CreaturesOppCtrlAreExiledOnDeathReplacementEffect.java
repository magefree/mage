package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Susucr
 */
public class CreaturesOppCtrlAreExiledOnDeathReplacementEffect extends ReplacementEffectImpl {

    public CreaturesOppCtrlAreExiledOnDeathReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature an opponent controls would die, exile it instead";
    }

    private CreaturesOppCtrlAreExiledOnDeathReplacementEffect(final CreaturesOppCtrlAreExiledOnDeathReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CreaturesOppCtrlAreExiledOnDeathReplacementEffect copy() {
        return new CreaturesOppCtrlAreExiledOnDeathReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null && permanent.isCreature()) {
                Player player = game.getPlayer(source.getControllerId());
                return player != null && player.hasOpponent(permanent.getControllerId(), game);
            }
        }
        return false;
    }
}
