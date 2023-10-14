package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class AdNauseam extends CardImpl {

    public AdNauseam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost. You may repeat this process any number of times.
        this.getSpellAbility().addEffect(new AdNauseamEffect());
    }

    private AdNauseam(final AdNauseam card) {
        super(card);
    }

    @Override
    public AdNauseam copy() {
        return new AdNauseam(this);
    }
}

class AdNauseamEffect extends OneShotEffect {

    public AdNauseamEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top card of your library and put that card into your hand. You lose life equal to its mana value. You may repeat this process any number of times";
    }

    private AdNauseamEffect(final AdNauseamEffect effect) {
        super(effect);
    }

    @Override
    public AdNauseamEffect copy() {
        return new AdNauseamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String message = "Reveal the top card of your library and put that card into your hand? You lose life equal to its mana value.";
        Card sourceCard = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || sourceCard == null) {
            return false;
        }
        while (controller.chooseUse(outcome, message, source, game) && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                break;
            }
            controller.moveCards(card, Zone.HAND, source, game);
            int cmc = card.getManaValue();
            if (cmc > 0) {
                controller.loseLife(cmc, game, source, false);
            }
            controller.revealCards(sourceCard.getIdName() + " put into hand", new CardsImpl(card), game);

            // AI workaround to stop infinite choose (only one card allows)
            if (controller.isComputer()) {
                break;
            }
        }
        return true;
    }
}
