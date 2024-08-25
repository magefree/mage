package mage.cards.i;

import java.util.*;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.CardState;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jimga150
 */
public final class IanMalcolmChaotician extends CardImpl {

    public IanMalcolmChaotician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player draws their second card each turn, that player exiles the top card of their library.
        this.addAbility(new IanMalcolmChaoticianDrawTriggerAbility(), new IanMalcolmChaoticianWatcher());

        // During each player's turn, that player may cast a spell from among the cards they don't own exiled with
        // Ian Malcolm, Chaotician, and mana of any type can be spent to cast it.
        Ability ability = new SimpleStaticAbility(new IanMalcolmChaoticianCastEffect())
                .setIdentifier(MageIdentifier.IanMalcolmChaoticianWatcher);
        ability.addEffect(new IanMalcolmChaoticianManaEffect());
        this.addAbility(ability);
    }

    private IanMalcolmChaotician(final IanMalcolmChaotician card) {
        super(card);
    }

    @Override
    public IanMalcolmChaotician copy() {
        return new IanMalcolmChaotician(this);
    }
}

class IanMalcolmChaoticianDrawTriggerAbility extends DrawNthCardTriggeredAbility {

    IanMalcolmChaoticianDrawTriggerAbility() {
        super(new IanMalcolmChaoticianExileEffect(), false, TargetController.ANY, 2);
    }

    private IanMalcolmChaoticianDrawTriggerAbility(final IanMalcolmChaoticianDrawTriggerAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)){
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public IanMalcolmChaoticianDrawTriggerAbility copy() {
        return new IanMalcolmChaoticianDrawTriggerAbility(this);
    }
}

class IanMalcolmChaoticianExileEffect extends OneShotEffect {

    IanMalcolmChaoticianExileEffect() {
        super(Outcome.Exile);
        staticText = "that player exiles the top card of their library";
    }

    private IanMalcolmChaoticianExileEffect(final IanMalcolmChaoticianExileEffect effect) {
        super(effect);
    }

    @Override
    public IanMalcolmChaoticianExileEffect copy() {
        return new IanMalcolmChaoticianExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetPlayerID = getTargetPointer().getFirst(game, source);
        Player targetPlayer = game.getPlayer(targetPlayerID);
        MageObject sourceObject = source.getSourceObject(game);
        if (targetPlayer == null || sourceObject == null) {
            return false;
        }

        Card card = targetPlayer.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        UUID exileZoneId = CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game));
        targetPlayer.moveCardsToExile(card, source, game, true, exileZoneId, sourceObject.getIdName());
        MageObjectReference sourceMOR = new MageObjectReference(source.getSourceId(), game);
        IanMalcolmChaoticianWatcher.addCard(sourceMOR, card, game);
        return true;
    }
}

class IanMalcolmChaoticianCastEffect extends AsThoughEffectImpl {

    IanMalcolmChaoticianCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree);
        staticText = "During each player's turn, that player may cast a spell from among the cards they don't own " +
                "exiled with {this}";
    }

    private IanMalcolmChaoticianCastEffect(final IanMalcolmChaoticianCastEffect effect) {
        super(effect);
    }

    @Override
    public IanMalcolmChaoticianCastEffect copy() {
        return new IanMalcolmChaoticianCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!game.isActivePlayer(affectedControllerId) || IanMalcolmChaoticianWatcher.checkUsed(source, game)) {
            return false;
        }
        Card card = game.getCard(CardUtil.getMainCardId(game, sourceId));
        if (card == null || card.isLand(game)){
            return false;
        }
        MageObjectReference sourceMOR = new MageObjectReference(source.getSourceId(), game);
        return !card.getOwnerId().equals(affectedControllerId)
                && IanMalcolmChaoticianWatcher.checkExile(sourceMOR, card, game, 0);
    }
}

class IanMalcolmChaoticianManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    IanMalcolmChaoticianManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", and mana of any type can be spent to cast it";
    }

    private IanMalcolmChaoticianManaEffect(final IanMalcolmChaoticianManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IanMalcolmChaoticianManaEffect copy() {
        return new IanMalcolmChaoticianManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!game.isActivePlayer(affectedControllerId) || IanMalcolmChaoticianWatcher.checkUsed(source, game)) {
            return false;
        }
        MageObjectReference sourceMOR = new MageObjectReference(source.getSourceId(), game);
        Card card = game.getCard(CardUtil.getMainCardId(game, sourceId));
        if (card == null) {
            return false;
        }
        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            return IanMalcolmChaoticianWatcher.checkExile(sourceMOR, card, game, 0);
        }
        // not exiled, must be on the stack--get LKI
        CardState cardState = game.getLastKnownInformationCard(card.getMainCard().getId(), Zone.EXILED);;
        return cardState != null && IanMalcolmChaoticianWatcher.checkExile(sourceMOR, card, game, 1);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class IanMalcolmChaoticianWatcher extends Watcher {

    // Maps MOR representing the specific instance of Ian Malcolm (changes when it changes zones, i.e. blinked)
    // to many exiled cards exiled with Ian Malcolm
    private final Map<MageObjectReference, Set<MageObjectReference>> exiledMap = new HashMap<>();

    // Maps instances of approving MORs (some of which might be instances of Ian Malcolm, if his ability was used to
    // cast that spell) to UUIDs of players that have used that approving object this turn
    private final Map<MageObjectReference, Set<UUID>> usedMap = new HashMap<>();

    IanMalcolmChaoticianWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST &&
                event.hasApprovingIdentifier(MageIdentifier.IanMalcolmChaoticianWatcher)) {
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

    static void addCard(MageObjectReference sourceObj, Card card, Game game) {
        Set<MageObjectReference> set = game
                .getState()
                .getWatcher(IanMalcolmChaoticianWatcher.class)
                .exiledMap
                .computeIfAbsent(sourceObj, x -> new HashSet<>());
        MageObjectReference mor = new MageObjectReference(card, game);
        set.add(mor);
    }

    static boolean checkUsed(Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) {
            return true;
        }
        return game
                .getState()
                .getWatcher(IanMalcolmChaoticianWatcher.class)
                .usedMap
                .getOrDefault(
                        new MageObjectReference(sourceObject, game),
                        Collections.emptySet()
                ).contains(source.getControllerId());
    }

    /**
     * Returns true if card was added to the tracked exiled cards under sourceObj
     *
     * @param sourceObj exiling card
     * @param card exiled card to check
     * @param game
     * @param offset zone change counter offset: 0 = in exile, 1 = on the stack waiting to be cast
     */
    static boolean checkExile(MageObjectReference sourceObj, Card card, Game game, int offset) {
        return game
                .getState()
                .getWatcher(IanMalcolmChaoticianWatcher.class)
                .exiledMap
                .getOrDefault(sourceObj, Collections.emptySet())
                .stream()
                .anyMatch(mor -> mor.refersTo(card, game, offset));
    }
}