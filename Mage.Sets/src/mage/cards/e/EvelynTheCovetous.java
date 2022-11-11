package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.CardState;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EvelynTheCovetous extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VAMPIRE, "Vampire");

    public EvelynTheCovetous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}{B}{B/R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Evelyn, the Covetous or another Vampire enters the battlefield under your control, exile the top card of each player's library with a collection counter on it.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new EvelynTheCovetousExileEffect(), filter, false, true
        ), new EvelynTheCovetousWatcher());

        // Once each turn, you may play a card from exile with a collection counter on it if it was exiled by an ability you controlled, and you may spend mana as though it were any color to cast it.
        Ability ability = new SimpleStaticAbility(new EvelynTheCovetousCastEffect());
        ability.addEffect(new EvelynTheCovetousManaEffect());
        this.addAbility(ability);
    }

    private EvelynTheCovetous(final EvelynTheCovetous card) {
        super(card);
    }

    @Override
    public EvelynTheCovetous copy() {
        return new EvelynTheCovetous(this);
    }
}

class EvelynTheCovetousExileEffect extends OneShotEffect {

    EvelynTheCovetousExileEffect() {
        super(Outcome.Exile);
        staticText = "exile the top card of each player's library with a collection counter on it";
    }

    private EvelynTheCovetousExileEffect(final EvelynTheCovetousExileEffect effect) {
        super(effect);
    }

    @Override
    public EvelynTheCovetousExileEffect copy() {
        return new EvelynTheCovetousExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.add(player.getLibrary().getFromTop(game));
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.getCards(game)
                .stream()
                .forEach(card -> card.addCounters(CounterType.COLLECTION.createInstance(), source, game));
        EvelynTheCovetousWatcher.addCards(source.getControllerId(), cards, game);
        return true;
    }
}

class EvelynTheCovetousCastEffect extends AsThoughEffectImpl {

    EvelynTheCovetousCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree);
        staticText = "once each turn, you may play a card from exile " +
                "with a collection counter on it if it was exiled by an ability you controlled";
    }

    private EvelynTheCovetousCastEffect(final EvelynTheCovetousCastEffect effect) {
        super(effect);
    }

    @Override
    public EvelynTheCovetousCastEffect copy() {
        return new EvelynTheCovetousCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || EvelynTheCovetousWatcher.checkUsed(source, game)) {
            return false;
        }
        Card card = game.getCard(CardUtil.getMainCardId(game, sourceId));
        return card != null
                && card.getCounters(game).getCount(CounterType.COLLECTION) > 0
                && EvelynTheCovetousWatcher.checkExile(affectedControllerId, card, game, 0);
    }
}

class EvelynTheCovetousManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    EvelynTheCovetousManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", and you may spend mana as though it were any color to cast it";
    }

    private EvelynTheCovetousManaEffect(final EvelynTheCovetousManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EvelynTheCovetousManaEffect copy() {
        return new EvelynTheCovetousManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || EvelynTheCovetousWatcher.checkUsed(source, game)) {
            return false;
        }
        Card card = game.getCard(CardUtil.getMainCardId(game, sourceId));
        if (card == null) {
            return false;
        }
        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            return card.getCounters(game).getCount(CounterType.COLLECTION) > 0
                    && EvelynTheCovetousWatcher.checkExile(affectedControllerId, card, game, 0);
        }
        CardState cardState;
        if (card instanceof ModalDoubleFacesCard) {
            cardState = game.getLastKnownInformationCard(((ModalDoubleFacesCard) card).getLeftHalfCard().getId(), Zone.EXILED);
        } else {
            cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
        }
        return cardState != null && cardState.getCounters().getCount(CounterType.COLLECTION) > 0
                && EvelynTheCovetousWatcher.checkExile(affectedControllerId, card, game, 1);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class EvelynTheCovetousWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> exiledMap = new HashMap<>();
    private final Map<MageObjectReference, Set<UUID>> usedMap = new HashMap<>();

    EvelynTheCovetousWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.getAdditionalReference() != null) {
            usedMap.computeIfAbsent(
                    event.getAdditionalReference()
                            .getApprovingMageObjectReference(),
                    x -> new HashSet<>()
            ).add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedMap.clear();
    }

    static void addCards(UUID playerId, Cards cards, Game game) {
        Set<MageObjectReference> set = game
                .getState()
                .getWatcher(EvelynTheCovetousWatcher.class)
                .exiledMap
                .computeIfAbsent(playerId, x -> new HashSet<>());
        cards.getCards(game)
                .stream()
                .map(card -> new MageObjectReference(card, game))
                .forEach(set::add);
    }

    static boolean checkUsed(Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return true;
        }
        return game
                .getState()
                .getWatcher(EvelynTheCovetousWatcher.class)
                .usedMap
                .getOrDefault(
                        new MageObjectReference(sourceObject, game),
                        Collections.emptySet()
                ).contains(source.getControllerId());
    }

    static boolean checkExile(UUID playerId, Card card, Game game, int offset) {
        return game
                .getState()
                .getWatcher(EvelynTheCovetousWatcher.class)
                .exiledMap
                .getOrDefault(playerId, Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(card, game, offset));
    }
}
