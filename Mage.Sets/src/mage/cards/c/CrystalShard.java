
package mage.cards.c;

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
 * @author LevelX2
 */
public final class CrystalShard extends CardImpl {

    public CrystalShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap} or {U}, {tap}: Return target creature to its owner's hand unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrystalShardEffect(new GenericManaCost(1)), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrystalShardEffect(new GenericManaCost(1)), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CrystalShard(final CrystalShard card) {
        super(card);
    }

    @Override
    public CrystalShard copy() {
        return new CrystalShard(this);
    }
}

class CrystalShardEffect extends OneShotEffect {

    protected Cost cost;

    public CrystalShardEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "Return target creature to its owner's hand unless its controller pays {1}";
        this.cost = cost;
    }

    public CrystalShardEffect(final CrystalShardEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public CrystalShardEffect copy() {
        return new CrystalShardEffect(this);
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
                    final StringBuilder sb = new StringBuilder("Pay {1}? (Otherwise ").append(targetCreature.getName()).append(" will be returned to its owner's hand)");
                    if (player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                        cost.pay(source, game, targetCreature.getControllerId(), targetCreature.getControllerId(), false, null);
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
