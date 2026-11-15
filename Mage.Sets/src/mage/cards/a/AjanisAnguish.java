package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class AjanisAnguish extends CardImpl {

    public AjanisAnguish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{R}");

        // When this enchantment enters, it deals X damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(GetXValue.instance));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private AjanisAnguish(final AjanisAnguish card) {
        super(card);
    }

    @Override
    public AjanisAnguish copy() {
        return new AjanisAnguish(this);
    }
}
