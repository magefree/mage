package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CastFromGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGreatWork extends CardImpl {

    public TheGreatWork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.SAGA);
        this.color.setRed(true);
        this.nightCard = true;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- The Great Work deals 3 damage to target opponent and each creature they control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new TheGreatWorkEffect(), new TargetOpponent()
        );

        // II -- Create three Treasure tokens.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new TreasureToken(), 3)
        );

        // III -- Until end of turn, you may cast instant and sorcery spells from any graveyard. If a spell cast this way would be put into a graveyard, exile it instead. Exile The Great Work, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, new TheGreatWorkCastFromGraveyardEffect(),
                new TheGreatWorkReplacementEffect(), new ExileSourceAndReturnFaceUpEffect()
        );
        this.addAbility(sagaAbility, new CastFromGraveyardWatcher());
    }

    private TheGreatWork(final TheGreatWork card) {
        super(card);
    }

    @Override
    public TheGreatWork copy() {
        return new TheGreatWork(this);
    }
}

class TheGreatWorkEffect extends OneShotEffect {

    TheGreatWorkEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to target opponent and each creature they control";
    }

    private TheGreatWorkEffect(final TheGreatWorkEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatWorkEffect copy() {
        return new TheGreatWorkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        player.damage(3, source, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game
        )) {
            permanent.damage(3, source, game);
        }
        return true;
    }
}

class TheGreatWorkCastFromGraveyardEffect extends AsThoughEffectImpl {

    TheGreatWorkCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may cast instant and sorcery spells from any graveyard";
    }

    private TheGreatWorkCastFromGraveyardEffect(final TheGreatWorkCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheGreatWorkCastFromGraveyardEffect copy() {
        return new TheGreatWorkCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (card != null
                && affectedControllerId.equals(source.getControllerId())
                && StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY.match(card, game)
                && Zone.GRAVEYARD.equals(game.getState().getZone(card.getId()))) {
            game.getState().setValue("TheGreatWork", card);
            return true;
        }
        return false;
    }
}

class TheGreatWorkReplacementEffect extends ReplacementEffectImpl {

    public TheGreatWorkReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "if a spell cast this way would be put into a graveyard, exile it instead";
    }

    private TheGreatWorkReplacementEffect(final TheGreatWorkReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatWorkReplacementEffect copy() {
        return new TheGreatWorkReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = (Card) game.getState().getValue("TheGreatWork");
        if (card != null) {
            ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (Zone.GRAVEYARD != ((ZoneChangeEvent) event).getToZone()) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        if (card == null || (!card.isInstant(game) && !card.isSorcery(game))) {
            return false;
        }
        CastFromGraveyardWatcher watcher = game.getState().getWatcher(CastFromGraveyardWatcher.class);
        return watcher != null
                && watcher.spellWasCastFromGraveyard(event.getTargetId(),
                game.getState().getZoneChangeCounter(event.getTargetId()));
    }
}
