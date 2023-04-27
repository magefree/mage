
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CastFromGraveyardWatcher;

/**
 *
 * @author spjspj & L_J
 */
public final class BosiumStrip extends CardImpl {

    public BosiumStrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}: Until end of turn, if the top card of your graveyard is an instant or sorcery card, you may cast that card. If a card cast this way would be put into a graveyard this turn, exile it instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BosiumStripCastFromGraveyardEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new BosiumStripReplacementEffect());
        this.addAbility(ability, new CastFromGraveyardWatcher());
    }

    private BosiumStrip(final BosiumStrip card) {
        super(card);
    }

    @Override
    public BosiumStrip copy() {
        return new BosiumStrip(this);
    }
}

class BosiumStripCastFromGraveyardEffect extends AsThoughEffectImpl {

    BosiumStripCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, if the top card of your graveyard is an instant or sorcery card, you may cast that card";
    }

    BosiumStripCastFromGraveyardEffect(final BosiumStripCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BosiumStripCastFromGraveyardEffect copy() {
        return new BosiumStripCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!(source instanceof FlashbackAbility)
                && affectedControllerId.equals(source.getControllerId())) {
            Player player = game.getPlayer(affectedControllerId);
            Card card = game.getCard(objectId);
            if (card != null
                    && player != null
                    && card.equals(player.getGraveyard().getTopCard(game))
                    && StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)
                    && game.getState().getZone(objectId) == Zone.GRAVEYARD) {
                game.getState().setValue("BosiumStrip", card);
                return true;
            }
        }
        return false;
    }
}

class BosiumStripReplacementEffect extends ReplacementEffectImpl {

    BosiumStripReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If a card cast this way would be put into a graveyard this turn, exile it instead";
    }

    BosiumStripReplacementEffect(final BosiumStripReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BosiumStripReplacementEffect copy() {
        return new BosiumStripReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Card card = (Card) game.getState().getValue("BosiumStrip");
        if (card == null) { return false; }

        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD) { return false; }

        Card card = game.getCard(event.getSourceId());
        if (card == null) { return false; }

        if (!StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)) { return false; }

        CastFromGraveyardWatcher watcher = game.getState().getWatcher(CastFromGraveyardWatcher.class);
        return watcher != null
                && watcher.spellWasCastFromGraveyard(event.getTargetId(),
                game.getState().getZoneChangeCounter(event.getTargetId()));
    }
}
