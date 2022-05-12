
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SuddenReclamation extends CardImpl {

    public SuddenReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Put the top four cards of your library into your graveyard, then return a creature card and a land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(4));
        this.getSpellAbility().addEffect(new SuddenReclamationEffect());
    }

    private SuddenReclamation(final SuddenReclamation card) {
        super(card);
    }

    @Override
    public SuddenReclamation copy() {
        return new SuddenReclamation(this);
    }
}

class SuddenReclamationEffect extends OneShotEffect {

    public SuddenReclamationEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then return a creature card and a land card from your graveyard to your hand";
    }

    public SuddenReclamationEffect(final SuddenReclamationEffect effect) {
        super(effect);
    }

    @Override
    public SuddenReclamationEffect copy() {
        return new SuddenReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToHand = new CardsImpl();
            Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            target = new TargetCardInYourGraveyard(new FilterLandCard("land card from your graveyard"));
            target.setNotTarget(true);
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
