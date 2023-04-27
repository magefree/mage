package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author weirddan455
 */
public final class HaukensInsight extends CardImpl {

    public HaukensInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.color.setBlue(true);
        // Back half of Jacob Hauken, Inspector
        this.nightCard = true;

        // At the beginning of your upkeep, exile the top card of your library face down. You may look at that card for as long as it remains exiled.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HaukensInsightExileEffect(), TargetController.YOU, false));

        // Once during each of your turns, you may play a land or cast a spell from among the cards exiled with this permanent without paying its mana cost.
        this.addAbility(new SimpleStaticAbility(new HaukensInsightPlayEffect())
                .setIdentifier(MageIdentifier.HaukensInsightWatcher),
                new HaukensInsightWatcher());
    }

    private HaukensInsight(final HaukensInsight card) {
        super(card);
    }

    @Override
    public HaukensInsight copy() {
        return new HaukensInsight(this);
    }
}

class HaukensInsightExileEffect extends OneShotEffect {

    public HaukensInsightExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library face down. You may look at that card for as long as it remains exiled";
    }

    private HaukensInsightExileEffect(final HaukensInsightExileEffect effect) {
        super(effect);
    }

    @Override
    public HaukensInsightExileEffect copy() {
        return new HaukensInsightExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                MageObject sourceObject = source.getSourceObject(game);
                String exileName = sourceObject == null ? null : sourceObject.getIdName();
                card.setFaceDown(true, game);
                controller.moveCardsToExile(card, source, game, false, exileId, exileName);
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                    card.setFaceDown(true, game);
                    HaukensInsightLookEffect effect = new HaukensInsightLookEffect(controller.getId());
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class HaukensInsightLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public HaukensInsightLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
    }

    private HaukensInsightLookEffect(final HaukensInsightLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public HaukensInsightLookEffect copy() {
        return new HaukensInsightLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
        }
        return affectedControllerId.equals(authorizedPlayerId)
                && objectId.equals(cardId);
    }
}

class HaukensInsightPlayEffect extends AsThoughEffectImpl {

    public HaukensInsightPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree, true);
        staticText = "Once during each of your turns, you may play a land or cast a spell from among the cards exiled with this permanent without paying its mana cost";
    }

    private HaukensInsightPlayEffect(final HaukensInsightPlayEffect effect) {
        super(effect);
    }

    @Override
    public HaukensInsightPlayEffect copy() {
        return new HaukensInsightPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.isActivePlayer(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            HaukensInsightWatcher watcher = game.getState().getWatcher(HaukensInsightWatcher.class);
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (controller != null && watcher != null && sourceObject != null && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game))) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()));
                ExileZone exileZone = game.getExile().getExileZone(exileId);
                if (exileZone != null && exileZone.contains(CardUtil.getMainCardId(game, objectId))) {
                    allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);
                    return true;
                }
            }
        }
        return false;
    }
}

class HaukensInsightWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public HaukensInsightWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED) {
            if (event.hasApprovingIdentifier(MageIdentifier.HaukensInsightWatcher)) {
                usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
