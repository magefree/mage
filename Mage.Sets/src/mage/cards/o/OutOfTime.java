package mage.cards.o;

import java.util.*;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class OutOfTime extends CardImpl {

    public OutOfTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Out of Time enters the battlefield, untap all creatures, then phase them out until Out of Time leaves the battlefield.
        // Put a time counter on Out of Time for each creature phased out this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OutOfTimePhaseOutEffect()));

        // Vanishing
        this.addAbility(new VanishingUpkeepAbility(0, "enchantment"));
        this.addAbility(new VanishingSacrificeAbility());
    }

    private OutOfTime(final OutOfTime card) {
        super(card);
    }

    @Override
    public OutOfTime copy() {
        return new OutOfTime(this);
    }
}

class OutOfTimePhaseOutEffect extends OneShotEffect {

    public OutOfTimePhaseOutEffect() {
        super(Outcome.Detriment);
        this.staticText = "untap all creatures, then phase them out until {this} leaves the battlefield. "
                + "Put a time counter on {this} for each creature phased out this way";
    }

    private OutOfTimePhaseOutEffect(final OutOfTimePhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public OutOfTimePhaseOutEffect copy() {
        return new OutOfTimePhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source.getSourceId(), game);
        int numCreatures = creatures.size();
        if (numCreatures > 0) {
            Set<UUID> creatureIds = new HashSet<>(numCreatures);
            for (Permanent creature : creatures) {
                creature.untap(game);
                creatureIds.add(creature.getId());
            }
            // https://magic.wizards.com/en/articles/archive/feature/modern-horizons-2-release-notes-2021-06-04
            // If Out of Time leaves the battlefield before its enter the battlefield trigger resolves, creatures will untap, but they won't phase out.
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                new PhaseOutAllEffect(new ArrayList<>(creatureIds)).apply(game, source);
                new AddCountersSourceEffect(CounterType.TIME.createInstance(numCreatures)).apply(game, source);
                game.getState().setValue(CardUtil.getCardZoneString("phasedOutCreatures", source.getSourceId(), game), creatureIds);
                game.addDelayedTriggeredAbility(new OutOfTimeDelayedTriggeredAbility(), source);
                game.addEffect(new OutOfTimeReplcementEffect(), source);
            }
        }
        return true;
    }
}

class OutOfTimeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public OutOfTimeDelayedTriggeredAbility() {
        super(new OutOfTimeLeavesBattlefieldEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private OutOfTimeDelayedTriggeredAbility(final OutOfTimeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OutOfTimeDelayedTriggeredAbility copy() {
        return new OutOfTimeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            return ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
        }
        return false;
    }
}

class OutOfTimeLeavesBattlefieldEffect extends OneShotEffect {

    public OutOfTimeLeavesBattlefieldEffect() {
        super(Outcome.Benefit);
    }

    private OutOfTimeLeavesBattlefieldEffect(final OutOfTimeLeavesBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public OutOfTimeLeavesBattlefieldEffect copy() {
        return new OutOfTimeLeavesBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> creatureIds = (Set<UUID>) game.getState().getValue(CardUtil.getCardZoneString(
                "phasedOutCreatures", source.getSourceId(), game, true));
        if (creatureIds != null) {
            for (UUID creatureId : creatureIds) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null && !creature.isPhasedIn()) {
                    creature.phaseIn(game);
                }
            }
            return true;
        }
        return false;
    }
}

// Stops creatures from phasing back in on their controller's next turn
class OutOfTimeReplcementEffect extends ReplacementEffectImpl {

    public OutOfTimeReplcementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
    }

    private OutOfTimeReplcementEffect(final OutOfTimeReplcementEffect effect) {
        super(effect);
    }

    @Override
    public OutOfTimeReplcementEffect copy() {
        return new OutOfTimeReplcementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Set<UUID> creatureIds = (Set<UUID>) game.getState().getValue(CardUtil.getCardZoneString(
                "phasedOutCreatures", source.getSourceId(), game));
        return creatureIds != null && creatureIds.contains(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }
}
