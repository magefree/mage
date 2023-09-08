
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ThallidOmnivore extends CardImpl {

    public ThallidOmnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature: Thallid Omnivore gets +2/+2 until end of turn. If a saproling was sacrificed in this way you gain 2 life.
        Effect effect = new BoostSourceEffect(2, 2, Duration.EndOfTurn);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        ability.addEffect(new ThallidOmnivoreEffect());
        this.addAbility(ability);

    }

    private ThallidOmnivore(final ThallidOmnivore card) {
        super(card);
    }

    @Override
    public ThallidOmnivore copy() {
        return new ThallidOmnivore(this);
    }
}

class ThallidOmnivoreEffect extends OneShotEffect {

    public ThallidOmnivoreEffect() {
        super(Outcome.GainLife);
        this.staticText = "If a Saproling was sacrificed this way, you gain 2 life";
    }

    private ThallidOmnivoreEffect(final ThallidOmnivoreEffect effect) {
        super(effect);
    }

    @Override
    public ThallidOmnivoreEffect copy() {
        return new ThallidOmnivoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                    List<Permanent> permanents = sacrificeCost.getPermanents();
                    if (!permanents.isEmpty() && permanents.get(0).hasSubtype(SubType.SAPROLING, game)) {
                        controller.gainLife(2, game, source);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
