package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
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
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class TransmuteArtifact extends CardImpl {

    public TransmuteArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        // Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new TransmuteArtifactEffect());
    }

    private TransmuteArtifact(final TransmuteArtifact card) {
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
        staticText = "Sacrifice an artifact. If you do, search your library for an artifact card. If that card's mana value is less than or equal to the sacrificed artifact's mana value, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle";
    }

    private TransmuteArtifactEffect(final TransmuteArtifactEffect effect) {
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
        int manaValue = 0;
        boolean sacrifice = false;
        TargetControlledPermanent targetArtifact = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
        if (controller.chooseTarget(Outcome.Sacrifice, targetArtifact, source, game)) {
            Permanent permanent = game.getPermanent(targetArtifact.getFirstTarget());
            if (permanent != null) {
                manaValue = permanent.getManaValue();
                sacrifice = permanent.sacrifice(source, game);
            }
        } else {
            return true;
        }
        //If you do, search your library for an artifact card.
        if (sacrifice && controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        //If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield.
                        if (card.getManaValue() <= manaValue) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        } else {
                            //If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield.
                            Cost cost = ManaUtil.createManaCost(card.getManaValue() - manaValue, true);
                            boolean payed = false;
                            if (controller.chooseUse(Outcome.Benefit, "Do you want to pay " + cost.getText() + " to put it onto the battlefield?", source, game)
                                    && cost.pay(source, game, source, source.getControllerId(), false)) {
                                payed = true;
                            }

                            if (payed) {
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
