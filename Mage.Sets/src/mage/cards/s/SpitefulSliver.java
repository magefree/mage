package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpitefulSliver extends CardImpl {

    public SpitefulSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "Whenever this creature is dealt damage, it deals that much damage to target player or planeswalker."
        Ability ability = new DealtDamageToSourceTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH, "it"), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        ).setText("Sliver creatures you control have \"Whenever this creature is dealt damage, " +
                "it deals that much damage to target player or planeswalker.\"")
        ));
    }

    private SpitefulSliver(final SpitefulSliver card) {
        super(card);
    }

    @Override
    public SpitefulSliver copy() {
        return new SpitefulSliver(this);
    }
}
