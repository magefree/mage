package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.SearchLibraryEvent;
import mage.players.Player;

/**
 * @author JayDi85
 */
public class YouControlYourOpponentsWhileSearchingReplacementEffect extends ReplacementEffectImpl {

    public YouControlYourOpponentsWhileSearchingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You control your opponents while they're searching their libraries";
    }

    YouControlYourOpponentsWhileSearchingReplacementEffect(final YouControlYourOpponentsWhileSearchingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        SearchLibraryEvent se = (SearchLibraryEvent) event;
        se.setSearchingControllerId(source.getControllerId());
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SEARCH_LIBRARY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null
                && game.isOpponent(controller, event.getPlayerId())
                // verify that the controller of the ability is searching their library
                && event.getTargetId().equals(event.getPlayerId());
    }

    @Override
    public YouControlYourOpponentsWhileSearchingReplacementEffect copy() {
        return new YouControlYourOpponentsWhileSearchingReplacementEffect(this);
    }
}
