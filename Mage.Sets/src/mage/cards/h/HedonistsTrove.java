package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class HedonistsTrove extends CardImpl {

    public HedonistsTrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");

        // When Hedonist's Trove enters the battlefield, exile all cards from target opponent's graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HedonistsTroveExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // You may play land cards exiled by Hedonist's Trove.
        this.addAbility(new SimpleStaticAbility(new HedonistsTrovePlayLandEffect()));

        // You may cast nonland cards exiled with Hedonist's Trove. You can't cast more than one spell this way each turn.
        this.addAbility(new SimpleStaticAbility(new HedonistsTroveCastNonlandCardsEffect()), new HedonistsTroveWatcher());
    }

    private HedonistsTrove(final HedonistsTrove card) {
        super(card);
    }

    @Override
    public HedonistsTrove copy() {
        return new HedonistsTrove(this);
    }
}

class HedonistsTroveExileEffect extends OneShotEffect {

    HedonistsTroveExileEffect() {
        super(Outcome.Exile);
        staticText = "exile target opponent's graveyard";
    }

    private HedonistsTroveExileEffect(final HedonistsTroveExileEffect effect) {
        super(effect);
    }

    @Override
    public HedonistsTroveExileEffect copy() {
        return new HedonistsTroveExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        UUID exileId = CardUtil.getExileZoneId(game, source);
        // save the exileId associated with this specific source
        game.getState().setValue(source.getSourceId().toString(), exileId);
        return controller != null
                && targetPlayer != null
                && controller.moveCardsToExile(
                targetPlayer.getGraveyard().getCards(game), source, game, true,
                exileId, CardUtil.getSourceName(game, source)
        );
    }
}

class HedonistsTrovePlayLandEffect extends AsThoughEffectImpl {

    HedonistsTrovePlayLandEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play lands from among cards exiled with {this}";
    }

    private HedonistsTrovePlayLandEffect(final HedonistsTrovePlayLandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HedonistsTrovePlayLandEffect copy() {
        return new HedonistsTrovePlayLandEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null
                || !cardToCheck.isLand(game)
                || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        // use the correct exileId
        UUID exileId = (UUID) game.getState().getValue(source.getSourceId().toString());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        return exileZone != null
                && exileZone.contains(cardToCheck.getMainCard().getId());
    }
}

class HedonistsTroveCastNonlandCardsEffect extends AsThoughEffectImpl {

    HedonistsTroveCastNonlandCardsEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast spells from among cards exiled with {this}. "
                + "You can't cast more than one spell this way each turn.";
    }

    private HedonistsTroveCastNonlandCardsEffect(final HedonistsTroveCastNonlandCardsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HedonistsTroveCastNonlandCardsEffect copy() {
        return new HedonistsTroveCastNonlandCardsEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        HedonistsTroveWatcher watcher = game.getState().getWatcher(HedonistsTroveWatcher.class);
        if (watcher == null
                || !watcher.checkPlayer(affectedControllerId, source, game)) {
            return false;
        }
        Card cardToCheck = game.getCard(objectId);
        // use the correct exileId
        UUID exileId = (UUID) game.getState().getValue(source.getSourceId().toString());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        return cardToCheck != null
                && !cardToCheck.isLand(game)
                && source.isControlledBy(affectedControllerId)
                && exileZone != null
                && exileZone.contains(cardToCheck.getMainCard().getId());
    }
}

class HedonistsTroveWatcher extends Watcher {

    private static final Set<MageObjectReference> emptySet = new HashSet<>();
    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap<>();

    HedonistsTroveWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || event.getAdditionalReference() == null) {
            return;
        }
        playerMap
                .computeIfAbsent(event.getPlayerId(), x -> new HashSet<>())
                .add(event.getAdditionalReference().getApprovingMageObjectReference());
        playerMap.get(event.getPlayerId()).removeIf(Objects::isNull);
    }

    boolean checkPlayer(UUID playerId, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null
                && playerMap
                .getOrDefault(playerId, emptySet)
                .stream()
                .noneMatch(mor -> mor.refersTo(permanent, game));
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }
}
