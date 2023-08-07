
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MirenTheMoaningWell extends CardImpl {

    public MirenTheMoaningWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {tap}, Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirenTheMoaningWellEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private MirenTheMoaningWell(final MirenTheMoaningWell card) {
        super(card);
    }

    @Override
    public MirenTheMoaningWell copy() {
        return new MirenTheMoaningWell(this);
    }
}

class MirenTheMoaningWellEffect extends OneShotEffect {

    public MirenTheMoaningWellEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to the sacrificed creature's toughness";
    }

    public MirenTheMoaningWellEffect(final MirenTheMoaningWellEffect effect) {
        super(effect);
    }

    @Override
    public MirenTheMoaningWellEffect copy() {
        return new MirenTheMoaningWellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    int amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                    if (amount > 0) {
                        controller.gainLife(amount, game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
