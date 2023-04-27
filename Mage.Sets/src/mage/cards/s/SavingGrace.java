
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class SavingGrace extends CardImpl {

    public SavingGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Saving Grace enters the battlefield, all damage that would be dealt this turn to you and permanents you control is dealt to enchanted creature instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SavingGraceReplacementEffect(), false));

        // Enchanted creature gets +0/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(0, 3, Duration.WhileOnBattlefield)));
    }

    private SavingGrace(final SavingGrace card) {
        super(card);
    }

    @Override
    public SavingGrace copy() {
        return new SavingGrace(this);
    }
}

class SavingGraceReplacementEffect extends ReplacementEffectImpl {

    SavingGraceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "all damage that would be dealt this turn to you and permanents you control is dealt to enchanted creature instead.";
    }

    SavingGraceReplacementEffect(final SavingGraceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            return event.getPlayerId().equals(source.getControllerId());
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null
                    && targetPermanent.isControlledBy(source.getControllerId())) {
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
            Permanent creature = game.getPermanent(sourcePermanent.getAttachedTo());

            if (creature == null) {
                return false;
            }

            // Name of old target
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            StringBuilder message = new StringBuilder();
            message.append(creature.getName()).append(": gets ");
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
            // Redirect damage
            creature.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SavingGraceReplacementEffect copy() {
        return new SavingGraceReplacementEffect(this);
    }
}
