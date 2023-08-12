package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MawOfTheObzedat extends CardImpl {

    public MawOfTheObzedat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice a creature: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT))));

    }

    private MawOfTheObzedat(final MawOfTheObzedat card) {
        super(card);
    }

    @Override
    public MawOfTheObzedat copy() {
        return new MawOfTheObzedat(this);
    }
}
