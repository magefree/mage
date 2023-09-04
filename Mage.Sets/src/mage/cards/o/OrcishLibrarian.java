
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Mitchel Stein
 */
public final class OrcishLibrarian extends CardImpl {

    public OrcishLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, {tap}: Look at the top eight cards of your library. Exile four of them at random, then put the rest on top of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrcishLibrarianEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private OrcishLibrarian(final OrcishLibrarian card) {
        super(card);
    }

    @Override
    public OrcishLibrarian copy() {
        return new OrcishLibrarian(this);
    }
}

class OrcishLibrarianEffect extends OneShotEffect {

    public OrcishLibrarianEffect() {
        super(Outcome.Neutral);
        this.staticText = "Look at the top eight cards of your library. Exile four of them at random, then put the rest on top of your library in any order";
    }

    private OrcishLibrarianEffect(final OrcishLibrarianEffect effect) {
        super(effect);
    }

    @Override
    public OrcishLibrarianEffect copy() {
        return new OrcishLibrarianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 8));
            if (!cards.isEmpty()) {
                Cards randomExit = new CardsImpl();
                for (int i = 0; i < 4; i++) {
                    if (!cards.isEmpty()) {
                        Card card = cards.getRandom(game);
                        if (card != null) {
                            randomExit.add(card);
                            cards.remove(card);
                        }
                    }
                }
                controller.moveCards(randomExit, Zone.EXILED, source, game);
                controller.lookAtCards(source, null, cards, game);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
