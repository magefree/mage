
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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

/**
 *
 * @author Styxo
 */
public final class CandlesOfLeng extends CardImpl {

    public CandlesOfLeng(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {4}, {tap}: Reveal the top card of your library. If it has the same name as a card in your graveyard, put it into your graveyard. Otherwise, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CandlesOfLengEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public CandlesOfLeng(final CandlesOfLeng card) {
        super(card);
    }

    @Override
    public CandlesOfLeng copy() {
        return new CandlesOfLeng(this);
    }
}

class CandlesOfLengEffect extends OneShotEffect {

    public CandlesOfLengEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top card of your library. If it has the same name as a card in your graveyard, put it into your graveyard. Otherwise, draw a card";
    }

    public CandlesOfLengEffect(final CandlesOfLengEffect effect) {
        super(effect);
    }

    @Override
    public CandlesOfLengEffect copy() {
        return new CandlesOfLengEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            boolean hasTheSameName = false;
            for (UUID uuid : controller.getGraveyard()) {
                if (card.getName().equals(game.getCard(uuid).getName())) {
                    hasTheSameName = true;
                }
            }

            if (hasTheSameName) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
            } else {
                controller.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}
