
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class CloneLegion extends CardImpl {

    public CloneLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // For each creature target player controls, create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new CloneLegionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private CloneLegion(final CloneLegion card) {
        super(card);
    }

    @Override
    public CloneLegion copy() {
        return new CloneLegion(this);
    }
}

class CloneLegionEffect extends OneShotEffect {

    public CloneLegionEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each creature target player controls, create a token that's a copy of that creature";
    }

    private CloneLegionEffect(final CloneLegionEffect effect) {
        super(effect);
    }

    @Override
    public CloneLegionEffect copy() {
        return new CloneLegionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                if (permanent != null) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
