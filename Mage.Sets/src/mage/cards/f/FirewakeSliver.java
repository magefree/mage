
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FirewakeSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");
    private static final FilterCreaturePermanent targetSliverFilter = new FilterCreaturePermanent("Sliver");

    static {
        filter.add(SubType.SLIVER.getPredicate());
        targetSliverFilter.add(SubType.SLIVER.getPredicate());
    }

    public FirewakeSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));

        // All Slivers have "{1}, Sacrifice this permanent: Target Sliver creature gets +2/+2 until end of turn."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(1));
        gainedAbility.addCost(new SacrificeSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(targetSliverFilter));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                gainedAbility, Duration.WhileOnBattlefield,
                filter, "All Slivers have \"{1}, Sacrifice this permanent: Target Sliver creature gets +2/+2 until end of turn.\"")));

    }

    private FirewakeSliver(final FirewakeSliver card) {
        super(card);
    }

    @Override
    public FirewakeSliver copy() {
        return new FirewakeSliver(this);
    }
}
