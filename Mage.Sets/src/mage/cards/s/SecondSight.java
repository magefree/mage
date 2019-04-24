
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecondSight extends CardImpl {

    public SecondSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        //Look at the top five cards of target opponent's library, then put them back in any order;
        this.getSpellAbility().addEffect(new SecondSightEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        //or look at the top five cards of your library, then put them back in any order.
        this.getSpellAbility().getModes().addMode(new Mode(new LookLibraryControllerEffect(5)));

        // Entwine {U}
        this.addAbility(new EntwineAbility("{U}"));
    }

    private SecondSight(final SecondSight card) {
        super(card);
    }

    @Override
    public SecondSight copy() {
        return new SecondSight(this);
    }
}

class SecondSightEffect extends OneShotEffect {

    SecondSightEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top five cards of target opponent's library, then put them back in any order";
    }

    private SecondSightEffect(final SecondSightEffect effect) {
        super(effect);
    }

    @Override
    public SecondSightEffect copy() {
        return new SecondSightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        return true;
    }
}
