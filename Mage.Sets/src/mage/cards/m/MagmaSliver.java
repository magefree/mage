package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class MagmaSliver extends CardImpl {

    public MagmaSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Slivers have "{tap}: Target Sliver creature gets +X/+0 until end of turn, 
        // where X is the number of Slivers on the battlefield."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(new PermanentsOnBattlefieldCount(
                        StaticFilters.FILTER_PERMANENT_ALL_SLIVERS),
                        StaticValue.get(0), Duration.EndOfTurn),
                new TapSourceCost());
        Target target = new TargetCreaturePermanent(
                new FilterCreaturePermanent(SubType.SLIVER, "Sliver creature"));
        ability.addTarget(target);
        Effect effect = new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ALL_SLIVERS);
        effect.setText("All Slivers have \"{T}: Target Sliver creature gets +X/+0 until end of turn, "
                + "where X is the number of Slivers on the battlefield.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                effect));
    }

    private MagmaSliver(final MagmaSliver card) {
        super(card);
    }

    @Override
    public MagmaSliver copy() {
        return new MagmaSliver(this);
    }
}
