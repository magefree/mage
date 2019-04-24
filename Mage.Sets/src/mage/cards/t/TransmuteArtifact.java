
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class TransmuteArtifact extends CardImpl {

    public TransmuteArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");

        // Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new TransmuteArtifactEffect());
    }

    public TransmuteArtifact(final TransmuteArtifact card) {
        super(card);
    }

    @Override
    public TransmuteArtifact copy() {
        return new TransmuteArtifact(this);
    }
}

class TransmuteArtifactEffect extends SearchEffect {

    public TransmuteArtifactEffect() {
        super(new TargetCardInLibrary(new FilterArtifactCard()), Outcome.PutCardInPlay);
        staticText = "Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle your library";
    }

    public TransmuteArtifactEffect(final TransmuteArtifactEffect effect) {
        super(effect);
    }

    @Override
    public TransmuteArtifactEffect copy() {
        return new TransmuteArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        //Sacrifice an artifact.
        int convertedManaCost = 0;
        boolean sacrifice = false;
        TargetControlledPermanent targetArtifact = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
        if (controller.chooseTarget(Outcome.Sacrifice, targetArtifact, source, game)) {
            Permanent permanent = game.getPermanent(targetArtifact.getFirstTarget());
            if (permanent != null) {
                convertedManaCost = permanent.getConvertedManaCost();
                sacrifice = permanent.sacrifice(source.getSourceId(), game);
            }
        } else {
            return true;
        }
        //If you do, search your library for an artifact card.
        if (sacrifice && controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        //If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield.
                        if (card.getConvertedManaCost() <= convertedManaCost) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        } else {
                            //If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield.
                            GenericManaCost cost = new GenericManaCost(card.getConvertedManaCost() - convertedManaCost);
                            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            } else {
                                //If you don't, put it into its owner's graveyard. Then shuffle your library
                                controller.moveCards(card, Zone.GRAVEYARD, source, game);
                            }
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }

}
