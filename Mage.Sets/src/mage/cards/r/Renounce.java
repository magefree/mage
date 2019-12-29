
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

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

    public Renounce(final Renounce card) {
        super(card);
    }

    @Override
    public Renounce copy() {
        return new Renounce(this);
    }
}

class RenounceEffect extends OneShotEffect {

    public RenounceEffect() {
        super(Outcome.Neutral);
        staticText  = "Sacrifice any number of permanents. You gain 2 life for each permanent sacrificed this way";
    }

    public RenounceEffect(final RenounceEffect effect) {
        super(effect);
    }

    @Override
    public RenounceEffect copy() {
        return new RenounceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null){
            return false;
        }
        int amount = 0;
        TargetControlledPermanent toSacrifice = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledPermanent(), true);
        if(player.chooseTarget(Outcome.Sacrifice, toSacrifice, source, game)) {
            for(UUID uuid : toSacrifice.getTargets()){
                Permanent permanent = game.getPermanent(uuid);
                if(permanent != null){
                    permanent.sacrifice(source.getSourceId(), game);
                    amount++;
                }
            }
            player.gainLife(amount * 2, game, source);
        }
        return true;
    }
}
