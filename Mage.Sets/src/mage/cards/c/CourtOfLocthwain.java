package mage.cards.c;

import mage.MageIdentifier;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class CourtOfLocthwain extends CardImpl {

    static UUID getExileZoneId(MageObjectReference mor, Game game) {
        return CardUtil.getExileZoneId("CourtOfLocthwain::" + mor.getSourceId() + "::" + mor.getZoneChangeCounter(), game);
    }

    public CourtOfLocthwain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Court of Locthwain enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, exile the top card of target opponent's library. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it. If you're the monarch, until end of turn, you may cast a spell from among cards exiled with Court of Locthwain without paying its mana cost.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CourtOfLocthwainFirstEffect(),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new ConditionalOneShotEffect(
                new CourtOfLocthwainSecondEffect(),
                MonarchIsSourceControllerCondition.instance
        ));

        this.addAbility(ability, new CourtOfLocthwainWatcher());
    }

    private CourtOfLocthwain(final CourtOfLocthwain card) {
        super(card);
    }

    @Override
    public CourtOfLocthwain copy() {
        return new CourtOfLocthwain(this);
    }
}

class CourtOfLocthwainFirstEffect extends OneShotEffect {

    CourtOfLocthwainFirstEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of target opponent's library. You may play that "
                + "card for as long as it remains exiled, and mana of any type can be spent to cast it";
    }

    private CourtOfLocthwainFirstEffect(final CourtOfLocthwainFirstEffect effect) {
        super(effect);
    }

    @Override
    public CourtOfLocthwainFirstEffect copy() {
        return new CourtOfLocthwainFirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || source == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        UUID exileId = CourtOfLocthwain.getExileZoneId(new MageObjectReference(sourceObject, game), game);
        String exileName = sourceObject.getIdName();
        controller.moveCardsToExile(card, source, game, true, exileId, exileName);

        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.EndOfGame,
                    true, controller.getId(), null
            );
        }

        return true;
    }
}

class CourtOfLocthwainSecondEffect extends OneShotEffect {

    CourtOfLocthwainSecondEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, you may cast a spell from among cards exiled "
                + "with {this} without paying its mana cost";
    }

    private CourtOfLocthwainSecondEffect(final CourtOfLocthwainSecondEffect effect) {
        super(effect);
    }

    @Override
    public CourtOfLocthwainSecondEffect copy() {
        return new CourtOfLocthwainSecondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CourtOfLocthwainWatcher watcher = game.getState().getWatcher(CourtOfLocthwainWatcher.class);
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || watcher == null || sourceObject == null) {
            return false;
        }

        MageObjectReference mor = new MageObjectReference(sourceObject, game);

        // We do copy the effect, to set the identifier.
        Ability sourceWithIdentifier = source.copy().setIdentifier(MageIdentifier.CourtOfLocthwainWatcher);
        game.addEffect(new CourtOfLocthwainCastForFreeEffect(mor), sourceWithIdentifier);

        // Can cast another spell among the exiled ones this turn.
        watcher.setOrIncrementCastAvailable(controller.getId(), mor);
        return true;
    }

}

class CourtOfLocthwainCastForFreeEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public CourtOfLocthwainCastForFreeEffect(MageObjectReference mor) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.mor = mor;
    }

    private CourtOfLocthwainCastForFreeEffect(final CourtOfLocthwainCastForFreeEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public CourtOfLocthwainCastForFreeEffect copy() {
        return new CourtOfLocthwainCastForFreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // Only applies for the controller of the ability.
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        CourtOfLocthwainWatcher watcher = game.getState().getWatcher(CourtOfLocthwainWatcher.class);
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || watcher == null || sourceObject == null) {
            return false;
        }

        UUID exileId = CourtOfLocthwain.getExileZoneId(mor, game);
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        // Is the card attempted to be played in the ExiledZone?
        if (exileZone == null || !exileZone.contains(objectId)) {
            return false;
        }
        // can this ability still be used this turn?
        if (1 > watcher.castStillAvailable(controller.getId(), new MageObjectReference(sourceObject, game))) {
            return false;
        }

        allowCardToPlayWithoutMana(objectId, source, affectedControllerId, MageIdentifier.CourtOfLocthwainWatcher, game);
        return true;
    }
}

class CourtOfLocthwainWatcher extends Watcher {

    // player -> permanent's mor -> number of free cast remaining for that turn.
    private final Map<UUID, Map<MageObjectReference, Integer>> usageRemaining = new HashMap<>();

    public CourtOfLocthwainWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        UUID playerId = event.getPlayerId();
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.CourtOfLocthwainWatcher)
                && playerId != null) {
            decrementCastAvailable(
                    playerId,
                    event.getAdditionalReference().getApprovingMageObjectReference()
            );
        }
    }

    @Override
    public void reset() {
        usageRemaining.clear();
        super.reset();
    }

    private void decrementCastAvailable(UUID playerId, MageObjectReference mor) {
        if (usageRemaining.containsKey(playerId)) {
            Map<MageObjectReference, Integer> usageForPlayer = usageRemaining.get(playerId);
            if (usageForPlayer.containsKey(mor)) {
                int newValue = usageForPlayer.get(mor) - 1;
                if (newValue > 0) {
                    usageForPlayer.put(mor, newValue);
                } else {
                    usageForPlayer.remove(mor);
                }
            }
        }
    }

    void setOrIncrementCastAvailable(UUID playerId, MageObjectReference mor) {
        usageRemaining.computeIfAbsent(playerId, k -> new HashMap<>());
        usageRemaining.get(playerId).compute(mor, CardUtil::setOrIncrementValue);
    }

    int castStillAvailable(UUID playerId, MageObjectReference mor) {
        return usageRemaining
                .getOrDefault(playerId, new HashMap<>())
                .getOrDefault(mor, 0);
    }
}
