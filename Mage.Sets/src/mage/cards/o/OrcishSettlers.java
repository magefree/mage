
package mage.cards.o;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class OrcishSettlers extends CardImpl {

    public OrcishSettlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{X}{R}, {tap}, Sacrifice Orcish Settlers: Destroy X target lands.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OrcishSettlersEffect(), new ManaCostsImpl<>("{X}{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OrcishSettlers(final OrcishSettlers card) {
        super(card);
    }

    @Override
    public OrcishSettlers copy() {
        return new OrcishSettlers(this);
    }
}

class OrcishSettlersEffect extends OneShotEffect {

    public OrcishSettlersEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy X target lands";
    }

    public OrcishSettlersEffect(final OrcishSettlersEffect effect) {
        super(effect);
    }

    @Override
    public OrcishSettlersEffect copy() {
        return new OrcishSettlersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        if (amount == 0) {
            return false;
        }
        TargetLandPermanent target = new TargetLandPermanent(amount);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && target.canChoose(controller.getId(), source, game)
                && controller.choose(Outcome.DestroyPermanent, target, source, game)) {
            List<UUID> targets = target.getTargets();
            targets.forEach((landId) -> {
                Permanent land = game.getPermanent(landId);
                if (land != null) {
                    land.destroy(source, game, false);
                }
            });
            return true;
        }
        return false;
    }
}
