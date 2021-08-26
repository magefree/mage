package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BagOfTricks extends CardImpl {

    public BagOfTricks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // {4}{G}, {T}: Roll a d8. Reveal cards from the top of your library until you reveal a creature card with mana value equal to the result. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new BagOfTricksEffect(), new ManaCostsImpl<>("{4}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BagOfTricks(final BagOfTricks card) {
        super(card);
    }

    @Override
    public BagOfTricks copy() {
        return new BagOfTricks(this);
    }
}

class BagOfTricksEffect extends OneShotEffect {

    BagOfTricksEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d8. Reveal cards from the top of your library until you reveal a creature card " +
                "with mana value equal to the result. Put that card onto the battlefield " +
                "and the rest on the bottom of your library in a random order";
    }

    private BagOfTricksEffect(final BagOfTricksEffect effect) {
        super(effect);
    }

    @Override
    public BagOfTricksEffect copy() {
        return new BagOfTricksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 8);
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game) && card.getManaValue() == result) {
                player.moveCards(card, Zone.BATTLEFIELD, source, game);
                break;
            }
        }
        player.revealCards(source, cards, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
