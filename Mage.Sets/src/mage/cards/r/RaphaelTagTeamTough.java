package mage.cards.r;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelTagTeamTough extends CardImpl {

    public RaphaelTagTeamTough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Raphael deals combat damage to a player for the first time each turn, untap all attacking creatures. After this combat phase, there is an additional combat phase.
        this.addAbility(new RaphaelTagTeamToughTriggeredAbility());
    }

    private RaphaelTagTeamTough(final RaphaelTagTeamTough card) {
        super(card);
    }

    @Override
    public RaphaelTagTeamTough copy() {
        return new RaphaelTagTeamTough(this);
    }
}

class RaphaelTagTeamToughTriggeredAbility extends TriggeredAbilityImpl {

    RaphaelTagTeamToughTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES));
        this.addEffect(new AdditionalCombatPhaseEffect().setText("After this combat phase, there is an additional combat phase"));
        this.setTriggerPhrase("Whenever {this} deals combat damage to a player for the first time each turn, ");
        this.addWatcher(new RaphaelTagTeamToughWatcher());
    }

    private RaphaelTagTeamToughTriggeredAbility(final RaphaelTagTeamToughTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RaphaelTagTeamToughTriggeredAbility copy() {
        return new RaphaelTagTeamToughTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return RaphaelTagTeamToughWatcher.checkEvent(event, this, game);
    }
}

class RaphaelTagTeamToughWatcher extends Watcher {

    private final Map<MageObjectReference, UUID> map = new HashMap<>();

    RaphaelTagTeamToughWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            map.putIfAbsent(new MageObjectReference(event.getSourceId(), game), event.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkEvent(GameEvent event, Ability source, Game game) {
        return Objects.equals(
                event.getId(),
                game.getState()
                        .getWatcher(RaphaelTagTeamToughWatcher.class)
                        .map
                        .get(new MageObjectReference(source.getSourceId(), game))
        );
    }
}
