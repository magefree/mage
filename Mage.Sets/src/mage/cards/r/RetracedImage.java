package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
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

    RetracedImageEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal a card in your hand, then put that card onto the battlefield if it has the same name as a permanent";
    }

    private RetracedImageEffect(final RetracedImageEffect effect) {
        super(effect);
    }

    @Override
    public RetracedImageEffect copy() {
        return new RetracedImageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || controller.getHand().isEmpty()) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand();
        controller.choose(outcome, target, source, game);
        Card chosenCard = game.getCard(target.getFirstTarget());
        return chosenCard != null
                && game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT,
                        source.getControllerId(), source, game
                )
                .stream()
                .anyMatch(permanent -> permanent.sharesName(chosenCard, game))
                && controller.moveCards(chosenCard, Zone.BATTLEFIELD, source, game);
    }
}
