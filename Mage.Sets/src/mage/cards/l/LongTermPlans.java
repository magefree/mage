
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class LongTermPlans extends CardImpl {

    public LongTermPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Search your library for a card, shuffle your library, then put that card third from the top.
        this.getSpellAbility().addEffect(new LongTermPlansEffect());
    }

    private LongTermPlans(final LongTermPlans card) {
        super(card);
    }

    @Override
    public LongTermPlans copy() {
        return new LongTermPlans(this);
    }
}

class LongTermPlansEffect extends OneShotEffect {

    LongTermPlansEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a card, then shuffle and put that card third from the top";
    }

    LongTermPlansEffect(final LongTermPlansEffect effect) {
        super(effect);
    }

    @Override
    public LongTermPlansEffect copy() {
        return new LongTermPlansEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (player.searchLibrary(target, source, game)) {
                Card card = player.getLibrary().remove(target.getFirstTarget(), game);
                if (card != null) {
                    player.shuffleLibrary(source, game);
                    // must hides the card name from other players
                    player.putCardOnTopXOfLibrary(card, game, source, 3, false);
                }
            }
            return true;
        }
        return false;
    }
}
