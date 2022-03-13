package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public final class SerraTheBenevolentEmblem extends Emblem {

    // -6: You get an emblem with "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead."
    public SerraTheBenevolentEmblem() {
        this.setName("Emblem Serra");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new SerraTheBenevolentEmblemEffect()));
    }
}

class SerraTheBenevolentEmblemEffect extends ReplacementEffectImpl {

    SerraTheBenevolentEmblemEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    private SerraTheBenevolentEmblemEffect(final SerraTheBenevolentEmblemEffect effect) {
        super(effect);
    }

    @Override
    public SerraTheBenevolentEmblemEffect copy() {
        return new SerraTheBenevolentEmblemEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() - event.getAmount()) < 1
                    && game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_CREATURE,
                    event.getPlayerId(), source, game) > 0
            ) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }
}
