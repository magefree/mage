
package mage.cards.j;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class JadeMonolith extends CardImpl {

    public JadeMonolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}: The next time a source of your choice would deal damage to target creature this turn, that source deals that damage to you instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JadeMonolithRedirectionEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JadeMonolith(final JadeMonolith card) {
        super(card);
    }

    @Override
    public JadeMonolith copy() {
        return new JadeMonolith(this);
    }
}

class JadeMonolithRedirectionEffect extends ReplacementEffectImpl {

    private final TargetSource targetSource;
    
    public JadeMonolithRedirectionEffect() {
        super(Duration.OneUse, Outcome.RedirectDamage);
        this.staticText = "The next time a source of your choice would deal damage to target creature this turn, that source deals that damage to you instead";
        this.targetSource = new TargetSource(new FilterObject("source of your choice"));
    }
    
    public JadeMonolithRedirectionEffect(final JadeMonolithRedirectionEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public JadeMonolithRedirectionEffect copy() {
        return new JadeMonolithRedirectionEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null && targetCreature != null && sourceObject != null) {
            controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            StringBuilder sb = new StringBuilder(sourceObject.getLogName());
            sb.append(": ").append(damageEvent.getAmount()).append(" damage redirected from ").append(targetCreature.getLogName());
            sb.append(" to ").append(controller.getLogName());
            game.informPlayers(sb.toString());
            discard(); // only one use           
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(targetSource.getFirstTarget())
                && event.getTargetId().equals(source.getFirstTarget());
    }
    
}
