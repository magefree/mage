
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;
import mage.cards.Cards;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.card.OwnerIdPredicate;

/**
 *
 * @author North
 */
public final class HarmonicConvergence extends CardImpl {

    public HarmonicConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Put all enchantments on top of their owners' libraries.
        this.getSpellAbility().addEffect(new HarmonicConvergenceEffect());
    }

    private HarmonicConvergence(final HarmonicConvergence card) {
        super(card);
    }

    @Override
    public HarmonicConvergence copy() {
        return new HarmonicConvergence(this);
    }
}

class HarmonicConvergenceEffect extends OneShotEffect {

    public HarmonicConvergenceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put all enchantments on top of their owners' libraries";
    }

    public HarmonicConvergenceEffect(final HarmonicConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public HarmonicConvergenceEffect copy() {
        return new HarmonicConvergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();
                filter.add(new OwnerIdPredicate(player.getId()));
                Cards toLib = new CardsImpl();
                for(Permanent enchantment: game.getBattlefield().getActivePermanents(filter, playerId, source, game)) {
                    toLib.add(enchantment);
                }
                player.putCardsOnTopOfLibrary(toLib, game, source, true);
            }            
        }        
        return true;
    }
}
