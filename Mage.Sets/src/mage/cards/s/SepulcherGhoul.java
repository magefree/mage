package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class SepulcherGhoul extends CardImpl {

    public SepulcherGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice another creature: Sepulcher Ghoul gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))
        ));
    }

    private SepulcherGhoul(final SepulcherGhoul card) {
        super(card);
    }

    @Override
    public SepulcherGhoul copy() {
        return new SepulcherGhoul(this);
    }
}
