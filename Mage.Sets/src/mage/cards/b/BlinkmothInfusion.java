
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class BlinkmothInfusion extends CardImpl {

    public BlinkmothInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{12}{U}{U}");

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Untap all artifacts.
        this.getSpellAbility().addEffect(new UntapAllArtifactsEffect());
    }

    private BlinkmothInfusion(final BlinkmothInfusion card) {
        super(card);
    }

    @Override
    public BlinkmothInfusion copy() {
        return new BlinkmothInfusion(this);
    }
}

class UntapAllArtifactsEffect extends OneShotEffect {
    
    public UntapAllArtifactsEffect() {
        super(Outcome.Untap);
        staticText = "Untap all artifacts";
    }

    public UntapAllArtifactsEffect(final UntapAllArtifactsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        for (Permanent artifact: game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), game)) {
            artifact.untap(game);
        }
        return true;
    }

    @Override
    public UntapAllArtifactsEffect copy() {
        return new UntapAllArtifactsEffect(this);
    }

}