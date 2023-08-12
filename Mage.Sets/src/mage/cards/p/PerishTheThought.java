package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class PerishTheThought extends CardImpl {

    public PerishTheThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a card from it. That player shuffles that card into their library.
        this.getSpellAbility().addEffect(new PerishTheThoughtEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PerishTheThought(final PerishTheThought card) {
        super(card);
    }

    @Override
    public PerishTheThought copy() {
        return new PerishTheThought(this);
    }
}

class PerishTheThoughtEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card in target opponent's hand");

    public PerishTheThoughtEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target opponent reveals their hand. You choose a card from it. That player shuffles that card into their library";
    }

    public PerishTheThoughtEffect(final PerishTheThoughtEffect effect) {
        super(effect);
    }

    @Override
    public PerishTheThoughtEffect copy() {
        return new PerishTheThoughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetOpponent != null && sourceObject != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                targetOpponent.revealCards(sourceObject.getIdName(), targetOpponent.getHand(), game);
                Player you = game.getPlayer(source.getControllerId());
                if (you != null) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    target.setNotTarget(true);
                    if (you.choose(Outcome.Neutral, targetOpponent.getHand(), target, source, game)) {
                        Card chosenCard = targetOpponent.getHand().get(target.getFirstTarget(), game);
                        if (chosenCard != null) {
                            targetOpponent.shuffleCardsToLibrary(chosenCard, game, source);
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
