package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author noxx
 */
public final class DivineDeflection extends CardImpl {

    public DivineDeflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");


        // Prevent the next X damage that would be dealt to you and/or permanents you control this turn. If damage is prevented this way, Divine Deflection deals that much damage to any target.
        this.getSpellAbility().addEffect(new DivineDeflectionPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private DivineDeflection(final DivineDeflection card) {
        super(card);
    }

    @Override
    public DivineDeflection copy() {
        return new DivineDeflection(this);
    }
}

class DivineDeflectionPreventDamageTargetEffect extends PreventionEffectImpl {

    public DivineDeflectionPreventDamageTargetEffect(Duration duration) {
        super(duration, Integer.MIN_VALUE, false, true);
        staticText = "Prevent the next X damage that would be dealt to you and/or permanents you control this turn. If damage is prevented this way, {this} deals that much damage to any target";
    }

    private DivineDeflectionPreventDamageTargetEffect(final DivineDeflectionPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public DivineDeflectionPreventDamageTargetEffect copy() {
        return new DivineDeflectionPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        /*
        If damage is dealt to multiple permanents you control, or is dealt to you and at least
        one permanent you control, you choose which of that damage to prevent if the chosen value
        for X won't prevent all the damage. For example, if 3 damage would be dealt to you and to
        each of two creatures you control, and Divine Deflection will prevent the next 3 damage,
        you might choose to prevent the next 2 damage it would deal to you and the next 1 damage
        it would deal to one of the creatures, among other choices. You don't decide until the
        point at which the damage would be dealt.
        TODO: Support to select which damage to prevent
        */

        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        /*
        Divine Deflection's effect is not a redirection effect. If it prevents damage,
        Divine Deflection (not the original source) deals damage to the targeted creature
        or player as part of that prevention effect. Divine Deflection is the source of
        the new damage, so the characteristics of the original source (such as its color,
        or whether it had lifelink or deathtouch) don't affect this damage. The new damage
        is not combat damage, even if the prevented damage was. Since you control the source
        of the new damage, if you targeted an opponent with Divine Deflection, you may
        have Divine Deflection deal its damage to a planeswalker that opponent controls.
        */
        // deal damage now
        int prevented = preventionData.getPreventedDamage();
        if (prevented > 0) {
            UUID dealDamageTo = source.getFirstTarget();
            /*
          	Whether the targeted creature or player is still a legal target is not checked after
            Divine Deflection resolves. For example, if a creature targeted by Divine Deflection
            gains shroud after Divine Deflection resolves, Divine Deflection can still deal damage
            to that creature.
            */

            Permanent permanent = game.getPermanent(dealDamageTo);
            if (permanent != null) {
                game.informPlayers("Dealing " + prevented + " to " + permanent.getName() + " instead");
                permanent.damage(prevented, source.getSourceId(), source, game, false, true);
            }
            Player player = game.getPlayer(dealDamageTo);
            if (player != null) {
                game.informPlayers("Dealing " + prevented + " to " + player.getLogName() + " instead");
                player.damage(prevented, source.getSourceId(), source, game);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (amountToPrevent == Integer.MIN_VALUE) {
                amountToPrevent = source.getManaCostsToPay().getX();
            }
            //   check permanent first
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.isControlledBy(source.getControllerId())) {
                    return true;
                }
            }
            //   check player
            return source.isControlledBy(event.getTargetId());
        }
        return false;
    }

}
