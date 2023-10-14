package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InterpretTheSigns extends CardImpl {

    public InterpretTheSigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Scry 3, then reveal the top card of your library. Draw cards equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new InterpretTheSignsEffect());
    }

    private InterpretTheSigns(final InterpretTheSigns card) {
        super(card);
    }

    @Override
    public InterpretTheSigns copy() {
        return new InterpretTheSigns(this);
    }
}

class InterpretTheSignsEffect extends OneShotEffect {

    public InterpretTheSignsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "scry 3, then reveal the top card of your library. Draw cards equal to that card's mana value";
    }

    private InterpretTheSignsEffect(final InterpretTheSignsEffect effect) {
        super(effect);
    }

    @Override
    public InterpretTheSignsEffect copy() {
        return new InterpretTheSignsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.scry(3, source, game);
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        controller.drawCards(card.getManaValue(), source, game);
        return true;
    }
}
