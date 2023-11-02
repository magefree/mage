
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class ArchangelsLight extends CardImpl {

    public ArchangelsLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{W}");

        // You gain 2 life for each card in your graveyard, then shuffle your graveyard into your library.
        this.getSpellAbility().addEffect(new ArchangelsLightEffect());

    }

    private ArchangelsLight(final ArchangelsLight card) {
        super(card);
    }

    @Override
    public ArchangelsLight copy() {
        return new ArchangelsLight(this);
    }
}

class ArchangelsLightEffect extends OneShotEffect {

    public ArchangelsLightEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 2 life for each card in your graveyard, then shuffle your graveyard into your library";
    }

    private ArchangelsLightEffect(final ArchangelsLightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        DynamicValue value = new CardsInControllerGraveyardCount();
        if (controller != null) {
            controller.gainLife(value.calculate(game, source, this) * 2, game, source);
            for (Card card: controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }            
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public ArchangelsLightEffect copy() {
        return new ArchangelsLightEffect(this);
    }

}
