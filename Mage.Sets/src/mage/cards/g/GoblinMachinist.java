
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class GoblinMachinist extends CardImpl {

    public GoblinMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {2}{R}: Reveal cards from the top of your library until you reveal a nonland card. Goblin Machinist gets +X/+0 until end of turn, where X is that card's converted mana cost. Put the revealed cards on the bottom of your library in any order.
        this.addAbility(new SimpleActivatedAbility(new GoblinMachinistEffect(), new ManaCostsImpl<>("{2}{R}")));
    }

    private GoblinMachinist(final GoblinMachinist card) {
        super(card);
    }

    @Override
    public GoblinMachinist copy() {
        return new GoblinMachinist(this);
    }
}

class GoblinMachinistEffect extends OneShotEffect {

    public GoblinMachinistEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonland card. {this} gets +X/+0 until end of turn, where X is that card's mana value. Put the revealed cards on the bottom of your library in any order";
    }

    public GoblinMachinistEffect(final GoblinMachinistEffect effect) {
        super(effect);
    }

    @Override
    public GoblinMachinistEffect copy() {
        return new GoblinMachinistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl cards = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (!card.isLand(game)) {
                        if (card.getManaValue() > 0) {
                            game.addEffect(new BoostSourceEffect(card.getManaValue(), 0, Duration.EndOfTurn), source);
                        }
                        break;
                    }
                }
            }
            controller.revealCards(source, cards, game);
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
