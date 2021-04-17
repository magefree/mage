
package mage.cards.t;

import java.util.UUID;
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
 * @author cbt33
 */
public final class ThinkTank extends CardImpl {

    public ThinkTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ThinkTankLookLibraryEffect(), TargetController.YOU, false));

    }

    private ThinkTank(final ThinkTank card) {
        super(card);
    }

    @Override
    public ThinkTank copy() {
        return new ThinkTank(this);
    }
}

class ThinkTankLookLibraryEffect extends OneShotEffect {

    public ThinkTankLookLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    public ThinkTankLookLibraryEffect(final ThinkTankLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ThinkTankLookLibraryEffect copy() {
        return new ThinkTankLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    controller.lookAtCards("Think Tank", cards, game);
                    if (controller.chooseUse(Outcome.Neutral, "Put that card into your graveyard?", source, game)) {
                        return controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
