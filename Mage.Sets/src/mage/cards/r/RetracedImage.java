package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class RetracedImage extends CardImpl {

    public RetracedImage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Reveal a card in your hand, then put that card onto the battlefield if it has the same name as a permanent.
        this.getSpellAbility().addEffect(new RetracedImageEffect());

    }

    private RetracedImage(final RetracedImage card) {
        super(card);
    }

    @Override
    public RetracedImage copy() {
        return new RetracedImage(this);
    }
}

class RetracedImageEffect extends OneShotEffect {

    public RetracedImageEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal a card in your hand, then put that card onto the battlefield if it has the same name as a permanent";
    }

    public RetracedImageEffect(final RetracedImageEffect effect) {
        super(effect);
    }

    @Override
    public RetracedImageEffect copy() {
        return new RetracedImageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInHand target = new TargetCardInHand();
            if (target.canChoose(controller.getId(), source, game)
                    && controller.choose(outcome, target, source, game)) {
                Card chosenCard = game.getCard(target.getFirstTarget());
                if (chosenCard != null) {
                    Cards cards = new CardsImpl();
                    cards.add(chosenCard);
                    controller.revealCards(source, cards, game);
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                        if (permanent.getName().equals(chosenCard.getName())) {
                            return controller.moveCards(chosenCard, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
        }
        return false;
    }
}
