
package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSource;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author ThomasLerner
 */
public final class DarkSphere extends CardImpl {

    public DarkSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");


        // {tap}, Sacrifice Dark Sphere: The next time a source of your choice would deal damage to you this turn, prevent half that damage, rounded down.
        Ability ability = new SimpleActivatedAbility(new DarkSpherePreventionEffect(), new TapSourceCost());
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


class DarkSpherePreventionEffect extends PreventNextDamageFromChosenSourceEffect {

    private static final FilterSource filter = new FilterSource("source");

    public DarkSpherePreventionEffect() {
        super(Duration.EndOfTurn, true, filter);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent half that damage, rounded down";
    }

    private DarkSpherePreventionEffect(final DarkSpherePreventionEffect effect) {
        super(effect);
    }

    @Override
    public DarkSpherePreventionEffect copy() {
        return new DarkSpherePreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        DamageEvent damageEvent = (DamageEvent) event;
        int damage = damageEvent.getAmount();
        if (controller == null || damage <= 0) {
            return false;
        }
        controller.damage(
                (int) Math.ceil(damage / 2.0), damageEvent.getSourceId(), source, game,
                damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects()
        );
        StringBuilder sb = new StringBuilder(sourceObject != null ? sourceObject.getLogName() : "");
        sb.append(": ").append(damage / 2).append(" damage prevented");
        sb.append(" from ").append(controller.getLogName());
        game.informPlayers(sb.toString());
        discard(); // only one use
        return true;
    }
}
