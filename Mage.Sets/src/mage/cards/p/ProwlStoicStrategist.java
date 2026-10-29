package mage.cards.p;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class ProwlStoicStrategist extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent("other target tapped creature or Vehicle");
    private static final FilterControlledPermanent filterBack = new FilterControlledPermanent("another creature or Vehicle you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter.add(TappedPredicate.TAPPED);
        filter.add(AnotherPredicate.instance);
        filterBack.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filterBack.add(AnotherPredicate.instance);
    }

    public ProwlStoicStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{3}{W}",
                "Prowl, Pursuit Vehicle",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "W");

        // Prowl, Stoic Strategist
        this.getLeftHalfCard().setPT(3, 3);

        // More Than Meets the Eye {2}{W}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{W}"));

        // Whenever Prowl attacks, exile up to one other target tapped creature or Vehicle. For as long as that card remains exiled, its owner may play it.
        Ability ability = new AttacksTriggeredAbility(new ProwlStoicStrategistExileEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.getLeftHalfCard().addAbility(ability);

        // Whenever a player plays a card exiled with Prowl, you draw a card and convert Prowl.
        this.getLeftHalfCard().addAbility(new ProwlStoicStrategistTriggeredAbility());

        // Prowl, Pursuit Vehicle
        this.getRightHalfCard().setPT(2, 3);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Whenever another creature or Vehicle you control enters, put a +1/+1 counter on Prowl. If this is the second time this ability has resolved this turn, convert Prowl.
        Ability backAbility = new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filterBack
        );
        backAbility.addEffect(new IfAbilityHasResolvedXTimesEffect(
                2, new TransformSourceEffect().setText("convert {this}")
        ));
        backAbility.addWatcher(new AbilityResolvedWatcher());
        this.getRightHalfCard().addAbility(backAbility);
    }

    private ProwlStoicStrategist(final ProwlStoicStrategist card) {
        super(card);
    }

    @Override
    public ProwlStoicStrategist copy() {
        return new ProwlStoicStrategist(this);
    }
}

class ProwlStoicStrategistExileEffect extends OneShotEffect {

    ProwlStoicStrategistExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one other target tapped creature or Vehicle. " +
                "For as long as that card remains exiled, its owner may play it";
    }

    private ProwlStoicStrategistExileEffect(final ProwlStoicStrategistExileEffect effect) {
        super(effect);
    }

    @Override
    public ProwlStoicStrategistExileEffect copy() {
        return new ProwlStoicStrategistExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || permanent == null || sourceObject == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        controller.moveCardsToExile(permanent, source, game, true, exileId, sourceObject.getIdName());
        // tokens cease to exist in exile, so there is nothing left to play
        Card card = game.getCard(permanent.getId());
        if (card == null || game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        card = card.getMainCard();
        ProwlStoicStrategistWatcher.addCard(exileId, card, game);
        CardUtil.makeCardPlayable(game, source, card, false, Duration.Custom, false, card.getOwnerId(), null);
        return true;
    }
}

class ProwlStoicStrategistTriggeredAbility extends TriggeredAbilityImpl {

    ProwlStoicStrategistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1, true));
        this.addEffect(new TransformSourceEffect().setText("and convert {this}"));
        this.addWatcher(new ProwlStoicStrategistWatcher());
        setTriggerPhrase("Whenever a player plays a card exiled with {this}, ");
    }

    private ProwlStoicStrategistTriggeredAbility(final ProwlStoicStrategistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ProwlStoicStrategistTriggeredAbility copy() {
        return new ProwlStoicStrategistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, getSourceId(), game.getState().getZoneChangeCounter(getSourceId()));
        return ProwlStoicStrategistWatcher.checkCard(exileId, card.getMainCard(), game);
    }
}

/**
 * Remembers which cards were exiled with each Prowl, since a played card has already left exile
 * when the play event fires. Not cleared between turns, because the card may be played on any later turn.
 */
class ProwlStoicStrategistWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> exiledCards = new HashMap<>();

    ProwlStoicStrategistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    @Override
    public void reset() {
        // keep the exiled cards for the whole game
        super.reset();
    }

    static void addCard(UUID exileId, Card card, Game game) {
        ProwlStoicStrategistWatcher watcher = game.getState().getWatcher(ProwlStoicStrategistWatcher.class);
        if (watcher != null) {
            watcher.exiledCards.computeIfAbsent(exileId, x -> new HashSet<>()).add(new MageObjectReference(card, game));
        }
    }

    static boolean checkCard(UUID exileId, Card card, Game game) {
        ProwlStoicStrategistWatcher watcher = game.getState().getWatcher(ProwlStoicStrategistWatcher.class);
        // the play moved the card out of exile, so compare against its previous zone change
        return watcher != null
                && watcher.exiledCards
                .getOrDefault(exileId, Collections.emptySet())
                .contains(new MageObjectReference(card, game, -1));
    }
}
