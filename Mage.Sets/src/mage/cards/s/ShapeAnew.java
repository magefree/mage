
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author ayratn
 */
public final class ShapeAnew extends CardImpl {

    public ShapeAnew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // The controller of target artifact sacrifices it, then reveals cards from the top
        // of their library until they reveal an artifact card. That player puts
        // that card onto the battlefield, then shuffles all other cards revealed this way into their library.
        this.getSpellAbility().addEffect(new ShapeAnewEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
    }

    private ShapeAnew(final ShapeAnew card) {
        super(card);
    }

    @Override
    public ShapeAnew copy() {
        return new ShapeAnew(this);
    }

    private static class ShapeAnewEffect extends OneShotEffect {

        public ShapeAnewEffect() {
            super(Outcome.PutCardInPlay);
            staticText = "The controller of target artifact sacrifices it, then reveals cards from the top of their library until they reveal an artifact card. That player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library";
        }

        private ShapeAnewEffect(final ShapeAnewEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent == null) {
                return false;
            }
            targetPermanent.sacrifice(source, game);
            Player targetController = game.getPlayer(targetPermanent.getControllerId());
            if (targetController == null) {
                return false;
            }
            Cards revealed = new CardsImpl();
            Card artifactCard = null;
            for (Card card : targetController.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isArtifact(game)) {
                    artifactCard = card;
                    break;
                }
            }
            targetController.revealCards(source, revealed, game);
            if (artifactCard != null) {
                targetController.moveCards(artifactCard, Zone.BATTLEFIELD, source, game);
            }
            // 1/1/2011: If the first card the player reveals is an artifact card, they will still have to shuffle their library even though no other cards were revealed this way.
            targetController.shuffleLibrary(source, game);
            return true;
        }

        @Override
        public ShapeAnewEffect copy() {
            return new ShapeAnewEffect(this);
        }
    }
}
