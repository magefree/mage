
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author cbt33, plopman (Entomb)
 */
public final class BuriedAlive extends CardImpl {

    
    public BuriedAlive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new BuriedAliveEffect());        
        
    }

    public BuriedAlive(final BuriedAlive card) {
        super(card);
    }

    @Override
    public BuriedAlive copy() {
        return new BuriedAlive(this);
    }
}

class BuriedAliveEffect extends SearchEffect {

  public BuriedAliveEffect() {
        super(new TargetCardInLibrary(0, 3, new FilterCreatureCard()), Outcome.Detriment);
        staticText = "Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library";
    }

    public BuriedAliveEffect(final BuriedAliveEffect effect) {
        super(effect);
    }

    @Override
    public BuriedAliveEffect copy() {
        return new BuriedAliveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, game)) {
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
    
}
