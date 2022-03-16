
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class BurningOfXinye extends CardImpl {

    public BurningOfXinye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // You destroy four lands you control, then target opponent destroys four lands they control. Then Burning of Xinye deals 4 damage to each creature.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new BurningOfXinyeEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));
    }

    private BurningOfXinye(final BurningOfXinye card) {
        super(card);
    }

    @Override
    public BurningOfXinye copy() {
        return new BurningOfXinye(this);
    }
}


class BurningOfXinyeEffect extends OneShotEffect{

    private static final FilterControlledLandPermanent filter =  new FilterControlledLandPermanent();
    
    public BurningOfXinyeEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "You destroy four lands you control, then target opponent destroys four lands they control";
    }

    public BurningOfXinyeEffect ( BurningOfXinyeEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean abilityApplied = false;

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            abilityApplied |= playerDestroys(game, source, controller);
        }

        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            abilityApplied |= playerDestroys(game, source, opponent);
        }

        return abilityApplied;
    }
    
    private boolean playerDestroys(Game game, Ability source, Player player){
        boolean abilityApplied = false;
        
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        int amount = Math.min(4, realCount);

        Target target = new TargetControlledPermanent(amount, amount, filter, true);
        if (amount > 0 && target.canChoose(source.getSourceId(), player.getId(), game)) {
            while (!target.isChosen() && target.canChoose(source.getSourceId(), player.getId(), game) && player.canRespond()) {
                player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            }

            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);

                if (permanent != null) {
                    abilityApplied |= permanent.destroy(source, game, false);
                }
            }
        }
        return abilityApplied;
    }

    @Override
    public BurningOfXinyeEffect copy() {
        return new BurningOfXinyeEffect(this);
    }
}