
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Backfir3
 */
public final class BloodshotCyclops extends CardImpl {

    public BloodshotCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.CYCLOPS, SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}, Sacrifice a creature: Bloodshot Cyclops deals damage equal to the sacrificed
        // creature's power to any target.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(SacrificeCostCreaturesPower.instance).setText("{this} deals damage equal to the sacrificed creature's power to any target"),
                new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BloodshotCyclops(final BloodshotCyclops card) {
        super(card);
    }

    @Override
    public BloodshotCyclops copy() {
        return new BloodshotCyclops(this);
    }
}
