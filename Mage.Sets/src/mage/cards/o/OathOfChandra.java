
package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class OathOfChandra extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public OathOfChandra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        addSuperType(SuperType.LEGENDARY);

        // When Oath of Chandra enters the battlefield, it deals 3 damage to target creature an opponent controls.
        Effect effect = new DamageTargetEffect(3);
        effect.setText("it deals 3 damage to target creature an opponent controls");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, Oath of Chandra deals 2 damage to each opponent.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, new StaticValue(2), TargetController.OPPONENT),
                TargetController.ANY, false), OathOfChandraCondition.instance,
                "At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, {this} deals 2 damage to each opponent."), new OathOfChandraWatcher());
    }

    public OathOfChandra(final OathOfChandra card) {
        super(card);
    }

    @Override
    public OathOfChandra copy() {
        return new OathOfChandra(this);
    }
}

enum OathOfChandraCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        OathOfChandraWatcher watcher = (OathOfChandraWatcher) game.getState().getWatchers().get(OathOfChandraWatcher.class.getSimpleName());
        return watcher != null && watcher.enteredPlaneswalkerForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a planeswalker entered the battlefield under your control this turn";
    }

}

class OathOfChandraWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public OathOfChandraWatcher() {
        super(OathOfChandraWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public OathOfChandraWatcher(final OathOfChandraWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().isPlaneswalker()) {
                players.add(zEvent.getTarget().getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        players.clear();
    }

    public boolean enteredPlaneswalkerForPlayer(UUID playerId) {
        return players.contains(playerId);
    }

    @Override
    public OathOfChandraWatcher copy() {
        return new OathOfChandraWatcher(this);
    }

}
