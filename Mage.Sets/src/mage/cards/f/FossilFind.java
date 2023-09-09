
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
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
 * @author North
 */
public final class FossilFind extends CardImpl {

    public FossilFind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R/G}");

        // Return a card at random from your graveyard to your hand, then reorder your graveyard as you choose.
        this.getSpellAbility().addEffect(new FossilFindEffect());
    }

    private FossilFind(final FossilFind card) {
        super(card);
    }

    @Override
    public FossilFind copy() {
        return new FossilFind(this);
    }
}

class FossilFindEffect extends OneShotEffect {

    public FossilFindEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return a card at random from your graveyard to your hand, then reorder your graveyard as you choose";
    }

    private FossilFindEffect(final FossilFindEffect effect) {
        super(effect);
    }

    @Override
    public FossilFindEffect copy() {
        return new FossilFindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getGraveyard().isEmpty()) {
            Card card = controller.getGraveyard().getRandom(game);
            if (card != null) {
                controller.moveCards(card, Zone.HAND, source, game);
                return true;
            }
            controller.moveCards(controller.getGraveyard(), Zone.GRAVEYARD, source, game);
        }
        return false;
    }
}
