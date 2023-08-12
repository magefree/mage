package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */

/*
 * Applying this redirection effect doesn't change whether the damage is combat damage.
 *
 * If noncombat damage would be dealt to a planeswalker you control, the planeswalker
 * redirection effect and Palisade Giant's redirection effect apply in whichever
 * order you choose. No matter which order you choose to apply them in, that damage
 * will be dealt to Palisade Giant instead.
 *
 * If you control more than one Palisade Giant, you choose which redirection effect
 * to apply. You can't divide damage dealt by one source. For example, if an attacking
 * creature would deal 8 damage to you and you control two Palisade Giants, you may
 * have that damage dealt to either of the Palisade Giants. You can't have 4 damage
 * dealt to each Giant or choose to have the 8 damage dealt to you.
 */



public final class PalisadeGiant extends CardImpl {

    public PalisadeGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // All damage that would be dealt to you or another permanent you control is dealt to Palisade Giant instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PalisadeGiantReplacementEffect()));
    }

    private PalisadeGiant(final PalisadeGiant card) {
        super(card);
    }

    @Override
    public PalisadeGiant copy() {
        return new PalisadeGiant(this);
    }
}

class PalisadeGiantReplacementEffect extends ReplacementEffectImpl {

    PalisadeGiantReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to you and other permanents you control is dealt to {this} instead";
    }

    PalisadeGiantReplacementEffect(final PalisadeGiantReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (targetPermanent != null &&
                    targetPermanent.isControlledBy(source.getControllerId()) &&
                    !CardUtil.haveSameNames(targetPermanent, sourcePermanent)) {  // no redirection from or to other Palisade Giants
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            // get name of old target
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            StringBuilder message = new StringBuilder();
            message.append(sourcePermanent.getName()).append(": gets ");
            message.append(damageEvent.getAmount()).append(" damage redirected from ");
            if (targetPermanent != null) {
                message.append(targetPermanent.getName());
            } else {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    message.append(targetPlayer.getLogName());
                } else {
                    message.append("unknown");
                }

            }
            game.informPlayers(message.toString());
            // redirect damage
            sourcePermanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PalisadeGiantReplacementEffect copy() {
        return new PalisadeGiantReplacementEffect(this);
    }
}
