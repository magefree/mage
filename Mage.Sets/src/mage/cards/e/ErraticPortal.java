
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ErraticPortal extends CardImpl {

    public ErraticPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}, {tap}: Return target creature to its owner's hand unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ErraticPortalEffect(new GenericManaCost(1)), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ErraticPortal(final ErraticPortal card) {
        super(card);
    }

    @Override
    public ErraticPortal copy() {
        return new ErraticPortal(this);
    }
}

class ErraticPortalEffect extends OneShotEffect {

    protected Cost cost;

    public ErraticPortalEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "Return target creature to its owner's hand unless its controller pays {1}";
        this.cost = cost;
    }

    public ErraticPortalEffect(final ErraticPortalEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public ErraticPortalEffect copy() {
        return new ErraticPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                Player player = game.getPlayer(targetCreature.getControllerId());
                if (player != null) {
                    cost.clearPaid();
                    if (player.chooseUse(Outcome.Benefit, "Pay {1}? (Otherwise " + targetCreature.getLogName() +" will be returned to its owner's hand)", source, game)) {
                        cost.pay(source, game, source, targetCreature.getControllerId(), false, null);
                    }
                    if (!cost.isPaid()) {
                        controller.moveCards(targetCreature, Zone.HAND, source, game);
                    }
                }
            }            
            return true;
        }
        return false;
    }
}
