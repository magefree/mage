
package mage.cards.p;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Persecute extends CardImpl {

    public Persecute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Choose a color. Target player reveals their hand and discards all cards of that color.
        this.getSpellAbility().addEffect(new PersecuteEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public Persecute(final Persecute card) {
        super(card);
    }

    @Override
    public Persecute copy() {
        return new Persecute(this);
    }
}

class PersecuteEffect extends OneShotEffect {

    public PersecuteEffect() {
        super(Outcome.Discard);
        this.staticText = "Choose a color. Target player reveals their hand and discards all cards of that color";
    }

    public PersecuteEffect(final PersecuteEffect effect) {
        super(effect);
    }

    @Override
    public PersecuteEffect copy() {
        return new PersecuteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && sourceObject != null && targetPlayer != null && controller.choose(outcome, choice, game)) {
            Cards hand = targetPlayer.getHand();
            targetPlayer.revealCards(sourceObject.getIdName(), hand, game);
            Set<Card> cards = hand.getCards(game);
            for (Card card : cards) {
                if (card != null && card.getColor(game).shares(choice.getColor())) {
                    targetPlayer.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
