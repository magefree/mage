package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class FollowedFootsteps extends CardImpl {

    public FollowedFootsteps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, create a token that's a copy of enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FollowedFootstepsEffect()));
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

    FollowedFootstepsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of enchanted creature";
    }

    private FollowedFootstepsEffect(final FollowedFootstepsEffect effect) {
        super(effect);
    }

    @Override
    public FollowedFootstepsEffect copy() {
        return new FollowedFootstepsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentOrLKI(game);
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
