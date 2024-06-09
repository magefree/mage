package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class LobeliaDefenderOfBagEnd extends CardImpl {

    public LobeliaDefenderOfBagEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Lobelia enters the battlefield, look at the top card of each opponent's library and exile those cards face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LobeliaDefenderOfBagEndETBEffect()));

        // {T}, Sacrifice an artifact: Choose one --
        // * Until end of turn, you may play a card exiled with Lobelia without paying its mana cost.
        // * Each opponent loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(new LobeliaDefenderOfBagEndPlayFromExileEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        ability.addWatcher(new LobeliaDefenderOfBagEndWatcher());

        ability.addMode(new Mode(
                new LoseLifeOpponentsEffect(2)
        ).addEffect(new GainLifeEffect(2).concatBy("and")));

        this.addAbility(ability);
    }

    private LobeliaDefenderOfBagEnd(final LobeliaDefenderOfBagEnd card) {
        super(card);
    }

    @Override
    public LobeliaDefenderOfBagEnd copy() {
        return new LobeliaDefenderOfBagEnd(this);
    }
}

class LobeliaDefenderOfBagEndETBEffect extends OneShotEffect {

    LobeliaDefenderOfBagEndETBEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of each opponent's library and exile those cards face down";
    }

    private LobeliaDefenderOfBagEndETBEffect(final LobeliaDefenderOfBagEndETBEffect effect) {
        super(effect);
    }

    @Override
    public LobeliaDefenderOfBagEndETBEffect copy() {
        return new LobeliaDefenderOfBagEndETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards topCards = new CardsImpl();
        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }

            card.setFaceDown(true, game);
            topCards.add(card);
        }
        controller.lookAtCards(source, null, topCards, game);

        Set<Card> cardSet = topCards.getCards(game);
        if (controller.moveCardsToExile(
                cardSet, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        )) {
            topCards.retainZone(Zone.EXILED, game);
            topCards.getCards(game).forEach(c -> c.setFaceDown(true, game));

            // You may look at these face-down exiled cards any time you wish. No other player may look at the face-down cards you exiled with Lobelia, Defender of Bag End, even if another player takes control of it.
            // (2023-06-16)
            for (Card card : cardSet) {
                ContinuousEffect effect = new LobeliaDefenderOfBagLookEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

}

class LobeliaDefenderOfBagLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public LobeliaDefenderOfBagLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "You may look at the cards exiled with {this}";
    }

    private LobeliaDefenderOfBagLookEffect(final LobeliaDefenderOfBagLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LobeliaDefenderOfBagLookEffect copy() {
        return new LobeliaDefenderOfBagLookEffect(this);
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

class LobeliaDefenderOfBagEndPlayFromExileEffect extends AsThoughEffectImpl {

    LobeliaDefenderOfBagEndPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may play a card exiled with {this} without paying its mana cost";
    }

    private LobeliaDefenderOfBagEndPlayFromExileEffect(final LobeliaDefenderOfBagEndPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public LobeliaDefenderOfBagEndPlayFromExileEffect copy() {
        return new LobeliaDefenderOfBagEndPlayFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        LobeliaDefenderOfBagEndWatcher.addPlayable(source, game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!LobeliaDefenderOfBagEndWatcher.checkPermission(affectedControllerId, source, game)) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(sourceId)) {
            return false;
        }
        return allowCardToPlayWithoutMana(sourceId, source, affectedControllerId, game);
    }
}

class LobeliaDefenderOfBagEndWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();

    LobeliaDefenderOfBagEndWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.PLAY_LAND) {
            if (event.getAdditionalReference() == null) {
                return;
            }
            morMap.computeIfAbsent(event.getAdditionalReference().getApprovingMageObjectReference(), m -> new HashMap<>())
                    .compute(event.getPlayerId(), (u, i) -> i == null ? 0 : Integer.sum(i, -1));
        }
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

    static boolean checkPermission(UUID playerId, Ability source, Game game) {
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(source);
        LobeliaDefenderOfBagEndWatcher watcher = game.getState().getWatcher(LobeliaDefenderOfBagEndWatcher.class);
        return watcher.morMap.containsKey(mor)
                && watcher.morMap.get(mor).getOrDefault(playerId, 0) > 0;
    }

    static void addPlayable(Ability source, Game game) {
        MageObjectReference mor = new MageObjectReference(source);
        game.getState()
                .getWatcher(LobeliaDefenderOfBagEndWatcher.class)
                .morMap
                .computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}
