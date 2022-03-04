
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author KholdFuzion
 */
public final class NecroticSliver extends CardImpl {

    public NecroticSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{3}, Sacrifice this permanent: Destroy target permanent."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addCost(new GenericManaCost(3));
        ability.addTarget(new TargetPermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS,
                        "All Slivers have \"{3}, Sacrifice this permanent: Destroy target permanent.\"")));
    }

    private NecroticSliver(final NecroticSliver card) {
        super(card);
    }

    @Override
    public NecroticSliver copy() {
        return new NecroticSliver(this);
    }
}
