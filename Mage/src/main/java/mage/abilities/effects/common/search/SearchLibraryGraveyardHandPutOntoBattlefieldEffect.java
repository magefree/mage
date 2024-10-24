package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Styxo
 */
public class SearchLibraryGraveyardHandPutOntoBattlefieldEffect extends OneShotEffect {

    private final FilterCard filter;

    public SearchLibraryGraveyardHandPutOntoBattlefieldEffect(FilterCard filter) {
        this(filter, false);
    }

    public SearchLibraryGraveyardHandPutOntoBattlefieldEffect(FilterCard filter, boolean youMay) {
        super(Outcome.Benefit);
        this.filter = filter;
        staticText = (youMay ? "You may " : "") + "search your graveyard, hand, and library for a " + filter.getMessage()
                + ", put it onto the battlefield. Then shuffle";
    }

    protected SearchLibraryGraveyardHandPutOntoBattlefieldEffect(final SearchLibraryGraveyardHandPutOntoBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public SearchLibraryGraveyardHandPutOntoBattlefieldEffect copy() {
        return new SearchLibraryGraveyardHandPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card cardFound = null;
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
        target.clearChosen();
        if (controller.choose(outcome, controller.getGraveyard(), target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                cardFound = game.getCard(target.getFirstTarget());
            }
        }

        if (cardFound == null) {
            TargetCardInHand targetCardInHand = new TargetCardInHand(0, 1, filter);
            targetCardInHand.clearChosen();
            if (controller.choose(outcome, controller.getHand(), targetCardInHand, source, game)) {
                if (!targetCardInHand.getTargets().isEmpty()) {
                    cardFound = game.getCard(targetCardInHand.getFirstTarget());
                }
            }
        }

        if (cardFound == null) {
            TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, 1, filter);
            targetCardInLibrary.clearChosen();
            if (controller.searchLibrary(targetCardInLibrary, source, game)) {
                if (!targetCardInLibrary.getTargets().isEmpty()) {
                    cardFound = game.getCard(target.getFirstTarget());
                }
            }
        }

        if (cardFound != null) {
            controller.moveCards(cardFound, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);


        return true;
    }
}
