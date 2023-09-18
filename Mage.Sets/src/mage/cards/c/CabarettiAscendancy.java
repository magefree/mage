package mage.cards.c;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class CabarettiAscendancy extends CardImpl {

    public CabarettiAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}{W}");

        // At the beginning of your upkeep, look at the top card of your library. If it's a creature or planeswalker card, you may reveal it and put it in your hand. If you don't, you may put it on the bottom of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CabarettiAscendencyEffect(), TargetController.YOU, false));
    }

    private CabarettiAscendancy(final CabarettiAscendancy card) {
        super(card);
    }

    @Override
    public CabarettiAscendancy copy() {
        return new CabarettiAscendancy(this);
    }
}

class CabarettiAscendencyEffect extends OneShotEffect {

    public CabarettiAscendencyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. If it's a creature or planeswalker card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it on the bottom of your library";
    }

    private CabarettiAscendencyEffect(final CabarettiAscendencyEffect effect) {
        super(effect);
    }

    @Override
    public CabarettiAscendencyEffect copy() {
        return new CabarettiAscendencyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObject(game);
        String objectName = sourceObject == null ? "Cabaretti Ascendency" : sourceObject.getIdName();
        controller.lookAtCards(objectName, card, game);
        if ((card.isCreature(game) || card.isPlaneswalker(game)) && controller.chooseUse(
                Outcome.DrawCard, "Reveal " + card.getIdName() + " and put it in your hand?", source, game)) {
            controller.revealCards(source, new CardsImpl(card), game);
            controller.moveCards(card, Zone.HAND, source, game);
        } else if (controller.chooseUse(Outcome.Neutral, "Put " + card.getIdName() + " on the bottom of your library?", source, game)) {
            controller.putCardsOnBottomOfLibrary(card, game, source, false);
        }
        return true;
    }
}
