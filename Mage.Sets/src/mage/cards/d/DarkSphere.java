
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 *
 * @author ThomasLerner
 */
public final class DarkSphere extends CardImpl {

    public DarkSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");
        

        // {tap}, Sacrifice Dark Sphere: The next time a source of your choice would deal damage to you this turn, prevent half that damage, rounded down.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DarkSpherePreventionEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DarkSphere(final DarkSphere card) {
        super(card);
    }

    @Override
    public DarkSphere copy() {
        return new DarkSphere(this);
    }
}


class DarkSpherePreventionEffect extends ReplacementEffectImpl {

    private final TargetSource targetSource;
    
    public DarkSpherePreventionEffect() {
        super(Duration.OneUse, Outcome.RedirectDamage);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent half that damage, rounded down";
        this.targetSource = new TargetSource(new FilterObject("source of your choice"));
    }
    
    public DarkSpherePreventionEffect(final DarkSpherePreventionEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public DarkSpherePreventionEffect copy() {
        return new DarkSpherePreventionEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            controller.damage((int) Math.ceil(damageEvent.getAmount() / 2.0), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            StringBuilder sb = new StringBuilder(sourceObject != null ? sourceObject.getLogName() : "");
            sb.append(": ").append(damageEvent.getAmount() / 2).append(" damage prevented");
            sb.append(" from ").append(controller.getLogName());
            game.informPlayers(sb.toString());
            discard(); // only one use           
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(targetSource.getFirstTarget()) && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }
    
}
