package mage.cards.c;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class CoramTheUndertaker extends CardImpl {

    public CoramTheUndertaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Coram, the Undertaker gets +X/+0, where X is the greatest power among creature cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                CoramTheUndertakerValue.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Whenever Coram attacks, each player mills a card.
        this.addAbility(new AttacksTriggeredAbility(
                new MillCardsEachPlayerEffect(1, TargetController.EACH_PLAYER), false
        ));

        // During each of your turns, you may play a land and cast a spell from among cards in graveyards that were put there from libraries this turn.
        this.addAbility(new CoramTheUndertakerStaticAbility());
    }

    private CoramTheUndertaker(final CoramTheUndertaker card) {
        super(card);
    }

    @Override
    public CoramTheUndertaker copy() {
        return new CoramTheUndertaker(this);
    }
}

enum CoramTheUndertakerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState()
                .getPlayersInRange(sourceAbility.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .flatMap(graveyard -> graveyard.getCards(game).stream())
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public CoramTheUndertakerValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the greatest power among creature cards in all graveyards";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class CoramTheUndertakerStaticAbility extends SimpleStaticAbility {

    CoramTheUndertakerStaticAbility() {
        super(new CoramTheUndertakerPlayLandFromGraveyardEffect());
        addEffect(new CoramTheUndertakerCastSpellFromGraveyardEffect());
        setIdentifier(MageIdentifier.CoramTheUndertakerWatcher);
        addWatcher(new CoramTheUndertakerWatcher());
    }

    private CoramTheUndertakerStaticAbility(final CoramTheUndertakerStaticAbility ability) {
        super(ability);
    }

    @Override
    public CoramTheUndertakerStaticAbility copy() {
        return new CoramTheUndertakerStaticAbility(this);
    }

    @Override
    public String getRule() {
        return "During each of your turns, you may play a land and cast a spell from among cards in graveyards that were put there from libraries this turn";
    }
}

class CoramTheUndertakerPlayLandFromGraveyardEffect extends AsThoughEffectImpl {

    CoramTheUndertakerPlayLandFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During each of your turns, you may play a land from among cards in graveyards that were put there from libraries this turn.";
    }

    private CoramTheUndertakerPlayLandFromGraveyardEffect(final CoramTheUndertakerPlayLandFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CoramTheUndertakerPlayLandFromGraveyardEffect copy() {
        return new CoramTheUndertakerPlayLandFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || !affectedControllerId.equals(game.getActivePlayerId()) // only during your turns (e.g. prevent flash creatures)
                || !Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            return false;
        }
        CoramTheUndertakerWatcher watcher = game.getState().getWatcher(CoramTheUndertakerWatcher.class);
        Card card = game.getCard(objectId);
        return card != null
                && watcher != null
                && watcher.cardPutFromGraveyardThisTurn(new MageObjectReference(card.getMainCard(), game))
                && card.isLand(game)
                && !watcher.landPlayedFromGraveyard(source, game);
    }
}

class CoramTheUndertakerCastSpellFromGraveyardEffect extends AsThoughEffectImpl {

    CoramTheUndertakerCastSpellFromGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During each of your turns, you may cast a spell from among cards in graveyards that were put there from libraries this turn.";
    }

    private CoramTheUndertakerCastSpellFromGraveyardEffect(final CoramTheUndertakerCastSpellFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CoramTheUndertakerCastSpellFromGraveyardEffect copy() {
        return new CoramTheUndertakerCastSpellFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)
                || !affectedControllerId.equals(game.getActivePlayerId()) // only during your turns (e.g. prevent flash creatures)
                || !Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            return false;
        }
        CoramTheUndertakerWatcher watcher = game.getState().getWatcher(CoramTheUndertakerWatcher.class);
        Card card = game.getCard(objectId);
        return card != null
                && watcher != null
                && watcher.cardPutFromGraveyardThisTurn(new MageObjectReference(card.getMainCard(), game))
                && card.isPermanent(game)
                && !watcher.spellCastFromGraveyard(source, game);
    }
}

/**
 * Holds track of both consumed effects, as well as cards valid to be cast by Coram.
 */
class CoramTheUndertakerWatcher extends Watcher {

    // mor -> has this mor's effect been used to play a land this turn
    private final Set<MageObjectReference> landPlayedForSource = new HashSet<>();
    // mor -> has this mor's effect been used to cast a permanent spell this turn
    private final Set<MageObjectReference> spellCastForSource = new HashSet<>();

    // mor of cards in graveyard that were put there from library this turn.
    private final Set<MageObjectReference> cardsAllowedToBePlayedOrCast = new HashSet<>();

    public CoramTheUndertakerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            if (zce == null || !Zone.LIBRARY.equals(zce.getFromZone()) || !Zone.GRAVEYARD.equals(zce.getToZone())) {
                return;
            }
            Card card = game.getCard(zce.getTargetId());
            if (card == null) {
                return;
            }
            Card mainCard = card.getMainCard();
            if (game.getState().getZone(mainCard.getId()) != Zone.GRAVEYARD) {
                // Ensure that the current zone is indeed the graveyard
                return;
            }
            cardsAllowedToBePlayedOrCast.add(new MageObjectReference(mainCard, game));
            return;
        }
        if (event.getAdditionalReference() == null
                || !MageIdentifier.CoramTheUndertakerWatcher.equals(event.getAdditionalReference().getApprovingAbility().getIdentifier())) {
            return;
        }
        if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            landPlayedForSource.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            spellCastForSource.add(event.getAdditionalReference().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        landPlayedForSource.clear();
        spellCastForSource.clear();
        cardsAllowedToBePlayedOrCast.clear();
        super.reset();
    }

    public boolean cardPutFromGraveyardThisTurn(MageObjectReference mor) {
        return cardsAllowedToBePlayedOrCast.contains(mor);
    }

    public boolean landPlayedFromGraveyard(Ability source, Game game) {
        return landPlayedForSource.contains(new MageObjectReference(source.getSourceObject(game), game));
    }

    public boolean spellCastFromGraveyard(Ability source, Game game) {
        return spellCastForSource.contains(new MageObjectReference(source.getSourceObject(game), game));
    }
}
