package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author miesma
 */
public final class DesertWereWorm extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public DesertWereWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        //This creature gets +2/+0 for each Mountain you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter, 2),
                StaticValue.get(0),
                Duration.WhileOnBattlefield)));

        // Whenever you attack with creatures with total power 12 or greater for the first time each turn,
        // untap all attacking creatures. After this phase, there is an additional combat phase.
        Ability ability = new DesertWereWormAttackAbility(new UntapAllControllerEffect(
                StaticFilters.FILTER_ATTACKING_CREATURES,
                "untap all attacking creatures"
        ));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        ability.addWatcher(new DesertWereWormWatcher());
        this.addAbility(ability);
    }

    private DesertWereWorm(final DesertWereWorm card) {
        super(card);
    }

    @Override
    public DesertWereWorm copy() {
        return new DesertWereWorm(this);
    }

}

class DesertWereWormAttackAbility extends TriggeredAbilityImpl {

    public DesertWereWormAttackAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.withRuleTextReplacement(false);
        setTriggerPhrase("Whenever you attack with creatures with total power 12 or greater for the first time each turn, ");
    }

    private DesertWereWormAttackAbility(final DesertWereWormAttackAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getCombat().getAttackingPlayerId())) {
            return false;
        }
        DesertWereWormWatcher watcher = game.getState().getWatcher(DesertWereWormWatcher.class);
        if (watcher != null) {
            // Only first time condition gets met
            return watcher.getFirstTimeMet() && watcher.conditionMet();
        }
        return false;
    }

    @Override
    public DesertWereWormAttackAbility copy() {
        return new DesertWereWormAttackAbility(this);
    }
}

class DesertWereWormWatcher extends Watcher {

    boolean firstTimeMet = false;

    DesertWereWormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DECLARED_ATTACKERS) {
            return;
        }
        // First attack with power 12 or greater not overall first attack
        // Could be triggered in an additional combat
        // From any creatures you control attacking
        List<Permanent> attackers = game
                 .getCombat()
                 .getAttackers()
                 .stream()
                 .map(game::getPermanent)
                 .filter(Objects::nonNull)
                 .filter(permanent -> StaticFilters.FILTER_PERMANENT_CREATURES.match(permanent, controllerId, null, game))
                 .collect(Collectors.toList());
        int power = 0;
        if (!attackers.isEmpty()) {
            // Check power of attackers
            for (Permanent attacker : attackers) {
                if (attacker != null) {
                     power += attacker.getPower().getValue();
                }
            }
        }
        // Only once both can be true
        if (!condition && !firstTimeMet && power >= 12) {
            firstTimeMet = true;
            condition = true;
        } else {
            firstTimeMet = false;
        }
    }

    @Override
    public void reset() {
        super.reset();
        firstTimeMet = false;
    }

    public boolean getFirstTimeMet() {
        return firstTimeMet;
    }
}

