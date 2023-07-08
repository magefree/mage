
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class DiscipleOfGriselbrand extends CardImpl {

    public DiscipleOfGriselbrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscipleOfGriselbrandEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private DiscipleOfGriselbrand(final DiscipleOfGriselbrand card) {
        super(card);
    }

    @Override
    public DiscipleOfGriselbrand copy() {
        return new DiscipleOfGriselbrand(this);
    }
}

class DiscipleOfGriselbrandEffect extends OneShotEffect {

    public DiscipleOfGriselbrandEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to the sacrificed creature's toughness";
    }

    public DiscipleOfGriselbrandEffect(final DiscipleOfGriselbrandEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfGriselbrandEffect copy() {
        return new DiscipleOfGriselbrandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                Player player = game.getPlayer(source.getControllerId());
                if (amount > 0 && player != null) {
                    player.gainLife(amount, game, source);
                    return true;
                }
            }
        }
        return false;
    }
}
