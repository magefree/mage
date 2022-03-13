
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class TributeToHunger extends CardImpl {

    public TributeToHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target opponent sacrifices a creature. You gain life equal to that creature's toughness.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new TributeToHungerEffect());
    }

    private TributeToHunger(final TributeToHunger card) {
        super(card);
    }

    @Override
    public TributeToHunger copy() {
        return new TributeToHunger(this);
    }
}

class TributeToHungerEffect extends OneShotEffect {

    TributeToHungerEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target opponent sacrifices a creature. You gain life equal to that creature's toughness";
    }

    TributeToHungerEffect(TributeToHungerEffect effect) {
        super(effect);
    }

    @Override
    public TributeToHungerEffect copy() {
        return new TributeToHungerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && opponent != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
            if (target.canChoose(opponent.getId(), source, game)) {
                opponent.chooseTarget(Outcome.Sacrifice, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                    controller.gainLife(permanent.getToughness().getValue(), game, source);
                }
            }
            return true;
        }
        return false;
    }
}
