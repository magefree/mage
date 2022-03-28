
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox & L_J
 */
public final class Scrounge extends CardImpl {

    public Scrounge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent chooses an artifact card in their graveyard. Put that card onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ScroungeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Scrounge(final Scrounge card) {
        super(card);
    }

    @Override
    public Scrounge copy() {
        return new Scrounge(this);
    }
}

class ScroungeEffect extends OneShotEffect {

    public ScroungeEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent chooses an artifact card in their graveyard. Put that card onto the battlefield under your control";
    }

    public ScroungeEffect(final ScroungeEffect effect) {
        super(effect);
    }

    @Override
    public ScroungeEffect copy() {
        return new ScroungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && opponent != null) {
            FilterArtifactCard filter = new FilterArtifactCard();
            filter.add(new OwnerIdPredicate(opponent.getId()));
            TargetCardInGraveyard chosenCard = new TargetCardInGraveyard(filter);
            chosenCard.setNotTarget(true);
            if (chosenCard.canChoose(opponent.getId(), source, game)) {
                opponent.chooseTarget(Outcome.ReturnToHand, chosenCard, source, game);
                Card card = game.getCard(chosenCard.getFirstTarget());
                if (card != null) {
                    game.informPlayers ("Scrounge: " + opponent.getLogName() + " has chosen " + card.getLogName());
                    Cards cardsToMove = new CardsImpl();
                    cardsToMove.add(card);
                    controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
