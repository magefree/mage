
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
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
 * @author fireshoes
 */
public final class FeldonsCane extends CardImpl {

    public FeldonsCane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {tap}, Exile Feldon's Cane: Shuffle your graveyard into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FeldonsCaneEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private FeldonsCane(final FeldonsCane card) {
        super(card);
    }

    @Override
    public FeldonsCane copy() {
        return new FeldonsCane(this);
    }
}

class FeldonsCaneEffect extends OneShotEffect {
    
    FeldonsCaneEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle your graveyard into your library";
    }
    
    private FeldonsCaneEffect(final FeldonsCaneEffect effect) {
        super(effect);
    }
    
    @Override
    public FeldonsCaneEffect copy() {
        return new FeldonsCaneEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Card card: controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }            
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}