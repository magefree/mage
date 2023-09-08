
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.ManaUtil;

/**
 *
 * @author TheElk801
 */
public final class CrookedScales extends CardImpl {

    public CrookedScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {4}, {tap}: Flip a coin. If you win the flip, destroy target creature an opponent controls. If you lose the flip, destroy target creature you control unless you pay {3} and repeat this process.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrookedScalesEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private CrookedScales(final CrookedScales card) {
        super(card);
    }

    @Override
    public CrookedScales copy() {
        return new CrookedScales(this);
    }
}

class CrookedScalesEffect extends OneShotEffect {

    CrookedScalesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Flip a coin. If you win the flip, destroy target creature an opponent controls. If you lose the flip, destroy target creature you control unless you pay {3} and repeat this process";
    }

    private CrookedScalesEffect(final CrookedScalesEffect effect) {
        super(effect);
    }

    @Override
    public CrookedScalesEffect copy() {
        return new CrookedScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent yourGuy = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent theirGuy = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            boolean keepGoing;
            Cost cost;
            String message = "You lost the flip. Pay {3} to prevent your creature from being destroyed?";
            do {
                if (controller.flipCoin(source, game, true)) {
                    if (theirGuy != null) {
                        theirGuy.destroy(source, game, false);
                    }
                    keepGoing = false;
                } else {
                    cost = ManaUtil.createManaCost(3, false);
                    if (!(controller.chooseUse(Outcome.Benefit, message, source, game) && cost.pay(source, game, source, controller.getId(), false, null))) {
                        if (yourGuy != null) {
                            yourGuy.destroy(source, game, false);
                        }
                        keepGoing = false;
                    } else {
                        keepGoing = true;
                    }
                }
            } while (keepGoing && controller.canRespond());
            return true;
        }
        return false;
    }
}
