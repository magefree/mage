
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class GatherSpecimens extends CardImpl {

    public GatherSpecimens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}{U}");

        // If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead.
        this.getSpellAbility().addEffect(new GatherSpecimensReplacementEffect());
    }

    public GatherSpecimens(final GatherSpecimens card) {
        super(card);
    }

    @Override
    public GatherSpecimens copy() {
        return new GatherSpecimens(this);
    }
}

class GatherSpecimensReplacementEffect extends ReplacementEffectImpl {

    public GatherSpecimensReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.GainControl);
        staticText = "If a creature would enter the battlefield under an opponent's control this turn, it enters the battlefield under your control instead";
    }

    public GatherSpecimensReplacementEffect(final GatherSpecimensReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GatherSpecimensReplacementEffect copy() {
        return new GatherSpecimensReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE || event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone().match(Zone.BATTLEFIELD)) {
            Card card = game.getCard(event.getTargetId());
            if (card.isCreature()) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.CREATE_TOKEN && event.getFlag()) { // flag indicates if it's a creature token
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            event.setPlayerId(controller.getId());
        }
        return false;
    }
}
