
package mage.cards.h;

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
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class HintOfInsanity extends CardImpl {

    public HintOfInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals his or her hand. That player discards all nonland cards with the same name as another card in his or her hand.
        this.getSpellAbility().addEffect(new HintOfInsanityEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public HintOfInsanity(final HintOfInsanity card) {
        super(card);
    }

    @Override
    public HintOfInsanity copy() {
        return new HintOfInsanity(this);
    }
}

class HintOfInsanityEffect extends OneShotEffect {

    public HintOfInsanityEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand. That player discards all nonland cards with the same name as another card in their hand";
    }

    public HintOfInsanityEffect(final HintOfInsanityEffect effect) {
        super(effect);
    }

    @Override
    public HintOfInsanityEffect copy() {
        return new HintOfInsanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("card from your hand");
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String nameOfChosenCard;
        Card chosenCard;
        if (targetPlayer != null) {
            TargetCardInHand targetCard = new TargetCardInHand(filter);
            targetCard.setNotTarget(true);
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());
            targetPlayer.revealCards("Hint of Insanity Reveal", cardsInHand, game);
            if (!cardsInHand.isEmpty()
                    && targetPlayer.choose(Outcome.Discard, targetCard, source.getSourceId(), game)) {
                chosenCard = game.getCard(targetCard.getFirstTarget());
                nameOfChosenCard = chosenCard.getName();
                for (Card card : cardsInHand.getCards(game)) {
                    if (card.getName().equals(nameOfChosenCard)
                            && !card.isLand()) {
                        targetPlayer.discard(card, source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
