package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CastFromGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JayaBallardEmblem extends Emblem {
    // You get an emblem with "You may cast instant and sorcery cards from your graveyard. If a card cast this way would be put into your graveyard, exile it instead."

    public JayaBallardEmblem() {
        super("Emblem Jaya Ballard");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new JayaBallardCastFromGraveyardEffect());
        ability.addEffect(new JayaBallardReplacementEffect());
        ability.addWatcher(new CastFromGraveyardWatcher());
        this.getAbilities().add(ability);
    }

    private JayaBallardEmblem(final JayaBallardEmblem card) {
        super(card);
    }

    @Override
    public JayaBallardEmblem copy() {
        return new JayaBallardEmblem(this);
    }
}

class JayaBallardCastFromGraveyardEffect extends AsThoughEffectImpl {

    JayaBallardCastFromGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast instant and sorcery spells from your graveyard";
    }

    JayaBallardCastFromGraveyardEffect(final JayaBallardCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JayaBallardCastFromGraveyardEffect copy() {
        return new JayaBallardCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (card != null
                && card.isOwnedBy(affectedControllerId)
                && affectedControllerId.equals(source.getControllerId())
                && StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)
                && Zone.GRAVEYARD.equals(game.getState().getZone(card.getId()))) {
            game.getState().setValue("JayaBallard", card);
            return true;
        }
        return false;
    }
}

class JayaBallardReplacementEffect extends ReplacementEffectImpl {

    public JayaBallardReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Exile);
        staticText = "If a spell cast this way would be put into a graveyard this turn, exile it instead";
    }

    protected JayaBallardReplacementEffect(final JayaBallardReplacementEffect effect) {
        super(effect);
    }

    @Override
    public JayaBallardReplacementEffect copy() {
        return new JayaBallardReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = (Card) game.getState().getValue("JayaBallard");
            if (card != null) {
                ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (Zone.GRAVEYARD == ((ZoneChangeEvent) event).getToZone()) {
            Card card = game.getCard(event.getSourceId());
            if (card != null
                    && (card.isInstant(game)
                    || card.isSorcery(game))) {
                CastFromGraveyardWatcher watcher = game.getState().getWatcher(CastFromGraveyardWatcher.class);
                return watcher != null
                        && watcher.spellWasCastFromGraveyard(event.getTargetId(),
                        game.getState().getZoneChangeCounter(event.getTargetId()));
            }
        }
        return false;
    }
}
