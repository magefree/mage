
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class LurkingPredators extends CardImpl {

    public LurkingPredators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{G}");

        // Whenever an opponent casts a spell, reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new LurkingPredatorsEffect(), false));
    }

    private LurkingPredators(final LurkingPredators card) {
        super(card);
    }

    @Override
    public LurkingPredators copy() {
        return new LurkingPredators(this);
    }
}

class LurkingPredatorsEffect extends OneShotEffect {

    public LurkingPredatorsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library";
    }

    public LurkingPredatorsEffect(final LurkingPredatorsEffect effect) {
        super(effect);
    }

    @Override
    public LurkingPredatorsEffect copy() {
        return new LurkingPredatorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
                if (card.isCreature(game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " on the bottom of your library?", source, game)) {
                    controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                }
            }
        }
        return true;
    }
}
