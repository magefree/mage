
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class InterpretTheSigns extends CardImpl {

    public InterpretTheSigns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");


        // Scry 3, then reveal the top card of your library. Draw cards equal to that card's converted mana cost.
        this.getSpellAbility().addEffect(new ScryEffect(3));
        this.getSpellAbility().addEffect(new InterpretTheSignsEffect());

    }

    public InterpretTheSigns(final InterpretTheSigns card) {
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
        this.staticText = ", then reveal the top card of your library. Draw cards equal to that card's converted mana cost";
    }

    public InterpretTheSignsEffect(final InterpretTheSignsEffect effect) {
        super(effect);
    }

    @Override
    public InterpretTheSignsEffect copy() {
        return new InterpretTheSignsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(sourceCard.getName(), new CardsImpl(card), game);
                controller.drawCards(card.getConvertedManaCost(), game);
            }
            return true;
        }
        return false;
    }
}
