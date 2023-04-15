package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author NinthWorld
 */
public final class LukeSkywalkerEmblem extends Emblem {

    // -6: You get an emblem with "Prevent all damage that would be dealt to you during combat." Exile Luke Skywalker, the Last Jedi.
    public LukeSkywalkerEmblem() {
        this.setName("Emblem Luke Skywalker");
        this.setExpansionSetCodeForImage("SWS");
        this.getAbilities().add(new SimpleStaticAbility(Zone.BATTLEFIELD, new LukeSkywalkerEmblemEffect()));
    }
}

class LukeSkywalkerEmblemEffect extends PreventionEffectImpl {

    public LukeSkywalkerEmblemEffect() {
        super(Duration.Custom, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to you during combat";
    }

    public LukeSkywalkerEmblemEffect(final LukeSkywalkerEmblemEffect effect) {
        super(effect);
    }

    @Override
    public LukeSkywalkerEmblemEffect copy() {
        return new LukeSkywalkerEmblemEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnPhaseType() == TurnPhase.COMBAT
                && super.applies(event, source, game)
                && event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && game.getState().getPlayersInRange(controller.getId(), game).contains(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }
}
