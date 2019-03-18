
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
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

/**
 *
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
        this.addAbility(new DiesTriggeredAbility(new TreasureKeeperEffect()));
    }

    public TreasureKeeper(final TreasureKeeper card) {
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
        this.staticText = "reveal cards from the top of your library until you reveal a nonland card with converted mana cost 3 or less. "
                + "You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order";
    }

    public TreasureKeeperEffect(TreasureKeeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl toReveal = new CardsImpl();
            Card nonLandCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand() && card.getConvertedManaCost() < 4) {
                    nonLandCard = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            if (nonLandCard != null && controller.chooseUse(outcome, "Cast " + nonLandCard.getLogName() + "without paying its mana cost?", source, game)) {
                controller.cast(nonLandCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                toReveal.remove(nonLandCard);
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
            return true;
        }
        return false;
    }

    @Override
    public TreasureKeeperEffect copy() {
        return new TreasureKeeperEffect(this);
    }
}
