
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GolgariRotwurm extends CardImpl {

    public GolgariRotwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {B}, Sacrifice a creature: Target player loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GolgariRotwurm(final GolgariRotwurm card) {
        super(card);
    }

    @Override
    public GolgariRotwurm copy() {
        return new GolgariRotwurm(this);
    }
}
