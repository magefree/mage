
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public final class ThranFoundry extends CardImpl {

    public ThranFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {1}, {tap}, Exile Thran Foundry: Target player shuffles their graveyard into their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThranFoundryEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ThranFoundry(final ThranFoundry card) {
        super(card);
    }

    @Override
    public ThranFoundry copy() {
        return new ThranFoundry(this);
    }
}

class ThranFoundryEffect extends OneShotEffect {
    
    ThranFoundryEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their graveyard into their library";
    }
    
    private ThranFoundryEffect(final ThranFoundryEffect effect) {
        super(effect);
    }
    
    @Override
    public ThranFoundryEffect copy() {
        return new ThranFoundryEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }              
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
