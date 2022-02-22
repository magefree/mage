
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author KholdFuzion
 */
public final class AcidicSliver extends CardImpl {

    public AcidicSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{2}, Sacrifice this permanent: This permanent deals 2 damage to any target."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new SacrificeSourceCost());
        ability.addCost(new GenericManaCost(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS,
                        "All Slivers have \"{2}, Sacrifice this permanent: This permanent deals 2 damage to any target.\"")));
    }

    private AcidicSliver(final AcidicSliver card) {
        super(card);
    }

    @Override
    public AcidicSliver copy() {
        return new AcidicSliver(this);
    }
}
