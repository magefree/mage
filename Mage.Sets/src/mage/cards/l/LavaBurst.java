package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author notgreat
 */
public final class LavaBurst extends CardImpl {

    public LavaBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");
        

        // Lava Burst deals X damage to any target. If Lava Burst would deal damage to a creature, that damage can't be prevented or dealt instead to another creature or player.
        this.getSpellAbility().addEffect(new LavaBurstEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    private LavaBurst(final LavaBurst card) {
        super(card);
    }

    @Override
    public LavaBurst copy() {
        return new LavaBurst(this);
    }
}

class LavaBurstEffect extends OneShotEffect {

    LavaBurstEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to any target. If {this} would deal damage to a creature, that damage can't be prevented or dealt instead to another permanent or player.";
    }

    private LavaBurstEffect(final LavaBurstEffect effect) {
        super(effect);
    }

    @Override
    public LavaBurstEffect copy() {
        return new LavaBurstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        int damage = CardUtil.getSourceCostsTag(game, source, "X", 0);
        if (targetPlayer != null) {
            targetPlayer.damage(damage, source.getSourceId(), source, game, false, true);
            return true;
        }
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null) {
            if (targetPermanent.isCreature(game)) {
                // hack to avoid needing to create a DAMAGE_REDIRECTION event
                // Pretend that all possible redirection effects have already been applied
                List<UUID> redirectionEffects = game.getState().getContinuousEffects().getReplacementEffects().stream()
                        .filter(x -> x instanceof RedirectionEffect).map(Effect::getId).collect(Collectors.toList());
                targetPermanent.damage(damage, source.getSourceId(), source, game, false, false, redirectionEffects);
            } else {
                targetPermanent.damage(damage, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
