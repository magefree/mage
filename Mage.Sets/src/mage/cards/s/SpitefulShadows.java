package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class SpitefulShadows extends CardImpl {

    public SpitefulShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature is dealt damage, it deals that much damage to its controller.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, new SpitefulShadowsEffect(),
                false, SetTargetPointer.PERMANENT));
    }

    private SpitefulShadows(final SpitefulShadows card) {
        super(card);
    }

    @Override
    public SpitefulShadows copy() {
        return new SpitefulShadows(this);
    }
}

class SpitefulShadowsEffect extends OneShotEffect {

    public SpitefulShadowsEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to its controller";
    }

    public SpitefulShadowsEffect(final SpitefulShadowsEffect effect) {
        super(effect);
    }

    @Override
    public SpitefulShadowsEffect copy() {
        return new SpitefulShadowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damage");
        if (damageAmount != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent == null) {
                FixedTarget fixedTarget = (FixedTarget) getTargetPointer();
                permanent = (Permanent) game.getLastKnownInformation(fixedTarget.getTarget(), Zone.BATTLEFIELD, fixedTarget.getZoneChangeCounter());
            }
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    player.damage(damageAmount, permanent.getId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
