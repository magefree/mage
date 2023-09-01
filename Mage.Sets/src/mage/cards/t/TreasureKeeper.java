package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author fireshoes
 */
public final class TreasureKeeper extends CardImpl {

    public TreasureKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Treasure Keeper dies, reveal cards from the top of your library until you reveal a nonland card with converted mana cost 3 or less.
        // You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order.
        this.addAbility(new DiesSourceTriggeredAbility(new TreasureKeeperEffect()));
    }

    private TreasureKeeper(final TreasureKeeper card) {
        super(card);
    }

    @Override
    public TreasureKeeper copy() {
        return new TreasureKeeper(this);
    }
}

class TreasureKeeperEffect extends OneShotEffect {

    public TreasureKeeperEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal cards from the top of your library until you reveal a "
                + "nonland card with mana value 3 or less. "
                + "You may cast that card without paying its mana cost. Put all revealed "
                + "cards not cast this way on the bottom of your library in a random order";
    }

    private TreasureKeeperEffect(final TreasureKeeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean cardWasCast = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            CardsImpl toReveal = new CardsImpl();
            Card nonLandCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game) && card.getManaValue() < 4) {
                    nonLandCard = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            if (nonLandCard != null
                    && controller.chooseUse(Outcome.PlayForFree, "Cast " + nonLandCard.getLogName() + " without paying its mana cost?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + nonLandCard.getId(), Boolean.TRUE);
                cardWasCast = controller.cast(controller.chooseAbilityForCast(nonLandCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + nonLandCard.getId(), null);
                if (cardWasCast) {
                    toReveal.remove(nonLandCard);
                }
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        }
        return cardWasCast;
    }

    @Override
    public TreasureKeeperEffect copy() {
        return new TreasureKeeperEffect(this);
    }
}
