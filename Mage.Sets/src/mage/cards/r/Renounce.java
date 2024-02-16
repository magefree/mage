
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author L_J
 */
public final class Renounce extends CardImpl {

    public Renounce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Sacrifice any number of permanents. You gain 2 life for each permanent sacrificed this way.
        this.getSpellAbility().addEffect(new RenounceEffect());
    }

    private Renounce(final Renounce card) {
        super(card);
    }

    @Override
    public Renounce copy() {
        return new Renounce(this);
    }
}

class RenounceEffect extends OneShotEffect {

    RenounceEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of permanents. You gain 2 life for each permanent sacrificed this way";
    }

    private RenounceEffect(final RenounceEffect effect) {
        super(effect);
    }

    @Override
    public RenounceEffect copy() {
        return new RenounceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = 0;
        TargetSacrifice toSacrifice = new TargetSacrifice(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT);
        if (player.choose(Outcome.Sacrifice, toSacrifice, source, game)) {
            for (UUID uuid : toSacrifice.getTargets()) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                    amount++;
                }
            }
            player.gainLife(amount * 2, game, source);
        }
        return true;
    }
}
