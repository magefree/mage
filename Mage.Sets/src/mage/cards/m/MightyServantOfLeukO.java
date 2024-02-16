package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class MightyServantOfLeukO extends CardImpl {

    public MightyServantOfLeukO(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward—Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost(), false));

        // Whenever Mighty Servant of Leuk-o becomes crewed for the first time each turn, if it was crewed by exactly two creatures, it gains "Whenever this creature deals combat damage to a player, draw two cards" until end of turn.
        this.addAbility(new MightyServantOfLeukOTriggeredAbility());

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private MightyServantOfLeukO(final MightyServantOfLeukO card) {
        super(card);
    }

    @Override
    public MightyServantOfLeukO copy() {
        return new MightyServantOfLeukO(this);
    }
}

class MightyServantOfLeukOTriggeredAbility extends TriggeredAbilityImpl {

    MightyServantOfLeukOTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(2), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player, "), Duration.EndOfTurn));
        this.addWatcher(new MightyServantOfLeukOWatcher());
    }

    private MightyServantOfLeukOTriggeredAbility(final MightyServantOfLeukOTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MightyServantOfLeukOTriggeredAbility copy() {
        return new MightyServantOfLeukOTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VEHICLE_CREWED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getSourceId())
                && MightyServantOfLeukOWatcher.checkVehicle(this, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes crewed for the first time each turn, if it was crewed by exactly two creatures, it gains \"Whenever this creature deals combat damage to a player, draw two cards\" until end of turn.";
    }
}

class MightyServantOfLeukOWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> crewCount = new HashMap<>();
    private final Map<MageObjectReference, Set<MageObjectReference>> crewMap = new HashMap<>();

    MightyServantOfLeukOWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        Permanent vehicle;
        Permanent crewer;
        switch (event.getType()) {
            case VEHICLE_CREWED:
                vehicle = game.getPermanent(event.getTargetId());
                crewer = null;
                break;
            case CREWED_VEHICLE:
                vehicle = game.getPermanent(event.getSourceId());
                crewer = game.getPermanent(event.getTargetId());
                break;
            default:
                return;
        }
        if (vehicle == null) {
            return;
        }
        if (crewer == null) {
            crewCount.compute(new MageObjectReference(vehicle, game), CardUtil::setOrIncrementValue);
            return;
        }
        crewMap.computeIfAbsent(
                new MageObjectReference(vehicle, game), x -> new HashSet<>()
        ).add(new MageObjectReference(crewer, game));
    }

    @Override
    public void reset() {
        super.reset();
        crewCount.clear();
        crewMap.clear();
    }

    public static boolean checkVehicle(Ability source, Game game) {
        MightyServantOfLeukOWatcher watcher = game
                .getState()
                .getWatcher(MightyServantOfLeukOWatcher.class);
        MageObjectReference mor = new MageObjectReference(game.getPermanent(source.getSourceId()), game);
        return watcher
                .crewCount
                .getOrDefault(mor, 0) < 2
                && watcher
                .crewMap
                .getOrDefault(mor, Collections.emptySet())
                .size() == 2;
    }
}
