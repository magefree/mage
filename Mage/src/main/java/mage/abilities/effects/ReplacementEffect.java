package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface ReplacementEffect extends ContinuousEffect {

    boolean replaceEvent(GameEvent event, Ability source, Game game);

    /**
     * This check for the relevant events is called at first to prevent further
     * actions if the current event is ignored from this effect
     *
     * @param event
     * @param game
     * @return
     */
    boolean checksEventType(GameEvent event, Game game);

    boolean applies(GameEvent event, Ability source, Game game);

    boolean hasSelfScope();

    @Override
    ReplacementEffect copy();
}
