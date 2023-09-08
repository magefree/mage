
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class CompellingDeterrence extends CardImpl {

    public CompellingDeterrence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. Then that player discards a card if you control a Zombie.
        this.getSpellAbility().addEffect(new CompellingDeterrenceEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private CompellingDeterrence(final CompellingDeterrence card) {
        super(card);
    }

    @Override
    public CompellingDeterrence copy() {
        return new CompellingDeterrence(this);
    }
}

class CompellingDeterrenceEffect extends OneShotEffect {

    public CompellingDeterrenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target nonland permanent to its owner's hand. Then that player discards a card if you control a Zombie";
    }

    private CompellingDeterrenceEffect(final CompellingDeterrenceEffect effect) {
        super(effect);
    }

    @Override
    public CompellingDeterrenceEffect copy() {
        return new CompellingDeterrenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(target.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && player != null) {
            player.moveCards(target, Zone.HAND, source, game);
            game.getState().processAction(game);
            FilterPermanent zombieFilter = new FilterPermanent();
            zombieFilter.add(SubType.ZOMBIE.getPredicate());
            if (game.getState().getBattlefield().countAll(zombieFilter, controller.getId(), game) > 0) {
                player.discard(1, false, false, source, game);
            }
            return true;
        }
        return false;
    }
}
