package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GateYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.GateYouControlHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HoldTheGates extends CardImpl {

    public HoldTheGates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Creatures you control get +0/+1 for each Gate you control and have vigilance.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(StaticValue.get(0), GateYouControlCount.instance, Duration.WhileOnBattlefield)
        );
        ability.addEffect(
                new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED)
                        .setText("and have vigilance")
        );
        ability.addHint(GateYouControlHint.instance);
        this.addAbility(ability);
    }

    private HoldTheGates(final HoldTheGates card) {
        super(card);
    }

    @Override
    public HoldTheGates copy() {
        return new HoldTheGates(this);
    }
}
