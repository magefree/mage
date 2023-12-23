
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class SoltariGuerrillas extends CardImpl {

    public SoltariGuerrillas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.subtype.add(SubType.SOLTARI);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());

        // {0}: The next time Soltari Guerrillas would deal combat damage to an opponent this turn, it deals that damage to target creature instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoltariGuerrillasReplacementEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SoltariGuerrillas(final SoltariGuerrillas card) {
        super(card);
    }

    @Override
    public SoltariGuerrillas copy() {
        return new SoltariGuerrillas(this);
    }
}

class SoltariGuerrillasReplacementEffect extends PreventionEffectImpl {

    SoltariGuerrillasReplacementEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        staticText = "The next time {this} would deal combat damage to an opponent this turn, it deals that damage to target creature instead";
    }

    private SoltariGuerrillasReplacementEffect(final SoltariGuerrillasReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                return controller.hasOpponent(event.getTargetId(), game);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionResult = preventDamageAction(event, source, game);
        if (preventionResult.getPreventedDamage() > 0) {
            Permanent redirectTo = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (redirectTo != null) {
                game.informPlayers("Dealing " + preventionResult.getPreventedDamage() + " to " + redirectTo.getLogName() + " instead.");
                DamageEvent damageEvent = (DamageEvent) event;
                redirectTo.damage(preventionResult.getPreventedDamage(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            }
            discard(); // (only once)
        }
        return false;
    }

    @Override
    public SoltariGuerrillasReplacementEffect copy() {
        return new SoltariGuerrillasReplacementEffect(this);
    }
}
