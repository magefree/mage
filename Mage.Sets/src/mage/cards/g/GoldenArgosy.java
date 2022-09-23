package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GoldenArgosy extends CardImpl {

    public GoldenArgosy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Whenever Golden Argosy attacks, exile each creature that crewed it this turn. Return them to the battlefield tapped under their owner's control at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new GoldenArgosyEffect()), new GoldenArgosyWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private GoldenArgosy(final GoldenArgosy card) {
        super(card);
    }

    @Override
    public GoldenArgosy copy() {
        return new GoldenArgosy(this);
    }
}

class GoldenArgosyEffect extends OneShotEffect {

    GoldenArgosyEffect() {
        super(Outcome.Benefit);
        staticText = "exile each creature that crewed it this turn. Return them to the battlefield " +
                "tapped under their owner's control at the beginning of the next end step";
    }

    private GoldenArgosyEffect(final GoldenArgosyEffect effect) {
        super(effect);
    }

    @Override
    public GoldenArgosyEffect copy() {
        return new GoldenArgosyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(GoldenArgosyWatcher.getCrewers(source, game));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false)
                        .setTargetPointer(new FixedTargets(cards, game))
                        .setText("return them to the battlefield tapped")
        ), source);
        return true;
    }
}

class GoldenArgosyWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> crewMap = new HashMap<>();

    GoldenArgosyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CREWED_VEHICLE) {
            return;
        }
        Permanent vehicle = game.getPermanent(event.getSourceId());
        Permanent crewer = game.getPermanent(event.getTargetId());
        if (vehicle == null) {
            return;
        }
        crewMap.computeIfAbsent(
                new MageObjectReference(vehicle, game), x -> new HashSet<>()
        ).add(new MageObjectReference(crewer, game));
    }

    @Override
    public void reset() {
        super.reset();
        crewMap.clear();
    }

    static Set<Permanent> getCrewers(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(GoldenArgosyWatcher.class)
                .crewMap
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
