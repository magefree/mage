
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class VirulentWound extends CardImpl {

    public VirulentWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Put a -1/-1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance(), Outcome.UnboostCreature));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When that creature dies this turn, its controller gets a poison counter.
        this.getSpellAbility().addEffect(new VirulentWoundEffect());
    }

    private VirulentWound(final VirulentWound card) {
        super(card);
    }

    @Override
    public VirulentWound copy() {
        return new VirulentWound(this);
    }
}

class VirulentWoundEffect extends OneShotEffect {

    public VirulentWoundEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "When that creature dies this turn, its controller gets a poison counter";
    }

    public VirulentWoundEffect(final VirulentWoundEffect effect) {
        super(effect);
    }

    @Override
    public VirulentWoundEffect copy() {
        return new VirulentWoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new VirulentWoundDelayedTriggeredAbility(source.getFirstTarget()), source);
        return true;
    }
}

class VirulentWoundDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID target;

    public VirulentWoundDelayedTriggeredAbility(UUID target) {
        super(new VirulentWoundDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public VirulentWoundDelayedTriggeredAbility(VirulentWoundDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public VirulentWoundDelayedTriggeredAbility copy() {
        return new VirulentWoundDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, its controller gets a poison counter.";
    }
}

class VirulentWoundDelayedEffect extends OneShotEffect {

    protected UUID target;

    public VirulentWoundDelayedEffect(UUID target) {
        super(Outcome.Damage);
        this.target = target;
    }

    public VirulentWoundDelayedEffect(final VirulentWoundDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public VirulentWoundDelayedEffect copy() {
        return new VirulentWoundDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.addCounters(CounterType.POISON.createInstance(1), source.getControllerId(), source, game);
                return true;
            }
        }
        return false;
    }
}
