
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class PhyrexianPlaguelord extends CardImpl {

    public PhyrexianPlaguelord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CARRIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {tap}, Sacrifice Phyrexian Plaguelord: Target creature gets -4/-4 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(-4, -4, Duration.EndOfTurn),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Sacrifice a creature: Target creature gets -1/-1 until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PhyrexianPlaguelord(final PhyrexianPlaguelord card) {
        super(card);
    }

    @Override
    public PhyrexianPlaguelord copy() {
        return new PhyrexianPlaguelord(this);
    }
}
