package mage.cards.c;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

public class ChandraHopesBeacon extends CardImpl {
    public ChandraHopesBeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.CHANDRA);
        this.startingLoyalty = 5;

        //Whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy. This ability
        //triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true).withSpellName("it"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, true
        ).setTriggersOnce(true));

        //+2: Add two mana in any combination of colors.
        this.addAbility(new LoyaltyAbility(new AddManaInAnyCombinationEffect(2), 2));

        //+1: Exile the top five cards of your library. Until the end of your next turn, you may cast an instant or
        //    sorcery spell from among those exiled cards.
        this.addAbility(new LoyaltyAbility(new ChandraHopesBeaconEffect(), 1), new ChandraHopesBeaconWatcher());

        //−X: Chandra, Hope’s Beacon deals X damage to each of up to two targets.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(
                GetXLoyaltyValue.instance, true, "each of up to two targets"
        ));
        loyaltyAbility.addTarget(new TargetAnyTarget(0, 2));
        this.addAbility(loyaltyAbility);
    }

    private ChandraHopesBeacon(final ChandraHopesBeacon card) {
        super(card);
    }

    @Override
    public ChandraHopesBeacon copy() {
        return new ChandraHopesBeacon(this);
    }
}

class ChandraHopesBeaconEffect extends OneShotEffect {

    ChandraHopesBeaconEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top five cards of your library. " +
                "Until the end of your next turn, you may cast an instant or sorcery spell from among those exiled cards";
    }

    private ChandraHopesBeaconEffect(final ChandraHopesBeaconEffect effect) {
        super(effect);
    }

    @Override
    public ChandraHopesBeaconEffect copy() {
        return new ChandraHopesBeaconEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        player.moveCards(cards, Zone.EXILED, source, game);
        Cards instantsOrSorceries = new CardsImpl(cards.stream()
                .map(game::getCard)
                .filter(card -> card.isInstantOrSorcery(game))
                .map(MageItem::getId)
                .collect(Collectors.toSet()));
        game.addEffect(new ChandraHopesBeaconPlayEffect(instantsOrSorceries, game), source);
        return true;
    }
}

class ChandraHopesBeaconPlayEffect extends AsThoughEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    ChandraHopesBeaconPlayEffect(Cards cards, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        cards.stream()
                .map(uuid -> new MageObjectReference(uuid, game))
                .forEach(morSet::add);
    }

    private ChandraHopesBeaconPlayEffect(final ChandraHopesBeaconPlayEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public ChandraHopesBeaconPlayEffect copy() {
        return new ChandraHopesBeaconPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && morSet.stream().anyMatch(mor -> mor.refersTo(objectIdToCast, game))
                && ChandraHopesBeaconWatcher.checkRef(source, morSet, game);
    }
}

class ChandraHopesBeaconWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> morMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = new HashSet<>();

    ChandraHopesBeaconWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || event.getAdditionalReference() == null) {
            return;
        }
        MageObjectReference mor = event.getAdditionalReference().getApprovingMageObjectReference();
        Spell spell = game.getSpell(event.getTargetId());
        if (mor == null || spell == null) {
            return;
        }
        morMap.computeIfAbsent(mor, x -> new HashSet<>())
                .add(new MageObjectReference(spell.getMainCard(), game, -1));
    }

    static boolean checkRef(Ability source, Set<MageObjectReference> morSet, Game game) {
        ChandraHopesBeaconWatcher watcher = game.getState().getWatcher(ChandraHopesBeaconWatcher.class);
        return watcher != null
                && watcher
                .morMap
                .getOrDefault(new MageObjectReference(source.getSourceObject(game), game), emptySet)
                .stream()
                .noneMatch(morSet::contains);
    }
}
