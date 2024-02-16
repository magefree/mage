package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class PlaguemawBeast extends CardImpl {

    public PlaguemawBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice a creature: Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private PlaguemawBeast(final PlaguemawBeast card) {
        super(card);
    }

    @Override
    public PlaguemawBeast copy() {
        return new PlaguemawBeast(this);
    }

}
