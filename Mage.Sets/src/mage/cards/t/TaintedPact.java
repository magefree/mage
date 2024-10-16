package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author cbt33, Ad Nauseum (North), Izzet Staticaster (LevelX2), Bane Alley
 * Broker (LevelX2)
 */
public final class TaintedPact extends CardImpl {

    public TaintedPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Exile the top card of your library. You may put that card into your hand unless it has the same name as another card exiled this way. Repeat this process until you put a card into your hand or you exile two cards with the same name, whichever comes first.
        this.getSpellAbility().addEffect(new TaintedPactEffect());
    }

    private TaintedPact(final TaintedPact card) {
        super(card);
    }

    @Override
    public TaintedPact copy() {
        return new TaintedPact(this);
    }
}

class TaintedPactEffect extends OneShotEffect {

    TaintedPactEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Exile the top card of your library. You may put that card into your hand unless it has the same name as another card exiled this way. Repeat this process until you put a card into your hand or you exile two cards with the same name, whichever comes first";
    }

    private TaintedPactEffect(final TaintedPactEffect effect) {
        super(effect);
    }

    @Override
    public TaintedPactEffect copy() {
        return new TaintedPactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        while (controller.canRespond() && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            // the card move is sequential, not all at once.
            controller.moveCards(card, Zone.EXILED, source, game);
            game.processAction();  // Laelia, the Blade Reforged
            // Checks if there was already exiled a card with the same name
            if (cards.getCards(game).stream().anyMatch(c -> c.sharesName(card, game))) {
                break;
            }
            cards.add(card);
            if (controller.chooseUse(outcome, "Put " + card.getName() + " into your hand?", source, game)) {
                //Adds the current card to hand if it is chosen.
                controller.moveCards(card, Zone.HAND, source, game);
                break;
            }
        }
        return true;
    }
}
