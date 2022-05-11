
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class HallowedBurial extends CardImpl {

    public HallowedBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Put all creatures on the bottom of their owners' libraries.
        this.getSpellAbility().addEffect(new HallowedBurialEffect());

    }

    private HallowedBurial(final HallowedBurial card) {
        super(card);
    }

    @Override
    public HallowedBurial copy() {
        return new HallowedBurial(this);
    }
}

class HallowedBurialEffect extends OneShotEffect {

    public HallowedBurialEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put all creatures on the bottom of their owners' libraries";
    }

    public HallowedBurialEffect(final HallowedBurialEffect effect) {
        super(effect);
    }

    @Override
    public HallowedBurialEffect copy() {
        return new HallowedBurialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), source, game)) {
                controller.moveCardToLibraryWithInfo(creature, source, game, Zone.BATTLEFIELD, false, true);
            }
            return true;
        }
        return false;
    }
}
