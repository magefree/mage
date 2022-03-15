
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noxx
 *
 */
public final class TeferisPuzzleBox extends CardImpl {

    public TeferisPuzzleBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of each player's draw step, that player puts the cards in their hand on the bottom of their library in any order, then draws that many cards.
        Ability ability = new BeginningOfDrawTriggeredAbility(new TeferisPuzzleBoxEffect(), TargetController.ANY, false);
        this.addAbility(ability);
    }

    private TeferisPuzzleBox(final TeferisPuzzleBox card) {
        super(card);
    }

    @Override
    public TeferisPuzzleBox copy() {
        return new TeferisPuzzleBox(this);
    }
}

class TeferisPuzzleBoxEffect extends OneShotEffect {

    public TeferisPuzzleBoxEffect() {
        super(Outcome.Neutral);
        staticText = "that player puts the cards in their hand on the bottom of their library in any order, then draws that many cards";
    }

    public TeferisPuzzleBoxEffect(final TeferisPuzzleBoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int count = player.getHand().size();
            player.putCardsOnBottomOfLibrary(player.getHand(), game, source, true);
            player.drawCards(count, source, game);
        }
        return true;
    }

    @Override
    public TeferisPuzzleBoxEffect copy() {
        return new TeferisPuzzleBoxEffect(this);
    }

}
