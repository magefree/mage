

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TectonicEdge extends CardImpl {

    public TectonicEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Tectonic Edge: Destroy target nonbasic land. Activate this ability only if an opponent controls four or more lands.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new DestroyTargetEffect(),
                new ManaCostsImpl<>("{1}"),
                new OpponentControlsPermanentCondition(
                        new FilterLandPermanent("an opponent controls four or more lands"),
                        ComparisonType.MORE_THAN, 3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    private TectonicEdge(final TectonicEdge card) {
        super(card);
    }

    @Override
    public TectonicEdge copy() {
        return new TectonicEdge(this);
    }

}

class TectonicEdgeCost extends CostImpl {


    public TectonicEdgeCost() {
        this.text = "Activate only if an opponent controls four or more lands";
    }

    public TectonicEdgeCost(final TectonicEdgeCost cost) {
        super(cost);
    }

    @Override
    public TectonicEdgeCost copy() {
        return new TectonicEdgeCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        for (UUID opponentId: game.getOpponents(controllerId)) {
            if (game.getBattlefield().countAll(StaticFilters.FILTER_LANDS, opponentId, game) > 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

}
