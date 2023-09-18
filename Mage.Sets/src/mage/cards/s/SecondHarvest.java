
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class SecondHarvest extends CardImpl {

    public SecondHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}");

        // For each token you control, create a token that's a copy of that permanent.
        this.getSpellAbility().addEffect(new SecondHarvestEffect());
    }

    private SecondHarvest(final SecondHarvest card) {
        super(card);
    }

    @Override
    public SecondHarvest copy() {
        return new SecondHarvest(this);
    }
}

class SecondHarvestEffect extends OneShotEffect {

    public SecondHarvestEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each token you control, create a token that's a copy of that permanent";
    }

    private SecondHarvestEffect(final SecondHarvestEffect effect) {
        super(effect);
    }

    @Override
    public SecondHarvestEffect copy() {
        return new SecondHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("each token you control");
            filter.add(TokenPredicate.TRUE);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
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
