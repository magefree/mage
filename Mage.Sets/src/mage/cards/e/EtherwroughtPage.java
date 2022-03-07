
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
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
 * @author jeffwadsworth
 */
public final class EtherwroughtPage extends CardImpl {

    public EtherwroughtPage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}{W}{U}{B}");

        // At the beginning of your upkeep, choose one - You gain 2 life; or look at the top card of your library, then you may put that card into your graveyard; or each opponent loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), TargetController.YOU, false);

        // or look at the top card of your library, then you may put that card into your graveyard;
        Mode mode = new Mode();
        mode.addEffect(new EtherwroughtPageEffect());
        ability.addMode(mode);

        // or each opponent loses 1 life
        Mode mode1 = new Mode();
        mode1.addEffect(new LoseLifeOpponentsEffect(1));
        ability.addMode(mode1);
        
        this.addAbility(ability);
    }

    private EtherwroughtPage(final EtherwroughtPage card) {
        super(card);
    }

    @Override
    public EtherwroughtPage copy() {
        return new EtherwroughtPage(this);
    }
}

class EtherwroughtPageEffect extends OneShotEffect {

    public EtherwroughtPageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    public EtherwroughtPageEffect(final EtherwroughtPageEffect effect) {
        super(effect);
    }

    @Override
    public EtherwroughtPageEffect copy() {
        return new EtherwroughtPageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards("Etherwrought Page", cards, game);
                if (controller.chooseUse(Outcome.Neutral, "Put that card into your graveyard?", source, game)) {
                    return controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
                return true;
            }
        }
        return false;
    }
}