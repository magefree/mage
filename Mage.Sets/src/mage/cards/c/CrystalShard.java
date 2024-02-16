package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
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
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CrystalShard extends CardImpl {

    public CrystalShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap} or {U}, {tap}: Return target creature to its owner's hand unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(
                new CrystalShardEffect(),
                new OrCost(
                        "{3}, {T} or {U}, {T}", new CompositeCost(new GenericManaCost(3), new TapSourceCost(), "{3}, {T}"),
                        new CompositeCost(new ManaCostsImpl<>("{U}"), new TapSourceCost(), "{U}, {T}")
                )
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CrystalShard(final CrystalShard card) {
        super(card);
    }

    @Override
    public CrystalShard copy() {
        return new CrystalShard(this);
    }
}

class CrystalShardEffect extends OneShotEffect {

    CrystalShardEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target creature to its owner's hand unless its controller pays {1}";
    }

    private CrystalShardEffect(final CrystalShardEffect effect) {
        super(effect);
    }

    @Override
    public CrystalShardEffect copy() {
        return new CrystalShardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCreature == null) {
            return true;
        }
        Player player = game.getPlayer(targetCreature.getControllerId());
        if (player == null) {
            return true;
        }
        Cost cost = ManaUtil.createManaCost(1, false);
        String message = "Pay {1}? (Otherwise " + targetCreature.getName() + " will be returned to its owner's hand)";
        if (player.chooseUse(Outcome.Benefit, message, source, game)) {
            cost.pay(source, game, source, targetCreature.getControllerId(), false, null);
        }
        return cost.isPaid() || controller.moveCards(targetCreature, Zone.HAND, source, game);
    }
}
