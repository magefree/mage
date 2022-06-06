
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class AncientExcavation extends CardImpl {

    public AncientExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{B}");

        // Draw cards equal to the number of cards in your hand, then discard a card for each card drawn this way.
        this.getSpellAbility().addEffect(new AncientExcavationEffect());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private AncientExcavation(final AncientExcavation card) {
        super(card);
    }

    @Override
    public AncientExcavation copy() {
        return new AncientExcavation(this);
    }
}

class AncientExcavationEffect extends OneShotEffect {

    public AncientExcavationEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the number of cards in your hand, then discard a card for each card drawn this way";
    }

    public AncientExcavationEffect(final AncientExcavationEffect effect) {
        super(effect);
    }

    @Override
    public AncientExcavationEffect copy() {
        return new AncientExcavationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DynamicValue numCards = CardsInControllerHandCount.instance;
            int amount = numCards.calculate(game, source, this);
            player.drawCards(amount, source, game);
            player.discard(amount, false, false, source, game);
            return true;
        }
        return false;
    }
}
