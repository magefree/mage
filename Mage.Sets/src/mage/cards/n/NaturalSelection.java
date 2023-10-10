
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author KholdFuzion
 *
 */
public final class NaturalSelection extends CardImpl {

    public NaturalSelection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle their library.
        this.getSpellAbility().addEffect(new NaturalSelectionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private NaturalSelection(final NaturalSelection card) {
        super(card);
    }

    @Override
    public NaturalSelection copy() {
        return new NaturalSelection(this);
    }
}

class NaturalSelectionEffect extends OneShotEffect {

    public NaturalSelectionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order. You may have that player shuffle";
    }

    private NaturalSelectionEffect(final NaturalSelectionEffect effect) {
        super(effect);
    }

    @Override
    public NaturalSelectionEffect copy() {
        return new NaturalSelectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        if (controller.chooseUse(Outcome.Neutral, "You may have that player shuffle", source, game)) {
            targetPlayer.shuffleLibrary(source, game);
        }
        return true;
    }
}
