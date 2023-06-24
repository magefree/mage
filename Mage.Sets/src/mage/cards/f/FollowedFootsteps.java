package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 *
 */
public final class FollowedFootsteps extends CardImpl {

    public FollowedFootsteps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, create a token that's a copy of enchanted creature.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new FollowedFootstepsEffect(), false));
    }

    private FollowedFootsteps(final FollowedFootsteps card) {
        super(card);
    }

    @Override
    public FollowedFootsteps copy() {
        return new FollowedFootsteps(this);
    }
}

class FollowedFootstepsEffect extends OneShotEffect {

    public FollowedFootstepsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of enchanted creature";
    }

    public FollowedFootstepsEffect(final FollowedFootstepsEffect effect) {
        super(effect);
    }

    @Override
    public FollowedFootstepsEffect copy() {
        return new FollowedFootstepsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that Followed Footsteps is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment != null) {
            Permanent target = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (target != null) {
                Effect effect = new CreateTokenCopyTargetEffect();
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
