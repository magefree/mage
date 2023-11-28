
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class ThoughtReflection extends CardImpl {

    public ThoughtReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");

        // If you would draw a card, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThoughtReflectionReplacementEffect()));

    }

    private ThoughtReflection(final ThoughtReflection card) {
        super(card);
    }

    @Override
    public ThoughtReflection copy() {
        return new ThoughtReflection(this);
    }
}

class ThoughtReflectionReplacementEffect extends ReplacementEffectImpl {

    public ThoughtReflectionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If you would draw a card, draw two cards instead";
    }

    private ThoughtReflectionReplacementEffect(final ThoughtReflectionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtReflectionReplacementEffect copy() {
        return new ThoughtReflectionReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player you = game.getPlayer(event.getPlayerId());
        if (you != null) {
            you.drawCards(2, source, game, event);
        }
        return true;
    }
}
