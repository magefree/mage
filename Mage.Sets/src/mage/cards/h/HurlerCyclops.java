package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class HurlerCyclops extends CardImpl {

    public HurlerCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {1}, Sacrifice another creature: Hurler Cyclops deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HurlerCyclops(final HurlerCyclops card) {
        super(card);
    }

    @Override
    public HurlerCyclops copy() {
        return new HurlerCyclops(this);
    }
}
