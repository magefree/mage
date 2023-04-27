
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class SoldeviDigger extends CardImpl {

    public SoldeviDigger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}: Put the top card of your graveyard on the bottom of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoldeviDiggerEffect(), new ManaCostsImpl<>("{2}")));
    }

    private SoldeviDigger(final SoldeviDigger card) {
        super(card);
    }

    @Override
    public SoldeviDigger copy() {
        return new SoldeviDigger(this);
    }
}

class SoldeviDiggerEffect extends OneShotEffect {

    public SoldeviDiggerEffect() {
        super(Outcome.Benefit);
        this.staticText = "put the top card of your graveyard on the bottom of your library";
    }

    public SoldeviDiggerEffect(final SoldeviDiggerEffect effect) {
        super(effect);
    }

    @Override
    public SoldeviDiggerEffect copy() {
        return new SoldeviDiggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = null;
            for (Card card :controller.getGraveyard().getCards(game)) {
                topCard = card;
            }
            if (topCard != null) {
                return controller.moveCardToLibraryWithInfo(topCard, source, game, Zone.GRAVEYARD, false, true);
            }
            return true;
        }
        return false;
    }
}
