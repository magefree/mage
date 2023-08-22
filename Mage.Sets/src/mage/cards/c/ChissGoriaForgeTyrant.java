package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Xanderhall, xenohedron
 */
public final class ChissGoriaForgeTyrant extends CardImpl {
    
    public ChissGoriaForgeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Whenever Chiss-Goria, Forge Tyrant attacks, exile the top five cards of your library. You may cast an artifact spell from among them this turn. If you do, it has affinity for artifacts.
        this.addAbility(new AttacksTriggeredAbility(new ChissGoriaForgeTyrantEffect()), new ChissGoriaForgeTyrantWatcher());
    }

    private ChissGoriaForgeTyrant(final ChissGoriaForgeTyrant card) {
        super(card);
    }

    @Override
    public ChissGoriaForgeTyrant copy() {
        return new ChissGoriaForgeTyrant(this);
    }
}

class ChissGoriaForgeTyrantEffect extends OneShotEffect {

    ChissGoriaForgeTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top five cards of your library. You may cast an artifact spell from among them this turn. If you do, it has affinity for artifacts.";
    }

    private ChissGoriaForgeTyrantEffect(final ChissGoriaForgeTyrantEffect effect) {
        super(effect);
    }

    @Override
    public ChissGoriaForgeTyrantEffect copy() {
        return new ChissGoriaForgeTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Return if no cards exiled, effect still applied
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        if (cards.isEmpty()) {
            return true;
        }
        player.moveCardsToExile(cards.getCards(game), source, game, false, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        cards.retainZone(Zone.EXILED, game);

        Cards castableCards = new CardsImpl(cards.getCards(StaticFilters.FILTER_CARD_NON_LAND, game));
        Set<MageObjectReference> morSet = new HashSet<>();
        castableCards.stream()
                .map(uuid -> new MageObjectReference(uuid, game))
                .forEach(morSet::add);

        game.addEffect(new ChissGoriaForgeTyrantCanPlayEffect(morSet), source);
        game.addEffect(new ChissGoriaForgeTyrantAffinityEffect(morSet), source);
        return true;
    }
}

class ChissGoriaForgeTyrantCanPlayEffect extends AsThoughEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    ChissGoriaForgeTyrantCanPlayEffect(Set<MageObjectReference> morSet) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.morSet.addAll(morSet);
    }

    private ChissGoriaForgeTyrantCanPlayEffect(final ChissGoriaForgeTyrantCanPlayEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public ChissGoriaForgeTyrantCanPlayEffect copy() {
        return new ChissGoriaForgeTyrantCanPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        Card card = game.getCard(objectIdToCast);
        
        return (card != null && card.isArtifact(game)
            && morSet.stream().anyMatch(mor -> mor.refersTo(objectIdToCast, game))
            && ChissGoriaForgeTyrantWatcher.checkRef(source, morSet, game));
    }
}

class ChissGoriaForgeTyrantAffinityEffect extends ContinuousEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    public ChissGoriaForgeTyrantAffinityEffect(Set<MageObjectReference> morSet) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        this.morSet.addAll(morSet);
    }

    protected ChissGoriaForgeTyrantAffinityEffect(final ChissGoriaForgeTyrantAffinityEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public ChissGoriaForgeTyrantAffinityEffect copy() {
        return new ChissGoriaForgeTyrantAffinityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
         if (!ChissGoriaForgeTyrantWatcher.checkRef(source, morSet, game)) {
             discard();
             return false;
         }

        for (Card card : game.getExile().getAllCardsByRange(game, source.getControllerId())) {
            if (morSet.contains(new MageObjectReference(card, game)) && card.isArtifact(game)) {
                game.getState().addOtherAbility(card, new AffinityForArtifactsAbility());
            }
        }

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Card card = game.getCard(stackObject.getSourceId());
            if (card != null && morSet.contains(new MageObjectReference(card, game, -1))) {
                game.getState().addOtherAbility(card, new AffinityForArtifactsAbility());
            }
        }
        return true;
    }
}

class ChissGoriaForgeTyrantWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> morMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = new HashSet<>();

    ChissGoriaForgeTyrantWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getAdditionalReference() == null) {
            return;
        }
        MageObjectReference mor = event.getAdditionalReference().getApprovingMageObjectReference();
        Spell spell = game.getSpell(event.getTargetId());
        if (mor == null || spell == null) {
            return;
        }
        morMap.computeIfAbsent(mor, x -> new HashSet<>()).add(new MageObjectReference(spell.getMainCard(), game, -1));
    }

    static boolean checkRef(Ability source, Set<MageObjectReference> morSet, Game game) {
        ChissGoriaForgeTyrantWatcher watcher = game.getState().getWatcher(ChissGoriaForgeTyrantWatcher.class);
        return watcher != null
                && watcher.morMap.getOrDefault(new MageObjectReference(source.getSourceObject(game), game), emptySet)
                .stream()
                .noneMatch(morSet::contains);
    }
}
