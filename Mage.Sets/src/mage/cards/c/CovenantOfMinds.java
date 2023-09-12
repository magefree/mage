
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class CovenantOfMinds extends CardImpl {

    public CovenantOfMinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Reveal the top three cards of your library. Target opponent may choose to put those cards into your hand.
        // If they don't, put those cards into your graveyard and draw five cards.
        this.getSpellAbility().addEffect(new CovenantOfMindsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CovenantOfMinds(final CovenantOfMinds card) {
        super(card);
    }

    @Override
    public CovenantOfMinds copy() {
        return new CovenantOfMinds(this);
    }
}

class CovenantOfMindsEffect extends OneShotEffect {

    public CovenantOfMindsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. Target opponent may choose to put those cards into your hand. If they don't, put those cards into your graveyard and draw five cards";
    }

    private CovenantOfMindsEffect(final CovenantOfMindsEffect effect) {
        super(effect);
    }

    @Override
    public CovenantOfMindsEffect copy() {
        return new CovenantOfMindsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (!cards.isEmpty()) {
            player.revealCards(source, cards, game);
            StringBuilder sb = new StringBuilder();
            sb.append("Put the revealed cards into ").append(player.getLogName()).append("'s hand?");
            sb.append(" If you don't, those cards are put into their graveyard and they will draw five cards.");

            if (opponent.chooseUse(Outcome.Neutral, sb.toString(), source, game)) {
                player.moveCards(cards, Zone.HAND, source, game);
            } else {
                player.moveCards(cards, Zone.GRAVEYARD, source, game);
                player.drawCards(5, source, game);
            }

        } else {
            if (!opponent.chooseUse(Outcome.Benefit, player.getLogName() + "'s library is empty? Do you want them to draw five cards?", source, game)) {
                player.drawCards(5, source, game);
            }
        }

        return true;
    }
}
