package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jonubuu
 */
public final class SmashToSmithereens extends CardImpl {

    public SmashToSmithereens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Destroy target artifact. Smash to Smithereens deals 3 damage to that artifact's controller.
        this.getSpellAbility().addEffect(new SmashToSmithereensEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT));

    }

    private SmashToSmithereens(final SmashToSmithereens card) {
        super(card);
    }

    @Override
    public SmashToSmithereens copy() {
        return new SmashToSmithereens(this);
    }
}

class SmashToSmithereensEffect extends OneShotEffect {

    SmashToSmithereensEffect() {
        super(Outcome.Detriment);
        staticText = "Destroy target artifact. Smash to Smithereens deals 3 damage to that artifact's controller";
    }

    private SmashToSmithereensEffect(final SmashToSmithereensEffect effect) {
        super(effect);
    }

    @Override
    public SmashToSmithereensEffect copy() {
        return new SmashToSmithereensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetArtifact = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetArtifact != null) {
            Player controllerOfArtifact = game.getPlayer(targetArtifact.getControllerId());
            targetArtifact.destroy(source, game, false);
            if (controllerOfArtifact != null) {
                controllerOfArtifact.damage(3, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
