
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class NaturesClaim extends CardImpl {

    public NaturesClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Destroy target artifact or enchantment. Its controller gains 4 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new NaturesClaimEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private NaturesClaim(final NaturesClaim card) {
        super(card);
    }

    @Override
    public NaturesClaim copy() {
        return new NaturesClaim(this);
    }
}

class NaturesClaimEffect extends OneShotEffect {

    NaturesClaimEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains 4 life";
    }

    private NaturesClaimEffect(final NaturesClaimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (target != null) {
            Player player = game.getPlayer(target.getControllerId());
            if (player != null) {
                player.gainLife(4, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public NaturesClaimEffect copy() {
        return new NaturesClaimEffect(this);
    }
}
