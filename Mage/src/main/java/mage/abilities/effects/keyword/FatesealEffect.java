
package mage.abilities.effects.keyword;

import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */

public class FatesealEffect extends OneShotEffect {

    protected static final FilterCard filter1 = new FilterCard("card to put on the bottom of opponent's library");

    protected int fatesealNumber;

    public FatesealEffect(int fatesealNumber) {
        super(Outcome.Benefit);
        this.fatesealNumber = fatesealNumber;
        this.setText();
    }

    protected FatesealEffect(final FatesealEffect effect) {
        super(effect);
        this.fatesealNumber = effect.fatesealNumber;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            if (controller.choose(outcome, target, source, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent == null) {
                    return false;
                }

                // 701.29a 
                // To “fateseal N” means to look at the top N cards of an opponent’s library, 
                // then put any number of them on the bottom of that library in any order 
                // and the rest on top of that library in any order.

                int count = Math.min(fatesealNumber, opponent.getLibrary().size());
                if (count == 0) {
                    return true;
                }
                Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, count));

                // put to bottom
                TargetCard targetBottom = new TargetCard(0, cards.size(), Zone.LIBRARY, filter1);
                targetBottom.setRequired(false);
                controller.choose(Outcome.Detriment, cards, targetBottom, source, game);
                Cards cardsToBottom = new CardsImpl(targetBottom.getTargets().stream()
                    .filter(cards::contains)
                    .collect(Collectors.toList()
                ));
                if (controller.putCardsOnBottomOfLibrary(cardsToBottom, game, source, true)) {
                    // put to top
                    cards.removeAll(cardsToBottom);
                    controller.putCardsOnTopOfLibrary(cards, game, source, true);
                }
                game.fireEvent(new GameEvent(GameEvent.EventType.FATESEALED, opponent.getId(), source, source.getControllerId()));
                return true;
            }

        }
        return false;
    }

    @Override
    public FatesealEffect copy() {
        return new FatesealEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("fateseal ").append(fatesealNumber);
        if (fatesealNumber == 1) {
            sb.append(". <i>(To fateseal 1, its controller looks at the top card of an opponent's library, then they may put that card on the bottom of that library.)</i>");
        } else {
            sb.append(". <i>(To fateseal ");
            sb.append(CardUtil.numberToText(fatesealNumber));
            sb.append(", look at the top two cards of an opponent's library, then put any number of them on the bottom of that player's library and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
