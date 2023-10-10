
package mage.cards.m;

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
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class MindFuneral extends CardImpl {

    public MindFuneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{B}");

        // Target opponent reveals cards from the top of their library until four land cards are revealed. That player puts all cards revealed this way into their graveyard.
        this.getSpellAbility().addEffect(new MindFuneralEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private MindFuneral(final MindFuneral card) {
        super(card);
    }

    @Override
    public MindFuneral copy() {
        return new MindFuneral(this);
    }
}

class MindFuneralEffect extends OneShotEffect {

    public MindFuneralEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent reveals cards from the top of their library until four land cards are revealed. That player puts all cards revealed this way into their graveyard";
    }

    private MindFuneralEffect(final MindFuneralEffect effect) {
        super(effect);
    }

    @Override
    public MindFuneralEffect copy() {
        return new MindFuneralEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            Cards cards = new CardsImpl();
            int landsFound = 0;
            for (Card card : opponent.getLibrary().getCards(game)) {
                cards.add(card);
                if (card.isLand(game) && ++landsFound == 4) {
                    break;
                }
            }
            opponent.revealCards(source, cards, game);
            opponent.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }

        return false;
    }
}
